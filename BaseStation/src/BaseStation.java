/**
 * Created by wills on 2017-02-13.
 */
public class BaseStation {
    private Point location = null;
    private boolean isMacro = false;

    public BaseStation(double x, double y, boolean isMacro){
        location = new Point(x, y);
        this.isMacro = isMacro;
    }

    public Point getLocation(){
        return location;
    }

    public boolean isMacro(){
        return isMacro;
    }

    public double getX(){
        return location.x;
    }

    public double getY(){
        return location.y;
    }
}
