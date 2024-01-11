package com.group.libraryapp.controller.book

import com.group.libraryapp.dto.book.BookLoanRequest
import com.group.libraryapp.dto.book.BookRequest
import com.group.libraryapp.dto.book.BookReturnRequest
import com.group.libraryapp.dto.book.BookStatResponse
import com.group.libraryapp.service.book.BookService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController(
    private val service : BookService,
) {

    @PostMapping("/book")
    fun saveBook(@RequestBody request : BookRequest){
        service.saveBook(request)
    }

    @PostMapping("/book/loan")
    fun loanBook(@RequestBody request: BookLoanRequest){
        service.loanBook(request)
    }

    @PutMapping("/book/return")
    fun returnBook(@RequestBody request: BookReturnRequest){
        service.returnBook(request)
    }

    @GetMapping("/book/loan")
    fun countLoanedBook(): Int {
        return service.countLoanBook()
    }

    @GetMapping("/book/stat")
    fun getBooksStatistics() : List<BookStatResponse>{
        return service.getBooksStatistics()
    }
}