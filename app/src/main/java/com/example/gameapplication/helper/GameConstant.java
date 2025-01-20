package com.example.gameapplication.helper;

public final class GameConstant {
    public static final class Face_Dir{
        public static final int DOWN = 0;
        public static final int UP = 1;
        public static final int LEFT = 2;
        public static final int RIGHT = 3;
    }
    public static final class Maps{
        public static final int DEFAULT_SIZE = 16;
        public static final int SCALE_MULTIPLIER = 6;
        public static final int SIZE = DEFAULT_SIZE * SCALE_MULTIPLIER;
        public static final int CHARACTER_HITBOX_SIZE = 12 * SCALE_MULTIPLIER;
        public static final int X_OFFSET = 2 * SCALE_MULTIPLIER;
        public static final int Y_OFFSET = 4 * SCALE_MULTIPLIER;
    }

    public static final class Animation{
        public static final int SPEED = 10;
        public static final int AMT = 4;
    }
}
