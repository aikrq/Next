package ru.aikrq.next.model.projects

enum class KeyboardSetting(override val id: Int) : IdInterface {
    Unspecified(0),
    Visible(1),
    Hidden(2)
}