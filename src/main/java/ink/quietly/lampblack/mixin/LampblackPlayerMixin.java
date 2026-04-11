package ink.quietly.lampblack.mixin;

import ink.quietly.lampblack.Lampblack;
import ink.quietly.lampblack.LampblackPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(Player.class)
public class LampblackPlayerMixin implements LampblackPlayer {
	@Unique
	private String lampblack$pronouns = Lampblack.DEFAULT;

	@Override
	public void lampblack$setPronouns(@Nullable String pronouns) {
		this.lampblack$pronouns = Objects.requireNonNullElse(pronouns, Lampblack.DEFAULT);
	}

	@Override
	public @NotNull String lampblack$getPronouns() {
		return lampblack$pronouns;
	}

	@Inject(at = @At("TAIL"), method = "readAdditionalSaveData")
	private void readAdditionalSaveData(ValueInput input, CallbackInfo ci) {
		lampblack$pronouns = input.getStringOr(Lampblack.PATH, Lampblack.DEFAULT);
	}

	@Inject(at = @At("TAIL"), method = "addAdditionalSaveData")
	private void addAdditionalSaveData(ValueOutput output, CallbackInfo ci) {
		output.putString(Lampblack.PATH, lampblack$pronouns);
	}
}
