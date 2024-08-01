package org.minigame.game.calculator;

import org.apache.commons.math3.util.CombinatoricsUtils;
import org.minigame.config.MiniGameConfig;
import org.minigame.game.GameInitializer;
import org.minigame.game.util.RewardUtils;

import java.util.List;
import java.util.Map;


public class RewardCalculatorImpl implements RewardCalculator {

    @Override
    public double calculateTotalExpectedReward() {
        GameInitializer initializer = new GameInitializer();

        Map<Integer, Integer> rewardDistribution = initializer.getGameElementsAsMap();
        Map<Integer, Integer> moneyBoxMap = RewardUtils.filterMoneyKeys(rewardDistribution);

        var totalBoxes = RewardUtils.getTotalBoxes(rewardDistribution);
        var gameOverBoxes = RewardUtils.countGameOverBoxes(rewardDistribution);
        var lifeBoxes = RewardUtils.countExtraLifeBoxes(rewardDistribution);
        List<Integer> additionalRewards = initializer.getAdditionalRewards();

        return expectedReward(totalBoxes, gameOverBoxes, moneyBoxMap, lifeBoxes, additionalRewards);
    }

    public double expectedReward(int totalBoxes, int gameOverBoxes, Map<Integer, Integer> moneyBoxMap, int lifeBoxes, List<Integer> additionalRewards) {
        var totalExpectedReward = calculateExpectedRewards(totalBoxes, gameOverBoxes, moneyBoxMap, lifeBoxes);

        if (additionalRewards.contains(MiniGameConfig.SECOND_CHANCE)) {
            totalExpectedReward += calculateRewardWithSecondChance(totalBoxes, gameOverBoxes, moneyBoxMap, lifeBoxes, additionalRewards);
        } else {
            totalExpectedReward += averageAdditionalRewards(additionalRewards);
        }

        return totalExpectedReward;
    }

    private double calculateExpectedRewards(int totalBoxes, int gameOverBoxes, Map<Integer, Integer> moneyBoxMap, int lifeBoxes) {
        var totalExpectedReward = 0.0;
        var totalMoneyBoxes = RewardUtils.getTotalBoxes(moneyBoxMap);

        for (Map.Entry<Integer, Integer> entry : moneyBoxMap.entrySet()) {
            var moneyAmount = entry.getKey();
            var count = entry.getValue();
            var expectedMoneyBoxes = calculateExpectedMoneyBoxes(totalBoxes, gameOverBoxes, lifeBoxes, count, totalMoneyBoxes);
            totalExpectedReward += expectedMoneyBoxes * moneyAmount;
        }
        return totalExpectedReward;
    }

    private double calculateExpectedMoneyBoxes(int totalBoxes, int gameOverBoxes, int lifeBoxes, int count, int totalMoneyBoxes) {
        double expectedMoneyBoxes = 0.0;
        for (var k = 0; k <= totalBoxes; k++) {
            if (totalBoxes - gameOverBoxes - lifeBoxes >= k) {
                var pNoSuccess = CombinatoricsUtils.binomialCoefficientDouble(totalBoxes - gameOverBoxes - lifeBoxes, k)
                        / CombinatoricsUtils.binomialCoefficientDouble(totalBoxes, k);
                var pSuccessNext = k < totalBoxes ? gameOverBoxes / (double) (totalBoxes - k) : 0;
                var pExtraLifeNext = k < totalBoxes ? lifeBoxes / (double) (totalBoxes - k) : 0;

                expectedMoneyBoxes += k * pNoSuccess * (
                        pSuccessNext * (1 + (totalBoxes - k - 1) * pExtraLifeNext) +
                                (1 - pSuccessNext) * pExtraLifeNext
                ) * (count / (double) totalMoneyBoxes);
            }
        }
        return expectedMoneyBoxes;
    }

    private double calculateRewardWithSecondChance(int totalBoxes, int gameOverBoxes, Map<Integer, Integer> moneyBoxMap, int lifeBoxes, List<Integer> additionalRewards) {
        additionalRewards.remove(Integer.valueOf(MiniGameConfig.SECOND_CHANCE));
        var expectedRewardWithChance = averageAdditionalRewards(additionalRewards);

        expectedRewardWithChance += 0.25 * expectedReward(totalBoxes, gameOverBoxes, moneyBoxMap, lifeBoxes, additionalRewards);
        expectedRewardWithChance *= 0.75;

        return expectedRewardWithChance;
    }

    private double averageAdditionalRewards(List<Integer> additionalRewards) {
        var sum = 0.0;
        for (var reward : additionalRewards) {
            sum += reward;
        }

        return sum / additionalRewards.size();
    }
}
