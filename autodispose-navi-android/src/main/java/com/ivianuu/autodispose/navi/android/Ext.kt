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
import com.ivianuu.autodispose.autoDisposable
import com.ivianuu.autodispose.lifecycle.autoDisposable
import com.ivianuu.navi.NaviComponent
import io.reactivex.*
import io.reactivex.parallel.ParallelFlowable

fun <N> ScopeProviders.from(naviActivity: N) where N : Activity, N : NaviComponent =
    NaviActivityLifecycleScopeProvider.from(naviActivity)

fun <N> ScopeProviders.from(naviFragment: N) where N : Fragment, N : NaviComponent =
    NaviFragmentLifecycleScopeProvider.from(naviFragment)

fun <N> N.scope()where N : Activity, N : NaviComponent = ScopeProviders.from(this)

fun <N> N.scope()where N : Fragment, N : NaviComponent = ScopeProviders.from(this)

fun <N> Completable.autoDisposable(naviActivity: N) where N : Activity, N : NaviComponent =
        autoDisposable(naviActivity.scope())

fun <N, T> Flowable<T>.autoDisposable(naviActivity: N) where N : Activity, N : NaviComponent =
    autoDisposable(naviActivity.scope())

fun <N, T> Maybe<T>.autoDisposable(naviActivity: N) where N : Activity, N : NaviComponent =
    autoDisposable(naviActivity.scope())

fun <N, T> Observable<T>.autoDisposable(naviActivity: N) where N : Activity, N : NaviComponent =
    autoDisposable(naviActivity.scope())

fun <N, T> ParallelFlowable<T>.autoDisposable(naviActivity: N) where N : Activity, N : NaviComponent =
    autoDisposable(naviActivity.scope())

fun <N, T> Single<T>.autoDisposable(naviActivity: N) where N : Activity, N : NaviComponent =
    autoDisposable(naviActivity.scope())

fun <N> Completable.autoDisposable(naviFragment: N) where N : Fragment, N : NaviComponent =
    autoDisposable(naviFragment.scope())

fun <N, T> Flowable<T>.autoDisposable(naviFragment: N) where N : Fragment, N : NaviComponent =
    autoDisposable(naviFragment.scope())

fun <N, T> Maybe<T>.autoDisposable(naviFragment: N) where N : Fragment, N : NaviComponent =
    autoDisposable(naviFragment.scope())

fun <N, T> Observable<T>.autoDisposable(naviFragment: N) where N : Fragment, N : NaviComponent =
    autoDisposable(naviFragment.scope())

fun <N, T> ParallelFlowable<T>.autoDisposable(naviFragment: N) where N : Fragment, N : NaviComponent =
    autoDisposable(naviFragment.scope())

fun <N, T> Single<T>.autoDisposable(naviFragment: N) where N : Fragment, N : NaviComponent =
    autoDisposable(naviFragment.scope())

fun <N> Completable.autoDisposable(naviActivity: N, untilEvent: ActivityEvent) where N : Activity, N : NaviComponent =
    autoDisposable(naviActivity.scope(), untilEvent)

fun <N, T> Flowable<T>.autoDisposable(naviActivity: N, untilEvent: ActivityEvent) where N : Activity, N : NaviComponent =
    autoDisposable(naviActivity.scope(), untilEvent)

fun <N, T> Maybe<T>.autoDisposable(naviActivity: N, untilEvent: ActivityEvent) where N : Activity, N : NaviComponent =
    autoDisposable(naviActivity.scope(), untilEvent)

fun <N, T> Observable<T>.autoDisposable(naviActivity: N, untilEvent: ActivityEvent) where N : Activity, N : NaviComponent =
    autoDisposable(naviActivity.scope(), untilEvent)

fun <N, T> ParallelFlowable<T>.autoDisposable(naviActivity: N, untilEvent: ActivityEvent) where N : Activity, N : NaviComponent =
    autoDisposable(naviActivity.scope(), untilEvent)

fun <N, T> Single<T>.autoDisposable(naviActivity: N, untilEvent: ActivityEvent) where N : Activity, N : NaviComponent =
    autoDisposable(naviActivity.scope(), untilEvent)

fun <N> Completable.autoDisposable(naviFragment: N, untilEvent: FragmentEvent) where N : Fragment, N : NaviComponent =
    autoDisposable(naviFragment.scope(), untilEvent)

fun <N, T> Flowable<T>.autoDisposable(naviFragment: N, untilEvent: FragmentEvent) where N : Fragment, N : NaviComponent =
    autoDisposable(naviFragment.scope(), untilEvent)

fun <N, T> Maybe<T>.autoDisposable(naviFragment: N, untilEvent: FragmentEvent) where N : Fragment, N : NaviComponent =
    autoDisposable(naviFragment.scope(), untilEvent)

fun <N, T> Observable<T>.autoDisposable(naviFragment: N, untilEvent: FragmentEvent) where N : Fragment, N : NaviComponent =
    autoDisposable(naviFragment.scope(), untilEvent)

fun <N, T> ParallelFlowable<T>.autoDisposable(naviFragment: N, untilEvent: FragmentEvent) where N : Fragment, N : NaviComponent =
    autoDisposable(naviFragment.scope(), untilEvent)

fun <N, T> Single<T>.autoDisposable(naviFragment: N, untilEvent: FragmentEvent) where N : Fragment, N : NaviComponent =
    autoDisposable(naviFragment.scope(), untilEvent)