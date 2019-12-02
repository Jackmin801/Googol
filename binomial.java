import java.util.Arrays;
public class Binomial {
  public static void main(String[] args){
    String exp = "-2+5";
    binomial(exp, 3);

       
    }

    public static void binomial(String exp, int power){
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
        String[] binomials = exp.split("[+-]");
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
                finale += aV;
                if(!aV.equals(" "))
                finale += "^" + (aP*j);
                finale += " ";
            }else if(i == power && midOp ==1){
                finale += "+";
                finale += power(bC, power);
                if(!bV.equals(" "))
                finale += "^" + (bP*j);

            }
        }
        
        System.out.println(finale);
    }

    public static int coefficientExt(String a){
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
        try{ while(a.charAt(counter) <= '9' && a.charAt(counter) >= '1'){
            b *= 10;
            b += a.charAt(counter) - '0';
            counter +=1;
        }
        }catch(IndexOutOfBoundsException e){

        }
       
        
        counter ++;
        b = 0;
        try{if(a.charAt(counter) == '^'){
            counter++;
        }else{
            return 1;
        }
        }catch(IndexOutOfBoundsException e){

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
    
    static int power(int x, int power){
        return (int)Math.pow(x,power);
    }
}