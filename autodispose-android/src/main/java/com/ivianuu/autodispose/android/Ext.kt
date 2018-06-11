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

import android.app.Dialog
import android.view.View
import com.ivianuu.autodispose.ScopeProviders
import com.ivianuu.autodispose.autoDispose
import io.reactivex.disposables.Disposable

fun ScopeProviders.from(dialog: Dialog) = DialogScopeProvider.from(dialog)

fun ScopeProviders.from(view: View) = ViewScopeProvider.from(view)

fun Dialog.scope() = ScopeProviders.from(this)

fun View.scope() = ScopeProviders.from(this)

fun Disposable.autoDispose(dialog: Dialog) = autoDispose(dialog.scope())

fun Disposable.autoDispose(view: View) = autoDispose(view.scope())