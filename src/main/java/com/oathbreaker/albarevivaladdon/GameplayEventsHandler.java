package com.oathbreaker.albarevivaladdon;

import com.oathbreaker.albarevivaladdon.attachments.PlayerLivesData;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.hardcorerevival.HardcoreRevivalManager;
import net.blay09.mods.hardcorerevival.api.PlayerKnockedOutEvent;
import net.blay09.mods.hardcorerevival.api.PlayerRescuedEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class GameplayEventsHandler
{
    public static void init()
    {
        Balm.getEvents().onEvent(PlayerRescuedEvent.class, GameplayEventsHandler::onPlayerRescued);
        Balm.getEvents().onEvent(PlayerKnockedOutEvent.class, GameplayEventsHandler::onPlayerKnockedOut);
    }

    private static void onPlayerRescued(PlayerRescuedEvent event)
    {
        Player rescuedPlayer = event.getPlayer();

        if (!rescuedPlayer.level().isClientSide() && rescuedPlayer instanceof ServerPlayer serverPlayer)
        {
            // get the lives count attachment (aka component)
            PlayerLivesData livesData = serverPlayer.getData(RevivalAddon.LIVES_ATTACHMENT.get());

            livesData.decrement();

            int remainingLives = livesData.getLives();

            serverPlayer.sendSystemMessage(
                    Component.literal("§cRescues remaining: §l" + remainingLives)
            );

            if (remainingLives <= 0)
            {
                serverPlayer.sendSystemMessage(
                        Component.literal("§4You have no lives left!")
                );
            }
        }
    }

    private static void onPlayerKnockedOut(PlayerKnockedOutEvent event)
    {
        Player player = event.getPlayer();

        if (!player.level().isClientSide() && player instanceof ServerPlayer serverPlayer)
        {
            PlayerLivesData livesData = serverPlayer.getData(RevivalAddon.LIVES_ATTACHMENT.get());

            if (livesData.getLives() <= 0 && !livesData.isImmortal())
            {
                MinecraftServer server = serverPlayer.getServer();
                if (server != null)
                {
                    server.getPlayerList().broadcastSystemMessage(
                            Component.literal(serverPlayer.getScoreboardName() + " is dead for sure"),
                            false
                    );

                    // to avoid deadlock we have to invoke death the next tick
                    server.execute(()-> { HardcoreRevivalManager.notRescuedInTime(player); });
                }
            }
        }
    }
}
