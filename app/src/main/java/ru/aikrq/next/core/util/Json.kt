package ru.aikrq.next.core.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import ru.aikrq.next.core.util.serializers.idSerializer
import ru.aikrq.next.model.projects.ActivityTheme
import ru.aikrq.next.model.projects.FileType
import ru.aikrq.next.model.projects.KeyboardSetting
import ru.aikrq.next.model.projects.LayoutOrientation
import ru.aikrq.next.model.projects.Orientation

private val serializationModule = SerializersModule {
    contextual(idSerializer<FileType>())
    contextual(idSerializer<KeyboardSetting>())
    contextual(idSerializer<Orientation>())
    contextual(idSerializer(LayoutOrientation.HORIZONTAL))
    contextual(idSerializer<ActivityTheme>())
}

val json = Json {
    serializersModule = serializationModule
}

inline fun <reified T> String.serialize() = json.decodeFromString<T>(this)
inline fun <reified T> T.deserialize() = json.encodeToString(this)
