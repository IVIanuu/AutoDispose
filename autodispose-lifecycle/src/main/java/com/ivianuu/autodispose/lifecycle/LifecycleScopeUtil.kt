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

import com.ivianuu.autodispose.AutoDisposePlugins
import com.ivianuu.autodispose.LifecycleNotStartedException
import io.reactivex.Completable
import io.reactivex.Observable

internal object LifecycleScopeUtil {

    fun <T> getScope(lifecycleScopeProvider: LifecycleScopeProvider<T>): Completable {
        val currentEvent = lifecycleScopeProvider.peekLifecycle()

        val exception = LifecycleNotStartedException()

        if (currentEvent == null) {
            val handler = AutoDisposePlugins.outsideLifecycleHandler
            if (handler != null) {
                handler.accept(exception)
                return Completable.complete()
            } else {
                throw exception
            }
        }

        val untilEvent = lifecycleScopeProvider.correspondingEvents()
            .apply(currentEvent)

        return getScope(lifecycleScopeProvider, untilEvent)
    }

    fun <T> getScope(
        lifecycleScopeProvider: LifecycleScopeProvider<T>,
        untilEvent: T
    ): Completable =
        getScope(lifecycleScopeProvider.lifecycle(), untilEvent)

    fun <T> getScope(lifecycle: Observable<T>, untilEvent: T): Completable = lifecycle
        .filter { it == untilEvent }
        .take(1)
        .ignoreElements()

}