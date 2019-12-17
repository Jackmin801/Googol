package com.jacksteam.googol;
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
        // Loads the list.csv file at root directory
        // for the first n links and adds http://www. prefix
        try{
        Scanner in = new Scanner(new FileInputStream("./list.csv"));
        String[] out = new String[n];
        for(int i=0;i<n;i++){
            String[] temp = in.nextLine().split(",");
            out[i]="https://www."+temp[1];
        }

        //Loading message
        System.out.println("Loaded "+out.length+" links.");
        
        for(int i=0;i<3;i++){System.out.println(out[i]);}
        System.out.println("...");
        //for(int i=out.length-2;i<out.length;i++){System.out.println(out[i]);}
        
        in.close();
        return out;
        }catch(Exception e){
            String[] err={"Error: "+e};
            System.out.println(err);
            return err;
        }

    }
    public static void main( String[] args )
    {   
        Scanner in = new Scanner(System.in);
        System.out.print("Number of total links: ");
        int numberOfLinks= in.nextInt();
        in.nextLine();
        String[] list=loadlink(numberOfLinks);

        System.out.print("Name of serialised word lookup file: ");
        String filename = in.nextLine();
        System.out.print("Name of serialised title file: ");
        String filename2 = in.nextLine();
        in.close();
 
        Map<String,ArrayList<Integer>> concord = new HashMap<String, ArrayList<Integer>>();
        ArrayList<String> titlelist = new ArrayList<>();
        int index =0;
    for(String link:list){
        String url=link;

        try{
            // Attempt connection and GET
            Document document = Jsoup.connect(url).get();
            // Obtain the title and add to list
            String title = document.head().getElementsByTag("title").text();
            titlelist.add(title);

            // Obtain text and remove punctuation and wildcards then split into string array of individual words
            String text = document.body().text();
            String[] dupwords = text.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
            
            //Removing duplicate words
            Arrays.sort(dupwords);           
            ArrayList<String> words = new ArrayList<String>();
            for(int i=0;i<dupwords.length-1;i++){
                if(!dupwords[i].equals(dupwords[i+1])){
                    words.add(dupwords[i]);
                }
            }
           
            // Add unique words to concordance
            for (String word:words){
                if (word=="\n"){continue;}
                concord.putIfAbsent(word, new ArrayList<Integer>());
                concord.get(word).add(index);
            }
                      
        }
        catch(Exception ex){
            // Print stacktrace of request error
            titlelist.add("Error: Title could not be found!");
            ex.printStackTrace();
        }
        //iterate to next index for referencing
        index++;
    };
    // Display the first 10 elements of concord to make sure it worked
    int count=0;
    for (String i : concord.keySet()) {
        System.out.println(i+": "+concord.get(i));
        if(count>10 ){break;}
        count++;
    }

    // Save the concordance as serialized "filename"
    try {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
        out.writeObject(concord);
        out.close();
        System.out.println("Serialized data for word lookup is saved in "+filename);
     } catch (Exception i) {
        // Print error
        i.printStackTrace();
     }

     // Save the titlelist as serialised Arraylist
     try {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename2));
        out.writeObject(titlelist);
        out.close();
        System.out.println("Serialized data of titles is saved in "+filename2);
     } catch (Exception i) {
        // Print error
        i.printStackTrace();
     }
        
    }
}
