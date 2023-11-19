package com.example.appmarvels.framework.model

data class CharacterPaging(
    val offset: Int,
    val total: Int,
    val characters: List<Character>
)
