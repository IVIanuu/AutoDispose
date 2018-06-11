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
import io.reactivex.parallel.ParallelFlowable
import io.reactivex.parallel.ParallelTransformer
import org.reactivestreams.Publisher

/**
 * @author Manuel Wrage (IVIanuu)
 */
interface AutoDisposeTransformer<T> : CompletableTransformer,
        FlowableTransformer<T, T>, MaybeTransformer<T, T>,
        ObservableTransformer<T, T>, ParallelTransformer<T, T>,
        SingleTransformer<T, T>

class AutoDisposeTransformerImpl<T>(private val scope: Maybe<*>) :
    AutoDisposeTransformer<T> {
    override fun apply(upstream: Completable): CompletableSource =
        AutoDisposeCompletable(upstream, scope)

    override fun apply(upstream: Flowable<T>): Publisher<T> = AutoDisposeFlowable(upstream, scope)

    override fun apply(upstream: Maybe<T>): MaybeSource<T> = AutoDisposeMaybe(upstream, scope)

    override fun apply(upstream: Observable<T>): ObservableSource<T> =
        AutoDisposeObservable(upstream, scope)

    override fun apply(upstream: ParallelFlowable<T>): ParallelFlowable<T> =
        AutoDisposeParallelFlowable(upstream, scope)

    override fun apply(upstream: Single<T>): SingleSource<T> = AutoDisposeSingle(upstream, scope)
}