import java.util.Arrays;
import java.util.Scanner;
public class Binomial {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the expression");
        String exp = sc.nextLine();

        //split the string into an array with the delimitter as ")"
        String[] splitArray = exp.split("\\)");
        splitArray[0] = splitArray[0].replace("(", "");
        //replace the right parenthesis in the expression
        int store;
        if(splitArray.length > 1){
            //replace "^" in the 2nd index of an array to get the power
            splitArray[1] = splitArray[1].replace("^", "");
            store = Integer.parseInt(splitArray[1]);
        }else{
            store = 1;
        }
        //run the program
        binomial(splitArray[0], store);


    }
    
    public static void binomial(String exp, int power){
        //try statement to catch wrong input format
        try{
                String finale = "";
            int firstOp = 1;
            if(exp.charAt(0) == '-'){
                exp = exp.substring(1);
                firstOp = -1;
            }
            int midOp = -1;
            if(exp.contains("+")){
                midOp = 1;
            }
            //split the expression by their middle section
            String[] binomials = exp.split("[+-]");
            //obtaining the separate terms of the binomial expansion
            String a = binomials[0];
            String b = binomials[1];
            int aC = coefficientExt(a) * firstOp;
            String aV = "" + varExt(a);
            int aP = powerExt(a);
            int bC = coefficientExt(b) * midOp;
            String bV = "" + varExt(b);
            int bP = powerExt(b);

            for (int i = 0, j = power; i <= power; i++, j --){
                if(i == 0){
                    finale += power(aC, power);
                    if(!aV.equals(" ")){
                        finale += aV;
                        if (power != 1){
                            finale += "^" + (aP*j);
                        }
                    }
                    
                    finale += " ";


                }else if(i == power && midOp ==1){
                    finale += "+";
                    finale += power(bC, power);
                    if(!bV.equals(" ")){
                        finale += bV;
                        if (power != 1){
                            finale += "^" + (bP*power);
                        }
                        
                    }
                    finale += " ";
                }else if(i == power && midOp == -1){
                    finale += power(bC, power);
                    if(!bV.equals(" ")){
                        finale += bV;
                        if (power != 1){
                            finale += "^" + (bP*power);
                        }
                    }
                    
                    finale += " ";
                }else if(j%2 == 0 && midOp == 1 || i%2 == 0 && firstOp == 1 || firstOp == -1 && midOp == -1 && power%2 == 0 || firstOp == 1 && midOp == 1){
                    finale += "+";
                    finale += (power(aC,j)* power(bC, i) * biCo(power, i));
                    if(!aV.equals(" ")){
                        finale += aV;
                        if(aP*j != 1)
                        finale += "^" + (aP*j);
                    }
                    if(!bV.equals(" ")){
                        finale += bV;
                        if(bP*i != 1)
                        finale += "^" + (bP*i);
                    }
                    finale += " ";
                }else{
                    finale += (power(aC,j)* power(bC, i) * biCo(power, i));
                    if(!aV.equals(" ")){
                        finale += aV;
                        if(aP*j != 1)
                        finale += "^" + (aP*j);
                    }
                    if(!bV.equals(" ")){
                        finale += bV;
                        if(bP*i != 1)
                        finale += "^" + (bP*i);
                    }
                    finale += " ";
                }
            }
            
            System.out.println(finale);
        }catch(IndexOutOfBoundsException e){
            System.out.println("Wrong formatting");
        }
        
    }

    //method for extracting coefficient
    static int coefficientExt(String a){
        int b = 0;
        int counter = 0;
        try{
          while(a.charAt(counter) <= '9' && a.charAt(counter) >= '1'){
            b *= 10;
            b += a.charAt(counter) - '0';
            counter +=1;
          }
        }
        catch(IndexOutOfBoundsException e){

        }
        return b;
      }
        
    
    //method for extracting variable
    public static char varExt(String a){
        int counter = 0;
        try{
            while(a.charAt(counter) <= '9' && a.charAt(counter) >= '1'){
                counter +=1;
            }
        }catch(IndexOutOfBoundsException e){
            return ' ';
        }

        return a.charAt(counter);
    }

    //method for extracting power
    public static int powerExt(String a){
        int b = 0;
        int counter = 0;
        try{ while(a.charAt(counter) <= '9' && a.charAt(counter) >= '1'){
            b *= 10;
            b += a.charAt(counter) - '0';
            counter +=1;
        }
        }catch(IndexOutOfBoundsException e){

        }
       
        
        counter ++;
        b = 0;
        try{
            if(a.charAt(counter) == '^'){
            counter++;
            }
        }catch(IndexOutOfBoundsException e){
            return 1;
        }
        
        try{
            while(a.charAt(counter) <= '9' && a.charAt(counter) >= '1'){
                b *= 10;
                b += a.charAt(counter) - '0';
                counter +=1;
            }
        }catch(IndexOutOfBoundsException e){

        }
        
        return b;
    }

    //binomial coefficients calculator
    public static int biCo(int n, int r) {
        if (r == 0 || r == n) return 1;  
        return biCo(n-1, r-1) + biCo(n-1, r); 
    }
    
    //returning int version of Math.pow
    static int power(int x, int power){
        return (int)Math.pow(x,power);
    }
}