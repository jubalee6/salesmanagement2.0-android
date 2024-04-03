package com.intravan.salesmanagement.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyResponse (
    @SerialName("result")
    val isResult: Boolean= false,
    @SerialName("message")
    val message: String ="",
    @SerialName("dataset")
    val items: List<Item> = emptyList()
){
    @Serializable
    data class Item(
        @SerialName("comcode")
        val comcode: String = "",
        @SerialName("comname")
        val comname: String = "",
        @SerialName("shopcode")
        val shopcode: String ="",
        @SerialName("boss")
        val boss: String = "",
        @SerialName("idno")
        val idno: String = "",
        @SerialName("uptae")
        val uptae: String = "",
        @SerialName("upjong")
        val upjong: String = "",
        @SerialName("tel0")
        val tel0: String = "",
        @SerialName("tel1")
        val tel1: String ="",
        @SerialName("tel2")
        val tel2: String = "",
        @SerialName("fax0")
        val fax0: String = "",
        @SerialName("fax1")
        val fax1: String = "",
        @SerialName("fax2")
        val fax2: String = "",
        @SerialName("hp0")
        val hp0: String = "",
        @SerialName("hp1")
        val hp1: String ="",
        @SerialName("hp2")
        val hp2: String = "",
        @SerialName("prgcode")
        val prgcode: String = "",
        @SerialName("prgname")
        val prgname: String = "",
        @SerialName("luse")
        val luse: String = "",
        @SerialName("limitday")
        val limitday: String = "",
        @SerialName("bizcomcode")
        val bizcomcode: String ="",
        @SerialName("bizid")
        val bizid: String = "",
        @SerialName("bizpassword")
        val bizpassword: String = "",
        @SerialName("user_max")
        val userMax: String = "",
        @SerialName("registday")
        val registday: String = "",
        @SerialName("address1")
        val address1: String = "",
        @SerialName("address2")
        val address2: String ="",
        @SerialName("address")
        val address: String = "",
        @SerialName("latitude")
        val latitude: String = "",
        @SerialName("longitude")
        val longitude: String = ""
    )
}