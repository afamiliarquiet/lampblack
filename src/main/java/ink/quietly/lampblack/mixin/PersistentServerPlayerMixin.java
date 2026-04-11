package ink.quietly.lampblack.mixin;

import com.mojang.authlib.GameProfile;
import ink.quietly.lampblack.LampblackPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class PersistentServerPlayerMixin extends Player {
	public PersistentServerPlayerMixin(Level level, BlockPos pos, float yRot, GameProfile gameProfile) {
		super(level, pos, yRot, gameProfile);
	}

	@Inject(at = @At("HEAD"), method = "restoreFrom")
	private void restoreFrom(ServerPlayer that, boolean keepEverything, CallbackInfo ci) {
		((LampblackPlayer)this).lampblack$setPronouns(((LampblackPlayer)that).lampblack$getPronouns());
	}
}
