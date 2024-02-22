package com.eco.ecowatch.DataClass

import java.io.Serializable

data class Berita(
    val id: String,
    val Judul: String,
    val Deskripsi:String
) : Serializable