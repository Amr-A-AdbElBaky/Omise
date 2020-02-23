package com.omise.tamboon.base.domain.executors

import io.reactivex.Scheduler


interface ExecutionThread {
    val scheduler: Scheduler
}