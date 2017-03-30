/**
 * Created by wills on 2017-02-13.
 */
public class User {
    private Point location = null;
    private BaseStation bs = null;

    public User(double x, double y){
        location = new Point(x,y);
    }

    public User(double x, double y, BaseStation bs){
        location = new Point(x, y);
        this.bs = bs;
    }

    public Point getLocation(){
        return location;
    }

    public BaseStation getAssignedBS(){
        return bs;
    }

    public void setAssignedBS(BaseStation bs){
        this.bs = bs;
    }

    public double getX(){
        return location.x;
    }

    public double getY(){
        return location.y;
    }




}
