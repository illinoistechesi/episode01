package tools;
import java.io.*;
import java.util.regex.*;

public class CaseReader {

    public static void main(String[] args){
        
        String filename = null;
        String startMonth;
        int startDay;
        FileReader fr = null;
        BufferedReader br = null;
        
        try{
            filename = args[0];
        }
        catch(Exception e){
            System.out.println("Something went wrong!");
            e.printStackTrace();
        }
        
        try{
            fr = new FileReader(filename);
            br = new BufferedReader(fr);
        }
        catch(IOException e){
            System.out.println("Something went wrong!");
            e.printStackTrace();
        }
        
        CaseReader caseReader = new CaseReader();
        
        String output = "";
        try{
            boolean endOfFile = false;
            String line = null;
            while(!endOfFile){
                line = br.readLine();
                if(line == null){
                    endOfFile = true;
                }
                else{
                    String caseData = caseReader.translateCaseLine(line);
                    output += caseData;
                }
            }
        }
        catch(IOException e){
            System.out.println("Something went wrong!");
            e.printStackTrace();
        }
        
        System.out.println("File Contents: " + filename);
        System.out.println(output);
        
    }
    
    private String startMonth;
    private int startDay;
    
    public CaseReader(){
        this.startMonth = "January";
        this.startDay = 1;
    }
    
    public CaseReader(String startMonth, int startDay){
        this.startMonth = startMonth;
        this.startDay = startDay;
    }
    
    public String getStartMonth(){
        return this.startMonth;
    }
    
    public int getStartDay(){
        return this.startDay;
    }
    
    public String translateCaseLine(String line){
        String delimiter = Pattern.quote("%");
        String[] data = line.split(delimiter);
        String name = "Case #" + data[0];
        int age = Integer.parseInt(data[1]);
        int timeSick = Integer.parseInt(data[2]);
        int dateSick = (int)Math.floor(timeSick / 24);
        String displayDateSick = getStartMonth() + " " + (getStartDay() + dateSick);
        int hourSick24Time = timeSick % 24;
        boolean isAM = hourSick24Time < 12;
        int hourSick = timeSick % 12;
        if(hourSick == 0){
            hourSick = 12;
        }
        String displayTimeSick = hourSick + ":00 ";
        if(isAM){
            displayTimeSick += "AM";
        }
        else{
            displayTimeSick += "PM";
        }
        String home = "Location " + data[3];
        String work = data[4];
        String comma = Pattern.quote(",");
        String[] history = data[5].split(comma);
        String output = "-------------------\n";
        output += name + "\n";
        output += "Started feeling sick on " + displayDateSick + " around " + displayTimeSick + "\n";
        output += "Age: " + age + "\n";
        output += "Home: " + home + "\n";
        output += "Workplace: " + work + "\n";
        output += "Outing History:\n";
        int daysSinceStart = 0;
        for(String place : history){
            String dateOfVisit = getStartMonth() + " " + (getStartDay() + daysSinceStart);
            output += "- Had lunch at Restaurant " + place + " (R) on " + dateOfVisit + "\n";
            daysSinceStart++;
        }
        return output;
    } 
    
}