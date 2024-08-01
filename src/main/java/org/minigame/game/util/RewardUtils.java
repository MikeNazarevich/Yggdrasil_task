package org.minigame.game.util;

import org.minigame.config.MiniGameConfig;

import java.util.Map;
import java.util.stream.Collectors;

public class RewardUtils {
    public static int countGameOverBoxes(Map<Integer, Integer> map) {
        return map.getOrDefault(MiniGameConfig.GAME_OVER, 0);
    }

    public static int countExtraLifeBoxes(Map<Integer, Integer> map) {
        return map.getOrDefault(MiniGameConfig.EXTRA_LIFE, 0);
    }

    public static int getTotalBoxes(Map<Integer, Integer> map) {
        return map.values().stream().mapToInt(Integer::intValue).sum();
    }

    public static Map<Integer, Integer> filterMoneyKeys(Map<Integer, Integer> map) {
        return map.entrySet().stream()
                .filter(entry -> entry.getKey() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
