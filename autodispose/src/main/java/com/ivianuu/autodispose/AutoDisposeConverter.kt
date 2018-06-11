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

package com.ivianuu.autodispose

import com.ivianuu.autodispose.internal.*
import io.reactivex.*
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.BiConsumer
import io.reactivex.functions.Consumer
import io.reactivex.observers.TestObserver
import io.reactivex.parallel.ParallelFlowable
import io.reactivex.parallel.ParallelFlowableConverter
import io.reactivex.subscribers.TestSubscriber
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * @author Manuel Wrage (IVIanuu)
 */
interface AutoDisposeConverter<T : Any> : CompletableConverter<CompletableSubscribeProxy>,
    FlowableConverter<T, FlowableSubscribeProxy<T>>,
    MaybeConverter<T, MaybeSubscribeProxy<T>>,
    ObservableConverter<T, ObservableSubscribeProxy<T>>,
    ParallelFlowableConverter<T, ParallelFlowableSubscribeProxy<T>>,
    SingleConverter<T, SingleSubscribeProxy<T>>

class AutoDisposeConverterImpl<T : Any>(private val scope: Maybe<*>) :
    AutoDisposeConverter<T> {
    override fun apply(upstream: Completable): CompletableSubscribeProxy {
        return object : CompletableSubscribeProxy {
            override fun subscribe(): Disposable {
                return AutoDisposeCompletable(upstream, scope).subscribe()
            }

            override fun subscribe(action: Action): Disposable {
                return AutoDisposeCompletable(upstream, scope).subscribe(action)
            }

            override fun subscribe(action: Action, onError: Consumer<in Throwable>): Disposable {
                return AutoDisposeCompletable(upstream, scope).subscribe(action, onError)
            }

            override fun subscribe(observer: CompletableObserver) {
                AutoDisposeCompletable(upstream, scope).subscribe(observer)
            }

            override fun <E : CompletableObserver> subscribeWith(observer: E): E {
                return AutoDisposeCompletable(upstream, scope).subscribeWith(observer)
            }

            override fun test(): TestObserver<Void> {
                return AutoDisposeCompletable(upstream, scope).test()
            }

            override fun test(cancelled: Boolean): TestObserver<Void> {
                return AutoDisposeCompletable(upstream, scope).test(cancelled)
            }

        }
    }

    override fun apply(upstream: Flowable<T>): FlowableSubscribeProxy<T> {
        return object : FlowableSubscribeProxy<T> {
            override fun subscribe(): Disposable {
                return AutoDisposeFlowable(upstream, scope).subscribe()
            }

            override fun subscribe(onNext: Consumer<in T>): Disposable {
                return AutoDisposeFlowable(upstream, scope).subscribe(onNext)
            }

            override fun subscribe(
                onNext: Consumer<in T>,
                onError: Consumer<in Throwable>
            ): Disposable {
                return AutoDisposeFlowable(upstream, scope).subscribe(onNext, onError)
            }

            override fun subscribe(
                onNext: Consumer<in T>,
                onError: Consumer<in Throwable>,
                onComplete: Action
            ): Disposable {
                return AutoDisposeFlowable(upstream, scope).subscribe(onNext, onError, onComplete)
            }

            override fun subscribe(
                onNext: Consumer<in T>,
                onError: Consumer<in Throwable>,
                onComplete: Action,
                onSubscribe: Consumer<in Subscription>
            ): Disposable {
                return AutoDisposeFlowable(upstream, scope).subscribe(
                    onNext,
                    onError,
                    onComplete,
                    onSubscribe
                )
            }

            override fun subscribe(observer: Subscriber<T>) {
                AutoDisposeFlowable(upstream, scope).subscribe(observer)
            }

            override fun <E : Subscriber<in T>> subscribeWith(observer: E): E {
                return AutoDisposeFlowable(upstream, scope).subscribeWith(observer)
            }

            override fun test(): TestSubscriber<T> {
                return AutoDisposeFlowable(upstream, scope).test()
            }

            override fun test(initialRequest: Long): TestSubscriber<T> {
                return AutoDisposeFlowable(upstream, scope).test(initialRequest)
            }

            override fun test(initialRequest: Long, cancel: Boolean): TestSubscriber<T> {
                return AutoDisposeFlowable(upstream, scope).test(initialRequest, cancel)
            }
        }
    }

    override fun apply(upstream: Maybe<T>): MaybeSubscribeProxy<T> {
        return object : MaybeSubscribeProxy<T> {
            override fun subscribe(): Disposable {
                return AutoDisposeMaybe(upstream, scope).subscribe()
            }

            override fun subscribe(onSuccess: Consumer<in T>): Disposable {
                return AutoDisposeMaybe(upstream, scope).subscribe(onSuccess)
            }

            override fun subscribe(
                onSuccess: Consumer<in T>,
                onError: Consumer<in Throwable>
            ): Disposable {
                return AutoDisposeMaybe(upstream, scope).subscribe(onSuccess, onError)
            }

            override fun subscribe(
                onSuccess: Consumer<in T>,
                onError: Consumer<in Throwable>,
                onComplete: Action
            ): Disposable {
                return AutoDisposeMaybe(upstream, scope).subscribe(onSuccess, onError, onComplete)
            }

            override fun subscribe(observer: MaybeObserver<T>) {
                AutoDisposeMaybe(upstream, scope).subscribe(observer)
            }

            override fun <E : MaybeObserver<in T>> subscribeWith(observer: E): E {
                return AutoDisposeMaybe(upstream, scope).subscribeWith(observer)
            }

            override fun test(): TestObserver<T> {
                return AutoDisposeMaybe(upstream, scope).test()
            }

            override fun test(cancel: Boolean): TestObserver<T> {
                return AutoDisposeMaybe(upstream, scope).test(cancel)
            }
        }
    }

    override fun apply(upstream: Observable<T>): ObservableSubscribeProxy<T> {
        return object : ObservableSubscribeProxy<T> {
            override fun subscribe(): Disposable {
                return AutoDisposeObservable(upstream, scope).subscribe()
            }

            override fun subscribe(onNext: Consumer<in T>): Disposable {
                return AutoDisposeObservable(upstream, scope).subscribe(onNext)
            }

            override fun subscribe(
                onNext: Consumer<in T>,
                onError: Consumer<in Throwable>
            ): Disposable {
                return AutoDisposeObservable(upstream, scope).subscribe(onNext, onError)
            }

            override fun subscribe(
                onNext: Consumer<in T>,
                onError: Consumer<in Throwable>,
                onComplete: Action
            ): Disposable {
                return AutoDisposeObservable(upstream, scope).subscribe(onNext, onError, onComplete)
            }

            override fun subscribe(
                onNext: Consumer<in T>,
                onError: Consumer<in Throwable>,
                onComplete: Action,
                onSubscribe: Consumer<in Disposable>
            ): Disposable {
                return AutoDisposeObservable(upstream, scope).subscribe(
                    onNext,
                    onError,
                    onComplete,
                    onSubscribe
                )
            }

            override fun subscribe(observer: Observer<T>) {
                AutoDisposeObservable(upstream, scope).subscribe(observer)
            }

            override fun <E : Observer<in T>> subscribeWith(observer: E): E {
                return AutoDisposeObservable(upstream, scope).subscribeWith(observer)
            }

            override fun test(): TestObserver<T> {
                return AutoDisposeObservable(upstream, scope).test()
            }

            override fun test(dispose: Boolean): TestObserver<T> {
                return AutoDisposeObservable(upstream, scope).test(dispose)
            }
        }
    }

    override fun apply(upstream: ParallelFlowable<T>): ParallelFlowableSubscribeProxy<T> {
        return object : ParallelFlowableSubscribeProxy<T> {
            override fun subscribe(subscribers: Array<Subscriber<in T>>) {
                AutoDisposeParallelFlowable(upstream, scope).subscribe(subscribers)
            }
        }
    }

    override fun apply(upstream: Single<T>): SingleSubscribeProxy<T> {
        return object : SingleSubscribeProxy<T> {
            override fun subscribe(): Disposable {
                return AutoDisposeSingle(upstream, scope).subscribe()
            }

            override fun subscribe(onSuccess: Consumer<in T>): Disposable {
                return AutoDisposeSingle(upstream, scope).subscribe(onSuccess)
            }

            override fun subscribe(biConsumer: BiConsumer<in T, in Throwable>): Disposable {
                return AutoDisposeSingle(upstream, scope).subscribe(biConsumer)
            }

            override fun subscribe(
                onSuccess: Consumer<in T>,
                onError: Consumer<in Throwable>
            ): Disposable {
                return AutoDisposeSingle(upstream, scope).subscribe(onSuccess, onError)
            }

            override fun subscribe(observer: SingleObserver<T>) {
                AutoDisposeSingle(upstream, scope).subscribe(observer)
            }

            override fun <E : SingleObserver<in T>> subscribeWith(observer: E): E {
                return AutoDisposeSingle(upstream, scope).subscribeWith(observer)
            }

            override fun test(): TestObserver<T> {
                return AutoDisposeSingle(upstream, scope).test()
            }

            override fun test(cancel: Boolean): TestObserver<T> {
                return AutoDisposeSingle(upstream, scope).test(cancel)
            }

        }
    }
}