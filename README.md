<!--suppress HtmlDeprecatedTag, XmlDeprecatedElement -->
<center><img alt="mod preview" src="https://cdn.modrinth.com/data/nFj2Gxsu/bf25076f5dbf8c0b64c98dccabf2e6c6cbb6b765.png"/></center>

<center>
a simple pronoun mod
</center>

---

# lampblack pronouns

lampblack is designed to be the simplest pronouns mod that suits my needs - 
having a [placeholder](https://modrinth.com/mod/placeholder-api) for displaying pronouns in various places, 
and, compatibility with [switchy](https://modrinth.com/mod/switchy).

---

to set your pronoun preference, use `/pronouns <up to 16 characters describing your preference>`

to reset/clear your pronoun preference, use `/pronouns`

and to view the preferences of other players, use`/showpronouns <player>` - but

if you are a server admin setting up a more convenient way to view pronouns, 
you can use the built-in [placeholder](https://modrinth.com/mod/placeholder-api) 
`%lampblack:pronouns%` wherever placeholders are supported 
(check out [styled player list](https://modrinth.com/mod/styledplayerlist))

configuration options are available in `<minecraft>/config/lampblack.toml`:
```toml
# Maximum allowed length for pronoun preference (inclusive)
# default: 16
maxLength = 16
# The default value used for players that have not set their pronoun preference yet
# default: 
defaultPronouns = ""
# Whether to provide basic suggestions for the /pronouns command
# default: false
suggestBasicPronouns = false
# The suggestions used when suggestBasicPronouns is true
basicPronouns = ["he/him", "she/her", "they/them"]
# Whether to provide suggestions for custom pronouns and clearing pronouns (<custom>, <blank>)
# default: false
suggestFakePrompts = false


```
