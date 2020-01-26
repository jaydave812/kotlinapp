package com.example.assignment.model

import com.google.gson.annotations.SerializedName

data class DataItem(
    @SerializedName("last_name")
    var lastName: String?,
    @SerializedName("id")
    var id: Int,
    @SerializedName("avatar")
    var avatar: String?,
    @SerializedName("first_name")
    var firstName: String?,
    @SerializedName("email")
    var email: String?
) {

    override fun toString(): String {
        return "DataItem{" +
            "last_name = '" + lastName + '\'' +
            ",id = '" + id + '\'' +
            ",avatar = '" + avatar + '\'' +
            ",first_name = '" + firstName + '\'' +
            ",email = '" + email + '\'' +
            "}"
    }
}