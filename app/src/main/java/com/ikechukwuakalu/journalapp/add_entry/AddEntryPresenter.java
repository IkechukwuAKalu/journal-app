package com.ikechukwuakalu.journalapp.add_entry;

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

public class AddEntryPresenter implements AddEntryContract.Presenter {

    private JournalRepository repository;
    private RxScheduler rxScheduler = new RxScheduler();
    private CompositeDisposable disposables = new CompositeDisposable();

    private AddEntryContract.View view = null;

    AddEntryPresenter(Context context) {
        this.repository = new JournalRepository(new LocalJournalDataSource(context));
    }

    @Override
    public void saveEntry(JournalEntry journalEntry) {
        EspressoIdlingResource.increment();
        Disposable disposable = repository.add(journalEntry)
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
                        if (view != null) view.showSaveSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.error(throwable.getMessage());
                        if (view != null) view.showSaveError(throwable.getMessage());
                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void editEntry(JournalEntry journalEntry) {

    }

    @Override
    public void attach(AddEntryContract.View view) {
        this.view = view;
    }

    @Override
    public void detach() {
        if (this.view != null) this.view = null;
    }
}
