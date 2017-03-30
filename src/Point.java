/**
 * Created by wills on 2017-01-17.
 * Simple Point class to make management of BaseStations and users easier, an element of their classes
 */
public class Point {
    double x = 0;
    double y = 0;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return "Point @ (" + x + "," + y +").";
    }

}
