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

import com.ivianuu.conductor.Controller
import com.ivianuu.autodispose.autoDispose
import io.reactivex.disposables.Disposable

fun Disposable.autoDispose(controller: Controller) {
    autoDispose(ControllerScopeProvider.from(controller))
}

fun Disposable.autoDispose(controller: Controller, event: ControllerEvent) {
    autoDispose(ControllerScopeProvider.from(controller), event)
}