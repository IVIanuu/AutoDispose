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

fun Disposable.autoDispose(scopeProvider: ScopeProvider) {
    AutoDispose.autoDispose(this, scopeProvider)
}

fun Disposable.autoDispose(scope: Maybe<*>) {
    AutoDispose.autoDispose(this, scope)
}

fun <T> Disposable.autoDispose(lifecycleScopeProvider: LifecycleScopeProvider<T>) {
    AutoDispose.autoDispose(this, lifecycleScopeProvider)
}

fun <T> Disposable.autoDispose(
    lifecycleScopeProvider: LifecycleScopeProvider<T>,
    untilEvent: T
) {
    AutoDispose.autoDispose(this, lifecycleScopeProvider, untilEvent)
}

fun <T> Disposable.autoDispose(lifecycle: Observable<T>, untilEvent: T) {
    AutoDispose.autoDispose(this, lifecycle, untilEvent)
}