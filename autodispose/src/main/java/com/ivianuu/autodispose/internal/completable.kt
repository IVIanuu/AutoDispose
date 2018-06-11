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

import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Maybe
import io.reactivex.disposables.Disposable

internal class AutoDisposeCompletable(
    private val source: Completable,
    private val scope: Maybe<*>
) : Completable() {
    override fun subscribeActual(s: CompletableObserver) {
        source.subscribe(AutoDisposeCompletableObserver(s, scope))
    }
}

internal class AutoDisposeCompletableObserver(
    private val delegate: CompletableObserver,
    private val scope: Maybe<*>
) : CompletableObserver {
    override fun onComplete() {
        delegate.onComplete()
    }

    override fun onSubscribe(d: Disposable) {
        delegate.onSubscribe(AutoDisposable(d, scope))
    }

    override fun onError(e: Throwable) {
        delegate.onError(e)
    }

}