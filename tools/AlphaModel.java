package tools;
import java.text.DecimalFormat;

public class AlphaModel {
    
    public static void main(String[] args) {
        AlphaModel model;
        if (args.length == 4) {
            try {
                int total = Integer.parseInt(args[0]);
                double infectedProportion = Double.parseDouble(args[1]);
                double beta = Double.parseDouble(args[2]);
                double gamma = Double.parseDouble(args[3]);
                model = new AlphaModel(total, infectedProportion, beta, gamma);
                model.runSimulation();
            }
            catch (Exception e){
                System.out.println("Error: Something went wrong.");
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Warning: Impromper arguments, running default.");
            model = new AlphaModel();
            model.runSimulation();
        }
    }
    
    private double beta = 0.5;
    private double gamma = 0.3;
    private double[] population = {0.95, 0.05, 0.0};
    private int total = 100;
    private double dt = 0.1;
    private final int MAX_TURNS = 1000;
    private final boolean SHOW_TIMELINE = false;
    
    public AlphaModel() {
        
    }
    
    public AlphaModel(int total, double infected, double beta, double gamma) {
        this.total = total;
        this.population[0] = 1.0 - infected;
        this.population[1] = infected;
        this.population[2] = 0.0;
        this.beta = beta;
        this.gamma = gamma;
    }
    
    public void runSimulation() {
        boolean outbreakActive = true;
        int outbreakPeak = 0;
        double turns = 0;
        while (outbreakActive && turns < MAX_TURNS) {
            boolean newDay = (turns * dt) % 1 == 0;
            if (SHOW_TIMELINE && newDay) {
                String snapshot = populationToString(population, turns * dt);
                System.out.println(snapshot);
            }
            population = getNextPopulationLevel(population);
            int numberInfected = getPeople(population[1]);
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
        double outbreakLength = turns * dt;
        int totalInfected = getPeople(population[2]);
        DecimalFormat df = new DecimalFormat("#.###");
        System.out.println("Outbreak Lasted " + df.format(outbreakLength) + " days.");
        System.out.println("At peak, " + outbreakPeak + " people were infected.");
        System.out.println("In total, " + totalInfected + " people were infected.");
    }
    
    public double[] getNextPopulationLevel(double[] current) {
        double[] next = current;
        double ds = (-1.0 * beta) * current[0] * current[1];
        double di = (beta * current[0] * current[1]) - (gamma * current[1]);
        double dr = gamma * current[1];
        next[0] = next[0] + (ds * dt);
        next[1] = next[1] + (di * dt);
        next[2] = next[2] + (dr * dt);
        return next;
    }
    
    public int getPeople(double proportion){
        return (int) Math.round(total * proportion);
    }
    
    public String populationToString(double current[], double turns) {
        String out = "T: " + turns + ", ";
            out += "S: " + getPeople(current[0]) + ", ";
            out += "I: " + getPeople(current[1]) + ", ";
            out += "R: " + getPeople(current[2]);
        return out;
    }
    
}