import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by wills on 2017-02-14.
 */
public class GenerateScenario {
    public static void main (String[] args) throws FileNotFoundException {

        PrintWriter out = new PrintWriter("scenario.txt");
        double size = ScenarioManager.AREA_SIZE/2;
        int maxMacro = 500;
        int minMacro = 100;
        int maxCandidates = 10000;
        int minCandidates = 5000;
        int minUsers = 200;
        int maxUsers = 200;


        int numMacro = (int)(Math.random()*(maxMacro - minMacro + 1) + minMacro);


        out.write(numMacro +"\n");

        for(int i = 0; i < numMacro; i++){
            double num1 = Math.random()*(size + size) - size;
            double num2 = (Math.random()*(size + size) - size);
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

        double efficiencyScale = Math.random()*(1.5 - 1.1) + 1.1;

        out.write(efficiencyScale + " ");

        out.close();
    }
}
