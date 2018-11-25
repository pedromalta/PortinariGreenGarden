package pedromalta.portinari.home.model.responses

import pedromalta.portinari.home.model.BaseModel

data class SprinklerStatus(
        val sprinkler1: Int,
        val sprinkler2: Int
) : BaseModel()