package ink.quietly.lampblack;

import com.mojang.authlib.GameProfile;
import dev.sisby.switchy.data.SwitchyComponentType;
import dev.sisby.switchy.data.SwitchyComponentTypes;
import dev.sisby.switchy.data.SwitchyProfile;
import dev.sisby.switchy.duck.SwitchyGameProfile;

public class SwitchyCompat {
	public static String getSayPronouns(GameProfile gameProfile) {
		SwitchyProfile sayProfile = ((SwitchyGameProfile) (Object) gameProfile).switchy$getSayProfile();
		if (sayProfile == null) return null;
		SwitchyComponentType<?> pronouns = SwitchyComponentTypes.instance().get(SwitchyComponentTypes.LAMPBLACK_PRONOUNS);
		if (pronouns == null) return null;
		return sayProfile.get(pronouns) instanceof String s ? s : null;
	}
}
