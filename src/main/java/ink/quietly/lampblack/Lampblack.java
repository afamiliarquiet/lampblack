package ink.quietly.lampblack;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;


public class Lampblack implements ModInitializer {
	public static final String ID = "lampblack";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);
	public static final String DEFAULT = "";

	public static final AttachmentType<String> PRONOUNS = AttachmentRegistry.create(
		id("pronouns"),
		builder -> builder
			.persistent(Codec.STRING)
			.copyOnDeath()
			.initializer(() -> DEFAULT)
	);

	@Override
	public void onInitialize() {
		log("scraping soot..");

		Placeholders.registerServer(
			id("pronouns"),
			(ctx, arg) -> {
				var player = ctx.serverPlayer();
				if (player != null) {
					return PlaceholderResult.value(player.getAttachedOrCreate(PRONOUNS));
				}

				return PlaceholderResult.value("");
			}
		);

		CommandRegistrationCallback.EVENT.register(
			(dispatcher, buildCtx, env) -> {
			dispatcher.register(literal("pronouns")
				.executes(this::clearPronouns)
				.then(
					argument("pronouns", StringArgumentType.greedyString())
						.suggests((ignored, builder) -> {
							builder.suggest("he/him");
							builder.suggest("she/her");
							builder.suggest("they/them");

							return builder.buildFuture();
						})
						.executes(this::setPronouns)
				)
			);

			// doesn't really need to be a command
			dispatcher.register(literal("showpronouns")
				.then(
					argument("target", EntityArgument.player())
						.executes(this::inspect)
				)
			);
		});

		log("initialized. let their pronouns be written.");
	}

	private int setPronouns(CommandContext<CommandSourceStack> ctx) {
		var pronouns = StringArgumentType.getString(ctx, "pronouns");
		if (pronouns.isEmpty()) {
			// i don't believe greedy string allows for this, but hey. why not.
			return clearPronouns(ctx);
		}

		var player = ctx.getSource().getPlayer();
		if (player != null) {
			if (pronouns.length() <= 16) {
				player.setAttached(PRONOUNS, pronouns);
				ctx.getSource().sendSystemMessage(Component.literal("Your preference has been saved! Some displays may not update until you send a chat message.").withStyle(ChatFormatting.GRAY));
				return 1;
			} else {
				ctx.getSource().sendSystemMessage(Component.literal("Could not set your preference.").withStyle(ChatFormatting.RED).append(Component.literal(" For display reasons, pronoun preference is limited to 16 characters.").withStyle(ChatFormatting.GRAY)));
				return 0;
			}
		} else {
			return 0;
		}
	}

	private int clearPronouns(CommandContext<CommandSourceStack> ctx) {
		var player = ctx.getSource().getPlayer();
		if (player != null) {
			player.setAttached(PRONOUNS, DEFAULT);
			ctx.getSource().sendSystemMessage(Component.literal("Your preference has been cleared! Some displays may not update until you send a chat message.").withStyle(ChatFormatting.GRAY));
			return 1;
		} else {
			return 0;
		}
	}

	private int inspect(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
		var inspectionTarget = EntityArgument.getEntity(ctx, "target");
		var pronouns = inspectionTarget.getAttachedOrCreate(PRONOUNS);
		if (!pronouns.equals(DEFAULT)) {
			ctx.getSource().sendSystemMessage(Component.empty().append(inspectionTarget.getDisplayName()).append(Component.literal("'s pronoun preference: ")).append(pronouns).withStyle(ChatFormatting.GRAY));
		} else {
			ctx.getSource().sendSystemMessage(Component.empty().append(inspectionTarget.getDisplayName()).append(Component.literal(" has not provided any pronoun preference.")).withStyle(ChatFormatting.GRAY));
		}
		return 1;
	}

	public static void log(String thing) {
		LOGGER.info("[lampblack] {}", thing);
	}

	public static Identifier id(String ego) {
		return Identifier.fromNamespaceAndPath(ID, ego);
	}
}
