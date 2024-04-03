package com.intravan.salesmanagement.domain.model

data class Company(
    val items: List<Item> = emptyList()
) {
    data class Item(
        val comcode: String = "",
        val comname: String = "",
        val shopcode: String = "",
        val boss: String = "",
        val idno: String = "",
        val uptae: String = "",
        val upjong: String = "",
        val tel0: String = "",
        val tel1: String = "",
        val tel2: String = "",
        val fax0: String = "",
        val fax1: String = "",
        val fax2: String = "",
        val hp0: String = "",
        val hp1: String = "",
        val hp2: String = "",
        val prgcode: String = "",
        val prgname: String = "",
        val luse: String = "",
        val limitday: String = "",
        val bizcomcode: String = "",
        val bizid: String = "",
        val bizpassword: String = "",
        val userMax: String = "",
        val registday: String = "",
        val address1: String = "",
        val address2: String = "",
        val address: String = "",
        val latitude: String = "",
        val longitude: String = ""
    )
}