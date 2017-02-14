import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by wills on 2017-02-14.
 */
public class GenerateScenario {
    public static void main (String[] args) throws FileNotFoundException {

        PrintWriter out = new PrintWriter("scenario.txt");

        int numMacro = (int)(Math.random()*(200 - 50 + 1) + 50);

        out.write(numMacro +"\n");

        for(int i = 0; i < numMacro; i++){
            double num1 = Math.random()*(5000 + 5000) - 5000;
            double num2 = (Math.random()*(5000 + 5000) - 5000);
            out.write(num1 + " " + num2 + " \n");
        }

        int numCandidates = (int)(Math.random()*(1000 - 500 + 1) + 500);

        out.write(numCandidates +"\n");

        for(int i = 0; i < numCandidates; i++){
            double num1 = Math.random()*(5000 + 5000) - 5000;
            double num2 = (Math.random()*(5000 + 5000) - 5000);
            out.write(num1 + " " + num2 + " \n");
        }

        int numUsers = (int)(Math.random()*(10000 - 5000 + 1) + 5000);

        out.write(numUsers +"\n");

        for(int i = 0; i < numUsers; i++){
            double num1 = Math.random()*(5000 + 5000) - 5000;
            double num2 = (Math.random()*(5000 + 5000) - 5000);
            out.write(num1 + " " + num2 + " \n");
        }

        double efficiencyScale = Math.random()*(3 - 1 + 1);

        out.write(efficiencyScale + " ");

        out.close();
    }
}
