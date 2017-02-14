import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by wills on 2017-01-17.
 * Main class for processing actual scenarios; the scenarios will be generated in a separate project
 * INITIAL CONCEPT:
 * Modularized into two parts.
 * Part A:
 * Pre-processing data, if any, before the actual algorithm is used (eg. grid squares).
 * Also all approaches to the solution. (eg. greedy)
 * This would be outside this class
 * Part B:
 * The necessary functions are calculations on the data, such as efficiency and capacity that are
 * independent of the candidate selection process.
 * This file contains this part.
 */
public class ScenarioManager {

    //constant variables given in paper
    static double TRANSMISSION_POWER_MICRO = 38; //TODO: verify units - previous work changed?
    static double TRANSMISSION_POWER_MACRO = 865;
    static double POWER_CONSUMPTION_MICRO = 0.5;
    static double POWER_CONSUMPTION_MACRO = 20;
    static double SMALL_SCALE_FADING = 1;
    static double THERMAL_NOISE = 0.0000000000000000000039810717055; //Taken from previous work, TODO: VERIFY
    static int RESOURCE_BLOCKS = 50; //from paper
    static double BANDWITH = 10; //paper

    static double TARGET_CAPACITY = 0;
    static BaseStation[] macroBaseStations = null;
    static BaseStation[] candidates = null;
    static User[] users = null;

    //the final result will be saved in this structure
    static ArrayList<BaseStation> solutionSet = new ArrayList<>();

    public static void init(String filename, boolean newScenario) throws FileNotFoundException {
        if(newScenario){
            generateScenario(filename);
        }
        java.io.File file = new java.io.File(filename);

        java.util.Scanner in = new java.util.Scanner(file);

        //Initialize BaseStation and User locations
        int numMacro = in.nextInt();
        macroBaseStations = new BaseStation[numMacro];

        for(int i = 0; i < numMacro; i++){
            macroBaseStations[i] = new BaseStation(in.nextDouble(), in.nextDouble(), true);

            //Macro base stations are already there, and are hence part of the solution set by default
            solutionSet.add(macroBaseStations[i]);
        }

        int numCandidate = in.nextInt();
        candidates = new BaseStation[numCandidate];

        for(int i = 0; i < numCandidate; i++){
            candidates[i] = new BaseStation(in.nextDouble(), in.nextDouble(), false);
        }

        //Users are only assigned base stations after this step, as it is part of the algorithm
        int numUsers = in.nextInt();
        users = new User[numUsers];

        for(int i = 0; i < numUsers; i++){
            users[i] = new User(in.nextDouble(), in.nextDouble());
        }

        double capacityFactor = in.nextDouble();

        TARGET_CAPACITY = calculateTotalCapacity() * capacityFactor;
    }

    /**
     * Assigns all users to a base, based on which has the strongest signal.
     * The base stations also keep track of the users which are connected.
     * Is called before ever efficiency calculation.
     */
    public static void assignUsersToBase(){
        double currStrength;
        double bestStrength;
        BaseStation currBestBase;
        double distance;

        //clear all assigned users before adding new ones
        for(int i = 0; i < solutionSet.size(); i ++){
            solutionSet.get(i).removeAllUsers();
        }

        for(int i = 0; i < users.length; i++){
            bestStrength = 0;
            currBestBase = null;
            for(int j = 0; j < solutionSet.size(); j++){
                distance = Calc.distance(users[i].getLocation(), solutionSet.get(j).getLocation());
                currStrength = (solutionSet.get(j).isMacro()?TRANSMISSION_POWER_MACRO:TRANSMISSION_POWER_MICRO)
                        * Calc.channelGain(distance, SMALL_SCALE_FADING, solutionSet.get(j).isMacro());

                if(currStrength > bestStrength){
                    bestStrength = currStrength;
                    currBestBase = solutionSet.get(j);
                }
            }

            //add user to base, and add base to user
            if(currBestBase != null){
                users[i].setAssignedBS(currBestBase);
                currBestBase.assignUser(users[i]);
            }
        }
    }

    /**
     * Calculated the Signal to Interference plus Noise Ratio of a specific user's connection
     * to a base
     * @param currUser  the user being assessed, it knows which base station it is connected to
     * @return N/A      A ratio of the signal strength to the interference/noise strength
     */
    public static double calculateSINR(User currUser){
        BaseStation base = currUser.getAssignedBS();
        double distance = Calc.distance(currUser.getLocation(), base.getLocation());

        double signal = (base.isMacro()?POWER_CONSUMPTION_MACRO:POWER_CONSUMPTION_MICRO)
                * Calc.channelGain(distance, SMALL_SCALE_FADING, base.isMacro());

        double noise = THERMAL_NOISE;
        double interference = 0;
        for(int i = 0; i < solutionSet.size(); i++){
            if(base != solutionSet.get(i)) {
                distance = Calc.distance(currUser.getLocation(), solutionSet.get(i).getLocation());
                interference +=
                        (solutionSet.get(i).isMacro()?POWER_CONSUMPTION_MACRO:POWER_CONSUMPTION_MICRO)
                        * Calc.channelGain(distance, SMALL_SCALE_FADING, solutionSet.get(i).isMacro());
            }
        }

        return signal/(interference + noise);
    }

    public static double calculateTotalCapacity(){
        assignUsersToBase();
        double totalCapacity = 0;
        for(int i = 0; i < solutionSet.size(); i ++){
            totalCapacity += calculateBaseStationCapacity(solutionSet.get(i));
        }

        return totalCapacity;
    }

    /**
     * Returns the overall capacity of all users connected to a specific base station (base)
     * @param   base        The base station in which all user's capacity is being assessed
     * @return  bits/sec    the overall capacity of all users connected ot this Base Station
     */
    public static double calculateBaseStationCapacity(BaseStation base){
        double capacity = 0;
        if(base.getNumUsers() != 0){
            int subcarrierGroupOneSize = RESOURCE_BLOCKS % base.getNumUsers();

            int groupOneSubcarriers = 12 * ((RESOURCE_BLOCKS/base.getNumUsers()) + 1);
            int groupTwoSubcarriers = 12 * (RESOURCE_BLOCKS/base.getNumUsers());

            //number of subcarriers divided amongst all users on the tower at one time
            for(int i = 0; i < base.getNumUsers(); i++){
                if(i < subcarrierGroupOneSize){
                    capacity += (BANDWITH
                            * (Math.log10(1 + calculateSINR(base.getUser(i))) / Math.log10(2)))
                            * groupOneSubcarriers;
                }
                else{
                    capacity += (BANDWITH
                            * (Math.log10(1 + calculateSINR(base.getUser(i))) / Math.log10(2)))
                            * groupTwoSubcarriers;
                }
            }
        }

        return capacity;
    }

    /**
     * Utilizes current "solutionSet" to calculate the overall efficiency
     * of the current network configuration. Returns a value in bit/Joule.
     * @return  bit/Joule   the overall efficiency of the network
     */
    public static double calculateEfficiency(){

        double totalCapacity = calculateTotalCapacity();

        double powerConsumption = POWER_CONSUMPTION_MACRO * macroBaseStations.length
                + POWER_CONSUMPTION_MICRO * (solutionSet.size() - macroBaseStations.length);

        return totalCapacity/powerConsumption;
    }

    public static void generateScenario(String filename) throws FileNotFoundException{
        PrintWriter out = new PrintWriter(filename);

        int numMacro = (int)(Math.random()*(10 - 5 + 1) + 5);

        out.write(numMacro +"\n");

        for(int i = 0; i < numMacro; i++){
            double num1 = Math.random()*(5000 + 5000) - 5000;
            double num2 = (Math.random()*(5000 + 5000) - 5000);
            out.write(num1 + " " + num2 + " \n");
        }

        int numCandidates = (int)(Math.random()*(100 - 50 + 1) + 50);

        out.write(numCandidates +"\n");

        for(int i = 0; i < numCandidates; i++){
            double num1 = Math.random()*(5000 + 5000) - 5000;
            double num2 = (Math.random()*(5000 + 5000) - 5000);
            out.write(num1 + " " + num2 + " \n");
        }

        int numUsers = (int)(Math.random()*(4000 - 500 + 1) + 500);

        out.write(numUsers +"\n");

        for(int i = 0; i < numUsers; i++){
            double num1 = Math.random()*(5000 + 5000) - 5000;
            double num2 = (Math.random()*(5000 + 5000) - 5000);
            out.write(num1 + " " + num2 + " \n");
        }

        double efficiencyScale = Math.random()*(9 + 2);

        out.write(efficiencyScale + " ");

        out.close();
    }

    public static void saveSolution(String filename) throws FileNotFoundException{
        PrintWriter out = new PrintWriter(filename);
        out.println("Number of Macro Base Stations: " + macroBaseStations.length);
        out.println("Number of Micro Base Stations: " + (solutionSet.size() - macroBaseStations.length));
        out.println("Total number of Base Stations: " + solutionSet.size());

        out.println("\nBase Stations : ");
        for(int i = 0; i < solutionSet.size(); i++){
            out.println("Base Station " + i + ": ");
            out.println((solutionSet.get(i).isMacro()?"Macro BS ":"Micro BS")
                    + " at " + solutionSet.get(i).getLocation());
        }
        out.close();
    }

}
