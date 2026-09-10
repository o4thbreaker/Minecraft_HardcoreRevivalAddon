package com.oathbreaker.albarevivaladdon.attachments;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class PlayerLivesData
{
    // for disc writing
    public static final Codec<PlayerLivesData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("lives").forGetter(PlayerLivesData::getLives),
                    Codec.BOOL.fieldOf("isImmortal").forGetter(PlayerLivesData::isImmortal)
            ).apply(instance, PlayerLivesData::new));

    // for sync
    public static final StreamCodec<RegistryFriendlyByteBuf, PlayerLivesData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, PlayerLivesData::getLives,
            ByteBufCodecs.BOOL, PlayerLivesData::isImmortal,
            PlayerLivesData::new);

    private int lives;
    private boolean isImmortal;

    public PlayerLivesData()
    {
        this.lives = 3;
        this.isImmortal = false;
    }

    public PlayerLivesData(int lives, boolean isImmortal)
    {
        this.lives = lives;
        this.isImmortal = isImmortal;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public boolean isImmortal() {
        return isImmortal;
    }

    public void setImmortal(boolean immortal) {
        isImmortal = immortal;
    }

    public void decrement()
    {
        if (isImmortal) return;

        if (lives > 0) lives--;
    }
}
