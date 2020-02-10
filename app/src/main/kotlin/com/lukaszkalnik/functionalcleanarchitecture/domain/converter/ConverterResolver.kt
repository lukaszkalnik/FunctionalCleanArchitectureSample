package com.lukaszkalnik.functionalcleanarchitecture.domain.converter

import com.lukaszkalnik.functionalcleanarchitecture.data.model.Entity

internal typealias EntityConverter<T> = (Entity) -> T

internal enum class ConverterTag {
    LIGHT_LIST,
    SYSTEM_DETAIL
    // TODO add other types which will have converters
}

/**
 * Resolves [EntityConverter] for the given [tag] which should convert
 * to the expected result domain type [T].
 */
@Suppress("UNCHECKED_CAST")
internal fun <T> resolveConverter(tag: ConverterTag): EntityConverter<T> =
    converters[tag] as? EntityConverter<T>?
        ?: throw IllegalArgumentException("No converter registered for $tag")

/**
 * Mapping of [ConverterTag] to [EntityConverter].
 *
 * When you create a new converter it always has to be registered here.
 * Otherwise [resolveConverter] will not be able to resolve it and your app will crash.
 */
private val converters: Map<ConverterTag, EntityConverter<*>> = mapOf(
    ConverterTag.LIGHT_LIST to convertToLightList
    // TODO add other converters here once they are created
)