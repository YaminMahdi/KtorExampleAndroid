package com.ktor.exp.data.dto


import com.fasterxml.jackson.annotation.JsonProperty
import com.ktor.exp.domain.model.User

data class LoginDto(
    @JsonProperty("data")
    val data: Data = Data(),
    @JsonProperty("message")
    val message: String = "",
    @JsonProperty("status")
    val status: String = ""
) {
    data class Data(
        @JsonProperty("token")
        val token: Token = Token(),
        @JsonProperty("user_info")
        val userInfo: UserInfo = UserInfo()
    ) {
        data class Token(
            @JsonProperty("access_token")
            val accessToken: String = "",
            @JsonProperty("expires_in")
            val expiresIn: Int = 0,
            @JsonProperty("token_type")
            val tokenType: String = ""
        )

        data class UserInfo(
            @JsonProperty("address")
            val address: String = "",
            @JsonProperty("email")
            val email: String = "",
            @JsonProperty("id")
            val id: Int = 0,
            @JsonProperty("image")
            val image: String = "",
            @JsonProperty("is_active")
            val isActive: Boolean = false,
            @JsonProperty("name")
            val name: String = "",
            @JsonProperty("nid")
            val nid: String = "",
            @JsonProperty("phone")
            val phone: String = "",
            @JsonProperty("point")
            val point: Double = 0.0,
            @JsonProperty("type")
            val type: String = "",
            @JsonProperty("vendor_info")
            val vendorInfo: VendorInfo = VendorInfo()
        ) {
            data class VendorInfo(
                @JsonProperty("phone")
                val phone: String = "",
                @JsonProperty("vendor_email")
                val vendorEmail: String = "",
                @JsonProperty("vendor_id")
                val vendorId: Int = 0,
                @JsonProperty("vendor_logo")
                val vendorLogo: String = "",
                @JsonProperty("vendor_name")
                val vendorName: String = ""
            ){
                fun toVendorInfo() =
                    User.VendorInfo(
                        phone = phone,
                        vendorEmail = vendorEmail,
                        vendorId = vendorId,
                        vendorLogo = vendorLogo,
                        vendorName = vendorName
                    )
            }

        }
        fun toUser() : User = User(
            token = "Bearer ${token.accessToken}",
            id =  userInfo.id,
            isActive = userInfo.isActive,
            name = userInfo.name,
            phone = userInfo.phone,
            image = userInfo.image,
            email = userInfo.email,
            type = userInfo.type,
            point = userInfo.point,
            vendorInfo = userInfo.vendorInfo.toVendorInfo()
        )
    }
}