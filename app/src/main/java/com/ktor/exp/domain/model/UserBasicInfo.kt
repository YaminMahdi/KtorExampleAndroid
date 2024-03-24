package com.ktor.exp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserBasicInfo(
    val name: String = "",
    val phone: String = "",
    val type: String = "",
) : Parcelable
