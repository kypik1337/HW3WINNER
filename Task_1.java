

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Task_1{

private static String fileName = null;

    public static void main(String[] args) {
        dataFile(checkUserInput(getUserInput()));  
    }

    public static String[] getUserInput(){
        System.out.println("Input your data. Use the following formats:");
        System.out.println("1. 'Last name, First name, Midle name'");
        System.out.println("2.  Date of birth:'dd.mm.yyyy'");
        System.out.println("3.  Phone:'111111111111'");
        System.out.println("4.  Sex:'f or m'");
        System.out.println("Use whitespace for separation:");
        String [] userInputArray = null;
        boolean status=true;
        while(status){
          try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            String userInput = bufferedReader.readLine();
            if(userInput.isBlank()){
                throw new IllegalArgumentException("Wrong input format! You entered empty or blank string.Try again!");
            }
            userInputArray = userInput.split(" ");
            } catch (IOException ex) {
             System.out.println("There are some IO problems.");
             ex.printStackTrace();
          }
        if (userInputArray.length!=4){
            throw new IllegalArgumentException("Wrong input format! You entered wrong numbers of the parameters. Try again!");
        }else {
            status=false;
        }
        
    }
    return userInputArray;
}

public static HashMap<String,String> checkUserInput(String [] arrayStr){
    int sexLen=1;
    int dateLen=10;
    HashMap<String, String> resultData = new HashMap<String,String>();
    for(int i=0; i<arrayStr.length;i++){
        if (arrayStr[i].length()==sexLen && (arrayStr[i].equalsIgnoreCase("f") || arrayStr[i].equalsIgnoreCase("m"))){
            resultData.put("Sex", arrayStr[i]);
        } else if (arrayStr[i].length()==dateLen && arrayStr[i].indexOf(".")==2){
            if (checkDate(arrayStr[i])){
                resultData.put("Date of birth", arrayStr[i]);
            }
        } else if (arrayStr[i].contains(",")){
            if (checkFullName(arrayStr[i])){
                resultData.put("Full name", arrayStr[i]);
            }
        } else if(!arrayStr[i].contains(".") && !arrayStr[i].contains(",") ){
            if (checkPhone(arrayStr[i])){
                resultData.put("Phone number", arrayStr[i]);
            }   
        } else{
            throw new IllegalArgumentException("Wrong input format. Check your input");
        }
    }
    return resultData;
}
    
public static boolean checkDate(String dateStr) {
    boolean result=false;
    String [] tempArray=dateStr.split("\\.");
    if (tempArray.length!=3){
        throw new IllegalArgumentException("Wrong DATE OF BIRTH format. You should use 'dd.mm.yyyy' format. Check please "+ dateStr);
    }else{
        if (Integer.parseInt(tempArray[2])>1900 && Integer.parseInt(tempArray[2])<2100){
            if (Integer.parseInt(tempArray[1])>0 && Integer.parseInt(tempArray[1])<13){
                if (Integer.parseInt(tempArray[1])==4 || Integer.parseInt(tempArray[1])==6 || Integer.parseInt(tempArray[1])==9 || Integer.parseInt(tempArray[1])==11 ){
                    if (Integer.parseInt(tempArray[0])<1 || Integer.parseInt(tempArray[0])>30){
                        throw new IllegalArgumentException("Wrong DAY format. Check "+ tempArray[0]+" ,should be in range [1..30].");
                    }
                } else if (Integer.parseInt(tempArray[1])==2){
                    if (Integer.parseInt(tempArray[0])<1 || Integer.parseInt(tempArray[0])>28){
                        throw new IllegalArgumentException("Wrong DAY format. Check "+ tempArray[0]+" ,should be in range [1..28].");
                    }   
                } else {
                    if (Integer.parseInt(tempArray[0])<1 || Integer.parseInt(tempArray[0])>31){
                        throw new IllegalArgumentException("Wrong DAY format. Check "+ tempArray[0]+" ,should be in range [1..31].");
                    } 
                }
            result=true;
            }else {
                  throw new IllegalArgumentException("Wrong MONTH format. Check "+ tempArray[1]+" ,should be in range [1..12].");
            }
        }else {
            throw new IllegalArgumentException("Wrong YEAR format. Check "+ tempArray[2]+" ,should be in range [1900..2100].");
        }
    }
    return result;
}


public static boolean checkFullName(String nameStr) {
    boolean result=false;
    String pattern="\\D+";
    String [] tempArray=nameStr.split(",");
    fileName=tempArray[0]+".txt";
    if (tempArray.length!=3){
        throw new IllegalArgumentException("Wrong FULL NAME format. You should use 'Last name, First name, Midle name' format. Check please "+nameStr);
    }else{
        for(int i=0; i<tempArray.length;i++){
            if(!Pattern.matches(pattern, tempArray[i])){
                throw new IllegalArgumentException ("Wrong '" + tempArray[i] + "' format. The FULL NAME cannot contain digits.");
            }
        }
        result=true;
    }
    return result;
}


public static boolean checkPhone(String phoneStr) {
    boolean result=false;
    try {
        Double.parseDouble(phoneStr);
        result=true;
    }
    catch (NumberFormatException exception) {
        System.out.println("Wrong PHONE NUMBER format. Only number is premised. Check please "+phoneStr);
    }
    return result;
}

public static void dataFile(HashMap<String,String> data) {
    String currPathName = System.getProperty("user.dir");
    File currUserFile = new File(currPathName, fileName);
    try (FileWriter userData = new FileWriter(currUserFile,true);){
        for (HashMap.Entry<String, String> item : data.entrySet()) {
                userData.write(item.getKey()+" - ");
                userData.write(item.getValue()+";"+"\n");
        }
    System.out.println("File '"+fileName+"' was created successfully "+" in the folder '"+currPathName+"'.");
    } catch (IOException e) {
        System.out.println("Cannot create file "+ fileName +".");
        e.printStackTrace();
    } 
}
}