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
import androidx.lifecycle.viewModelScope
import io.github.horaciocome1.busflow.data.GetPostsEvent
import io.github.horaciocome1.busflow.data.PostsRepository
import io.github.horaciocome1.busflow.util.getEventsAsFlow
import kotlinx.coroutines.launch

/**
 * Modern view model adopting the coroutine flow approach
 */
class CoroutineViewModel(
    private val repository: PostsRepository = PostsRepository.getInstance()
) : ViewModel() {

    fun retrievePosts() {
        viewModelScope.launch {
            getEventsAsFlow<GetPostsEvent> {
                repository.getPosts(listenToUpdates = true, updatesCount = 5)
            }.collect { event ->
                Log.d("CoroutineViewModel", "retrievePosts posts=${event.posts}")
            }
        }
    }
}
