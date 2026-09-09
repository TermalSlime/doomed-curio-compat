package net.termalslime.doomed_curio.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.mattlives.doomedmatu.body.ArmorReduction;
import net.mattlives.doomedmatu.body.BodyPart;
import net.mattlives.doomedmatu.capability.DoomedData;
import net.mattlives.doomedmatu.capability.DoomedDataUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.termalslime.doomed_curio.DoomedCurio;
import net.termalslime.doomed_curio.helpers.CurioStatsCalculator;
import net.termalslime.doomed_curio.mixin.TemperatureHandlerMixin;

@Mod.EventBusSubscriber(modid = DoomedCurio.MODID)
public class DoomedCurioInspect {
    private static final String PLAYERS_ARG = "players";

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        register(event.getDispatcher());
    }

    private static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("doomcurioinspect")
                .requires((src) -> src.hasPermission(2))
                .executes(DoomedCurioInspect::inspect)
        );

    }

    private static int inspect(CommandContext<CommandSourceStack> ctx)
    {
        CommandSourceStack source = ctx.getSource();
        ServerPlayer player = source.getPlayer();
        if (player == null) return 0;
        DoomedDataUtil.get(player).ifPresent( data -> {

            for (BodyPart part : BodyPart.VALUES) {
                sendChatMessage(source, "part: " + part.displayName() + " - " + ArmorReduction.partProtection(player, data, part));
            }
            sendChatMessage(source, "isolation: " + (data.getWearables().totalIsolation() + CurioStatsCalculator.calculateInsulation(player)));
        });
        return 1;
    }

    private static void sendChatMessage(CommandSourceStack source, String message)
    {
        source.sendSystemMessage(Component.literal(message));
    }
}
