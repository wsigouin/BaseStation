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

        int gridDivisor = 64;

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

            grid[x][y].addUser(u);

            if(x > 0) {
                grid[x-1][y].addUser(u);
                if(y > 0) {
                    grid[x - 1][y - 1].addUser(u);
                }
            }
            else if(y > 0){
                grid[x][y - 1].addUser(u);
            }

            if(x < gridDivisor-1) {
                grid[x + 1][y].addUser(u);
                if(y < gridDivisor-1) {
                    grid[x + 1][y + 1].addUser(u);
                }

            }
            else if( y < gridDivisor-1) {
                grid[x][y + 1].addUser(u);
            }



        }

        for(int i = 0; i < grid.length-1; i++){
            for(int j = 0; j < grid[i].length - 1; j++){
                if(grid[i][j].hasStations + grid[i+1][j].hasStations + grid[i][j+1].hasStations + grid[i+1][j+1].hasStations == 1){
                    grid[i][j].size *= 2;
                    grid[i][j].addAllBase(grid[i+1][j].assignedBaseStations);
                    grid[i][j].addAllBase(grid[i][j+1].assignedBaseStations);
                    grid[i][j].addAllBase(grid[i+1][j+1].assignedBaseStations);

                    grid[i+1][j].removeAllBase();
                    grid[i][j+1].removeAllBase();
                    grid[i+1][j+1].removeAllBase();

                    grid[i+1][j].hasStations = 2;
                    grid[i][j+1].hasStations = 2;
                    grid[i+1][j+1].hasStations = 2;
                }
            }
        }

        for(GridSquare[] row : grid){
            for(GridSquare g : row){
                if(g.hasStations == 1) {
                    BaseStation b = g.chooseCandidateRelativeGrid();
                    if (b != null) {
                        promotedCandidates.add(b);
                    }
                }
            }
        }

        ScenarioManager.greedySolution(promotedCandidates, "solutionGridRelative.txt");


    }

}
