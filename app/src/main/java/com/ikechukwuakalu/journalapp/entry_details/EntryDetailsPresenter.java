package com.ikechukwuakalu.journalapp.entry_details;

import com.ikechukwuakalu.journalapp.data.JournalDataSource;
import com.ikechukwuakalu.journalapp.data.local.JournalEntry;
import com.ikechukwuakalu.journalapp.utils.Logger;
import com.ikechukwuakalu.journalapp.utils.espresso.EspressoIdlingResource;
import com.ikechukwuakalu.journalapp.utils.rx.BaseScheduler;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class EntryDetailsPresenter implements EntryDetailsContract.Presenter{

    public static final String MSG_ENTRY_NOT_FOUND = "Journal Entry not found";
    public static final String MSG_ID_IS_NULL = "Invalid entry ID";

    private String entryId;
    private JournalDataSource repository;
    private BaseScheduler rxScheduler;
    private CompositeDisposable disposables = new CompositeDisposable();

    private EntryDetailsContract.View view = null;

    EntryDetailsPresenter(JournalDataSource repository, String entryId, BaseScheduler scheduler) {
        this.entryId = entryId;
        this.repository = repository;
        this.rxScheduler = scheduler;
    }

    @Override
    public void deleteEntry(JournalEntry journalEntry) {
        EspressoIdlingResource.increment();
        Disposable disposable = repository.remove(journalEntry)
                .subscribeOn(rxScheduler.io())
                .observeOn(rxScheduler.ui())
                .doFinally(new Action() {
                    @Override
                    public void run() {
                        if (! EspressoIdlingResource.getIdlingResource().isIdleNow())
                            EspressoIdlingResource.decrement();
                    }
                })
                .subscribe(new Action() {
                    @Override
                    public void run() {
                        if (view != null) {
                            view.hideLoading();
                            view.showDeleteSuccess();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        if (view != null) {
                            view.hideLoading();
                            view.showError(throwable.getMessage());
                        }
                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void attach(EntryDetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void fetchDetails() {
        if (this.entryId == null) {
            if (view != null) view.showError(MSG_ID_IS_NULL);
            return;
        }
        EspressoIdlingResource.increment();
        Disposable disposable = repository.getEntry(Integer.valueOf(this.entryId))
                .subscribeOn(rxScheduler.io())
                .observeOn(rxScheduler.ui())
                .doFinally(new Action() {
                    @Override
                    public void run() {
                        if (! EspressoIdlingResource.getIdlingResource().isIdleNow())
                            EspressoIdlingResource.decrement();
                    }
                })
                .subscribe(new Consumer<JournalEntry>() {
                    @Override
                    public void accept(JournalEntry journalEntry) {
                        if (view != null) {
                            view.hideLoading();
                            if (journalEntry != null) view.showEntryDetails(journalEntry);
                            else view.showError(MSG_ENTRY_NOT_FOUND);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.error(throwable.getMessage());
                        if (view != null) {
                            view.hideLoading();
                            view.showError(throwable.getMessage());
                        }
                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void detach() {
        if (this.view != null) this.view = null;
        disposables.clear();
    }
}
