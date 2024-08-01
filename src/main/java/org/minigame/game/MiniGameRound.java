package org.minigame.game;

import org.minigame.config.MiniGameConfig;

import java.util.List;

public class MiniGameRound implements GameRound {
    private List<Integer> boxes;
    private boolean extraLife;
    private boolean gameOver;
    private boolean secondChanceUsed;
    private final GameInitializer initializer;

    public MiniGameRound(boolean secondChanceUsed, GameInitializer initializer) {
        this.secondChanceUsed = secondChanceUsed;
        this.initializer = initializer;
        initializeRound();
    }

    private void initializeRound() {
        this.boxes = initializer.initializeGameBoxes();
        this.extraLife = false;
        this.gameOver = false;
    }

    @Override
    public int playRound() {
        int rewards = 0;

        for (var box : boxes) {
            if (box == MiniGameConfig.GAME_OVER) {
                if (extraLife) {
                    extraLife = false;
                } else {
                    gameOver = true;
                    break;
                }
            } else if (box == MiniGameConfig.EXTRA_LIFE) {
                extraLife = true;
            } else {
                rewards += box;
            }
        }

        return gameOver ? handleGameOver(rewards) : rewards;
    }

    @Override
    public int handleGameOver(int rewards) {
        var additionalReward = initializer.getRandomAdditionalReward(secondChanceUsed);

        if (additionalReward == MiniGameConfig.SECOND_CHANCE) {
            secondChanceUsed = true;
            return rewards + new MiniGameRound(true, initializer).playRound();
        } else {
            return rewards + additionalReward;
        }
    }
}