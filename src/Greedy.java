import com.sun.org.apache.xerces.internal.impl.xs.SchemaNamespaceSupport;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wills on 2017-02-14.
 */
public class Greedy {
    public static void main (String[] args) throws FileNotFoundException{
        ScenarioManager.init("scenario.txt");

        ArrayList<BaseStation> remainingCandidates = new ArrayList<>();

        remainingCandidates.addAll(Arrays.asList(ScenarioManager.candidates));

        ScenarioManager.greedySolution(remainingCandidates, "solutionGreedy.txt");

    }
}
