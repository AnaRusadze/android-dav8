package com.example.myapplication.user_list.module

import com.google.gson.annotations.SerializedName

data class User(
    val id: Long?,
    var email: String?,
    @SerializedName("first_name")
    var firstName: String?,
    @SerializedName("last_name")
    var lastName: String?,
    var avatar: String?

)

data class ReqResData<T>(
    val page: Int?,
    val total: Int?,
    val data: T
)