package org.firstinspires.ftc.teamcode.base;

/**
 * Vector in 2 dimensions.
 */
public class Vector2d {
    /**
     * Creates a vector.
     * @param x The x position.
     * @param y The y position.
     */
    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return Gets the X position.
     */
    public double getX() {
        return x;
    }

    /**
     * @return Gets the Y position.
     */
    public double getY() {
        return y;
    }

    /**
     * Adds the given vector to this vector.
     * @param other The other vector to add to this one.
     */
    public void add(Vector2d other) {
        this.x += other.x;
        this.y += other.y;
    }

    /**
     * Subtracts the given vector from this vector.
     * @param other The other vector to subtract from this one.
     */
    public void sub(Vector2d other) {
        this.x -= other.x;
        this.y -= other.y;
    }

    /**
     * Multiply the vector by the given constant.
     * @param v The constant to multiply by.
     */
    public void mul(double v) {
        this.x *= v;
        this.y *= v;
    }

    /**
     * Divide the vector by the given constant.
     * @param v The constant to divide by.
     */
    public void div(double v) {
        this.x /= v;
        this.y /= v;
    }

    // The x position.
    private double x;
    // The y position.
    private double y;
}
