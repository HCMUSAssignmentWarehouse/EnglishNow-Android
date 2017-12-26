package com.iceteaviet.englishnow.utils.rx;

import io.reactivex.Scheduler;

/**
 * Created by Genius Doan on 12/26/2017.
 */

public interface SchedulerProvider {
    Scheduler ui();

    Scheduler computation();

    Scheduler io();

}