import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by wills on 2017-01-17.
 * Main class for processing actual scenarios; the scenarios will be generated in a separate project
 * INITIAL CONCEPT:
 * Modularized into two parts.
 * Part A:
 * Pre-processing data, if any, before the actual algorithm is used (eg. grid squares)
 * Part B:
 * Actual algorithm on data, or processed data (eg. greedy)
 */
public class Main  {

    //constant variables given in paper
    static double TRANSMISSION_POWER_MICRO = 38; //TODO: verify units - previous work changed?
    static double TRANSMISSION_POWER_MACRO = 865;
    static double SMALL_SCALE_FADING = 1;
    static double THERMAL_NOISE = 0.0000000000000000000039810717055; //Taken from previous work, TODO: VERIFY

    static BaseStation[] macroBaseStations = null;
    static BaseStation[] candidates = null;
    static User[] users = null;

    //the final result will be saved in this structure
    static ArrayList<BaseStation> solutionSet = new ArrayList<>();

    public static void main (String[] args) throws FileNotFoundException {
        java.io.File file = new java.io.File("scenario.txt");

        java.util.Scanner in = new java.util.Scanner(file);

        //Initialize BaseStation and User locations
        int numMacro = in.nextInt();
        macroBaseStations = new BaseStation[numMacro];

        for(int i = 0; i < numMacro; i++){
            macroBaseStations[i] = new BaseStation(in.nextInt(), in.nextInt(), true);

            //Macro base stations are already there, and are hence part of the solution set by default
            solutionSet.add(macroBaseStations[i]);
        }

        int numCandidate = in.nextInt();
        candidates = new BaseStation[numCandidate];

        for(int i = 0; i < numCandidate; i++){
            candidates[i] = new BaseStation(in.nextInt(), in.nextInt(), false);
        }

        //Users are only assigned base stations after this step, as it is part of the algorithm
        int numUsers = in.nextInt();
        users = new User[numUsers];

        for(int i = 0; i < numUsers; i++){
            users[i] = new User(in.nextInt(), in.nextInt());
        }

        assignUsersToBase();
        System.out.println("done");
    }


    public static void assignUsersToBase(){
        double currStrength;
        double bestStrength;
        BaseStation currBestBase;
        double distance;

        for(int i = 0; i < users.length; i++){
            bestStrength = 0;
            currBestBase = null;
            for(int j = 0; j < solutionSet.size(); j++){
                distance = Calc.distance(users[i].getLocation(), solutionSet.get(j).getLocation());
                currStrength = (solutionSet.get(j).isMacro()?TRANSMISSION_POWER_MACRO:TRANSMISSION_POWER_MICRO)*Calc.channelGain(distance, SMALL_SCALE_FADING, solutionSet.get(j).isMacro());
                if(currStrength > bestStrength){
                    bestStrength = currStrength;
                    currBestBase = solutionSet.get(j);
                }
            }

            if(currBestBase != null){
                users[i].setAssignedBS(currBestBase);
            }
        }
    }

}
