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

import io.reactivex.Maybe
import io.reactivex.disposables.Disposable
import java.util.concurrent.atomic.AtomicReference

/**
 * Auto disposes the main disposable on emission of the scope
 */
internal class AutoDisposer(
    mainDisposable: Disposable,
    scope: Maybe<*>
) {

    private val mainDisposable = AtomicReference<Disposable?>(mainDisposable)
    private val autoDisposable = AtomicReference<Disposable?>()

    init {
        autoDisposable.set(scope.subscribe { dispose() })
    }

    private fun dispose() {
        mainDisposable.getAndSet(null)?.dispose()
        autoDisposable.getAndSet(null)?.dispose()
    }

}