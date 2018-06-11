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

package com.ivianuu.autodispose.conductor

import com.bluelinelabs.conductor.Controller
import com.ivianuu.autodispose.ScopeProviders
import com.ivianuu.autodispose.autoDisposable
import com.ivianuu.autodispose.lifecycle.autoDisposable
import io.reactivex.*
import io.reactivex.parallel.ParallelFlowable

fun ScopeProviders.from(controller: Controller) = ControllerScopeProvider.from(controller)

fun Controller.scope() = ScopeProviders.from(this)

fun Completable.autoDisposable(controller: Controller) = autoDisposable(controller.scope())

fun <T : Any> Flowable<T>.autoDisposable(controller: Controller) =
    autoDisposable(controller.scope())

fun <T : Any> Maybe<T>.autoDisposable(controller: Controller) = autoDisposable(controller.scope())

fun <T : Any> Observable<T>.autoDisposable(controller: Controller) =
    autoDisposable(controller.scope())

fun <T : Any> ParallelFlowable<T>.autoDisposable(controller: Controller) =
    autoDisposable(controller.scope())

fun <T : Any> Single<T>.autoDisposable(controller: Controller) = autoDisposable(controller.scope())

fun Completable.autoDisposable(controller: Controller, untilEvent: ControllerEvent) =
    autoDisposable(controller.scope(), untilEvent)

fun <T : Any> Flowable<T>.autoDisposable(controller: Controller, untilEvent: ControllerEvent) =
    autoDisposable(controller.scope(), untilEvent)

fun <T : Any> Maybe<T>.autoDisposable(controller: Controller, untilEvent: ControllerEvent) =
    autoDisposable(controller.scope(), untilEvent)

fun <T : Any> Observable<T>.autoDisposable(controller: Controller, untilEvent: ControllerEvent) =
    autoDisposable(controller.scope(), untilEvent)

fun <T : Any> ParallelFlowable<T>.autoDisposable(
    controller: Controller,
    untilEvent: ControllerEvent
) =
    autoDisposable(controller.scope(), untilEvent)

fun <T : Any> Single<T>.autoDisposable(controller: Controller, untilEvent: ControllerEvent) =
    autoDisposable(controller.scope(), untilEvent)