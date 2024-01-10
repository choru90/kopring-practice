package com.group.libraryapp.dto.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus

data class UserHistoryResponse(
    val name: String,
    val books: List<BookHistoryResponse>

){
    companion object{
        fun of(user : User) : UserHistoryResponse{
            return UserHistoryResponse(
                name = user.name,
                books = user.userLoanHistories.map(BookHistoryResponse::of)
            )
        }
    }
}


data class BookHistoryResponse(
    val name: String,
    val isReturn: Boolean,
) {
    companion object {
        fun of(history : UserLoanHistory) : BookHistoryResponse{
            return BookHistoryResponse(
                name = history.bookName,
                isReturn = history.isReuturn
            )
        }
    }
}