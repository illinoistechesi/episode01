package tools;
import java.util.List;
import java.util.ArrayList;

public class BetaModel {
    
    public static void main(String[] args) {
        BetaModel model;
        if (args.length == 6) {
            try {
                int total = Integer.parseInt(args[0]);
                double infected = Double.parseDouble(args[1]);
                double transmissionProbability = Double.parseDouble(args[2]);
                int encounterPerInfected = Integer.parseInt(args[3]);
                int daysToContagious = Integer.parseInt(args[4]);
                int daysToHealthy = Integer.parseInt(args[5]);
                model = new BetaModel(total, infected, transmissionProbability, encounterPerInfected, daysToContagious, daysToHealthy);
                model.runSimulation();
            }
            catch (Exception e) {
                System.out.println("Error: Something went wrong.");
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Warning: Impromper arguments, running default.");
            model = new BetaModel();
            model.runSimulation();
        }
    }
    
    private int encounters = 10;
    private double transmissionProbability = 0.3;
    private int daysToContagious = 1;
    private int daysToHealthy = 2;
    private int[] population = {95, 0, 5, 0};
    private final int MAX_TURNS = 1000;
    private final boolean SHOW_TIMELINE = false;
    private List<int[]> transitions = new ArrayList<int[]>();
    
    public BetaModel() {
        
    }
    
    public BetaModel(int total, double infected, double transmission, int encounters, int contagion, int recovery) {
        int numberInfected = (int) Math.round(total * infected);
        this.population[0] = total - numberInfected;
        this.population[1] = 0;
        this.population[2] = numberInfected;
        this.population[3] = 0;
        this.encounters = encounters;
        this.transmissionProbability = transmission;
        this.daysToContagious = contagion;
        this.daysToHealthy = recovery;
    }
    
    public void runSimulation() {
        boolean outbreakActive = true;
        int outbreakPeak = 0;
        int turns = 0;
        int[] initialChange = {population[1], population[2], population[3]};
        transitions.add(initialChange);
        while (outbreakActive && turns < MAX_TURNS) {
            if (SHOW_TIMELINE) {
                String snapshot = populationToString(population, getCurrentDay());
                System.out.println(snapshot);
            }
            population = getNextPopulationLevel(population);
            int numberInfected = population[2];
            if (numberInfected > outbreakPeak) {
                outbreakPeak = numberInfected;
            }
            boolean allHealthy = numberInfected <= 0;
            if (allHealthy) {
                outbreakActive = false;
            }
            else {
                turns++;
            }
        }
        int outbreakLength = getCurrentDay();
        int totalInfected = population[3];
        System.out.println("Outbreak Lasted " + outbreakLength + " days.");
        System.out.println("At peak, " + outbreakPeak + " people were infected.");
        System.out.println("In total, " + totalInfected + " people were infected.");
    }
    
    public int[] getNextPopulationLevel(int[] current) {
        int[] next = current;
        int totalEncounters = current[2] * encounters;
        if (totalEncounters > current[0]) {
            totalEncounters = current[0];
        }
        int becomeExposed = (int) Math.round(totalEncounters * transmissionProbability);
        int becomeInfected = 0;
        int becomeHealthy = 0;
        int infectionDate = (getCurrentDay() - daysToContagious) + 1;
        int recoveryDate = (getCurrentDay() - daysToHealthy) + 1;
        if (infectionDate >= 0) {
            becomeInfected = transitions.get(infectionDate)[0];
        }
        if (recoveryDate >= 0) {
            becomeHealthy = transitions.get(recoveryDate)[1];
        }
        int[] change = {becomeExposed, becomeInfected, becomeHealthy};
        transitions.add(change);
        next[0] = current[0] - becomeExposed;
        next[1] = (current[1] - becomeInfected) + becomeExposed;
        next[2] = (current[2] - becomeHealthy) + becomeInfected;
        next[3] = current[3] + becomeHealthy;
        return next;
    }
    
    public int getCurrentDay() {
        return transitions.size() - 1;
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