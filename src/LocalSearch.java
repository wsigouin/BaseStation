import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wills on 2017-05-01.
 */
public class LocalSearch{
        public static void main (String[] args) throws FileNotFoundException {
            ScenarioManager.init("scenario.txt");
            System.out.println("GRID SOLUTION");

            ArrayList<BaseStation> remainingCandidates = new ArrayList<>();
            ArrayList<BaseStation> promotedCandidates = new ArrayList<>();

            remainingCandidates.addAll(Arrays.asList(ScenarioManager.candidates));

            int gridDivisor = 32;

            GridSquare[][] grid = new GridSquare[gridDivisor][gridDivisor];

            double size = ScenarioManager.AREA_SIZE/(32*2);
            for(int i = 0; i < grid.length; i++){
                for(int j = 0; j < grid[i].length; j++){
                    grid[i][j] = new GridSquare(size, (size*i)+((ScenarioManager.AREA_SIZE/4)*-1), (size*j)+((ScenarioManager.AREA_SIZE/4)*-1));
                }
            }


            double xCoord = 0;
            double yCoord = 0;
            for (BaseStation b : remainingCandidates) {
                xCoord = b.getX();
                yCoord = b.getY();

                double xmod = Math.abs(xCoord / size);
                int x;
                int y;
                if (xCoord > 0) {
                    x = (int) (gridDivisor / 2 + xmod);
                } else {
                    x = (int) (gridDivisor / 2 - xmod);
                }

                double ymod = Math.abs(yCoord / size);
                if (yCoord > 0) {
                    y = (int) (gridDivisor / 2 - ymod);
                } else {
                    y = (int) (gridDivisor / 2 + ymod);
                }

                grid[x][y].addBase(b);
            }

            for (int i = 0; i < grid.length - 1; i++) {
                for (int j = 0; j < grid[i].length - 1; j++) {
                    if (grid[i][j].hasStations + grid[i + 1][j].hasStations + grid[i][j + 1].hasStations + grid[i + 1][j + 1].hasStations == 1) {
                        grid[i][j].size *= 2;
                        grid[i][j].addAllBase(grid[i + 1][j].assignedBaseStations);
                        grid[i][j].addAllBase(grid[i][j + 1].assignedBaseStations);
                        grid[i][j].addAllBase(grid[i + 1][j + 1].assignedBaseStations);

                        grid[i + 1][j].removeAllBase();
                        grid[i][j + 1].removeAllBase();
                        grid[i + 1][j + 1].removeAllBase();

                        grid[i + 1][j].hasStations = 2;
                        grid[i][j + 1].hasStations = 2;
                        grid[i + 1][j + 1].hasStations = 2;
                        System.out.println("Happened");
                    }
                }
            }

            for (GridSquare[] row : grid) {
                for (GridSquare g : row) {
                    if (g.hasStations == 1) {
                        BaseStation b = g.chooseCandidate();
                        if (b != null) {
                            promotedCandidates.add(b);
                        }
                    }
                }
            }

            ScenarioManager.greedySolution(promotedCandidates, "solutionGrid.txt");

            double bestEfficiency = ScenarioManager.calculateEfficiency();
            double currEfficiency = bestEfficiency;
            boolean changeMade = true;
            while(changeMade){
                changeMade = false;
                for(int i = ScenarioManager.macroBaseStations.length-1; i < ScenarioManager.solutionSet.size(); i++){
                    BaseStation b = ScenarioManager.solutionSet.get(i);
                    if(!b.isMacro()){
                        ScenarioManager.solutionSet.remove(b);
                        currEfficiency = ScenarioManager.calculateEfficiency();
                        if(currEfficiency > bestEfficiency && ScenarioManager.calculateTotalCapacity() > ScenarioManager.TARGET_CAPACITY){
                            changeMade = true;
                            System.out.println("Removal of Basestation at " + b.getLocation() + "for gain of " + (currEfficiency - bestEfficiency));
                            bestEfficiency = currEfficiency;
                            break;
                        }
                        else{
                            ScenarioManager.solutionSet.add(b);
                        }
                    }
                }

                if(changeMade){
                    continue;
                }

                for(int i = 0; i < ScenarioManager.candidates.length; i++){
                    BaseStation b = ScenarioManager.candidates[i];
                    if(!ScenarioManager.solutionSet.contains(b)){
                        ScenarioManager.solutionSet.add(b);
                        currEfficiency = ScenarioManager.calculateEfficiency();
                        if(currEfficiency > bestEfficiency + 20 && ScenarioManager.calculateTotalCapacity() > ScenarioManager.TARGET_CAPACITY){
                            changeMade = true;
                            System.out.println("Addition of Basestation at " + b.getLocation() + "for gain of " + (currEfficiency - bestEfficiency));
                            bestEfficiency = currEfficiency;
                            break;
                        }
                        else{
                            ScenarioManager.solutionSet.remove(b);
                        }
                    }
                }
            }

            ScenarioManager.saveSolution( "localSearch.txt");


        }
}
