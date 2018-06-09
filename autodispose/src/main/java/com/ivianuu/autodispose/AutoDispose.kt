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
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

/**
 * Auto dispose
 */
object AutoDispose {

    fun autoDispose(disposable: Disposable, scope: Maybe<*>) {
        AutoDisposer(disposable, scope)
    }

    fun autoDispose(disposable: Disposable, scopeProvider: ScopeProvider) {
        autoDispose(disposable, scopeProvider.requestScope())
    }

    fun <T> autoDispose(
        disposable: Disposable,
        lifecycleScopeProvider: LifecycleScopeProvider<T>
    ) {
        autoDispose(disposable, lifecycleScopeProvider.requestScope())
    }

    fun <T> autoDispose(
        disposable: Disposable,
        lifecycleScopeProvider: LifecycleScopeProvider<T>,
        untilEvent: T
    ) {
        autoDispose(disposable, lifecycleScopeProvider.requestScope(untilEvent))
    }

    fun <T> autoDispose(
        disposable: Disposable,
        lifecycle: Observable<T>,
        untilEvent: T
    ) {
        val scope = lifecycle
            .filter { it == untilEvent }
            .take(1)
            .singleElement()
        autoDispose(disposable, scope)
    }
}