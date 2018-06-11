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

import io.reactivex.functions.Consumer

/**
 * Utility class to inject handlers to certain standard AutoDispose operations.
 */
object AutoDisposePlugins {

    @Volatile var outsideLifecycleHandler: Consumer<in OutsideLifecycleException>? = null
        set(handler) {
            if (isLockdown) {
                throw IllegalStateException("Plugins can't be changed anymore")
            }
            field = handler
        }

    @Volatile var fillInOutsideLifecycleExceptionStacktraces = false
        set(fillInStacktrace) {
            if (isLockdown) {
                throw IllegalStateException("Plugins can't be changed anymore")
            }
            field = fillInStacktrace
        }

    @Volatile var isLockdown = false

    fun reset() {
        outsideLifecycleHandler = null
    }
}