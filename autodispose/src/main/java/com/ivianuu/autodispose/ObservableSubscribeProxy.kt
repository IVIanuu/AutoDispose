/*
 * Copyright 2018 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.autodispose

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.observers.TestObserver

/**
 * Subscribe proxy that matches [Observable]'s subscribe overloads.
 */
interface ObservableSubscribeProxy<T> {

    fun subscribe(): Disposable

    fun subscribe(onNext: Consumer<in T>): Disposable

    fun subscribe(onNext: Consumer<in T>, onError: Consumer<in Throwable>): Disposable

    fun subscribe(
        onNext: Consumer<in T>, onError: Consumer<in Throwable>,
        onComplete: Action
    ): Disposable

    fun subscribe(
        onNext: Consumer<in T>, onError: Consumer<in Throwable>,
        onComplete: Action,
        onSubscribe: Consumer<in Disposable>
    ): Disposable

    fun subscribe(observer: Observer<T>)

    fun <E : Observer<in T>> subscribeWith(observer: E): E

    fun test(): TestObserver<T>

    fun test(dispose: Boolean): TestObserver<T>
}

fun <T : Any> ObservableSubscribeProxy<T>.subscribe(onNext: (T) -> Unit): Disposable {
    return subscribe(Consumer { onNext.invoke(it) })
}

fun <T : Any> ObservableSubscribeProxy<T>.subscribeBy(
    onError: (Throwable) -> Unit = onErrorStub,
    onComplete: () -> Unit = onCompleteStub,
    onNext: (T) -> Unit = onNextStub
): Disposable {
    return subscribe(Consumer { onNext.invoke(it) },
        Consumer { onError.invoke(it) }, Action { onComplete.invoke() })
}