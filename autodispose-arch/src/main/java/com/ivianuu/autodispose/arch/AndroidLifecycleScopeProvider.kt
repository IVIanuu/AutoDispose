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

package com.ivianuu.autodispose.arch

import androidx.fragment.app.Fragment
import androidx.lifecycle.GenericLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.Lifecycle.Event.ON_CREATE
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.Lifecycle.Event.ON_PAUSE
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.Lifecycle.Event.ON_START
import androidx.lifecycle.Lifecycle.Event.ON_STOP
import androidx.lifecycle.Lifecycle.State.CREATED
import androidx.lifecycle.Lifecycle.State.DESTROYED
import androidx.lifecycle.Lifecycle.State.INITIALIZED
import androidx.lifecycle.Lifecycle.State.RESUMED
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.LifecycleOwner
import com.ivianuu.autodispose.LifecycleEndedException
import com.ivianuu.autodispose.LifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A [LifecycleScopeProvider] for [LifecycleOwner]'s
 */
class AndroidLifecycleScopeProvider(owner: LifecycleOwner) : LifecycleScopeProvider<Event> {

    private val lifecycleObservable = LifecycleObservable(owner.lifecycle)

    override fun lifecycle(): Observable<Event> = lifecycleObservable

    override fun correspondingEvents() = CORRESPONDING_EVENTS

    override fun peekLifecycle(): Event? {
        lifecycleObservable.backfillEvents()
        return lifecycleObservable.value
    }

    companion object {
        private val CORRESPONDING_EVENTS: (Event) -> Event = { lastEvent ->
                when (lastEvent) {
                    ON_CREATE -> Event.ON_DESTROY
                    ON_START -> Event.ON_STOP
                    ON_RESUME -> Event.ON_PAUSE
                    ON_PAUSE -> ON_STOP
                    ON_STOP -> ON_DESTROY
                    else -> throw LifecycleEndedException(
                        "Lifecycle has ended! last event was $lastEvent"
                    )
                }
            }
    }
}

private class LifecycleObservable(private val lifecycle: Lifecycle) : Observable<Event>() {
    private val subject = BehaviorSubject.create<Event>()

    val value: Event?
        get() = subject.value

    fun backfillEvents() {
        val correspondingEvent = when (lifecycle.currentState) {
            INITIALIZED -> ON_CREATE
            CREATED -> Event.ON_START
            STARTED, RESUMED -> ON_RESUME
            DESTROYED -> ON_DESTROY
            else ->  ON_DESTROY
        }

        subject.onNext(correspondingEvent)
    }

    override fun subscribeActual(observer: Observer<in Event>) {
        val archObserver = ArchLifecycleObserver(lifecycle, observer, subject)
        observer.onSubscribe(archObserver)
        lifecycle.addObserver(archObserver)
    }

    class ArchLifecycleObserver(
        private val lifecycle: Lifecycle,
        private val observer: Observer<in Event>,
        private val subject: BehaviorSubject<Event>
    ) : Disposable, GenericLifecycleObserver {

        private val disposed = AtomicBoolean(false)

        override fun onStateChanged(source: LifecycleOwner, event: Event) {
            if (!isDisposed) {
                if (!(event == ON_CREATE && subject.value == event)) {
                    subject.onNext(event)
                }
                observer.onNext(event)
            }
        }

        override fun isDisposed() = disposed.get()

        override fun dispose() {
            if (!disposed.getAndSet(true)) {
                lifecycle.removeObserver(this)
            }
        }

    }
}

fun LifecycleOwner.scope(): LifecycleScopeProvider<Event> = AndroidLifecycleScopeProvider(this)

fun Fragment.viewScope(): LifecycleScopeProvider<Event> =
    AndroidLifecycleScopeProvider(viewLifecycleOwner)