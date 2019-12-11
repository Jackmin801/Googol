import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class KMap{
    public static void main(String[] args){
        int bit=0;
        int counter=0,counter1=0;//counter1 for line 38 to insert output value,while counter is not specific
        boolean pass=false;//use for line 38 to be compatible to graycode
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the boolean expression below: ");
        System.out.println("Note: AND operators must always be explicitly written in the form of * or && operators");
        
        String expression = sc.nextLine();
        String[] arr = uniqChars(expression);
        
        //System.out.println(Arrays.toString(arr)); //to test whether I'm filtering out the correct characters

        int[][] truthTable = TruthTable(arr, expression);
        bit=truthTable[0].length-1;
        int [] temp=new int[(int)Math.pow(2,bit)];
        for(int i=0;i<truthTable.length;i++){   //store the output into an array follow the graycode for printing
            if(counter1>15)
                break;
            if(i==8&&pass==false){
                i+=4;
            }
            if(i==15&&pass==false){
                pass=true;
                i-=7;
            }
            if((i==2||i==6||i==10||i==14)&&arr.length!=2){
                temp[counter1]=truthTable[i+1][arr.length];
                temp[counter1+1]=truthTable[i][arr.length];
                i++;
                counter1++;
            }
            else
                temp[counter1]=truthTable[i][arr.length];
            if(i==15)
                i--;
            counter1++;
        }
          String[] test = grayGen2(bit);
//        System.out.println(Arrays.toString(test)); //Used to test out the advanced gray code generator
            //K-map
          System.out.println("K-map");
          for(int i=2;i<=arr.length+1;i++){     //generate A\BC etc.
                System.out.print(arr[i-2]);
                if(arr.length%i!=0&&i<=arr.length||arr.length==2&&(i-2)<arr.length-1)
                    System.out.print("\\");
            }
          System.out.print("\t");
          if(bit==2)
              counter=2;
          else if(bit==3)
              counter=4;
          else if(bit==4)
              counter=4;
          for(int i=0;i<counter;i++){           //print out first row of k-map
              for(int j=(int)Math.ceil(arr.length/2);j<arr.length;j++){
                  System.out.print(test[i].charAt(j));
              }
              System.out.print("\t");
          }
          System.out.println("");
          counter1=0;
          for(int i=0;i<Math.pow(2,bit);i+=counter){       //print out the consecutive line 
              for(int j=0;j<bit/2;j++){
                  System.out.print(test[i].charAt(j));
              }
              System.out.print("\t");
              for(int j=0;j<counter;j++){
                  System.out.print(temp[counter1]+"\t");
                  counter1++;
              }
              System.out.println("");
          }
    }
    

    public static String[] uniqChars(String exp) {
        // split the original string by logical operators
        // loop through the split array and store values in arraylisst
        // return arraylist as String array
        String[] unique;
        //String should only contain these few symbols
        String[] listChars = exp.split("[&!|()~+* ]");
        ArrayList<String> unique1 = new ArrayList<String>();
        for (int i = 0; i < listChars.length; i++) {
            if(!listChars[i].equals("") && (!(listChars[i].compareTo("a") >= 0 && listChars[i].compareTo("z") <= 0)&&!(
                    listChars[i].compareTo("A") >= 0 && listChars[i].compareTo("Z") <= 0 ))){
                // checks whether the split string contains any other character aside from letters or empty string
                if(listChars[i].equals("1") || listChars[i].equals("0")){
                    continue;
                }
                System.out.println("Wrong Input Format");
                return new String[0];
            }else if (!unique1.contains(listChars[i]) && !listChars[i].equals("") && listChars[i].length() <= 1) {
                unique1.add(listChars[i]);
            }
        }
 
        unique = unique1.toArray(new String[unique1.size()]);
        if(unique.length > 4){
            System.out.println("Cannot have more than 4 variables");
            return new String[0];
        }
        
        System.out.println(Arrays.toString(unique));
        return unique;
        
    }

    public static int[][] TruthTable(String[] arr, String exp){
        //if fed with an empty array, no truth table can be generated as there are no variables
        if(arr.length == 0){
            System.out.println("Unable to generate truth table");
            return new int[0][0];
        }
        int n = arr.length;
        int rows = (int) Math.pow(2, n);
        int[][] result = new int[rows][n+1];
        System.out.println("Truth Table");
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.print("Output");

        System.out.println();
        for (int i = 0; i < rows; i++) {
            //runs as many times as the number of rows
            int k = 0;
            String tempString = exp;
            for (int j = n - 1; j >= 0; j--, k++) {
                //divides i by the possible factor and modulus it by 2 to obtain remainder,
                //i.e. 15/8 = 1
                //modulus 2 to obtain 1 again, 
                //so bit4 is 1
                int store = (i / (int) Math.pow(2, j)) % 2;
                result[i][k] = store;
                System.out.print(store + " ");
            }
            
            //replacing the individual characters in the string into their current truth values in the table
            for(int j = 0; j < result[i].length - 1; j++){
                if(result[i][j] == 1){
                    tempString = tempString.replaceAll(arr[j], "1");
                }else{
                    tempString = tempString.replaceAll(arr[j], "0");
                }
            }
            //System.out.println(tempString); used to check whether I'm replacing correctly
            //System.out.println(Arrays.toString(result[i])); (used to check whether I'm storing the correct values)
            //Finds NOT operators and negates the following variable's value
            while(tempString.contains("!")){
                    int index = tempString.indexOf("!");
                    if(tempString.charAt(index+1) > '0'){
                        tempString = tempString.substring(0, index) + '1' + tempString.substring(index+1);
                    }else{
                        tempString = tempString.substring(0, index) + '0' + tempString.substring(index+1);
                    }
                }
                
                while(tempString.contains("~")){
                    int index = tempString.indexOf("~");
                    if(tempString.charAt(index+1) > '0'){
                        tempString = tempString.substring(0, index) + '1' + tempString.substring(index+1);
                    }else{
                        tempString = tempString.substring(0, index) + '0' + tempString.substring(index+1);
                    }
                }

            //formatting so that the output result looks neat
            System.out.printf(" %3d", expParser(tempString));
            result[i][k] = expParser(tempString);
            System.out.println();
        }

        return result;
    }

    public static int expParser(String expression) {
        String store = expression;
        // replacing the operators into operators that are used in the calculator
        store  = store.replaceAll("&&", "*");
        store  = store.replaceAll("\\|\\|", "+");
        //uses the calculator class but slightly modified
        Expression e = new Expression(store);
        //a boolean expression functions just like a normal algebraic expression, but the output can only be 1 or 0
        //so, modify the output so that if it's greater than 1, it outputs 1 and zero otherwise
        return  Double.parseDouble(e.calculate(e.expression)) > 0? 1:0;
    }
    
    //hardcoded version for gray code generator 
    public static String grayGen(int i, int bits){
        if(bits == 1 && i == 0){
            return "0";
        }else if(bits == 1 && i == 1){
            return "1";
        }
        
        switch(i){
            case 0: 
                return "00";
            case 2:
                return "01";
            case 3:
                return "11";
            case 4:
                return "10";
        }
        
        return "ERROR";
    }
    
    //advanced gray code generator, but is slow and is in the form of an array
    public static String[] grayGen2(int bits){
        //Gray code has a pattern where it repeats its prior bits after the MSB when the MSB is zero. 
        //When the MSB is 1, it repeats its prior bits but in reverse.
        String[] memo;
        //Default case
        if(bits == 1){
            memo = new String[]{"0","1"};
            return memo;
        }else{
            //otherwise, iterates through the array of lesser bits and adds a zero or one in front
            memo = new String[(int)Math.pow(2, bits)];
            for(int i = 0; i < (int)Math.pow(bits, 2)/2; i++){
                memo[i] = "0"+grayGen2(bits-1)[i];
            }
            for(int i = (int)Math.pow(2, bits)/2, j = (int)Math.pow(2, bits)/2-1; i < (int)Math.pow(2, bits); i++, j--){
                memo[i] = "1"+grayGen2(bits-1)[j];
            }
        }
        return memo;
    }

}
