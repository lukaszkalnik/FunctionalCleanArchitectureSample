package com.lukaszkalnik.functionalcleanarchitecture.domain.converter

import com.lukaszkalnik.functionalcleanarchitecture.domain.model.Light

/**
 * This is actually a value of a functional type [EntityConverter]
 * so we define it as a lambda which takes an [Entity] as argument
 * and returns [Light]
 */
internal val convertToLight: EntityConverter<Light> = { entity ->
    with(entity) {
        Light(
            id = properties["id"] ?: throw IllegalStateException("Light id missing in $entity"),
            name = properties["name"] ?: "",
            actions = actions ?: throw IllegalStateException("Light actions missing in $entity")
        )
    }
}

internal val convertToLightList: EntityConverter<List<Light>> = { entity ->
    with(entity) {
        entities.orEmpty().map { convertToLight(it) }
    }
}
