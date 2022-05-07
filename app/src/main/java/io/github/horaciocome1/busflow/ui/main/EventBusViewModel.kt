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

package io.github.horaciocome1.busflow.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import io.github.horaciocome1.busflow.data.GetPostsEvent
import io.github.horaciocome1.busflow.data.PostsRepository
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Classic view model adopting the EventBusApproach
 */
class EventBusViewModel(
    private val repository: PostsRepository = PostsRepository.getInstance()
) : ViewModel() {

    init {
        EventBus.getDefault().register(this)
    }

    override fun onCleared() {
        super.onCleared()
        EventBus.getDefault().unregister(this)
    }

    fun retrievePosts() {
        repository.getPosts(listenToUpdates = true, updatesCount = 5)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGetPostsEvent(event: GetPostsEvent) {
        Log.d("EventBusViewModel", "onGetPostsEvent posts=${event.posts}")
    }
}
