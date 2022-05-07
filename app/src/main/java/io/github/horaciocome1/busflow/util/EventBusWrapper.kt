/*
 * Copyright 2022 Horácio Flávio Comé Júnior
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.horaciocome1.busflow.util

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class EventBusWrapper<T> {

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleEvent(event: T) {
        onEvent(event)
    }

    abstract fun onEvent(event: T)
}

fun <T> getEventsAsFlow(block: () -> Unit) = callbackFlow {
    val wrapper = object : EventBusWrapper<T>() {

        override fun onEvent(event: T) {
            trySend(event)
        }
    }
    EventBus.getDefault().register(wrapper)
    block()
    awaitClose {
        EventBus.getDefault().unregister(wrapper)
    }
}
