package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.book.BookType
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.BookLoanRequest
import com.group.libraryapp.dto.BookRequest
import com.group.libraryapp.dto.BookReturnRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookServiceTest @Autowired constructor(
        private val bookService: BookService,
        private val bookRepository: BookRepository,
        private val userRepository: UserRepository,
        private val userLoanHistoryRepository: UserLoanHistoryRepository
        ){

    @AfterEach
    fun clean(){
        bookRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    fun saveBookTest(){
        // given
        val request = BookRequest("이상한 나라의 엘리스", BookType.COMPUTER)
        // when
        bookService.saveBook(request)
        // then
        val book = bookRepository.findAll()
        assertThat(book).hasSize(1)
        assertThat(book[0].name).isEqualTo("이상한 나라의 엘리스")
    }

    @Test
    fun loanBookTest(){
        // given
        bookRepository.save(Book.fixture("이상한 나라의 엘리스"))
        val savedUser = userRepository.save(User("정대만", 20))
        val request = BookLoanRequest("정대만","이상한 나라의 엘리스")
        // when
        bookService.loanBook(request)
        // then
        val result = userLoanHistoryRepository.findAll();
        assertThat(result).hasSize(1)
        assertThat(result[0].bookName).isEqualTo("이상한 나라의 엘리스")
        assertThat(result[0].user.id).isEqualTo(savedUser.id)
        assertThat(result[0].status).isEqualTo(UserLoanStatus.LOANED)
    }

    @Test
    fun loanBookFailTest(){
        // given
        bookRepository.save(Book.fixture("이상한 나라의 엘리스"))
        val savedUser = userRepository.save(User("정대만", 20))
        userLoanHistoryRepository.save(UserLoanHistory.fixture(savedUser,"이상한 나라의 엘리스"))
        val request = BookLoanRequest("정대만","이상한 나라의 엘리스")
        // when

        val message = assertThrows<IllegalArgumentException> {
            bookService.loanBook(request)
        }.message
        assertThat(message).isEqualTo("진작 대출되어 있는 책입니다")
    }

    @Test
    fun returnBookTest(){
        // given
        bookRepository.save(Book.fixture("이상한 나라의 엘리스"))
        val savedUser = userRepository.save(User("정대만", 20))
        userLoanHistoryRepository.save(UserLoanHistory(savedUser,"이상한 나라의 엘리스", ))
        val request = BookReturnRequest("정대만", "이상한 나라의 엘리스")

        // when
        bookService.returnBook(request)
        // then
        val result = userLoanHistoryRepository.findAll()
        assertThat(result).hasSize(1)
        assertThat(result[0].status).isEqualTo(UserLoanStatus.RETURNED)
    }


}