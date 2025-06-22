package ru.aikrq.next.model.projects

enum class LayoutOrientation(override val id: Int) : IdInterface {
    VERTICAL(1),
    HORIZONTAL(-1)
}