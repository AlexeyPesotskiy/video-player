package com.example.videoplayer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoplayer.domain.usecase.GetVideoInfoListUseCase
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
    private val getVideoInfoListUseCase: GetVideoInfoListUseCase,
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
        getVideoInfoList()
    }

    fun getVideoInfoList() = viewModelScope.launch(Dispatchers.IO) {
        _uiState.update {
            it.copy(
                isLoading = true,
            )
        }
        delay(20)
        try {
            _uiState.update {
                VideoListUiState(
                    isLoading = false,
                    isError = false,
                    videoInfoList = getVideoInfoListUseCase.execute()
                )
            }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(
                    isLoading = false,
                    isError = true,
                )
            }
        }
    }
}