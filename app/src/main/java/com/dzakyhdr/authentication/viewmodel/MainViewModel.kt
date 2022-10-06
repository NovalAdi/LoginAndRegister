package com.dzakyhdr.authentication.viewmodel

import androidx.lifecycle.*
import com.dzakyhdr.authentication.UserDataStoreManager
import com.dzakyhdr.authentication.data.UserEntity
import com.dzakyhdr.authentication.data.UserRepository
import com.dzakyhdr.authentication.utils.Resource
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: UserRepository,
    private val pref: UserDataStoreManager
) : ViewModel() {

    private var _userData = MutableLiveData<Resource<UserEntity>>()
    val userData: LiveData<Resource<UserEntity>> get() = _userData

    fun userData(id: Int) {
        viewModelScope.launch {
            try {
                val data = repository.getUser(id)
                _userData.postValue(Resource.success(data, 0))
            } catch (exception: Exception) {
                _userData.postValue(Resource.error(null, exception.message!!))
            }
        }
    }

    fun getIdUser(): LiveData<Int> {
        return pref.getId().asLiveData()
    }

    fun clearDatauser() {
        viewModelScope.launch {
            pref.logOutUser()
        }
    }

}