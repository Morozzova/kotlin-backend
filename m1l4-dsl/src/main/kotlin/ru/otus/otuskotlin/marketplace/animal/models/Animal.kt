package ru.otus.otuskotlin.marketplace.animal.models

data class Animal (
    val type: String = "",
    val name: String = "",

    val color: String = "",
    val fur: Boolean = false,

    val actions: Set<String> = emptySet()
)