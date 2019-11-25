import java.util.Scanner;

class Binomial{
    public static void main(String[] args){
        System.out.println(varExt("5x^7"));
    }

    public static void binomial(String exp, int power){
        String[] binomials = exp.split("[+-]");
        for(int i = 0; i < binomials.length ; i++){

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
        while(a.charAt(counter) <= '9' && a.charAt(counter) >= '1'){
            counter +=1;
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

}