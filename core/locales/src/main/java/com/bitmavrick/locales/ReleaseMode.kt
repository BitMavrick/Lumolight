package com.bitmavrick.locales

enum class ReleaseMode {
    DEBUG,
    PRODUCTION;

    companion object {
        val STATUS = ReleaseMode.PRODUCTION
    }
}