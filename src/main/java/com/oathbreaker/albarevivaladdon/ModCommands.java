package com.oathbreaker.albarevivaladdon;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.oathbreaker.albarevivaladdon.attachments.PlayerLivesData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ModCommands
{
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        dispatcher.register(
        Commands.literal("setlives")
                .requires(source -> source.hasPermission(4)) // 4 is op
                .then(Commands.argument("target", EntityArgument.player())
                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                            .executes(context ->
                            {
                                ServerPlayer target = EntityArgument.getPlayer(context, "target");
                                int amount = IntegerArgumentType.getInteger(context, "amount");

                                PlayerLivesData data = target.getData(RevivalAddon.LIVES_ATTACHMENT.get());
                                data.setLives(amount);
                                target.setData(RevivalAddon.LIVES_ATTACHMENT.get(), data);

                                context.getSource().sendSuccess(() -> Component.literal("§aSet " + amount + " lives for " + target.getScoreboardName()), true);
                                return 1;
                            })
                        )
                )
        );

        dispatcher.register(
        Commands.literal("infinitelives")
                .requires(source -> source.hasPermission(4))
                .then(Commands.argument("target", EntityArgument.player())
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(context ->
                                {
                                    ServerPlayer target = EntityArgument.getPlayer(context, "target");
                                    boolean enabled = BoolArgumentType.getBool(context, "enabled");

                                    PlayerLivesData data = target.getData(RevivalAddon.LIVES_ATTACHMENT.get());
                                    data.setImmortal(enabled);

                                    String status = enabled ? "§аON" : "§сOFF";
                                    context.getSource().sendSuccess(() -> Component.literal("§aInfinite lives for " + target.getScoreboardName() + " are " + status), true);
                                    return 1;
                                })
                        )
                )
        );
    }
}
