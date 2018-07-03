package model;

/**
 * A person boards and alights lifts.
 */
public class Person {
    private int id;
    private String name;
    private int level;
    private int destination;
    private boolean aboard;

    public Person(int id, String name, int level) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.destination = level;
    }

    // PROPERTIES

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getDestination() {
        return destination;
    }

    public boolean isAboard() {
        return aboard;
    }

    // FUNCTIONS and PROCEDURES

    public void call(int destination) {
        this.destination = destination;
    }

    public void move(int direction) {
        level = level + direction;
    }

    public boolean hasId(int id) {
        return this.id == id;
    }

    public boolean canBoard(int liftLevel, int liftDirection) {
        return isAt(liftLevel) && isHeadingIn(liftDirection);
    }

    public boolean isAt(int level) {
        return this.level == level;
    }

    public boolean isHeadingIn(int direction) {
        return direction == direction();
    }

    public int direction() {
        return Direction.fromTo(level, destination);
    }

    public boolean isIdle() {
        return !aboard && level == destination;
    }

    public boolean isWaiting() {
        return !aboard && level != destination;
    }

    public boolean hasArrived() {
        return level == destination;
    }

    /**
     * Determine the direction that this person wants the lift to go in.
     */
    public int liftDirection(int liftLevel) {
        return Direction.fromTo(liftLevel, level == liftLevel ? destination : level);
    }

    public void board() {
        aboard = true;
    }

    public void alight() {
        aboard = false;
    }

    @Override
    public String toString() {
        return name;
    }
}
