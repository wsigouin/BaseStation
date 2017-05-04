import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by wills on 2017-02-14.
 */
public class GenerateScenario {
    public static void main (String[] args) throws FileNotFoundException {

        PrintWriter out = new PrintWriter("scenario.txt");
        double size = ScenarioManager.AREA_SIZE/4;
        double macroRange = ScenarioManager.AREA_SIZE*0.3;
        int maxMacro = 10;
        int minMacro = 10;
        int maxCandidates = 5000;
        int minCandidates = 2500;
        int minUsers = 100;
        int maxUsers = 100;


        int numMacro = (int)(Math.random()*(maxMacro - minMacro + 1) + minMacro);


        out.write(numMacro +"\n");

        for(int i = 0; i < numMacro; i++){
            double num1 = Math.random()*(macroRange + macroRange) - macroRange;
            double num2 = (Math.random()*(macroRange + macroRange) - macroRange);
            out.write(num1 + " " + num2 + " \n");
        }

        int numCandidates = (int)(Math.random()*(maxCandidates - minCandidates + 1) + minCandidates);

        out.write(numCandidates +"\n");

        for(int i = 0; i < numCandidates; i++){
            double num1 = Math.random()*(size + size) - size;
            double num2 = (Math.random()*(size + size) - size);
            out.write(num1 + " " + num2 + " \n");
        }

        int numUsers = (int)(Math.random()*(maxUsers - minUsers + 1) + minUsers);

        out.write(numUsers +"\n");

        for(int i = 0; i < numUsers; i++){
            double num1 = Math.random()*(size + size) - size;
            double num2 = (Math.random()*(size + size) - size);
            out.write(num1 + " " + num2 + " \n");
        }

        double efficiencyScale = 20;

        out.write(efficiencyScale + " ");

        out.close();
    }
}
