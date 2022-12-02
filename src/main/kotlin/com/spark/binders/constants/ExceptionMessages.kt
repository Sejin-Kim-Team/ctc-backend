package com.spark.binders.constants

enum class ExceptionMessages(
    val message: String
) {
    NOT_EXIST_MEETING("존재하지 않는 모임입니다."),
    NOT_EXIST_GROUP("그룹이 존재하지 않습니다."),
    NOT_JOINED_GROUP("가입하지 않은 그룹입니다.")
}