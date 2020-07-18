package composite;

import java.util.Objects;

public class Point {
    private int x,y;

    public Point (int x,int y){
        this.x=x; this.y=y;
    }
    public Point(){}

    public Point(Point p){
        this.x=p.getX();
        this.y=p.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Point)) return false;
        if (this == o) return true;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
