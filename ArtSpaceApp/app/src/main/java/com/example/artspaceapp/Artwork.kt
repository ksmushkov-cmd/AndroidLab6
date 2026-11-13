package com.example.artspaceapp

data class Artwork(
    val imageRes: Int,
    val titleRes: Int,
    val artistRes: Int
)

val artworks = listOf(
    Artwork(R.drawable.image1, R.string.artwork1_title, R.string.artwork1_artist),
    Artwork(R.drawable.image2, R.string.artwork2_title, R.string.artwork2_artist),
    Artwork(R.drawable.image3, R.string.artwork3_title, R.string.artwork3_artist)
)