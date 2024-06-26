package model;

import model.entities.Trap;
import org.json.JSONObject;
import persistence.Writable;

/*
 * Player
 * --------------
 * This is the class that represents the abstraction of the player
 *
 * This contains a lot of values directly and indirectly controlled
 *    by the player
 *
 * This also contains a number of methods that the player can use to
 *    help them survive
 */
public class Player implements Writable {
    private int health;

    private int xpos;
    private int ypos;

    public Player(int x, int y, int health) {
        this.xpos = x;
        this.ypos = y;
        this.health = health;
    }


    public int getX() {
        return xpos;
    }

    public int getY() {
        return ypos;
    }

    public int getHealth() {
        return health;
    }

    // MODIFIES: this
    // EFFECTS: moves the player up in the world until border
    public void moveUp(World w) {
        this.ypos = Math.max(0, ypos - 1);
    }

    // MODIFIES: this
    // EFFECTS: moves the player down in the world until border
    public void moveDown(World w) {
        this.ypos = Math.min(w.getHeight() - 1, ypos + 1);
    }

    // MODIFIES: this
    // EFFECTS: moves the player left in the world until border
    public void moveLeft(World w) {
        this.xpos = Math.max(0, xpos - 1);
    }

    // MODIFIES: this
    // EFFECTS: moves the player right in the world until border
    public void moveRight(World w) {
        this.xpos = Math.min(w.getWidth() - 1, xpos + 1);
    }

    // MODIFIES: w
    // EFFECTS: kills all enemies in + shape around player
    public void attack(World w) {
        w.killEnemyAt(this.xpos, this.ypos); //on player
        w.killEnemyAt(this.xpos - 1, this.ypos); //left of player
        w.killEnemyAt(this.xpos, this.ypos - 1); //up from player
        w.killEnemyAt(this.xpos + 1, this.ypos); //right of player
        w.killEnemyAt(this.xpos, this.ypos + 1); //down from player
    }

    //MODIFIES: this
    // EFFECTS: decreases health by 1
    public void takeDamage() {
        this.health--;
    }

    //EFFECTS: returns whether the player has died or not
    public boolean isDead() {
        return this.health <= 0;
    }

    // MODIFIES: world
    // EFFECTS: places a trap at the player's current position in world
    public void placeTrap(World world) {
        world.spawnTrap(new Trap(this.getX(), this.getY()));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("health", this.getHealth());
        json.put("x", this.getX());
        json.put("y", this.getY());
        return json;
    }
}
