# Marius' Admin Plugin

## Commands
**&#x2714;**|**Command**|**Usage**|**Description**|**Permission**
---|---|---|---|---
&#x2714;|/sm|`<message ...>`|Sends a message to all online staff members|`map.sm.send`
&#x2714;|/tpto|`player <player>`|Teleport you to a given player|`map.tpto.player`
&#x2714;|/tpto|`coord <x> <y> <z>`|Teleport you to a given location|`map.tpto.coord`
&#x2714;|/tphere|`<player>`|Teleport a given player to you|`map.tphere`
&#x2714;|/gm or /gamemode|`<0, 1, 2, 3>`|Change your gamemode|`map.gamemode`
&#x2714;|/gm or /gamemode|`<survival, creative, adventure, spectator>`|Change your gamemode|`map.gamemode`
&#x2714;|/playergui| |Give access to the `Player Click` Event|`map.playergui`
&#x2714;|/vanish| |Vanish yourself for other players|`map.vanish`

## Events
**&#x2714;**|**Event**|**Class**|**Action**|**[Permission]**
---|---|---|---|---
&#x2714;|Player Click|PlayerInteractAtEntityEvent|If left click on player, opens a GUI where you can kick and ban the player (only if enabled with `/playergui`)|`map.playergui`

## Permissions
**Permission**|**Usage**
---|---
`map.sm.get`|Let you receive staff chat messages
`map.sm.send`|Send a message to staff chat
`map.tpto.player`|Teleport to a player
`map.tpto.coord`|Teleport to a coord
`map.tphere`|Teleport a player to you
`map.gamemode`|Change you gamemode
`map.playergui`|Player interact GUI
`map.vanish`|Player vanish
`map.vanish.bypass`|You can see ALL players, whether they are vanished or not