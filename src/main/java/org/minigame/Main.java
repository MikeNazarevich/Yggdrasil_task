package org.minigame;

import org.minigame.game.calculator.RewardCalculator;
import org.minigame.game.calculator.RewardCalculatorImpl;
import org.minigame.game.simulation.GameSimulator;

public class Main {

    public static void main(String[] args) {
        var numberOfRounds = 10_000_000;
        GameSimulator simulator = new GameSimulator(numberOfRounds);
        var expectedValue = simulator.simulateGame();
        System.out.printf("Expected Reward with simulation: %.1f%n", expectedValue);

        RewardCalculator rewardCalculator = new RewardCalculatorImpl();
        var totalExpectedReward = rewardCalculator.calculateTotalExpectedReward();
        System.out.printf("Expected Reward with probability theory: %.1f%n", totalExpectedReward);
    }
}