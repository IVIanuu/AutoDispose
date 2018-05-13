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

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ivianuu.autodispose.archcomponents.autoDispose
import com.ivianuu.conductor.Conductor
import com.ivianuu.conductor.Router
import com.ivianuu.conductor.RouterTransaction

class MainActivity : AppCompatActivity() {

    private lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        router = Conductor.attachRouter(this,
            findViewById(android.R.id.content), savedInstanceState)

        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(TestController()))
        }
    }

    override fun onResume() {
        super.onResume()

        testObservable()
            .subscribe()
            .autoDispose(this)
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }
}
