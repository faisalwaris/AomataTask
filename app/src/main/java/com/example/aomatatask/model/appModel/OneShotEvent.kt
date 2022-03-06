package com.example.aomatatask.model.appModel

class OneShotEvent<T>(private val data: T?) {
    var isDataUsed = false

    fun getData(): T? {
        return if (!isDataUsed) {
            isDataUsed = true;
            data
        } else
            null
    }
}