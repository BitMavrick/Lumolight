package com.bitmavrick.lumolight.util

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

data class Duration(
    val duration: String,
    val time: Int
)