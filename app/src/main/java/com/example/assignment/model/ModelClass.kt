package com.example.assignment.model

import com.google.gson.annotations.SerializedName

data class ModelClass(
    @SerializedName("per_page")
    var perPage: Int,
    @SerializedName("total")
    var total: Int,
    @SerializedName("data")
    var data: List<DataItem>?,
    @SerializedName("page")
    var page: Int,
    @SerializedName("total_pages")
    var totalPages: Int
) {
    override fun toString(): String {
        return "ModelClass{" +
            "per_page = '" + perPage + '\'' +
            ",total = '" + total + '\'' +
            ",data = '" + data + '\'' +
            ",page = '" + page + '\'' +
            ",total_pages = '" + totalPages + '\'' +
            "}"
    }
}