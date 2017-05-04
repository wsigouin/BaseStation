import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by wills on 2017-05-02.
 */
public class Distance {
    public static void main(String[] args) throws FileNotFoundException {
        ScenarioManager.init("scenario.txt");

        ArrayList<BaseStation> promotedCandidates = new ArrayList<>();
        promotedCandidates.addAll(Arrays.asList(ScenarioManager.candidates));

        ScenarioManager.distanceSolutionGreedy("distanceSolution.txt");
    }
}

