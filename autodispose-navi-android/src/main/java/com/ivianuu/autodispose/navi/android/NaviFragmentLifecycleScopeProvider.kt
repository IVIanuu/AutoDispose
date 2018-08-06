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

import android.support.v4.app.Fragment
import com.ivianuu.autodispose.LifecycleScopeProvider
import com.ivianuu.autodispose.navi.android.FragmentEvent.*
import com.ivianuu.navi.Listener
import com.ivianuu.navi.NaviComponent
import com.ivianuu.navi.addListener
import com.ivianuu.navi.android.*
import com.ivianuu.navi.handlesEvents
import io.reactivex.functions.Function
import io.reactivex.subjects.BehaviorSubject

/**
 * A [LifecycleScopeProvider] for [Fragment]'s
 * Should be used as a local variable and should be instantiated pre on create
 */
class NaviFragmentLifecycleScopeProvider private constructor(
    naviComponent: NaviComponent
) : Listener<Any>, LifecycleScopeProvider<FragmentEvent> {

    private val lifecycle = BehaviorSubject.create<FragmentEvent>()

    init {
        if (!naviComponent.handlesEvents(
                Attach::class, Create::class,
                ViewCreated::class, Start::class, Resume::class,
                Pause::class, Stop::class, DestroyView::class,
                Destroy::class, Detach::class)) {
            throw IllegalArgumentException("not all required events are handled")
        }

        naviComponent.addListener(this)
    }

    override fun call(event: Any) {
        val fragmentEvent = when(event) {
            is Attach -> ATTACH
            is Create -> CREATE
            is ViewCreated -> CREATE_VIEW
            is Start -> START
            is Resume -> RESUME
            is Pause -> PAUSE
            is Stop -> STOP
            is DestroyView -> DESTROY_VIEW
            is Destroy -> DESTROY
            is Detach -> DETACH
            else -> null
        } ?: return

        lifecycle.onNext(fragmentEvent)
    }

    override fun correspondingEvents() = CORRESPONDING_FRAGMENT_EVENTS

    override fun lifecycle() = lifecycle

    override fun peekLifecycle() = lifecycle.value

    companion object {

        private val CORRESPONDING_FRAGMENT_EVENTS = Function<FragmentEvent, FragmentEvent> {
            when (it) {
                ATTACH -> DETACH
                CREATE -> DESTROY
                CREATE_VIEW -> DESTROY_VIEW
                START -> STOP
                RESUME -> PAUSE
                PAUSE -> STOP
                STOP -> DESTROY_VIEW
                DESTROY -> DETACH
                else -> throw IllegalStateException("out of lifecycle ${it::class.java.simpleName}")
            }
        }

        fun <N> from(fragment: N): LifecycleScopeProvider<FragmentEvent> where N : Fragment, N : NaviComponent =
            NaviFragmentLifecycleScopeProvider(fragment)

    }

}