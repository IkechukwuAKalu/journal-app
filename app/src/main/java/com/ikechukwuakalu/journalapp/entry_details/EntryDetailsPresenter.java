package com.ikechukwuakalu.journalapp.entry_details;

import android.content.Context;

import com.ikechukwuakalu.journalapp.data.JournalRepository;
import com.ikechukwuakalu.journalapp.data.local.JournalEntry;
import com.ikechukwuakalu.journalapp.data.local.LocalJournalDataSource;
import com.ikechukwuakalu.journalapp.utils.Logger;
import com.ikechukwuakalu.journalapp.utils.espresso.EspressoIdlingResource;
import com.ikechukwuakalu.journalapp.utils.rx.RxScheduler;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class EntryDetailsPresenter implements EntryDetailsContract.Presenter{

    private String entryId;
    private JournalRepository repository;
    private RxScheduler rxScheduler = new RxScheduler();
    private CompositeDisposable disposables = new CompositeDisposable();

    private EntryDetailsContract.View view = null;

    EntryDetailsPresenter(Context context, String entryId) {
        this.entryId = entryId;
        this.repository = new JournalRepository(new LocalJournalDataSource(context));
    }

    @Override
    public void deleteEntry(int id) {
        // TODO "Make an entry deletable"
    }

    @Override
    public void attach(EntryDetailsContract.View view) {
        this.view = view;
        fetchEntryDetails();
    }

    private void fetchEntryDetails() {
        if (this.entryId == null) {
            if (view != null) view.showError("Invalid entry ID");
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
                            if (journalEntry != null) view.showEntryDetails(journalEntry);
                            else view.showError("Journal Entry not found");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.error(throwable.getMessage());
                        if (view != null) view.showError(throwable.getMessage());
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
