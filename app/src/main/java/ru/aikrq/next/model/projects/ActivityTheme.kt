package ru.aikrq.next.model.projects

enum class ActivityTheme(override val id: Int) : IdInterface {
    /**
     * Says that activity don't have any special theme.
     */
    NONE(-1),

    /**
     * Says that activity has default theme.
     */
    DEFAULT(0),

    /**
     * Says that activity has no action bar.
     */
    NO_ACTIONBAR(1),

    /**
     * Says that activity has fullscreen theme.
     */
    FULLSCREEN(2)
}