/* *
* Lumolight :: Open-source program under GPL-3.0 :: Copyright - BitMavrick :: https://github.com/BitMavrick
* */

package com.bitmavrick.lumolight.util
class AppConstants{
    companion object {
        val APP_PRODUCTION_MODE = ProductionMode.DEBUG // !! Change it to Release before release !!
        const val APP_NAME = "Lumolight"
        const val DEVELOPER = "BitMavrick"
        const val REPOSITORY = "https://github.com/BitMavrick/Lumolight"
        // ? const val REDDIT = "https://www.reddit.com/r/Lumolight" // ? The community is Banned
        const val PRIVACY_POLICY = "https://bitmavrick.github.io/privacy-policy/lumolight.html"
    }
}

enum class ProductionMode {
    DEBUG,
    RELEASE
}

class TimeDuration{
    companion object {
        val list: List<Duration> = listOf(
            Duration("N/A", -1),
            Duration("1 min", 1),
            Duration("2 min", 2),
            Duration("5 min", 5),
            Duration("10 min", 10),
            Duration("15 min", 15),
            Duration("30 min", 30),
            Duration("45 min", 45),
            Duration("60 min", 60),
            Duration("120 min", 120),
        )
    }
}

class ColorValue{
    companion object {
        val list: List<Color> = listOf(
            Color("White", "#FFFFFF"),
            // Color("Silver", "#C0C0C0"),
            Color("Gray", "#808080"),
            Color("Black", "#000000"),
            Color("Red", "#FF0000"),
            // Color("Maroon", "#800000"),
            Color("Yellow", "#FFFF00"),
            // Color("Olive", "#808000"),
            Color("Lime", "#00FF00"),
            Color("Green", "#008000"),
            Color("Aqua", "#00FFFF"),
            // Color("Teal", "#008080"),
            Color("Blue", "#0000FF"),
            // Color("Navy", "#000080"),
            Color("Fuchsia", "#FF00FF"),
            Color("Purple", "#800080")
        )
    }
}

class BrightnessValue{
    companion object {
        val list : List<Brightness> = listOf(
            Brightness("100%", 1f),
            Brightness("80%", .8f),
            Brightness("60%", .6f),
            Brightness("40%", .4f),
            Brightness("20%", .2f)
        )
    }
}

class BpmValue{
    companion object {
        val list : List<BPM> = listOf(
            BPM("0 bpm", 0),
            BPM("30 bpm", 30),
            BPM("60 bpm", 60),
            BPM("90 bpm", 90),
            BPM("120 bpm", 120),
            BPM("140 bpm", 140),
        )
    }
}

data class Duration(
    val duration: String,
    val time: Int
)

data class Color(
    val name: String,
    val code: String,
)

data class Brightness(
    val title: String,
    val value: Float
)

data class BPM(
    val title : String,
    val value : Int,
)