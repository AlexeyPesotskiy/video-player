package com.example.videoplayer.ui.videoList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoplayer.domain.model.VideoInfo
import com.example.videoplayer.domain.usecase.FetchVideoInfoListUseCase
import com.example.videoplayer.domain.usecase.GetCachedVideoInfoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val fetchVideoInfoListUseCase: FetchVideoInfoListUseCase,
    private val getCachedVideoInfoListUseCase: GetCachedVideoInfoListUseCase,
) : ViewModel() {

    private val _uiState: MutableStateFlow<VideoListUiState> =
        MutableStateFlow(
            VideoListUiState(
                videoInfoList = emptyList(),
                isLoading = false,
                isError = false
            )
        )
    var uiState = _uiState.asStateFlow()

    init {
        initVideoInfoList()
    }

    fun fetchVideoInfoList() = viewModelScope.launch(Dispatchers.IO) {
        showLoadingState()
        delay(20)
        try {
            showSuccessState(fetchVideoInfoListUseCase.execute())
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


    private fun initVideoInfoList() = viewModelScope.launch(Dispatchers.IO) {
        showLoadingState()
        delay(20)
        try {
            showSuccessState(fetchVideoInfoListUseCase.execute())
        } catch (e: Exception) {
            getCachedVideoInfoList()
        }
    }

    private suspend fun getCachedVideoInfoList() {
        try {
            val cachedVideoList = getCachedVideoInfoListUseCase.execute()
            if (cachedVideoList.isNotEmpty())
                showErrorWithCacheState(cachedVideoList)
            else
                showErrorState()
        } catch (e: Exception) {
            showErrorState()
        }
    }

    private fun showLoadingState() =
        _uiState.update {
            it.copy(
                isLoading = true,
            )
        }

    private fun showSuccessState(videoList: List<VideoInfo>) =
        _uiState.update {
            VideoListUiState(
                isLoading = false,
                isError = false,
                videoInfoList = videoList
            )
        }

    private fun showErrorWithCacheState(videoList: List<VideoInfo>) =
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
