package com.intravan.salesmanagement.mapper

import com.intravan.salesmanagement.databinding.FragmentCompanyBinding
import com.intravan.salesmanagement.domain.model.Company
import com.intravan.salesmanagement.presentation.model.CompanyDisplayable
import com.intravan.salesmanagement.presentation.ui.company.CompanyAdapter

fun FragmentCompanyBinding.toPresentationModel() = CompanyDisplayable(
    items = (recyclerview.adapter as CompanyAdapter).currentList
)

fun CompanyDisplayable.toDomainModel() = Company(
    items = items.map {
        it.toDomainModel()
    }
)

fun CompanyDisplayable.Item.toDomainModel() = Company.Item(
    comcode = comcode,
    comname = comname,
    shopcode = shopcode,
    boss = boss,
    idno = idno,
    uptae = uptae,
    upjong = upjong,
    tel0 = tel0,
    tel1 = tel1,
    tel2 = tel2,
    fax0 = fax0,
    fax1 = fax1,
    fax2 = fax2,
    hp0 = hp0,
    hp1 = hp1,
    hp2 = hp2,
    prgcode = prgcode,
    prgname = prgname,
    luse = luse,
    limitday = limitday,
    bizcomcode = bizcomcode,
    bizid = bizid,
    bizpassword = bizpassword,
    userMax = userMax,
    registday = registday,
    address1 = address1,
    address2 = address2,
    address = address,
    latitude = latitude,
    longitude = longitude
)

fun Company.toPresentationModel() = CompanyDisplayable(
    items = items.map {
        it.toPresentationModel()
    }
)

fun Company.Item.toPresentationModel() = CompanyDisplayable.Item(
    comcode = comcode,
    comname = comname,
    shopcode = shopcode,
    boss = boss,
    idno = idno,
    uptae = uptae,
    upjong = upjong,
    tel0 = tel0,
    tel1 = tel1,
    tel2 = tel2,
    fax0 = fax0,
    fax1 = fax1,
    fax2 = fax2,
    hp0 = hp0,
    hp1 = hp1,
    hp2 = hp2,
    prgcode = prgcode,
    prgname = prgname,
    luse = luse,
    limitday = limitday,
    bizcomcode = bizcomcode,
    bizid = bizid,
    bizpassword = bizpassword,
    userMax = userMax,
    registday = registday,
    address1 = address1,
    address2 = address2,
    address = address,
    latitude = latitude,
    longitude = longitude
)