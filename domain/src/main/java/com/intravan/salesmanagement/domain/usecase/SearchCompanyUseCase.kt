package com.intravan.salesmanagement.domain.usecase

import com.intravan.salesmanagement.core.extension.resultOf
import com.intravan.salesmanagement.core.extension.toFailedThrowable
import com.intravan.salesmanagement.domain.model.Company
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

// 업체 목록 검색.
class SearchCompanyUseCase @Inject constructor() {

    fun execute(domainModel: Company, searchText: String): Flow<Result<Company>> = flow {

        //
        domainModel.items.filter {
            it.comname.contains(searchText)
        }.run {
            emit(
                resultOf {
                    domainModel.copy(
                        searchText = searchText,
                        searchedItems = this
                    )
                }
            )
        }

       /* // 검색 결과를 저장할 items 리스트 초기화
        val searchedItems = mutableListOf<Company.Item>()

        // domainModel의 items를 반복시켜 searchText와 일치하는지 확인(contains 사용하기)
        domainModel.items.forEach { item ->
            if (item.comname.contains(searchText)) {
                searchedItems.add(item)
            }
        }

        // 일치하는 결과가 있는 경우 결과를 저장(?여쭤보기)
        emit(
            resultOf {
                domainModel.copy(
                    searchText = searchText,
                    searchedItems = searchedItems
                )
            }
        )*/
    }.catch {
        emit(Result.failure(it.toFailedThrowable()))
    }
}