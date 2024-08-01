package org.minigame.game;

public interface GameRound {
    int playRound();

    int handleGameOver(int rewards);
}
