
package fopassignment;

/* game 
 */
import java.util.Scanner;
import java.util.Random;
public class FOPASSIGNMENT {
    
    public static boolean checkName1(String a){
    String game_1 = "tic tac toe";
   
    if (a.equalsIgnoreCase(game_1)){
        return true;
    }
    else{
        checkName2(a);
    }
    return false;
    
    }
    
    public static void main(String[]args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter game name : ");
        String name =sc.nextLine();
        if(checkName1(name)){
            System.out.println("Game Found!");
            System.out.println("Tic tac toe!");
        }
    }
    public static void checkName2(String a){
  
    String game_2 = "die dice";
    if (a.equalsIgnoreCase(game_2)){
        System.out.println("Game Found!");
        System.out.println("Die dice! ");
    
    }
    else{
        System.out.println("Game Not found!");
        System.out.println("Error input.");
    }
    }
}
    
