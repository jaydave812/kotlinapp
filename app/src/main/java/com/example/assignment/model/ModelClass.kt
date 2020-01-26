package com.example.assignment.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ModelClass(
    @SerializedName("per_page")
    var perPage: Int,
    @SerializedName("total")
    var total: Int,
    @SerializedName("data")
    var data: List<User>?,
    @SerializedName("page")
    var page: Int,
    @SerializedName("total_pages")
    var totalPages: Int
)

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
    @SerializedName("id")
    var id: Int,

    @SerializedName("last_name")
    var lastName: String?,
    @SerializedName("avatar")
    var avatar: String?,
    @SerializedName("first_name")
    var firstName: String?,
    @SerializedName("email")
    var email: String?
)