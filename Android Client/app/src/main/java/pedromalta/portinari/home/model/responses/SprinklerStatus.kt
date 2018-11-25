package pedromalta.portinari.home.model.responses

import pedromalta.portinari.home.model.BaseModel

data class SprinklerStatus(
        val sprinkler1: Boolean,
        val sprinkler2: Boolean
) : BaseModel()