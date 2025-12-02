package com.bitmavrick.store.preset

class ColorPreset {
    companion object {
        val list: List<Color> = listOf(
            Color("Warm", "#FFF8E7"),
            Color("White", "#FFFFFF"),
            Color("Red", "#FF0000"),
            Color("Yellow", "#FFFF00"),
            Color("Lime", "#00FF00"),
            Color("Aqua", "#00FFFF"),
            Color("Blue", "#0000FF"),
            Color("Fuchsia", "#FF00FF"),
            Color("Black", "#000000")
        )
    }
}

data class Color(
    val name: String,
    val code: String,
)