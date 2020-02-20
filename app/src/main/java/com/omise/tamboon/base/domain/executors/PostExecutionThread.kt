package com.omise.tamboon.base.domain.executors

import io.reactivex.Scheduler

interface PostExecutionThread {
    val scheduler: Scheduler
}
