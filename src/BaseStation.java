import java.util.ArrayList;

/**
 * Created by wills on 2017-02-13.
 */
public class BaseStation {
    private Point location = null;
    private boolean isMacro = false;
    private ArrayList<User> assignedUsers = new ArrayList<>();

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

    public User getUser(int index){
        return assignedUsers.get(index);
    }

    public int getNumUsers(){
        return assignedUsers.size();
    }

    public void assignUser(User u){
        assignedUsers.add(u);
    }

    public void removeUser(int index){
        assignedUsers.remove(index);
    }

    public void removeAllUsers(){
        assignedUsers.clear();
    }

    @Override
    public String toString(){
        return (isMacro?"Macro ":"Micro ") + " Base Station located at " + location;
    }
}
