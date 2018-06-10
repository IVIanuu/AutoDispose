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

package com.ivianuu.autodispose.android

import android.view.View
import com.ivianuu.autodispose.OutsideLifecycleException
import com.ivianuu.autodispose.ScopeProvider
import io.reactivex.Maybe

/**
 * A [ScopeProvider] for [View]'s
 */
class ViewScopeProvider private constructor(private val view: View) : ScopeProvider {

    override fun requestScope(): Maybe<*> {
        val isAttached = AutoDisposeAndroidUtil.isAttached(view)

        if (!isAttached) {
            throw OutsideLifecycleException("view not attached")
        }

        return Maybe.create<Unit> { e ->
            val listener = object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(v: View?) {
                }

                override fun onViewDetachedFromWindow(v: View?) {
                    if (!e.isDisposed) {
                        e.onSuccess(Unit)
                    }
                }
            }

            e.setCancellable { view.removeOnAttachStateChangeListener(listener) }

            view.addOnAttachStateChangeListener(listener)
        }
    }

    companion object {

        fun from(view: View): ScopeProvider {
            return ViewScopeProvider(view)
        }

    }

}