package com.ktor.exp.data.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.ktor.exp.domain.model.UserBasicInfo

data class UserListDto(
    @JsonProperty("current_page")
    val currentPage: String = "",
    @JsonProperty("data")
    val data: List<Data> = listOf(),
    @JsonProperty("message")
    val message: String = "",
    @JsonProperty("number_of_pages")
    val numberOfPages: Int = 0,
    @JsonProperty("pagination")
    val pagination: List<Int> = listOf(),
    @JsonProperty("status")
    val status: String = "",
    @JsonProperty("total_row")
    val totalRow: Int = 0
) {
    data class Data(
        @JsonProperty("name")
         val name: String = "",
        @JsonProperty("phone")
        val phone: String = "",
        @JsonProperty("transfer_type")
        val transferType: String = "",
        @JsonProperty("type")
        val type: String = "",
        @JsonProperty("vendor_name")
        val vendorName: String = ""
    ){
        fun toUserBasicInfo() = UserBasicInfo(
            name = name, phone = phone, type = type
        )
    }
}