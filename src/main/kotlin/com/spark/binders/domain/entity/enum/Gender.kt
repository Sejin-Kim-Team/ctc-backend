package com.spark.binders.domain.entity.enum

enum class Gender {
    MALE, FEMALE, NONE;

    companion object {
        operator fun invoke(gender: String) = valueOf(gender.uppercase())
    }
}