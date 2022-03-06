package com.example.aomatatask.util

data class BasicAlertDialogModel(
    var imgIcon: Int? = null,
    var title: String,
    var description: String? = null,
    var negativeButtonTitle: String? = null,
    var positiveButtonTitle: String? = null
)