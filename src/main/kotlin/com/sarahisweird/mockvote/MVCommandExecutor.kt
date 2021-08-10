package com.sarahisweird.mockvote

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MVCommandExecutor(
    private val voteTracker: VoteTracker,
    private val config: Config
) : CommandExecutor {
    override fun onCommand(
        sender: CommandSender?,
        command: Command?,
        label: String?,
        args: Array<out String>?
    ): Boolean {
        if (command?.name != "vote") return false

        if (sender !is Player) {
            sender?.sendMessage("You can only run this command as a player.")
            return true
        }

        val timePassed = voteTracker.timeSinceLastVote(sender.uniqueId)
        if (timePassed < config.cooldown) {
            sender.sendMessage(config.cooldownMessage
                .replace("\$remaining", (config.cooldown - timePassed).toString()))
            return true
        }

        voteTracker.registerVote(sender.uniqueId)
        sender.sendMessage(config.successMessage)

        config.commands.map { it.replace("\$player", sender.name) }.forEach {
            sender.server.dispatchCommand(sender.server.consoleSender, it)
        }

        return true
    }
}