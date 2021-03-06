package com.example.myapplication.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.common.Resource
import com.example.myapplication.domain.model.EntityPictureInfo
import com.example.myapplication.domain.model.ProfileInfo
import com.example.myapplication.domain.model.ProfileRequestBody
import com.example.myapplication.domain.model.toEntityPictureInfo
import com.example.myapplication.domain.usecase.MainUseCases
import com.example.myapplication.presentation.home.main_screen.PictureInfoListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainUseCases: MainUseCases
) : ViewModel() {

    private val _state = mutableStateOf(PictureInfoListState())
    val state: State<PictureInfoListState> = _state

    private val _localState = mutableStateOf(listOf<EntityPictureInfo>())
    val localState: State<List<EntityPictureInfo>> = _localState

    fun getPictureInfo(token: String) {
        mainUseCases.getPictureInfo(token).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = PictureInfoListState(value = result.data ?: emptyList())
                    viewModelScope.launch(Dispatchers.IO) {
                        mainUseCases.deleteAllMenuItems()
                        result.data?.let {
                            mainUseCases.insertPicturesInfo(it.map { it.toEntityPictureInfo() })
                        }
                    }
                }
                is Resource.Error ->
                    _state.value = PictureInfoListState(error = "internet error")
                is Resource.Loading ->
                    _state.value = PictureInfoListState(isLoading = true)
            }
        }.launchIn(viewModelScope)
    }

    fun getProfileInfo(profileRequestBody: ProfileRequestBody): Resource<ProfileInfo> =
        runBlocking {
            mainUseCases.getProfileInfo(profileRequestBody)
        }

    fun postAuthLogout(token: String): Boolean =
        runBlocking {
            mainUseCases.postAuthLogout(token)
        }


    fun getLocalPictureInfo() {
        mainUseCases.getLocalPictureInfo().onEach { result ->
            _localState.value = result
        }
    }

    fun getLocalProfileInfo(): ProfileInfo? =
        runBlocking(Dispatchers.IO) {
            mainUseCases.getLocalProfileInfo()
        }


    fun insertProfileInfo(profileInfo: ProfileInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            mainUseCases.insertProfileInfo(profileInfo)
        }
    }

    fun deleteProfileInfo() {
        runBlocking(Dispatchers.IO) {
            mainUseCases.deleteProfileInfo()
        }
    }
}