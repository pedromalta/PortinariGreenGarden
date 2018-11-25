package br.com.drivin.model.responses

import br.com.drivin.model.BaseModel

data class SprinklerStatus(
        val sprinkler1: Int,
        val sprinkler2: Int
) : BaseModel()