package org.minigame.game.simulation;

import org.minigame.game.GameInitializer;
import org.minigame.game.MiniGameRound;

public class GameSimulator {
    private final int numberOfRounds;

    public GameSimulator(int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }

    public double simulateGame() {
        double totalReward = 0;
        GameInitializer initializer = new GameInitializer();


        for (int i = 0; i < numberOfRounds; i++) {
            var round = new MiniGameRound(false, initializer);
            totalReward += round.playRound();
        }
        return totalReward / numberOfRounds;
    }
}
