package model;

import java.util.*;

/**
 * A Lift carries people to their destinations.
 */
public class Lift {
    private int number;
    private int bottom;
    private int top;
    private int level;
    private int direction;
    private LinkedList<Person> passengers = new LinkedList<Person>();
    private LinkedList<Person> queue = new LinkedList<Person>();

    public Lift(int number, int bottom, int top, int level) {
        this.number = number;
        this.bottom = bottom;
        this.top = top;
        this.level = level;
        this.direction = Direction.STATIONARY;
    }

    // PROPERTIES

    public int getNumber() {
        return number;
    }

    public int getBottom() {
        return bottom;
    }

    public int getTop() {
        return top;
    }

    public int getLevel() {
        return level;
    }

    public int getDirection() {
        return direction;
    }

    public LinkedList<Person> getPassengers() {
        return passengers;
    }

    public LinkedList<Person> getQueue() {
        return queue;
    }

    // FUNCTIONS and PROCEDURES

    public void call(Person person) {
        queue.add(person);
    }

    private void board(Person person) {
        queue.remove(person);
        passengers.add(person);
        person.board();
    }

    private void alight(Person person) {
        passengers.remove(person);
        person.alight();
    }

    /**
     * This procedure carries out the operation of a lift for one step of time.
     * It is intended to be called repeatedly.
     */
    public void operate() {
        // This is slightly different from Assignment 1
        Person nextPerson = nextPerson();
        if (direction == Direction.STATIONARY) {
            if (nextPerson != null)
                direction = nextPerson.liftDirection(getLevel());
        }
        if (alight() || board()) {
            if (passengers.isEmpty())
                direction = Direction.STATIONARY;
        }
        else {
            move();
            if (isAtBoundary() || passengers.isEmpty() && anyoneWaitingHere())
                direction = Direction.STATIONARY;
        }
    }

    private boolean anyoneWaitingHere() {
        if (queue.isEmpty())
            return false;
        Person person = queue.get(0);
        return person.isAt(level);
    }

    private void move() {
        level = level + direction;
        for (Person person : passengers)
            person.move(direction);
    }

    /**
     * Determine the next person to service.
     */
    private Person nextPerson() {
        // Take the next passenger if there is one
        if (passengers.size() > 0)
            return passengers.get(0);
        // Otherwise go to pick up the next waiting if there is one
        else if (queue.size() > 0)
            return queue.get(0);
        // Otherwise there is no next person
        else
            return null;
    }

    private boolean board() {
        int count = 0;
        for (Person person : new LinkedList<Person>(queue))
            if (person.canBoard(getLevel(), direction)) {
                board(person);
                count++;
            }
        return count > 0;
    }

    private boolean alight() {
        int count = 0;
        for (Person person : new ArrayList<Person>(passengers))
            if (person.hasArrived()) {
                alight(person);
                count++;
            }
        return count > 0;
    }

    private boolean isAtBoundary() {
        return getLevel() == bottom || getLevel() == top;
    }

    private int distanceTo(int target) {
        return Math.abs(target - getLevel());
    }

    public int suitability(int distance, int start, int destination) {
        if (!canTake(start, destination))
            return 0;
        else if (direction * Direction.fromTo(getLevel(), start) < 0)
            return 1;
        else if (direction == -Direction.fromTo(start, destination))
            return distance + 1 - distanceTo(start);
        else
            return distance + 2 - distanceTo(start);
    }

    private boolean canTake(int start, int destination) {
        return canReach(start) && canReach(destination);
    }

    private boolean canReach(int level) {
        return level >= bottom && level <= top;
    }

    @Override
    public String toString() {
        return "Lift " + number;
    }
}
