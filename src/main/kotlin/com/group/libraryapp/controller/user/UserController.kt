package com.group.libraryapp.controller.user

import com.group.libraryapp.dto.user.UserHistoryResponse
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.dto.user.response.UserResponse
import com.group.libraryapp.service.user.UserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController (
    private val service: UserService,
) {

    @PostMapping("/user")
    fun saveUser(@RequestBody request: UserCreateRequest){
        service.saveUser(request);
    }

    @GetMapping("/user")
    fun getUser() : List<UserResponse>{
        return service.getUsers();
    }

    @PutMapping("/user")
    fun updateUserName(@RequestBody request: UserUpdateRequest){
        service.updateUserName(request);
    }

    @DeleteMapping("/user")
    fun deleteUser(@RequestParam name: String){
        service.deleteUser(name);
    }

    @GetMapping("/user/loan")
    fun getUserLoanHistories(): List<UserHistoryResponse>{
        return service.getUserLoanHistories();
    }
}