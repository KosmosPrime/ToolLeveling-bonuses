# Changelog

### Version 1.20.1 - 2.0.1

- fix error where `bonus_item_more_enchantments` and `bonus_item_more_levels` only allowed single objects
    - now conform with vanilla ingredient format
    - allows objects and arrays of objects now

### Version 1.20.1 - 2.0.0

- port to 1.20.1

### Version 1.19.4 - 2.0.0

- new interface for the tool leveling table

### Version 1.19.4 - 1.4.5

- fix incorrect calculation when using up items from the inventory

### Version 1.19.4 - 1.4.4

- port to 1.19.4

### Version 1.19.3 - 1.4.4

- fix forge creative tab crash

### Version 1.19.3 - 1.4.3

- port to 1.19.3
- ToolLeveling Table is now in the creative tab of functional blocks
- extended the `/toolleveling` command
    - `/toolleveling github` shows the url to the github page
    - `/toolleveling discord` shows the url to the discord server
    - `/toolleveling wiki` shows the url to the wiki
    - `/toolleveling issues` shows the url to the issues page
    - `/toolleveling curseforge` shows the url to the curseforge page
    - `/toolleveling modrinth` shows the url to the modrinth page

### Version 1.19.2 - 1.4.2

- allow tags/wildcards to be used as config values
    - formattings
        - wildcard: `modid:*`, example: `minecraft:*`
        - tag: `#modid:tag`, example: `#minecraft:logs`
    - can be used in following options
        - itemvalues
        - enchantment blacklist
        - enchantment whitelist
        - enchantment caps
        - enchantment modifier
        - minimum enchantment levels
- add chinese translation by [@GodGun968](https://github.com/GodGun968)

### Version 1.19.2 - 1.4.1

- port to 1.19.2
- fix error where whitelisted enchantment could be leveled above the enchantment cap
- add additional message for enchantments that are not whitelisted

### Version 1.19 - 1.4.0 `Config-Update`

- **IMPORTANT**: if you have customized your config, you need to backup the config before updating the mod
- filter out items while parsing the `item_values.json`
    - this way there are proper messages in the log for items that are not valid as payment items
    - currently filters out items that are enchantable or damageable
- enchantment modifier are now allowed in range [0, Double.MAX_VALUE] instead of [1, Double.MAX_VALUE]
- overhaul the format of the config files
    - config options are now grouped better together
    - renamed all options to use snake_case instead of camelCase
- add new config option `global_minimum_enchantment_level`
    - can be used to set a minimum level for all enchantments before they can be leveled in the tool leveling table
- add new config option `minimum_enchantment_levels`
    - can be used to set a minimum level for specific enchantment before it can be leveled in the tool leveling table
    - for example: can be used to allow minecraft:sharpness to be leveled only if the enchantment level is already at
      level X
- if both `global_minimum_enchantment_level` and `minimum_enchantment_levels` are set, the higher of the two will be
  used
- update in-game mod icon to the newer blockmodel
- add new command `/toolleveling config info <config>` to display links to the config-explanation
- create consistent formatting for command responses

### Version 1.19 - 1.3.5

- filter out items while parsing the `item_values.json`
    - this way there are proper messages in the log for items that are not valid as payment items

### Version 1.19 - 1.3.4

- fix error `toolleveling failed to load correctly`
    - requires forge to be greater than 41.0.95

### Version 1.19 - 1.3.3

- rename config-identifier from `itemValues` to `item_values`

### Version 1.19 - 1.3.1

- port to 1.19
- move the command to view the item values from `/openitemvalues` to `/toolleveling openitemvalues`

### Version 1.18.1 - 1.3.0

- support wildcards in enchantmentBlacklist and enchantmentWhitelist
    - this will whitelist/blacklist all enchantments from the specified mod
    - formatting example `examplemod:*`
- fix config not resetting properly
- change when the buttons are clickable
    - buttons for disabled enchantments are now actually disabled, this was not the case for useless enchantmens like
      mending
- new config option to change whether or not creative players can have free upgrades
- splitting `upgradeCostMultiplier` into `globalUpgradeCostMultiplier` and `enchantmentUpgradeCostModifier`
    - globalUpgradeCostMultiplier can be used to modify all upgrade costs
    - enchantmentUpgradeCostModifier can be used to modify the upgrade cost of a specific enchantment
    - makes it possible now to make a single enchantment cheaper or more expensive than others
- update blockmodel and recipe

### Version 1.18 - 1.2.12

- fix overflow for large inventory worth
- add romanian translation by **Potato man**
- add item values for new 1.18-items (delete old `item_values.json` to get a new config)

### Version 1.18 - 1.2.11

- port to 1.18

### Version 1.17.1 - 1.2.11

- fix serverside crashes

### Version 1.17.1 - 1.2.10

- the item worth can now be shown as a tooltip
    - needs advanced tooltips `F3 + H` activated
    - press shift while hovering over an item

### Version 1.17.1 - 1.2.9

- enchantments can now be leveled higher than 255 again

### Version 1.17.1 - 1.2.8

- fix formula for upgrade cost
    - incorrect formula was `((0.0015x)^4 + 300x) * modifier`
    - new formula is `((0.87 * x * x) + 300x) * modifier`

### Version 1.17.1 - 1.2.7

- needs forge version higher than 37.0.32
- add `/openitemvalues` command to show the worth of the items added by the config
- add block to the minecraft tags to be mineable
    - fixes the error for forge versions over 37.0.30

### Version 1.17.1 - 1.2.6

- improve handling of items through hoppers
    - insert only works into payment slots
    - but hoppers can remove items from all slots, even the top slot

### Version 1.17.1 - 1.2.5

- port to 1.17.1

### Version 1.16.5 - 1.2.5

- the datatype for item values changed from integer to long
- the datatype for enchantment caps changed from integer to short, since the actual maximum enchantment level is a short
- globalEnchantmentCap and enchantmentCaps can now be used together
    - if the enchantmentCap for one enchantment is lower than the globalEnchantmentCap, the enchantmentCap for this
      enchantment will be used
    - otherwise the globalEnchantmentCap will be used

### Version 1.16.5 - 1.2.4

- fix bug where configs were not synced properly to the client on the first server start

### Version 1.16.5 - 1.2.3

- added translation ru_ru

### Version 1.16.5 - 1.2.2

- allow default item worth to be zero

### Version 1.16.5 - 1.2.1

- allow item values to be zero
- added new config-option : enchantmentWhitelist
    - is by default empty
    - if it contains enchantments, all enchantments not listed in here are blacklisted
    - this option will deactivate the enchantmentBlacklist
- added new config-option : globalEnchantmentCap
    - if set to a value higher than zero all enchantments are capped at this value
    - this option will deactivate the list enchantmentCaps

### Version 1.16.5 - 1.2.0

- **clear your toollevleing table before updating, or you will loose all items that are stored in it**
- support for 1.16.5
- extended the command 'superenchant'
    - works now similar to 'enchant', but with no limits to the level
    - allows also enchantments that were previous not allowed together (e.g. silktouch & fortune on pickaxe)
    - requieres permission level 3
- updated gui
    - more items allowed as payment
    - different items are having different worth
    - if you insert an item that has more worth than required, the extra points are going to be stored in the table for
      future usage
- new formula to determine the payment cost
    - cheaper at lower levels then before
    - gets exponential more expensive in the higher levels
- new config structure

### Version 1.16.2 - 1.1.1

- support 1.16.4
- fix not using payment slot 3 & 4

### Version 1.16.2 - 1.1.0

- finished design for the leveling table
- higher enchantment level -> higher cost for upgrading
- set the payment item to netherite ingots, to prevent the creation of overpowered items
    - can be changed in the config
- added tooltips in the gui

### Version 1.16.2 - 1.0.2

- added caps to enchantments that will break when leveled to high

### Version 1.16.2 - 1.0.1

- added enchantment blacklist
- changed payment item
    - for armor/tools -> their repair material
    - everything else -> diamonds

### Version 1.16.2 - 1.0.0

- initial release
