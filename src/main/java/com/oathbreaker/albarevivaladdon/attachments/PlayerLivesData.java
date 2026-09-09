package com.oathbreaker.albarevivaladdon.attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class PlayerLivesData
{
    public static final Codec<PlayerLivesData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("lives").forGetter(PlayerLivesData::getLives),
                    Codec.BOOL.fieldOf("isInfinite").forGetter(PlayerLivesData::isImmortal)
            ).apply(instance, PlayerLivesData::new));

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
