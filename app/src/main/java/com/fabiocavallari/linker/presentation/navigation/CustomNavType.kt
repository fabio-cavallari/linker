package com.fabiocavallari.linker.presentation.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.fabiocavallari.linker.domain.model.Alias
import kotlinx.serialization.json.Json

object CustomNavType {
    val AliasNavType = object : NavType<Alias>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): Alias? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): Alias {
            return Json.decodeFromString(value)
        }

        override fun put(bundle: Bundle, key: String, value: Alias) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun serializeAsValue(value: Alias): String {
            return Uri.encode(Json.encodeToString(value))
        }
    }
}