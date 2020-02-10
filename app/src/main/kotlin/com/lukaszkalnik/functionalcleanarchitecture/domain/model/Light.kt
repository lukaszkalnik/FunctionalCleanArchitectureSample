package com.lukaszkalnik.functionalcleanarchitecture.domain.model

import com.lukaszkalnik.functionalcleanarchitecture.data.model.Action

data class Light(
    val id: String,
    val name: String,
    val actions: List<Action>
)