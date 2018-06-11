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
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

internal class AutoDisposeSingle<T>(
    private val source: Single<T>,
    private val scope: Maybe<*>
) : Single<T>() {
    override fun subscribeActual(observer: SingleObserver<in T>) {
        source.subscribeWith(AutoDisposeSingleObserver(observer, scope))
    }
}

internal class AutoDisposeSingleObserver<T>(
    private val delegate: SingleObserver<T>,
    private val scope: Maybe<*>
) : SingleObserver<T> {
    override fun onSubscribe(d: Disposable) {
        delegate.onSubscribe(AutoDisposable(d, scope))
    }

    override fun onSuccess(t: T) {
        delegate.onSuccess(t)
    }

    override fun onError(e: Throwable) {
        delegate.onError(e)
    }
}