package ru.aikrq.next.model.projects

enum class FileType(override val id: Int) : IdInterface {
    Activity(0),
    CustomView(1),
    Drawer(2)
}