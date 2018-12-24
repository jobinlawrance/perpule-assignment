package com.jobinlawrance.perpuleassignment.extensions

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun <T> Observable<T>.applySchedulers(): Observable<T> {
    return this.compose(com.jobinlawrance.perpuleassignment.utils.applySchedulers())
}