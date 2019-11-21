package par;
import java.util.Map;
import java.util.Scanner;
import java.util.Arrays;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class App 
{
    public static String[] loadlink(int n){
        //loads the list.csv file at root directory art
        // for the first n links
        try{
        Scanner in = new Scanner(new FileInputStream("./list.csv"));
        String[] out = new String[n];
        for(int i=0;i<n;i++){
            String[] temp = in.nextLine().split(",");
            out[i]="https://www."+temp[1];
        }
        
        in.close();
        return out;
        }catch(Exception e){
            String[] err={"Error: "+e};
            return err;
        }

    }
    public static void main( String[] args )
    {   
        String[] list=loadlink(500);
        for(String link:list){
            System.out.println(link);
        }
     
        Map<String,ArrayList<Integer>> concord = new HashMap<String, ArrayList<Integer>>();
        int index =0;
    for(String link:list){
        String url=link;

        try{
            Document document = Jsoup.connect(url).get();
            String text = document.body().text();
            String[] dupwords = text.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
            //for(String word: words){System.out.println(word);}
            Arrays.sort(dupwords);
            
            ArrayList<String> words = new ArrayList<String>();
            for(int i=0;i<dupwords.length-1;i++){
                if(!dupwords[i].equals(dupwords[i+1])){
                    //System.out.println(dupwords[i]);
                    words.add(dupwords[i]);
                }
            }
           

            for (String word:words){
                if (word=="\n"){continue;}
                concord.putIfAbsent(word, new ArrayList<Integer>());
                concord.get(word).add(index);
                //System.out.print(word+": ");
                //System.out.println(concord.get(word));
            }
            
           
        }
        catch(Exception ex){
            ex.printStackTrace();

        }
        index++;
    };
    int count=0;
    for (String i : concord.keySet()) {
        System.out.println(i+": "+concord.get(i));
        if(count>10 ){break;}
        count++;
    }
    try {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("concord500.ser"));
        out.writeObject(concord);
        out.close();
        System.out.println("Serialized data is saved in concord500.ser");
     } catch (Exception i) {
        i.printStackTrace();
     }
        
    }
}
