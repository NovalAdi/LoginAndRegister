package com.dzakyhdr.authentication.viewmodel

import androidx.lifecycle.*
import com.dzakyhdr.authentication.UserDataStoreManager
import com.dzakyhdr.authentication.data.UserEntity
import com.dzakyhdr.authentication.data.UserRepository
import com.dzakyhdr.authentication.utils.Resource
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UserRepository,
    private val pref: UserDataStoreManager
) : ViewModel() {

    private var _loginStatus = MutableLiveData<Resource<UserEntity>>()
    val loginStatus: LiveData<Resource<UserEntity>> get() = _loginStatus

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginStatus.postValue(Resource.loading(null))
            try {
                val data = repository.readLogin(email, password)
                _loginStatus.postValue(Resource.success(data, 0))
            } catch (exception: Exception) {
                _loginStatus.postValue(Resource.error(null, exception.message!!))
            }
        }
    }

    fun saveUserDataStore(status: Boolean, id: Int) {
        viewModelScope.launch {
            pref.saveUser(status, id)
        }
    }

    fun getStatus(): LiveData<Boolean> {
        return pref.getStatus().asLiveData()
    }


}