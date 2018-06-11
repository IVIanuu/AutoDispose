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

import io.reactivex.*
import io.reactivex.parallel.ParallelFlowable

/**
 * Auto dispose
 */
object AutoDispose {

    fun <T : Any> autoDisposable(scope: Maybe<*>): AutoDisposeConverter<T> =
        AutoDisposeConverterImpl(scope)

    fun <T : Any> autoDisposable(scopeProvider: ScopeProvider): AutoDisposeConverter<T> =
        autoDisposable(scopeProvider.requestScope())

}

fun Completable.autoDisposable(scope: Maybe<*>) =
    `as`(AutoDispose.autoDisposable<Any>(scope))

fun <T : Any> Flowable<T>.autoDisposable(scope: Maybe<*>) =
    `as`(AutoDispose.autoDisposable<T>(scope))

fun <T : Any> Maybe<T>.autoDisposable(scope: Maybe<*>) =
    `as`(AutoDispose.autoDisposable<T>(scope))

fun <T : Any> Observable<T>.autoDisposable(scope: Maybe<*>) =
    `as`(AutoDispose.autoDisposable<T>(scope))

fun <T : Any> ParallelFlowable<T>.autoDisposable(scope: Maybe<*>) =
    `as`(AutoDispose.autoDisposable<T>(scope))

fun <T : Any> Single<T>.autoDisposable(scope: Maybe<*>) =
    `as`(AutoDispose.autoDisposable<T>(scope))

fun Completable.autoDisposable(provider: ScopeProvider) =
    `as`(AutoDispose.autoDisposable<Any>(provider))

fun <T : Any> Flowable<T>.autoDisposable(provider: ScopeProvider) =
    `as`(AutoDispose.autoDisposable<T>(provider))

fun <T : Any> Maybe<T>.autoDisposable(provider: ScopeProvider) =
    `as`(AutoDispose.autoDisposable<T>(provider))

fun <T : Any> Observable<T>.autoDisposable(provider: ScopeProvider) =
    `as`(AutoDispose.autoDisposable<T>(provider))

fun <T : Any> ParallelFlowable<T>.autoDisposable(provider: ScopeProvider) =
    `as`(AutoDispose.autoDisposable<T>(provider))

fun <T : Any> Single<T>.autoDisposable(provider: ScopeProvider) =
    `as`(AutoDispose.autoDisposable<T>(provider))