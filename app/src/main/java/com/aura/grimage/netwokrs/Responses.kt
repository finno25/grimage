package com.aura.co99.networks

import com.aura.grimage.models.Hit

data class ImagesResponses(
    val total: Int,
    val totalHits: Int,
    val hits: List<Hit>
)