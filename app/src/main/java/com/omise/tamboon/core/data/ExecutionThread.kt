package com.omise.tamboon.core.data

import io.reactivex.Scheduler


interface ExecutionThread {
    val scheduler: Scheduler
}