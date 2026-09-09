package com.oathbreaker.albarevivaladdon.attachments;

import com.mojang.serialization.Codec;

public class PlayerLivesData
{
    public static final Codec<PlayerLivesData> CODEC = Codec.INT.xmap(PlayerLivesData::new, PlayerLivesData::getLives);

    private int lives;

    public PlayerLivesData(int lives)
    {
        this.lives = lives;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void decrement()
    {
        lives--;
    }
}
