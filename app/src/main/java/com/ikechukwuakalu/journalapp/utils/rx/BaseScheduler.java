package com.ikechukwuakalu.journalapp.utils.rx;

import io.reactivex.Scheduler;

public interface BaseScheduler {
    Scheduler io();

    Scheduler ui();

    Scheduler computation();
}
