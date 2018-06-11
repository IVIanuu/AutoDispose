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

package com.ivianuu.autodispose.lifecycle

import com.ivianuu.autodispose.ScopeProvider
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * Lifecycle scope provider
 */
interface LifecycleScopeProvider<T> : ScopeProvider {

    fun lifecycle(): Observable<T>

    fun correspondingEvents(): Function<T, T>

    fun peekLifecycle(): T?

    override fun requestScope(): Maybe<T> {
        return LifecycleScopeUtil.getScope(this)
    }

    fun requestScope(untilEvent: T): Maybe<T> {
        return LifecycleScopeUtil.getScope(lifecycle(), untilEvent)
    }
}