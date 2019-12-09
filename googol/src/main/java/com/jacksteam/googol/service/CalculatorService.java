package com.jacksteam.googol.service;

import java.util.ArrayList;
//modulus 
//powers

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CalculatorService{
    /**
     * calculate(String equation) takes in string equation and outputs evaluated value
     * The rest I give to God liao hahahaha
     */
    @Autowired
    public CalculatorService(){

    }

    public String calcheck(String in){
        //recieves string input formula and returns string reversed with $ appended
        //if it is valid and error message if it is not 

        //cannot start with operator unless -
        if(oper(in.charAt(0))!='z' && in.charAt(0)!='-'){return "illegal start of expression";}
        //cannot end on operator
        if(oper(in.charAt(in.length()-1))!='z'){return "illegal end of expression";}

        int para=0;
        int i=0;
        int l=in.length();
        char cur=in.charAt(i);

        // cannot close parenthesis that arent opened, cannot contain chars that arent number ,operators or parenthesis
        while(para!=-1 && (num(cur) || oper(cur)!='z' || cur=='(' || cur==')')){
            //System.out.println(cur);
            //chars now consist of only operators, numbers and parenthesis

            //if operator except the first one
            if(oper(cur)!='z' && i!=0){
                if(oper(in.charAt(i-1))!='z'){return "illegal double operand";}
                if(in.charAt(i+1)==')'){return "illegal operator placement at end of bracket expression";}
                if(in.charAt(i-1)=='(' && cur!='-'){return "illegal operator placement at start of bracket expression";}
            }

            //
            if(cur==')'){para--;if(in.charAt(i-1)=='('){return "empty bracket";}}
            if(cur=='('){para++;}

            i++;
            if(i < l){cur=in.charAt(i);}else{break;}

        }
        if(!(num(cur) || oper(cur)!='z' || cur=='(' || cur==')')){return "illegal char";}
        if(para!=0){return "illegal parenthesizing";}
        String rev="";
        int j=in.length()-1;
        String temp="";
        while(j>-1){
            //if not a number
            if(!num(in.charAt(j))){
                for(int k=temp.length()-1;k>-1;k--){
                    rev=rev+temp.charAt(k);
                }//add the reversed reversed to rev

                //add the current char             
                rev=rev+in.charAt(j);
                if(j!=0 && in.charAt(j)=='(' && num(in.charAt(j-1))){rev=rev+"*";}
                j--;temp="";continue;}
            temp=temp+in.charAt(j);
            j--;
        }

        
        return rev+"$";
    }
    public String calculate(String in){
        //
        String inClean="";
        for(int i=0;i<in.length();i++){
            char cur=in.charAt(i);
            if(cur==' '){continue;}else{inClean=inClean+cur;}
        }
        String bracketin= '('+inClean+')';
        String check = calcheck(bracketin);
        //check is reversed and $ appended
        if(check.charAt(check.length()-1)=='$'){return inClean+" = "+Double.toString(calc(check));}
        else{return check;}
    }
    public double calc(String in){
        //first char must be num
        //)3*2+5/3(+2$
        ArrayList<Double> buffer = new ArrayList<Double>();
        ArrayList<Character> op = new ArrayList<Character>();
        String temp="";//current number
        double car=1;//current multiplier
        for(int l=0;l<in.length();l++){
            char cur=in.charAt(l);
            // cur is current char under consideration
            if(num(cur)){
                temp=temp+cur;
            }
            else{
                switch(cur){
                    case'+':
                        buffer.add(Double.valueOf(temp)*car);
                        op.add('+');
                        temp="";
                        car=1;

                    break;
                    case'-':
                        buffer.add(Double.valueOf(temp)*car);
                        op.add('-');
                        temp="";
                        car=1;
                    break;
                    case'*':
                        car *= Double.valueOf(temp);
                        temp="";

                    break;
                    case'/':
                        car /= Double.valueOf(temp);
                        temp="";
                    
                    break;
                    case')':
                        int count=1;
                        String bra="";
                        l++;cur=in.charAt(l);
                        if(cur==')'){count++;}
                        while(count>0){
                            bra=bra+cur;
                            l++;cur=in.charAt(l);
                            if(cur==')'){count++;}
                            if(cur=='('){count--;}
                        }
                        bra=bra+"$";
                        //System.out.println();
                        //System.out.println(bra);
                        double intermediate=calc(bra);
                        //System.out.println(intermediate);
                        temp=Double.toString(intermediate);                  
                    break;                  
                    case'$':
                        buffer.add(Double.valueOf(temp)*car);break;        
                }
            }
        };
        int operations=op.size();
        double ans=buffer.get(operations);
        for(int i=operations-1;i>-1;i--){
            switch(op.get(i)){
                case '+':
                    ans+=buffer.get(i);
                break;
                case'-':
                    ans-=buffer.get(i);
                break;
            }
        }

        return ans;
    }

    public boolean num(char a){
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
    public char oper(char a){
        switch(a){
            case'+':return 'a';
            case'-':return 's';
            case'*':return 'm';
            case'/':return 'd';
            
        }
        return 'z';
    }

    public static void main(String[] args) {
        //(2/2)-5*3*2/5
        //double a=calc("5/2*3*5-(2/2+2)$");
        //System.out.println(a);
        CalculatorService a = new CalculatorService();
        String b=a.calculate("1/3 + 6(3(2+1))");
        System.out.println(b);

        //double c = calc(")41+31($");
        //System.out.println(c);
        
    }

}