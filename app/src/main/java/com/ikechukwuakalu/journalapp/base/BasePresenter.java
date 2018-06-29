package com.ikechukwuakalu.journalapp.base;

public interface BasePresenter<T> {

    /**
     * Attaches a View to this Presenter
     * @param view the View instance
     */
    void attach(T view);

    /**
     * Detaches a View from this Presenter
     */
    void detach();
}
