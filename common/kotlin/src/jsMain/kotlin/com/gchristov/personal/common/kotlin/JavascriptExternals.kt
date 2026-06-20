package com.gchristov.personal.common.kotlin

fun requireModule(module: String) = require(module)

private external fun require(module: String): dynamic

external val __dirname: dynamic

external val process: dynamic
