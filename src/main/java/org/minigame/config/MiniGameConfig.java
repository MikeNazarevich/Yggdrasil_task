package org.minigame.config;

import java.util.*;

public class MiniGameConfig {
    public static final int EXTRA_LIFE = -2;
    public static final int GAME_OVER = -1;
    public static final int SECOND_CHANCE = -3;
    public static final List<Integer> MONEY_REWARDS = List.of(100, 20, 20, 5, 5, 5, 5, 5, GAME_OVER, GAME_OVER, GAME_OVER, EXTRA_LIFE);
    public static final List<Integer> ADDITIONAL_REWARD_OPTIONS = List.of(5, 10, 20, SECOND_CHANCE);

}
