package ru.aikrq.next.model.projects

enum class Orientation(override val id: Int) : IdInterface {
    Portrait(0),
    Landscape(1),
    Both(2)
}