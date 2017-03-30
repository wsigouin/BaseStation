import com.sun.org.apache.xerces.internal.impl.xs.SchemaNamespaceSupport;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wills on 2017-02-14.
 */
public class Greedy {
    public static void main (String[] args) throws FileNotFoundException{
        ScenarioManager.init("scenario.txt", false);

        ArrayList<BaseStation> remainingCandidates = new ArrayList<>();

        remainingCandidates.addAll(Arrays.asList(ScenarioManager.candidates));

        System.out.println("NUM CANDIDATES: " + remainingCandidates.size());

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
            for (int i = 0; i < remainingCandidates.size(); i++) {
                if (i % 100 == 0) {
                    System.out.println(i * 100 / remainingCandidates.size() + "% complete");
                }
                BaseStation b = remainingCandidates.get(i);
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
            ScenarioManager.solutionSet.add(remainingCandidates.get(bestCandidateIndex));
            remainingCandidates.remove(bestCandidateIndex);
        }

        ScenarioManager.saveSolution("solution.txt");
    }
}
