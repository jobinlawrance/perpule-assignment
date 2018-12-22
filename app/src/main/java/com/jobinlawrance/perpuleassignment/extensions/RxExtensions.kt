package com.jobinlawrance.perpuleassignment.extensions

import io.reactivex.Observable

fun <T> Observable<T>.applySchedulers(): Observable<T> {
    return this.compose(com.jobinlawrance.perpuleassignment.utils.applySchedulers())
}