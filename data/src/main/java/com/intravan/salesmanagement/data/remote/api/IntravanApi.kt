package com.intravan.salesmanagement.data.remote.api

import com.intravan.salesmanagement.data.remote.response.AuthNumberResponse
import com.intravan.salesmanagement.data.remote.response.CompanyResponse
import com.intravan.salesmanagement.data.remote.response.StartingAuthResponse
import retrofit2.http.*

// POST field.
private const val API_POST_FIELD = "params"


/**
 * Api.
 */
interface IntravanApi {

    // 인증번호 요청.
    @FormUrlEncoded
    @POST("intra/int_luse_c{extension}")
    suspend fun getAuthNumber(
        @Field(API_POST_FIELD) params: String,
        @Path("extension") extension: String = ".aspx"
    ): AuthNumberResponse

    // 인증 체크후 메인.
    @FormUrlEncoded
    @POST("intra/int_luse{extension}")
    suspend fun getStarting(
        @Field(API_POST_FIELD) params: String,
        @Path("extension") extension: String = ".aspx"
    ): StartingAuthResponse

    // 업체목록
    @FormUrlEncoded
    @POST("intra/int_shop{extension}")
    suspend fun getCompany(
        @Field(API_POST_FIELD) params: String,
        @Path("extension") extension: String = ".aspx"
    ): CompanyResponse
}