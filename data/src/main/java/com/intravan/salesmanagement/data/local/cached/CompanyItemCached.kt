package com.intravan.salesmanagement.data.local.cached

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyItemCached(
    @PrimaryKey
    @ColumnInfo("comcode")
    val comcode: String = "",
    @ColumnInfo("comname")
    val comname: String = "",
    @ColumnInfo("shopcode")
    val shopcode: String = "",
    @ColumnInfo("boss")
    val boss: String = "",
    @ColumnInfo("idno")
    val idno: String = "",
    @ColumnInfo("uptae")
    val uptae: String = "",
    @ColumnInfo("upjong")
    val upjong: String = "",
    @ColumnInfo("tel0")
    val tel0: String = "",
    @ColumnInfo("tel1")
    val tel1: String = "",
    @ColumnInfo("tel2")
    val tel2: String = "",
    @ColumnInfo("fax0")
    val fax0: String = "",
    @ColumnInfo("fax1")
    val fax1: String = "",
    @ColumnInfo("fax2")
    val fax2: String = "",
    @ColumnInfo("hp0")
    val hp0: String = "",
    @ColumnInfo("hp1")
    val hp1: String = "",
    @ColumnInfo("hp2")
    val hp2: String = "",
    @ColumnInfo("prgcode")
    val prgcode: String = "",
    @ColumnInfo("prgname")
    val prgname: String = "",
    @ColumnInfo("luse")
    val luse: String = "",
    @ColumnInfo("limitday")
    val limitday: String = "",
    @ColumnInfo("bizcomcode")
    val bizcomcode: String = "",
    @ColumnInfo("bizid")
    val bizid: String = "",
    @ColumnInfo("bizpassword")
    val bizpassword: String = "",
    @ColumnInfo("user_max")
    val userMax: String = "",
    @ColumnInfo("registday")
    val registday: String = "",
    @ColumnInfo("address1")
    val address1: String = "",
    @ColumnInfo("address2")
    val address2: String = "",
    @ColumnInfo("address")
    val address: String = "",
    @ColumnInfo("latitude")
    val latitude: String = "",
    @ColumnInfo("longitude")
    val longitude: String = ""
)