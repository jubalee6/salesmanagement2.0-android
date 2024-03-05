package com.intravan.salesmanagement.data.remote.api

import com.intravan.salesmanagement.data.remote.response.AuthNumberResponse
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
}