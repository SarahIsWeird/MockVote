package com.sarahisweird.mockvote

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.time.Instant
import java.util.*
import java.util.logging.Logger

class VoteTracker(
    private val logger: Logger,
    private val file: File
) {
    private lateinit var votes: MutableMap<UUID, Instant>
    private val fileConfig = YamlConfiguration()

    fun load() {
        file.createNewFile()
        fileConfig.load(file)

        votes = fileConfig.getValues(false)
            .mapKeys { UUID.fromString(it.key) }
            .mapValues { Instant.ofEpochSecond((it.value as String).toLong()) }
            .toMutableMap()

        logger.info("Loaded votes from votes.yml.")
    }

    fun save() {
        votes.forEach { (uuid, instant) ->
            fileConfig.set(uuid.toString(), instant.epochSecond.toString())
        }

        fileConfig.save(file)

        logger.info("Saved votes to votes.yml.")
    }

    fun timeSinceLastVote(uuid: UUID): Long =
        Instant.now().epochSecond - (votes[uuid]?.epochSecond ?: Instant.MIN.epochSecond)

    fun registerVote(uuid: UUID) {
        votes[uuid] = Instant.now()
    }
}