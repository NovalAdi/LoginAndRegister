package com.dzakyhdr.authentication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dzakyhdr.authentication.data.UserEntity
import com.dzakyhdr.authentication.data.UserRepository
import com.dzakyhdr.authentication.utils.Event
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {

    private val _saved = MutableLiveData<Event<Boolean>>()
    val saved: LiveData<Event<Boolean>> get() = _saved

    fun insert(user: UserEntity) {
        if (user.username.isEmpty() || user.password.isEmpty()) {
            _saved.value = Event(false)
        } else {
            viewModelScope.launch {
                repository.insert(user)
            }
            _saved.value = Event(true)
        }
    }
}