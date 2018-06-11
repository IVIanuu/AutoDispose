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

import io.reactivex.Flowable
import io.reactivex.Maybe
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

internal class AutoDisposeFlowable<T>(
    private val source: Flowable<T>,
    private val scope: Maybe<*>
) : Flowable<T>() {
    override fun subscribeActual(s: Subscriber<in T>) {
        source.subscribeWith(AutoDisposeSubscriber(s, scope))
    }
}

internal class AutoDisposeSubscriber<T>(
    private val delegate: Subscriber<T>,
    private val scope: Maybe<*>
) : Subscriber<T> {
    override fun onComplete() {
        delegate.onComplete()
    }

    override fun onSubscribe(s: Subscription) {
        delegate.onSubscribe(AutoSubscription(s, scope))
    }

    override fun onNext(t: T) {
        delegate.onNext(t)
    }

    override fun onError(e: Throwable) {
        delegate.onError(e)
    }

}