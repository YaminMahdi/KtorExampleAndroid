package com.ktor.exp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val token: String = "",
    val id: Int = 0,
    val isActive: Boolean = false,
    val name: String = "",
    val phone: String = "",
    val image: String = "",
    val email: String = "",
    val nid: String = "",
    val address: String = "",
    val type: String = "",
    val point: Double = 0.0,
    val vendorInfo: VendorInfo = VendorInfo(),
) : Parcelable {
    @Parcelize
    data class VendorInfo(
        val phone: String = "",
        val vendorEmail: String = "",
        val vendorId: Int = 0,
        val vendorLogo: String = "",
        val vendorName: String = "",
    ) : Parcelable
}