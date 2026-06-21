package com.gchristov.personal.common.kotlin

interface ParameterMap {
    operator fun <T> get(key: String): T?
}
