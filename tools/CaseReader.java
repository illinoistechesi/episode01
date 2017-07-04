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
        
        CaseReader caseReader;
        
        if(args.length == 2){
            caseReader = new CaseReader(args[1]);
        }
        else if(args.length == 3){
            caseReader = new CaseReader(args[1], Integer.parseInt(args[2]));
        }
        else{
            caseReader = new CaseReader();
        }
        
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
                    String caseData = "";//CaseReader.BLOCK_LINE;
                    try{
                        String firstSymbol = line.substring(0, 1);
                        if(!firstSymbol.equals(CaseReader.COMMENT_SYMBOL)){
                            caseData = caseReader.translateCaseLine(line);   
                        }
                    }
                    catch(Exception e){
                        System.out.println("Something went wrong!");
                        e.printStackTrace();
                    }
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
    
    public static final String BLOCK_LINE = "-------------------\n";
    public static final String COMMENT_SYMBOL = "#";
    
    private String startMonth;
    private int startDay;
    
    public CaseReader(){
        this.startMonth = "January";
        this.startDay = 1;
    }
    
    public CaseReader(String startMonth){
        this.startMonth = startMonth;
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
        int idNum = Integer.parseInt(data[0]);
        String name = "Case ID: UP" + String.format("%04d", idNum);
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
        String output = BLOCK_LINE;
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