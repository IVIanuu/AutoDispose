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

package com.ivianuu.autodispose.internal

import io.reactivex.Maybe
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableHelper
import io.reactivex.internal.subscriptions.SubscriptionHelper
import org.reactivestreams.Subscription
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference

internal class AutoDisposable(
    mainDisposable: Disposable,
    scope: Maybe<*>
) : Disposable {

    private val mainDisposable = AtomicReference<Disposable?>(mainDisposable)
    private val scopeDisposable = AtomicReference<Disposable?>(scope.subscribe { dispose() })

    override fun isDisposed() = mainDisposable == DisposableHelper.DISPOSED

    override fun dispose() {
        DisposableHelper.dispose(mainDisposable)
        DisposableHelper.dispose(scopeDisposable)
    }

}

internal class AutoSubscription(
    mainSubscription: Subscription,
    scope: Maybe<*>
) : Subscription {

    private val mainSubscription = AtomicReference<Subscription?>(mainSubscription)
    private val scopeDisposable = AtomicReference<Disposable?>(scope.subscribe { cancel() })
    private val requested = AtomicLong()

    override fun request(n: Long) {
        SubscriptionHelper.deferredRequest(mainSubscription, requested, n)
    }

    override fun cancel() {
        SubscriptionHelper.cancel(mainSubscription)
        DisposableHelper.dispose(scopeDisposable)
    }
}