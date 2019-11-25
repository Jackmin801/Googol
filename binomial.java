import java.util.Scanner;

class Binomial{
    public static void main(String[] args){
        System.out.println("4");
        System.out.println(varExt("5"));
        System.out.println(biCo(3,2));
    }

    public static void binomial(String exp, int power){
        String midOp = -;
        if(exp.contains("+")){
            midOp = "+";
        }
        String[] binomials = exp.split("[+-]");
        String a = binomials[0];
        String b = binomials[1];
        int aC = coefficientExt(a);
        int aV = varExt(a);
        int aP = varExt(a);
        int bC = coefficientExt(b);
        int bV = varExt(b);
        int bP = varExt(b);
        for(int i = 0; i < power; i++){
            if(aV == ' '){
                System.out.printf("%d^%d");
            }
        }
    }

    public static int coefficientExt(String a){
        int b = 0;
        int counter = 0;
        while(a.charAt(counter) <= '9' && a.charAt(counter) >= '1'){
            b *= 10;
            b += a.charAt(counter) - '0';
            counter +=1;
        }
        return b;
    }

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

    public static int powerExt(String a){
        int b = 0;
        int counter = 0;
        while(a.charAt(counter) <= '9' && a.charAt(counter) >= '1'){
            b *= 10;
            b += a.charAt(counter) - '0';
            counter +=1;
        }
        
        counter ++;
        b = 0;
        if(a.charAt(counter) == '^'){
            counter++;
        }else{
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

    public static int biCo(int n, int r) {
        if (r == 0 || r == n) return 1;  
        return biCo(n-1, r-1) + biCo(n-1, r); 
    }
}