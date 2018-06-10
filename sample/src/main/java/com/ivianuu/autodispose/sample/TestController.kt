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

package com.ivianuu.autodispose.sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.ivianuu.autodispose.ScopeProviders
import com.ivianuu.autodispose.autoDispose
import com.ivianuu.autodispose.conductor.autoDispose
import com.ivianuu.autodispose.conductor.from
import com.ivianuu.autodispose.conductor.scope

/**
 * @author Manuel Wrage (IVIanuu)
 */
class TestController : Controller() {

    init {
        testObservable()
            .subscribe()
            .autoDispose(ScopeProviders.from(this))

        testObservable()
            .subscribe()
            .autoDispose(scope())

        testObservable()
            .subscribe()
            .autoDispose(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup
    ): View {
        return inflater.inflate(R.layout.controller_test, container, false)
    }
}