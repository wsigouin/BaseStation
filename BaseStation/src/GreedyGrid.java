import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wills on 2017-02-16.
 */
public class GreedyGrid {
    public static void main (String[] args) throws FileNotFoundException {
        ScenarioManager.init("scenario.txt", false);

        ArrayList<BaseStation> remainingCandidates = new ArrayList<>();

        remainingCandidates.addAll(Arrays.asList(ScenarioManager.candidates));

        int gridDivisor = 32;

        GridSquare[][] grid = new GridSquare[gridDivisor][gridDivisor];

        double size = ScenarioManager.AREA_SIZE/32;
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                grid[i][j] = new GridSquare(size, size*j-ScenarioManager.AREA_SIZE/2, ScenarioManager.AREA_SIZE/2-size*i);
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
        /*
        double bestCapacity = 0;
        while(bestCapacity < ScenarioManager.TARGET_CAPACITY) {
            bestCapacity = ScenarioManager.calculateTotalCapacity();
            int bestCandidateIndex = -1;
            double currCapacity = 0;

            System.out.println(bestCapacity + " < " + ScenarioManager.TARGET_CAPACITY);
            for (int i = 0; i < remainingCandidates.size(); i++) {
                if (i % 100 == 0) {
                    System.out.println(i * 100 / remainingCandidates.size() + "% complete");
                }
                BaseStation b = remainingCandidates.get(i);
                ScenarioManager.solutionSet.add(b);
                currCapacity = ScenarioManager.calculateTotalCapacity();

                if (currCapacity > bestCapacity) {
                    bestCapacity = currCapacity;
                    bestCandidateIndex = i;
                }

                ScenarioManager.solutionSet.remove(ScenarioManager.solutionSet.size() - 1);
            }
            ScenarioManager.solutionSet.add(remainingCandidates.get(bestCandidateIndex));
            remainingCandidates.remove(bestCandidateIndex);
        }

        ScenarioManager.saveSolution("solutionGrid.txt");
    */
    }

}
