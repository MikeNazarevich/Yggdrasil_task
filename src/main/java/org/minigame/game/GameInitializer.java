package org.minigame.game;

import org.minigame.config.MiniGameConfig;

import java.util.*;

public class GameInitializer {

    public List<Integer> initializeGameBoxes() {
        Random random = new Random();
        List<Integer> boxes = new ArrayList<>(MiniGameConfig.MONEY_REWARDS);
        Collections.shuffle(boxes, random);
        return boxes;
    }

    public int getRandomAdditionalReward(boolean secondChanceUsed) {
        Random random = new Random();
        List<Integer> rewardOptions = new ArrayList<>(MiniGameConfig.ADDITIONAL_REWARD_OPTIONS);

        if (secondChanceUsed) {
            rewardOptions.remove((Integer) MiniGameConfig.SECOND_CHANCE);
        }
        return rewardOptions.get(random.nextInt(rewardOptions.size()));
    }

    public Map<Integer, Integer> getGameElementsAsMap() {
        Map<Integer, Integer> elementsMap = new HashMap<>();

        for (int reward : MiniGameConfig.MONEY_REWARDS) {
            elementsMap.put(reward, elementsMap.getOrDefault(reward, 0) + 1);
        }
        return elementsMap;
    }

    public Map<Integer, Integer> getAdditionalRewardsAsMap() {
        Random random = new Random();
        var shuffledRewards = new ArrayList<>(MiniGameConfig.ADDITIONAL_REWARD_OPTIONS);
        Collections.shuffle(shuffledRewards, random);

        Map<Integer, Integer> elementsMap = new HashMap<>();
        for (int reward : shuffledRewards) {
            elementsMap.put(reward, elementsMap.getOrDefault(reward, 0) + 1);
        }

        return elementsMap;
    }

    public List<Integer> getAdditionalRewards() {
        Random random = new Random();
        List<Integer> shuffledRewards = new ArrayList<>(MiniGameConfig.ADDITIONAL_REWARD_OPTIONS);
        Collections.shuffle(shuffledRewards, random);

        return shuffledRewards;
    }
}
