package com.sarahisweird.mockvote

import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class MockVote : JavaPlugin() {
    private lateinit var voteTracker: VoteTracker
    private lateinit var mvConfig: Config

    override fun onEnable() {
        config.addDefault("cooldown", 300)
        config.addDefault("messages.cooldown",
            "You can use this command again in \$remaining seconds.")
        config.addDefault("messages.success", "You've successfully voted for this server!")
        config.addDefault("commands", listOf("give \$player minecraft:diamond 1"))
        saveDefaultConfig()

        val messages = config.getConfigurationSection("messages")
        mvConfig = Config(
            cooldown = config.getLong("cooldown"),
            cooldownMessage = messages.getString("cooldown"),
            successMessage = messages.getString("success"),
            commands = config.getStringList("commands") as List<String>
        )

        voteTracker = VoteTracker(logger, File(dataFolder, "votes.yml"))
        voteTracker.load()

        getCommand("vote").executor = MVCommandExecutor(voteTracker, mvConfig)

        logger.info("MockVote enabled.")
    }

    override fun onDisable() {
        voteTracker.save()

        logger.info("MockVote disabled.")
    }
}