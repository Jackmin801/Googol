
package fopassignment2;

/*die dice
 */
import  java.util.Random;
public class FOPASSIGNMENT2 {
    public static void main(String[] args) {
       Random rd = new Random();
        
       for (int i = 0 ; i<50 ; i++){
           int result = rd.nextInt(5)+1;
            System.out.println("You rolled a "+result+" !");
    }
    }
}