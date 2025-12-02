package com.bitmavrick.store.preset

class BpmPreset {
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
data class BPM(
    val title : String,
    val value : Int,
)