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

import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.observers.TestObserver

/**
 * @author Manuel Wrage (IVIanuu)
 */
interface CompletableSubscribeProxy {

    fun subscribe(): Disposable

    fun subscribe(action: Action): Disposable

    fun subscribe(action: Action, onError: Consumer<in Throwable>): Disposable

    fun subscribe(observer: CompletableObserver)

    fun <E : CompletableObserver> subscribeWith(observer: E): E

    fun test(): TestObserver<Void>

    fun test(cancelled: Boolean): TestObserver<Void>
}

fun CompletableSubscribeProxy.subscribe(onComplete: () -> Unit): Disposable {
    return subscribe(Action { onComplete.invoke() })
}

fun CompletableSubscribeProxy.subscribeBy(
    onComplete: () -> Unit = onCompleteStub,
    onError: (Throwable) -> Unit = onErrorStub
): Disposable {
    return subscribe(Action { onComplete.invoke() }, Consumer { onError.invoke(it) })
}