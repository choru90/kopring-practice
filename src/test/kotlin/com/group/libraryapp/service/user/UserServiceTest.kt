package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.service.user.UserService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
     private val userRepository: UserRepository,
     private val userLoanHistoryRepository: UserLoanHistoryRepository,
     private val userService: UserService
) {

    @AfterEach
    fun clean(){
        userRepository.deleteAll()
    }

    @Test
    fun saveUserTest(){
        // given
        val request = UserCreateRequest("홍길동", null)
        // when
        userService.saveUser(request)
        // then

        val result = userRepository.findAll()
        assertThat(result).hasSize(1)
        assertThat(result[0].name).isEqualTo("홍길동")
        assertThat(result[0].age).isNull()
    }

    @Test
    fun getUsers(){
        // given
        userRepository.saveAll(listOf(
                User("A", 20),
                User("B", null)))
        // when
        val results = userService.getUsers()
        // then
        assertThat(results).hasSize(2)
        assertThat(results).extracting("name").containsExactlyInAnyOrder("A","B")
        assertThat(results).extracting("age").containsExactlyInAnyOrder(20, null)

    }

    @Test
    fun updateUserNameTest(){
        //given
        val saved = userRepository.save(User("A",null))
        val request = UserUpdateRequest(saved.id!!, "B")
        // when
        userService.updateUserName(request)
        // then
        val result = userRepository.findAll()[0]
        assertThat(result.name).isEqualTo("B")
    }

    @Test
    fun deleteUserTest(){
        // given
        userRepository.save(User("A", null))
        // when
        userService.deleteUser("A")
        // then
        assertThat(userRepository.findAll()).isEmpty()
    }

    @Test
    fun getUserLoanHistoriesTest(){
        // given
        userRepository.save(User("A",null))
        // when
        val results = userService.getUserLoanHistories()
        // then
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("A")
        assertThat(results[0].books).isEmpty()
    }

    @Test
    fun getUserLoanHistoriesTest2(){
        // given
        val savedUser = userRepository.save(User("A", null))
        userLoanHistoryRepository.saveAll(listOf(
         UserLoanHistory.fixture(savedUser, "책1", UserLoanStatus.LOANED),
         UserLoanHistory.fixture(savedUser, "책2", UserLoanStatus.LOANED),
         UserLoanHistory.fixture(savedUser, "책3", UserLoanStatus.RETURNED)
        ))

        // when
        val results = userService.getUserLoanHistories()
        // then
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("A")
        assertThat(results[0].books).hasSize(3)
        assertThat(results[0].books).extracting("name")
            .containsExactlyInAnyOrder("책1", "책2","책3")
        assertThat(results[0].books).extracting("isReturn")
            .containsExactlyInAnyOrder(false, false, true)
    }


}