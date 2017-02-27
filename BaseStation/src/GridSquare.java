import java.util.ArrayList;

/**
 * Created by wills on 2017-02-16.
 */
public class GridSquare {
    double size = 0;
    Point location = null;
    Point center = null;
    ArrayList<BaseStation> assignedBaseStations = new ArrayList<>();
    BaseStation centralBS = null;

    public GridSquare(double size, double x, double y){
        this.size = size;
        location = new Point(x,y);
        center = new Point(x+(size/2), y-(size/2));
    }

    public void chooseCandidate(){
        if(assignedBaseStations.size() != 0){
            BaseStation best = assignedBaseStations.get(0);
            double bestDistance = Calc.distance(best.getLocation(), this.center);
            double currDistance = 0;
            for(BaseStation b: assignedBaseStations) {
                currDistance = Calc.distance(b.getLocation(), this.center);
                if(currDistance < bestDistance){
                    best = b;
                    bestDistance = currDistance;
                }
            }

            centralBS = best;
        }
    }


    public void addBase(BaseStation b){

    }
}
