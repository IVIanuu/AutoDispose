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

package com.ivianuu.autodispose.arch

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.support.v4.app.Fragment
import com.ivianuu.autodispose.ScopeProviders
import com.ivianuu.autodispose.autoDispose
import com.ivianuu.autodispose.lifecycle.autoDispose
import io.reactivex.disposables.Disposable

fun ScopeProviders.from(owner: LifecycleOwner) = AndroidLifecycleScopeProvider.from(owner)

fun LifecycleOwner.scope() = ScopeProviders.from(this)

fun Fragment.viewScope() = ScopeProviders.from(viewLifecycleOwner)

fun Disposable.autoDispose(owner: LifecycleOwner) = autoDispose(owner.scope())

fun Disposable.autoDispose(owner: LifecycleOwner, untilEvent: Lifecycle.Event) =
    autoDispose(owner.scope(), untilEvent)