import java.io.FileNotFoundException;

/**
 * Created by wills on 2017-05-02.
 */
public class Test {
    public static void main (String[] args) throws FileNotFoundException{
        ScenarioManager.init("scenario.txt");

        System.out.println(ScenarioManager.calculateTotalCapacity());
    }
}
