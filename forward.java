public class forward{

public static double calculate(String in){
    //Takes string equation eg (123+23*(1+2))$
    //outputs double eg 192
    char uwu=in.charAt(0); //current char
    double answer=0;
    boolean posi;
    //check if first number is positive or negative
    int i=0;
    if(uwu=='-'){posi=false;i=1;}else{posi=true;i=0;}
    int parenthesis=0;
    while(uwu!='$'){
        uwu=in.charAt(i);
        String temp="";
        while((uwu!='+' && uwu!='-' && uwu!='$' || parenthesis>0)){
            if(uwu=='('){parenthesis++;}
            if(uwu==')'){parenthesis--;}
            temp=temp+uwu;
            //System.out.println(uwu);
            uwu=in.charAt(++i);
        }
        System.out.println(temp);
        if(uwu=='$'){System.out.println("BREAK");break;}
        i++;
    }

    return 1;
}

public static double multi(String in){
    //recieves string input only containing multiply and divide operations
    //outputs resolve double
    int i=0;
    double result=1;
    char owo='*'; // owo is true when multiply false when dividing
    String UwU="";// a temp to store numbers
    while(i<in.length()){
        char cur=in.charAt(i);
        if(num(cur)){UwU=UwU+cur;}
        else{
            if(owo=='*'){result*=Double.valueOf(UwU);}else if(owo=='/'){result/=Double.valueOf(UwU);}else{result%=Double.valueOf(UwU);}
            System.out.println(owo);
            System.out.println(UwU);
            System.out.println(result);
            System.out.println();
            UwU="";
            owo=cur;
        }
        i++;
    }

    if(owo=='*'){result*=Double.valueOf(UwU);}else if(owo=='/'){result/=Double.valueOf(UwU);}else{result%=Double.valueOf(UwU);}
    
    return result;
}

public static boolean num(char a){
    switch(a){
        case '0': return true;
        case '1': return true;
        case '2': return true;
        case '3': return true;
        case '4': return true;
        case '5': return true;
        case '6': return true;
        case '7': return true;
        case '8': return true;
        case '9': return true;
        
    }
    return false;

}
public static void main(String[] args) {
    /*
    double a =multi("2/4*55%3*8");
    System.out.println(a++);
    System.out.println(a++);
    System.out.println(--a);
*/

    calculate("-4444+11111-2(2+2+(3*3-4+5-6))2222*22223$");



}

}