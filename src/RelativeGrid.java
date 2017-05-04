import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wills on 2017-02-16.
 */
public class RelativeGrid {
    public static void main (String[] args) throws FileNotFoundException {
        ScenarioManager.init("scenario.txt");
        System.out.println("RELATIVE GRID SOLUTION");

        ArrayList<BaseStation> remainingCandidates = new ArrayList<>();
        ArrayList<BaseStation> promotedCandidates = new ArrayList<>();

        remainingCandidates.addAll(Arrays.asList(ScenarioManager.candidates));

        int gridDivisor = 32;

        GridSquare[][] grid = new GridSquare[gridDivisor][gridDivisor];

        double size = ScenarioManager.AREA_SIZE/(gridDivisor*2);
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                grid[i][j] = new GridSquare(size, (size*i)+((ScenarioManager.AREA_SIZE/4)*-1), (size*j)+((ScenarioManager.AREA_SIZE/4)*-1));
            }
        }

        double xCoord = 0;
        double yCoord = 0;
        for(BaseStation b: remainingCandidates){
            xCoord = b.getX();
            yCoord = b.getY();

            double xmod = Math.abs(xCoord/size);
            int x;
            int y;
            if(xCoord > 0){
                x = (int)(gridDivisor/2 + xmod);
            }
            else{
                x = (int)(gridDivisor/2 - xmod);
            }

            double ymod = Math.abs(yCoord/size);
            if(yCoord > 0){
                y = (int)(gridDivisor/2 - ymod);
            }
            else{
                y = (int)(gridDivisor/2 + ymod);
            }

            grid[x][y].addBase(b);
        }

        for(User u: ScenarioManager.users){
            xCoord = u.getX();
            yCoord = u.getY();

            double xmod = Math.abs(xCoord/size);
            int x;
            int y;
            if(xCoord > 0){
                x = (int)(gridDivisor/2 + xmod);
            }
            else{
                x = (int)(gridDivisor/2 - xmod);
            }

            double ymod = Math.abs(yCoord/size);
            if(yCoord > 0){
                y = (int)(gridDivisor/2 - ymod);
            }
            else{
                y = (int)(gridDivisor/2 + ymod);
            }


           for(int i = 0; i < grid.length; i ++){
               for(int j = 0; j < grid[i].length; j++){
                   if(i >= x-1 && i <= x+1 && j >= y-1 && j <= y+1){
                       grid[i][j].addUser(u);
                   }
               }
           }
        }

        for(GridSquare[] row : grid){
            for(GridSquare g : row){
                if(g.hasStations == 1) {
                    BaseStation b = g.chooseCandidateRelativeAll();
                    if (b != null) {
                        promotedCandidates.add(b);
                    }
                }
            }
        }

        ScenarioManager.greedySolution(promotedCandidates, "solutionGridRelative.txt");


    }

}
