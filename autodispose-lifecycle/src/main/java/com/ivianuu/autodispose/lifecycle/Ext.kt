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

import com.ivianuu.autodispose.AutoDispose
import io.reactivex.*
import io.reactivex.parallel.ParallelFlowable

fun <T : Any, E> AutoDispose.autoDisposable(
    provider: LifecycleScopeProvider<E>,
    untilEvent: E
) = autoDisposable<T>(provider.requestScope(untilEvent))

fun <T : Any, E> AutoDispose.autoDisposable(
    lifecycle: Observable<E>,
    untilEvent: E
) = autoDisposable<T>(LifecycleScopeUtil.getScope(lifecycle, untilEvent))

fun <E> Completable.autoDisposable(provider: LifecycleScopeProvider<E>, untilEvent: E) =
    `as`(AutoDispose.autoDisposable<Any, E>(provider, untilEvent))

fun <T : Any, E> Flowable<T>.autoDisposable(provider: LifecycleScopeProvider<E>, untilEvent: E) =
    `as`(AutoDispose.autoDisposable<T, E>(provider, untilEvent))

fun <T : Any, E> Maybe<T>.autoDisposable(provider: LifecycleScopeProvider<E>, untilEvent: E) =
    `as`(AutoDispose.autoDisposable<T, E>(provider, untilEvent))

fun <T : Any, E> Observable<T>.autoDisposable(provider: LifecycleScopeProvider<E>, untilEvent: E) =
    `as`(AutoDispose.autoDisposable<T, E>(provider, untilEvent))

fun <T : Any, E> ParallelFlowable<T>.autoDisposable(
    provider: LifecycleScopeProvider<E>,
    untilEvent: E
) =
    `as`(AutoDispose.autoDisposable<T, E>(provider, untilEvent))

fun <T : Any, E> Single<T>.autoDisposable(provider: LifecycleScopeProvider<E>, untilEvent: E) =
    `as`(AutoDispose.autoDisposable<T, E>(provider, untilEvent))

fun <E> Completable.autoDisposable(lifecycle: Observable<E>, untilEvent: E) =
    `as`(AutoDispose.autoDisposable<Any, E>(lifecycle, untilEvent))

fun <T : Any, E> Flowable<T>.autoDisposable(lifecycle: Observable<E>, untilEvent: E) =
    `as`(AutoDispose.autoDisposable<T, E>(lifecycle, untilEvent))

fun <T : Any, E> Maybe<T>.autoDisposable(lifecycle: Observable<E>, untilEvent: E) =
    `as`(AutoDispose.autoDisposable<T, E>(lifecycle, untilEvent))

fun <T : Any, E> Observable<T>.autoDisposable(lifecycle: Observable<E>, untilEvent: E) =
    `as`(AutoDispose.autoDisposable<T, E>(lifecycle, untilEvent))

fun <T : Any, E> ParallelFlowable<T>.autoDisposable(lifecycle: Observable<E>, untilEvent: E) =
    `as`(AutoDispose.autoDisposable<T, E>(lifecycle, untilEvent))

fun <T : Any, E> Single<T>.autoDisposable(lifecycle: Observable<E>, untilEvent: E) =
    `as`(AutoDispose.autoDisposable<T, E>(lifecycle, untilEvent))