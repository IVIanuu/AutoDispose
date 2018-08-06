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

package com.ivianuu.autodispose.navi.android

import android.app.Activity
import android.support.v4.app.Fragment
import com.ivianuu.autodispose.ScopeProviders
import com.ivianuu.autodispose.autoDispose
import com.ivianuu.navi.NaviComponent
import io.reactivex.disposables.Disposable

fun <T> ScopeProviders.from(naviActivity: T) where T : Activity, T : NaviComponent =
    NaviActivityLifecycleScopeProvider.from(naviActivity)

fun <T> ScopeProviders.from(naviFragment: T) where T : Fragment, T : NaviComponent =
    NaviFragmentLifecycleScopeProvider.from(naviFragment)

fun <T> T.scope() where T : Activity, T : NaviComponent = ScopeProviders.from(this)

fun <T> T.scope() where T : Fragment, T : NaviComponent = ScopeProviders.from(this)

fun <T> Disposable.autoDispose(naviActivity: T) where T : Activity, T : NaviComponent =
    autoDispose(naviActivity.scope())

fun <T> Disposable.autoDispose(
    naviActivity: T,
    untilEvent: ActivityEvent
) where T : Activity, T : NaviComponent =
    autoDispose(naviActivity.scope(), untilEvent)

fun <T> Disposable.autoDispose(naviFragment: T) where T : Fragment, T : NaviComponent =
    autoDispose(naviFragment.scope())

fun <T> Disposable.autoDispose(
    naviFragment: T,
    untilEvent: FragmentEvent
) where T : Fragment, T : NaviComponent =
    autoDispose(naviFragment.scope(), untilEvent)