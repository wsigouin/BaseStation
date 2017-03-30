import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wills on 2017-02-16.
 */
public class GreedyGrid {
    public static void main (String[] args) throws FileNotFoundException {
        ScenarioManager.init("scenario.txt", false);
        System.out.println("GRID SOLUTION");

        ArrayList<BaseStation> remainingCandidates = new ArrayList<>();
        ArrayList<BaseStation> promotedCandidates = new ArrayList<>();

        remainingCandidates.addAll(Arrays.asList(ScenarioManager.candidates));

        int gridDivisor = 32;

        GridSquare[][] grid = new GridSquare[gridDivisor][gridDivisor];

        double size = ScenarioManager.AREA_SIZE/32;
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                grid[i][j] = new GridSquare(size, (size*i)+((ScenarioManager.AREA_SIZE/2)*-1), (size*j)+((ScenarioManager.AREA_SIZE/2)*-1));
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
                    BaseStation b = g.chooseCandidate();
                    if (b != null) {
                        promotedCandidates.add(b);
                    }
                }
            }
        }

        System.out.println("NUM CANDIDATES: " + promotedCandidates.size());
        int iterationNum = 1;
        double bestCapacity = 0;
        double lastCapacity = 0;
        double lastEfficiency = 0;
        while(bestCapacity < ScenarioManager.TARGET_CAPACITY) {
            bestCapacity = ScenarioManager.calculateTotalCapacity();
            int bestCandidateIndex = -1;
            double currCapacity = 0;
            double currEfficiency = ScenarioManager.calculateEfficiency();
            System.out.println("NUM MICRO BS: " + iterationNum++);
            System.out.println("CURRENT CAPACITY: " + bestCapacity);
            System.out.println("CURRENT EFFICIENCY: " + ScenarioManager.calculateEfficiency());
            System.out.println("TARGET CAPACITY: " + ScenarioManager.TARGET_CAPACITY);
            System.out.println("CAPACITY CHANGE: " + (bestCapacity - lastCapacity));
            System.out.println("EFFICIENCY CHANGE: " + (currEfficiency - lastEfficiency));
            for (int i = 0; i < promotedCandidates.size(); i++) {
                if (i % 100 == 0) {
                    System.out.println(i * 100 / promotedCandidates.size() + "% complete");
                }
                BaseStation b = promotedCandidates.get(i);
                ScenarioManager.solutionSet.add(b);
                currCapacity = ScenarioManager.calculateTotalCapacity();

                if (currCapacity > bestCapacity) {
                    lastCapacity = bestCapacity;
                    lastEfficiency = currEfficiency;
                    bestCapacity = currCapacity;
                    bestCandidateIndex = i;
                }

                ScenarioManager.solutionSet.remove(ScenarioManager.solutionSet.size() - 1);
            }
            ScenarioManager.solutionSet.add(promotedCandidates.get(bestCandidateIndex));
            promotedCandidates.remove(bestCandidateIndex);
        }


        ScenarioManager.saveSolution("solutionGrid.txt");

    }

}
