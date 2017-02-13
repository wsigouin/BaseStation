/**
 * Created by wills on 2017-01-17.
 * Calc class holds useful functions for the various calculations of the project
 * All functions static, emulates the MATH class though with specific functions
 */
public class Calc {
    //Path loss formula taken from previous work
    //TODO: Check for correctness
    static double  pathLoss(double distance, boolean isMacro){
        if(isMacro){
            return 128.1+37.6*(Math.log10(distance));
        }
        else{
            return 140.7+37.6*(Math.log10(distance));
        }
    }
    //Channel Gain formula taken from previous work
    //TODO: Check for correctness
    static double channelGain(double distance, double smallScaleFading, boolean isMacro){
        double pLoss = pathLoss(distance, isMacro);

        pLoss = Math.pow(10, -1 * pLoss/10);
        return pLoss * smallScaleFading;
    }

    //Basic distance function modified to use the Point Class
    static double distance(Point a, Point b){
        return Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y));
    }
}
