package com.ikechukwuakalu.journalapp.entries;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.ikechukwuakalu.journalapp.data.JournalDataSource;
import com.ikechukwuakalu.journalapp.data.local.JournalEntry;
import com.ikechukwuakalu.journalapp.utils.Logger;
import com.ikechukwuakalu.journalapp.utils.UserSharedPreferenceHelper;
import com.ikechukwuakalu.journalapp.utils.espresso.EspressoIdlingResource;
import com.ikechukwuakalu.journalapp.utils.rx.BaseScheduler;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class EntriesPresenter implements EntriesContract.Presenter {

    private JournalDataSource repository;
    private GoogleSignInClient signInClient;
    private UserSharedPreferenceHelper spHelper;
    private BaseScheduler rxScheduler;
    private CompositeDisposable disposables = new CompositeDisposable();

    private EntriesContract.View view = null;

    EntriesPresenter(JournalDataSource repository, UserSharedPreferenceHelper spHelper, GoogleSignInClient signInClient, BaseScheduler scheduler) {
        this.repository = repository;
        this.rxScheduler = scheduler;
        this.signInClient = signInClient;
        this.spHelper = spHelper;
    }

    @Override
    public void deleteAllEntries() {
        EspressoIdlingResource.increment();
        Disposable disposable = repository.removeAll()
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
                        if (view != null) view.showNoEntryFound();
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
    public void signOutUser(Activity activity) {
        signInClient.signOut().addOnCompleteListener(activity, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    spHelper.signUserOut();
                    deleteAllEntries();
                    if (view != null) view.showSignOutSuccess();
                } else {
                    if (view != null) view.showError("Unable to complete Sign out");
                }
            }
        });
    }

    @Override
    public void attach(EntriesContract.View view) {
        this.view = view;
        if (! spHelper.isUserSignedIn()) {
            view.showAuthScreen();
        } else {
            view.showUserDetails(spHelper.getUserDetails());
            fetchEntries();
        }
    }

    private void fetchEntries() {
        EspressoIdlingResource.increment();
        Disposable disposable = repository.getEntries()
                .subscribeOn(rxScheduler.io())
                .observeOn(rxScheduler.ui())
                .doFinally(new Action() {
                    @Override
                    public void run() {
                        if (! EspressoIdlingResource.getIdlingResource().isIdleNow())
                            EspressoIdlingResource.decrement();
                    }
                })
                .subscribe(new Consumer<List<JournalEntry>>() {
                    @Override
                    public void accept(List<JournalEntry> entries) {
                        if (view != null) {
                            if (entries.size() > 0) view.showEntries(entries);
                            else view.showNoEntryFound();
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
