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
import io.reactivex.parallel.ParallelFlowable
import org.reactivestreams.Subscriber

internal class AutoDisposeParallelFlowable<T>(
    private val source: ParallelFlowable<T>,
    private val scope: Maybe<*>
) : ParallelFlowable<T>() {
    override fun subscribe(subscribers: Array<out Subscriber<in T>>) {
        val newSubscribers = subscribers
            .map { AutoDisposeSubscriber(it, scope) }
            .toTypedArray()

        source.subscribe(newSubscribers)
    }

    override fun parallelism() = source.parallelism()
}
