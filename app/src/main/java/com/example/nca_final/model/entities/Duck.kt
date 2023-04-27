package com.example.nca_final.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import com.google.gson.annotations.SerializedName

@Entity
data class Duck(
    @PrimaryKey(autoGenerate = true) var id: Int?,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    var name: String?,

    @SerializedName("value")
    @ColumnInfo(name = "value")
    var value: Double?,

    @SerializedName("rarity")
    @ColumnInfo(name = "rarity")
    var rarity: Int?
): Serializable {
    @SerializedName("seed")
    @ColumnInfo(name = "seed")
    var seed: Int = ((1..1000).random() + (System.currentTimeMillis() % 1000).toInt() % 1000) + 1

    @SerializedName("image")
    @ColumnInfo(name = "image")
    var image: String? = calcImage()

    @SerializedName("url")
    @ColumnInfo(name = "url")
    var url: String = "https://en.wikipedia.org/wiki/Rubber_duck"

    private fun calcImage(): String {
        return "pato_%03d".format(
            when (seed) {
                in 994..1000 -> {((seed % 7) + 121)}
                in 901..993 -> {((seed % 40) + 81)}
                in 401..900 -> {((seed % 40) + 41)}
                else -> {((seed % 40) + 1)}
            }
        )
    }
}