package com.dzakyhdr.authentication.utils

open class Event<out T>(private val content: T) {

    var eventHandled = false
        private set

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (eventHandled) {
            null
        } else {
            eventHandled = true
            content
        }
    }

}