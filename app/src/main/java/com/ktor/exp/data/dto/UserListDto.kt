package com.ktor.exp.data.dto

import com.ktor.exp.domain.model.UserBasicInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserListDto(
    @SerialName("current_page")
    val currentPage: String = "",
    @SerialName("data")
    val data: List<Data>? = listOf(),
    @SerialName("message")
    val message: String = "",
    @SerialName("number_of_pages")
    val numberOfPages: Int = 0,
    @SerialName("pagination")
    val pagination: List<Int> = listOf(),
    @SerialName("status")
    val status: String = "",
    @SerialName("total_row")
    val totalRow: Int = 0
) {
    @Serializable
    data class Data(
        @SerialName("name")
        val name: String = "",
        @SerialName("phone")
        val phone: String = "",
        @SerialName("transfer_type")
        val transferType: String = "",
        @SerialName("type")
        val type: String = "",
        @SerialName("vendor_name")
        val vendorName: String = ""
    ){
        fun toUserBasicInfo() = UserBasicInfo(
            name = name, phone = phone, type = type
        )
    }
}