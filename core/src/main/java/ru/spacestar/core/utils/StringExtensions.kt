package ru.spacestar.core.utils

import java.net.URLDecoder
import java.net.URLEncoder

object StringExtensions {

    val String.urlEncoded: String
        get() = URLEncoder.encode(this, "UTF-8")

    val String.decodeFromUrl: String
        get() = URLDecoder.decode(this, "UTF-8")
}