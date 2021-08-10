# MockVote

MockVote is a simple plugin that will [mock](https://en.wikipedia.org/wiki/Mock_object) a voting system.
This plugin is useful if you have an alpha or beta phase, and as a result, don't have a real voting
system yet. This plugin can fill the gap.

Written in 100% [Kotlin](https://kotlinlang.org), definitely compatible with 1.12.2 - 1.17.1.

## Configuration

There's a configuration file in the resource folder, in which you can change the behaviour of MockVote.

```yaml
# The cooldown of /vote, in seconds.
cooldown: 300
messages:
  # This message will be displayed if the cooldown isn't over yet.
  # '$remaining' is substituted with the time remaining, in seconds.
  onCooldown: "You can use this command again in $remaining seconds."
  # This message will be displayed if the user successfully "voted".
  success: "You've successfully voted for this server!"
# A list of commands to execute when the user successfully voted.
# There is no limit on how many commands there are, and they are executed in order.
# '$player' is substituted with the player name.
commands:
  - "give $player minecraft:oak_boat 1"
# - "effect give $player minecraft:speed 30 2"
```