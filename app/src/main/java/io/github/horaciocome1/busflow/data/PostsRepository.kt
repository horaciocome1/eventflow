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

package io.github.horaciocome1.busflow.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

/**
 * Repository designed to use the EventBus implementation approach
 */
class PostsRepository private constructor() {

    companion object {

        @Volatile
        private var instance: PostsRepository? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: PostsRepository().also { instance = it }
        }
    }

    private fun getPosts(prefix: String = "") {
        val randomPosts = listOf(
            "$prefix Cold Jacuzzi",
            "$prefix Summer in East Africa",
            "$prefix Visit Vilankulos",
        )
        val event = GetPostsEvent(randomPosts)
        EventBus.getDefault().post(event)
    }

    fun getPosts(listenToUpdates: Boolean, updatesCount: Int = 10) {
        CoroutineScope(Dispatchers.IO).launch {
            if (!listenToUpdates) {
                getPosts()
                return@launch
            }
            (updatesCount downTo 0).forEach { index ->
                delay(5000L)
                getPosts(prefix = index.toString())
            }
        }
    }
}
