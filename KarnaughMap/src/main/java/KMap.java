import java.util.ArrayList;
import java.util.Arrays;
import org.mariuszgromada.math.mxparser.*;
import java.util.Scanner;

public class KMap{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the boolean expression below: ");
        System.out.println("Note: AND operators must always be explicitly written in the form of parentheses or * and && operators");
        
        String expression = sc.nextLine();
        
        String[] arr = uniqChars(expression);
        
        //System.out.println(Arrays.toString(arr)); to test whether I'm filtering out the correct characters

        int[][] truthTable = TruthTable(arr, expression);

        
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
            if (!unique1.contains(listChars[i]) && !listChars[i].equals("") && listChars[i].length() <= 1) {
                unique1.add(listChars[i]);
            } else if(!listChars[i].equals("") && (listChars[i].compareToIgnoreCase("a") >= 0 && listChars[i].compareToIgnoreCase("z") <= 0)){
                // checks whether the split string contains any other character aside from letters or empty string
                System.out.println("Wrong Input Format");
                return new String[0];
            }
        }

        unique = unique1.toArray(new String[unique1.size()]);
        if(unique.length > 4){
            System.out.println("Cannot have more than 4 variables");
            return new String[0];
        }
        
        System.out.println(Arrays.toString(unique));
        return unique;
        /*
         * } catch (IllegalArgumentException e) {
         * System.out.println("Wrong input format"); String[] arr = new String[0];
         * return arr; }
         */

    }

    public static int[][] TruthTable(String[] arr, String exp){
        if(arr.length == 0){
            System.out.println("Unable to generate truth table");
            return new int[0][0];
        }
        int n = arr.length;
        int rows = (int) Math.pow(2, n);
        int[][] result = new int[rows][n+1];
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
            System.out.printf(" %3d", expParser(tempString));
            result[i][k] = expParser(tempString);
            System.out.println();
        }

        return result;
    }

    public static int expParser(String expression) {
//        Constant T = new Constant("1 = 1");
//        Constant F = new Constant("0 = 0");
        // replacing the operators into operators that can be found in the parsing library
        expression  = expression.replaceAll("!", "~");
        expression  = expression.replaceAll("\\*", "&&");
        expression  = expression.replaceAll("\\+", "||");
        Expression e = new Expression(expression);
        return (int)e.calculate();
    }
}
