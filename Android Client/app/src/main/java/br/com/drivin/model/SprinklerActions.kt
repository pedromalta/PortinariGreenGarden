package br.com.drivin.model

enum class SprinklerActions {
    START, STOP, STATUS;

    override fun toString(): String {
        return super.toString().toLowerCase()
    }
}