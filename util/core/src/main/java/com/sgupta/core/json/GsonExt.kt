package com.sgupta.core.json

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.sgupta.core.EMPTY_STRING

inline fun <reified T> Gson.fromJson(json: String): T = fromJson(
    json,
    object : TypeToken<T>() {}.type
)

inline fun <reified T> JsonDeserializationContext.fromJsonElement(json: JsonElement): T =
    deserialize(
        json,
        object : TypeToken<T>() {}.type
    )

inline fun <reified T> GsonBuilder.registerTypeAdapter(adapter: Any): GsonBuilder =
    registerTypeAdapter(
        object : TypeToken<T>() {}.type,
        adapter
    )

fun JsonElement?.safeAsString() = if (this?.isJsonNull == true) EMPTY_STRING else this?.asString