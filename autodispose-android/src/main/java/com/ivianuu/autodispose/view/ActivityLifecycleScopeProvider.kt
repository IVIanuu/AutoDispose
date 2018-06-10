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

package com.ivianuu.autodispose.view

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.ivianuu.autodispose.LifecycleScopeProvider
import com.ivianuu.autodispose.view.ActivityEvent.*
import io.reactivex.functions.Function
import io.reactivex.subjects.BehaviorSubject

/**
 * A [LifecycleScopeProvider] for [Activity]s'
 * Should be used as a local variable and should be instantiated pre on create
 */
class ActivityLifecycleScopeProvider private constructor(private val act: Activity): LifecycleScopeProvider<ActivityEvent> {
    
    private val lifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            if (act == activity) {
                lifecycle.onNext(CREATE)
            }
        }

        override fun onActivityStarted(activity: Activity) {
            if (act == activity) {
                lifecycle.onNext(START)
            }
        }

        override fun onActivityResumed(activity: Activity) {
            if (act == activity) {
                lifecycle.onNext(RESUME)
            }
        }

        override fun onActivityPaused(activity: Activity) {
            if (act == activity) {
                lifecycle.onNext(PAUSE)
            }
        }
        
        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }
        
        override fun onActivityStopped(activity: Activity) {
            if (act == activity) {
                lifecycle.onNext(STOP)
            }
        }
        
        override fun onActivityDestroyed(activity: Activity) {
            if (act == activity) {
                lifecycle.onNext(DESTROY)
                activity.application.unregisterActivityLifecycleCallbacks(this)
            }
        }
    }
    
    private val lifecycle = BehaviorSubject.create<ActivityEvent>()
    
    init {
        act.application.registerActivityLifecycleCallbacks(lifecycleCallbacks)
    }

    override fun lifecycle() = lifecycle

    override fun correspondingEvents() = CORRESPONDING_ACTIVITY_EVENTS

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
        
        fun from(activity: Activity): LifecycleScopeProvider<ActivityEvent> {
            return ActivityLifecycleScopeProvider(activity)
        }
        
    }
}