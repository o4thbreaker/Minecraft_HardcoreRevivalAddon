package com.oathbreaker.albarevivaladdon.client;

import com.oathbreaker.albarevivaladdon.RevivalAddon;
import com.oathbreaker.albarevivaladdon.attachments.PlayerLivesData;
import net.minecraft.client.Minecraft;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.blay09.mods.hardcorerevival.client.KnockoutScreen;
import net.neoforged.neoforge.client.event.ScreenEvent;

@EventBusSubscriber(modid = RevivalAddon.MODID, value = Dist.CLIENT)
public class PlayerLivesOverlay
{
    @SubscribeEvent
    public static void onScreenRender(ScreenEvent.Render.Post event)
    {
        if (!(event.getScreen() instanceof KnockoutScreen)) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        PlayerLivesData data = mc.player.getData(RevivalAddon.LIVES_ATTACHMENT.get());

        event.getGuiGraphics().drawCenteredString(mc.font, "Lives left: " + data.getLives(), event.getScreen().width / 2, 40, 0xFFFFFF);
    }
}
