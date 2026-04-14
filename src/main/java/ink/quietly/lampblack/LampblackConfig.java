package ink.quietly.lampblack;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ValueList;

public class LampblackConfig extends WrappedConfig {
	@Comment("Maximum allowed length for pronoun preference (inclusive)")
	public int maxLength = 16;

	@Comment("The default value used for players that have not set their pronoun preference yet")
	public String defaultPronouns = "";

	@Comment("Whether to provide basic suggestions for the /pronouns command")
	public boolean suggestBasicPronouns = false;

	@Comment("The suggestions used when suggestBasicPronouns is true")
	public ValueList<String> basicPronouns = ValueList.create("", "he/him", "she/her", "they/them");

	@Comment("Whether to provide suggestions for custom pronouns and clearing pronouns (<custom>, <blank>)")
	public boolean suggestFakePrompts = false;
}
