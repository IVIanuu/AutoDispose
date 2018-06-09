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
import com.ivianuu.autodispose.LifecycleScopeProvider
import com.ivianuu.autodispose.navi.android.ActivityEvent.*
import com.ivianuu.navi.Listener
import com.ivianuu.navi.NaviComponent
import com.ivianuu.navi.addListener
import com.ivianuu.navi.android.*
import com.ivianuu.navi.handlesEvents
import io.reactivex.functions.Function
import io.reactivex.subjects.BehaviorSubject

/**
 * A [LifecycleScopeProvider] for [Activity]'s
 */
class ActivityLifecycleScopeProvider private constructor(
    naviComponent: NaviComponent
) : Listener<Any>, LifecycleScopeProvider<ActivityEvent> {

    private val lifecycle = BehaviorSubject.create<ActivityEvent>()

    init {
        if (!naviComponent.handlesEvents(
                Create::class, Start::class, Resume::class,
                Pause::class, Stop::class, Destroy::class)) {
            throw IllegalArgumentException("not all required events are handled")
        }

        naviComponent.addListener(this)
    }

    override fun call(event: Any) {
        val activityEvent = when(event) {
            is Create -> CREATE
            is Start -> START
            is Resume -> RESUME
            is Pause -> PAUSE
            is Stop -> STOP
            is Destroy -> DESTROY
            else -> null
        } ?: return

        lifecycle.onNext(activityEvent)
    }

    override fun correspondingEvents() = CORRESPONDING_ACTIVITY_EVENTS

    override fun lifecycle() = lifecycle

    override fun peekLifecycle() = lifecycle.value

    companion object {

        private val CORRESPONDING_ACTIVITY_EVENTS = Function<ActivityEvent, ActivityEvent> {
            when (it) {
                CREATE -> DESTROY
                START -> STOP
                RESUME -> PAUSE
                PAUSE -> STOP
                STOP -> DESTROY
                else -> throw IllegalStateException("out of lifecycle ${it::class.java.simpleName}")
            }
        }


        fun <T> from(activity: T): LifecycleScopeProvider<ActivityEvent> where T : Activity, T : NaviComponent{
            return ActivityLifecycleScopeProvider(activity)
        }
    }
}