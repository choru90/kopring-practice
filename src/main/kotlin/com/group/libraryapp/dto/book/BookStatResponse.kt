package com.group.libraryapp.dto.book

import com.group.libraryapp.domain.book.BookType

data class BookStatResponse(
    val type: BookType,
    var count: Int
) {
    fun plusOne() {
        count++
    }
}
