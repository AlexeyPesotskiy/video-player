package com.example.videoplayer.ui.videoList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoplayer.domain.model.VideoInfo
import com.example.videoplayer.domain.usecase.FetchVideoInfoFlowUseCase
import com.example.videoplayer.domain.usecase.GetCachedVideoInfoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.LinkedList
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val fetchVideoInfoFlowUseCase: FetchVideoInfoFlowUseCase,
    private val getCachedVideoInfoListUseCase: GetCachedVideoInfoListUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<VideoListUiState> =
        MutableStateFlow(
            VideoListUiState(
                videoInfoList = LinkedList(),
                isLoading = false,
                isError = false
            )
        )
    var uiState = _uiState.asStateFlow()
    private var fetchJob: Job? = null

    init {
        initVideoInfoList()
    }

    private fun initVideoInfoList() {
        fetchJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                collectVideoInfoFlow()
            } catch (e: Exception) {
                getCachedVideoInfoList()
            }
        }
    }

    fun fetchVideoInfoList() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                collectVideoInfoFlow()
            } catch (e: Exception) {
                showErrorState()
            }
        }
    }


    private suspend fun collectVideoInfoFlow() {
        showLoadingState()
        delay(20)
        try {
            fetchVideoInfoFlowUseCase.execute().collect { videoInfo ->
                val currentList = uiState.value.videoInfoList
                val newList = currentList.apply {
                    val index = indexOfFirst { it.videoUrl == videoInfo.videoUrl }
                    if (index >= 0) {
                        set(index, videoInfo)
                    } else {
                        add(videoInfo)
                    }
                }
                showSuccessState(
                    newList
                )
            }
        } catch (_: CancellationException) {}
    }

    private suspend fun getCachedVideoInfoList() {
        try {
            val cachedVideoList = getCachedVideoInfoListUseCase.execute()
            if (cachedVideoList.isNotEmpty())
                showErrorWithCacheState(LinkedList(cachedVideoList))
            else
                showErrorState()
        } catch (e: Exception) {
            showErrorState()
        }
    }

    fun clearError() =
        _uiState.update {
            it.copy(
                isError = false,
            )
        }

    private fun showLoadingState() =
        _uiState.update {
            it.copy(
                isLoading = true,
            )
        }

    private fun showSuccessState(videoList: LinkedList<VideoInfo>) =
        _uiState.update {
            VideoListUiState(
                isLoading = false,
                isError = false,
                videoInfoList = videoList
            )
        }

    private fun showErrorWithCacheState(videoList: LinkedList<VideoInfo>) =
        _uiState.update {
            it.copy(
                isLoading = false,
                isError = true,
                videoInfoList = videoList
            )
        }

    private fun showErrorState() =
        _uiState.update {
            it.copy(
                isLoading = false,
                isError = true,
            )
        }
}
