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
    int hasStations = 0;

    public GridSquare(double size, double x, double y){
        this.size = size;
        location = new Point(x,y);
        center = new Point(x+(size/2), y-(size/2));
    }

    public BaseStation chooseCandidate(){
        BaseStation best = null;
        if(assignedBaseStations.size() != 0){
            best = assignedBaseStations.get(0);
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
        return best;
    }


    public void addBase(BaseStation b){
        assignedBaseStations.add(b);
        hasStations = 1;
    }

    public void addAllBase(ArrayList<BaseStation> list){
        assignedBaseStations.addAll(list);
        if(assignedBaseStations.size() != 0){
            hasStations = 1;
        }
    }

    public boolean removeBase(BaseStation b){
        if(assignedBaseStations.contains(b)) {
            assignedBaseStations.remove(b);
            if(assignedBaseStations.size() == 0){
                hasStations = 0;
            }
            return true;
        }
        return false;
    }

    public void removeAllBase(){
        assignedBaseStations.clear();
        hasStations = 0;
    }

    @Override
    public String toString(){
        String toString =  "Grid Square at " + location.toString() + " with base stations at: \n";

        for(BaseStation b: assignedBaseStations){
            toString += "\t" + b.toString();
        }

        return toString;
    }
}
