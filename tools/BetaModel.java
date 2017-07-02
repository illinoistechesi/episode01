package tools;
import java.util.List;
import java.util.ArrayList;

public class BetaModel {
    
    public static void main(String[] args) {
        
        BetaModel model = new BetaModel();
        model.runSimulation();
        
    }
    
    private int encounters = 10;
    private double transmissionProbability = 0.3;
    private int daysToContagious = 1;
    private int daysToHealthy = 2;
    private int[] population = {95, 0, 5, 0};
    private final int MAX_TURNS = 5;
    private final boolean SHOW_TIMELINE = true;
    private List<int[]> history = new ArrayList<int[]>();
    
    public BetaModel() {
        

        
    }
    
    public void runSimulation() {
        boolean outbreakActive = true;
        int outbreakPeak = 0;
        int turns = 0;
        while (outbreakActive && turns < MAX_TURNS) {
            history.add(population);
            if (SHOW_TIMELINE) {
                String snapshot = populationToString(population, getCurrentDay());
                System.out.println(snapshot);
            }
            population = getNextPopulationLevel(population);
            int numberInfected = population[2];
            if (numberInfected > outbreakPeak) {
                outbreakPeak = numberInfected;
            }
            boolean allHealthy = numberInfected == 0;
            if (allHealthy) {
                outbreakActive = false;
            }
            else {
                turns++;
            }
        }
        int outbreakLength = getCurrentDay() + 1;
        int totalInfected = population[3];
        System.out.println("Outbreak Lasted " + outbreakLength + " days.");
        System.out.println("At peak, " + outbreakPeak + " people were infected.");
        System.out.println("In total, " + totalInfected + " people were infected.");
    }
    
    public int[] getNextPopulationLevel(int[] inpop) {
        int[] current = {0,0,0,0};
        current[0] = inpop[0];
        current[1] = inpop[1];
        current[2] = inpop[2];
        current[3] = inpop[3];
        for(int j = 0; j < history.size(); j++){
            //System.out.println(">>> " +populationToString(history.get(j), j));
        }
        int[] next = current;
        int totalEncounters = current[2] * encounters;
        if (totalEncounters > current[0]) {
            totalEncounters = current[0];
        }
        int becomeExposed = (int) Math.round(totalEncounters * transmissionProbability);
        int infectionDate = (getCurrentDay() - daysToContagious) + 1;
        int recoveryDate = (getCurrentDay() - daysToHealthy) + 1;
        int becomeInfected = 0;
        int becomeHealthy = 0;
        //System.out.println(history.size() + ", " + infectionDate + ", " + recoveryDate);
        if (infectionDate >= 0) {
            int[] entry = history.get(infectionDate);
            becomeInfected = history.get(infectionDate)[1];
            //System.out.println("Entry at index " + infectionDate + ": S: " + entry[0] + ", E: " + entry[1] + ", I: " + entry[2] + ", R: " + entry[3]);
        }
        if (recoveryDate >= 0) {
            int[] z = history.get(recoveryDate);
            becomeHealthy = history.get(recoveryDate)[2];
            //System.out.println("Entry at index " + recoveryDate + ": S: " + z[0] + ", E: " + z[1] + ", I: " + z[2] + ", R: " + z[3]);
        }
        //System.out.println(becomeInfected + ", " + becomeHealthy);
        next[0] = current[0] - becomeExposed;
        next[1] = (current[1] - becomeInfected) + becomeExposed;
        next[2] = (current[2] - becomeHealthy) + becomeInfected;
        next[3] = current[3] + becomeHealthy;
        return next;
    }
    
    public int getCurrentDay() {
        return history.size()  - 1;
    }
    
    public String populationToString(int current[], int day) {
        String out = "T: " + day + ", ";
            out += "S: " + current[0] + ", ";
            out += "E: " + current[1] + ", ";
            out += "I: " + current[2] + ", ";
            out += "R: " + current[3];
        return out;
    }
    
}