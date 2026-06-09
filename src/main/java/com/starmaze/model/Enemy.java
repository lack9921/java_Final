package com.starmaze.model;

public final class Enemy {
    private Position position;
    private Direction direction;
    private int stunnedTicks;

    public Enemy(Position position, Direction direction) {
        this.position = position;
        this.direction = direction;
    }

    public Position position() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Direction direction() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int stunnedTicks() {
        return stunnedTicks;
    }

    public void stun(int ticks) {
        stunnedTicks = Math.max(stunnedTicks, ticks);
    }

    public void coolDown() {
        if (stunnedTicks > 0) {
            stunnedTicks--;
        }
    }

    public Enemy copy() {
        Enemy copy = new Enemy(position, direction);
        copy.stunnedTicks = stunnedTicks;
        return copy;
    }
}
