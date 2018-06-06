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

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Lifecycle.Event
import android.arch.lifecycle.Lifecycle.Event.*
import android.arch.lifecycle.Lifecycle.State.*
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import com.ivianuu.autodispose.LifecycleScopeProvider
import com.ivianuu.autodispose.OutsideLifecycleException
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A [LifecycleScopeProvider] for [LifecycleOwner]'s
 */
class AndroidLifecycleScopeProvider private constructor(
    owner: LifecycleOwner
) : LifecycleScopeProvider<Event> {

    private val lifecycleObservable = LifecycleObservable(owner.lifecycle)

    override fun lifecycle(): Observable<Event> = lifecycleObservable

    override fun correspondingEvents() = CORRESPONDING_EVENTS

    override fun peekLifecycle(): Event? {
        lifecycleObservable.backfillEvents()
        return lifecycleObservable.value
    }

    companion object {
        private val CORRESPONDING_EVENTS =
            Function<Event, Event> { lastEvent ->
                when (lastEvent) {
                    ON_CREATE -> Event.ON_DESTROY
                    ON_START -> Event.ON_STOP
                    ON_RESUME -> Event.ON_PAUSE
                    ON_PAUSE -> ON_STOP
                    ON_STOP -> ON_DESTROY
                    else -> throw OutsideLifecycleException(
                        "Lifecycle has ended! last event was $lastEvent"
                    )
                }
            }

        fun from(owner: LifecycleOwner): LifecycleScopeProvider<Event> {
            return AndroidLifecycleScopeProvider(owner)
        }

    }
}

internal class LifecycleObservable(private val lifecycle: Lifecycle) : Observable<Event>() {
    private val subject = BehaviorSubject.create<Event>()

    val value: Event?
        get() = subject.value

    fun backfillEvents() {
        val correspondingEvent = when (lifecycle.currentState) {
            INITIALIZED -> ON_CREATE
            CREATED ->  ON_START
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
        private val lifecycle: Lifecycle, private val observer: Observer<in Event>,
        private val subject: BehaviorSubject<Event>
    ) : Disposable, LifecycleObserver {

        private val disposed = AtomicBoolean(false)

        override fun isDisposed(): Boolean = disposed.get()

        override fun dispose() {
            if (!disposed.getAndSet(true)) {
                lifecycle.removeObserver(this)
            }
        }

        @OnLifecycleEvent(Event.ON_ANY)
        fun onStateChange(owner: LifecycleOwner, event: Event) {
            if (!isDisposed) {
                if (!(event == ON_CREATE && subject.value == event)) {
                    subject.onNext(event)
                }
                observer.onNext(event)
            }
        }
    }
}