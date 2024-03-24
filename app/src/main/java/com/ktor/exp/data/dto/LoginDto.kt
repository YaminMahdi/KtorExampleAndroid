package com.ktor.exp.dto


import com.ktor.exp.domain.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginDto(
    @SerialName("data")
    val `data`: Data? = null,
    @SerialName("message")
    val message: String = "",
    @SerialName("status")
    val status: String = ""
) {
    @Serializable
    data class Data(
        @SerialName("token")
        val token: Token = Token(),
        @SerialName("user_info")
        val userInfo: UserInfo = UserInfo()
    ) {
        @Serializable
        data class Token(
            @SerialName("access_token")
            val accessToken: String = "",
            @SerialName("expires_in")
            val expiresIn: Int = 0,
            @SerialName("token_type")
            val tokenType: String = ""
        )

        @Serializable
        data class UserInfo(
            @SerialName("address")
            val address: String = "",
            @SerialName("email")
            val email: String = "",
            @SerialName("id")
            val id: Int = 0,
            @SerialName("image")
            val image: String = "",
            @SerialName("is_active")
            val isActive: Boolean = false,
            @SerialName("name")
            val name: String = "",
            @SerialName("nid")
            val nid: String = "",
            @SerialName("phone")
            val phone: String = "",
            @SerialName("point")
            val point: Double = 0.0,
            @SerialName("type")
            val type: String = "",
            @SerialName("vendor_info")
            val vendorInfo: VendorInfo = VendorInfo()
        ) {
            @Serializable
            data class VendorInfo(
                @SerialName("phone")
                val phone: String = "",
                @SerialName("vendor_email")
                val vendorEmail: String = "",
                @SerialName("vendor_id")
                val vendorId: Int = 0,
                @SerialName("vendor_logo")
                val vendorLogo: String = "",
                @SerialName("vendor_name")
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