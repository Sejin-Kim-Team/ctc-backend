package com.spark.binders.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    @GetMapping("/login-success")
    fun kakaoLogin() :String {
        //TODO Hello 대신 API 호출을 위한 Jwt Token 리턴
        return "hello!"
    }
}