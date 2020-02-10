package com.lukaszkalnik.functionalcleanarchitecture.data.model

/**
 * Generic response type.
 *
 * Each [Entity] may contain nested [entities].
 */
internal data class Entity(
    val properties: Map<String, String>,
    val actions: List<Action>?,
    val entities: List<Entity>?
)

/**
 * Actions which can be called on current [Entity].
 *
 * From given properties you can construct REST API calls which will execute the action.
 */
data class Action(
    val name: String,
    val href: String,
    val method: String,
    val fields: List<String>
)
