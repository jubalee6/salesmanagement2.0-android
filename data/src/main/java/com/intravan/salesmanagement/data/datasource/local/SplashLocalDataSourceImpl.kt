package com.intravan.salesmanagement.data.datasource.local

import android.content.Context
import com.intravan.salesmanagement.domain.datasource.local.SplashLocalDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SplashLocalDataSourceImpl @Inject constructor(
    @ApplicationContext val context: Context
) : SplashLocalDataSource{
    override suspend fun beginScreen() {

        // 임시 디렉토리 삭제.
        context
            .getExternalFilesDir("temporary")
            ?.listFiles()
            ?.forEach {
                it?.delete()
            }
    }
}