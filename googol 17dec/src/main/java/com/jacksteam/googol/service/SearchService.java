package com.jacksteam.googol.service;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService{
    /**
     * //Constants
     * concord - Hashmap of words mapping to AL of integer indexes for the websites
     * list - List of links
     * 
     * //Methods
     * loadlink(int n) loads the first n links from list.csv in root file
     * load(String file) loads the concord from serialized file "file"
     * singleQuery(String query) queries concord for the ArrayList of the single word in query and returns null if it does not exist
     * AndOperator(AL a, AL b) returns ArrayList of a AND b
     * OrOperator(AL a, AL b) returns ArrayList of a OR b
     * AndNotOperator(AL a, AL b) retusn ArrayList of a AND NOT b
     * searchParse(String input) takes input string and splits it by spaces and processes the query returning the final ArrayList
     */
    private final HashMap<String,ArrayList<Integer>> concord;
    private final String[] list;

    @Autowired
    public SearchService(){
        this.concord = this.load("concord3k.ser");
        this.list = this.loadlink(3000);
    }

    public String[] loadlink(int n){
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
    public HashMap<String,ArrayList<Integer>> load(String filename){
        //Attempts to load concordance from filename
        try{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));

        HashMap<String,ArrayList<Integer>> concord = (HashMap<String,ArrayList<Integer>>) in.readObject();

        // Success message
        System.out.println("Loaded "+filename);
        int count=0;
        for (String i : concord.keySet()) {
            System.out.println(i+": "+concord.get(i));
            if(count>3 ){break;}
            count++;
        }
        
        in.close();
        return concord;
        }catch(Exception ex){
            //Print error return null
            System.out.println(ex);
            return null;
        }
    }
    public ArrayList<Integer> singleQuery(String query){
        try {
            return concord.get(query);       
        } catch (Exception e) {
            ArrayList<Integer> out = null;
            return out;
        }
    }
    public ArrayList<Integer> AndOperater(ArrayList<Integer> a,ArrayList<Integer> b){
        //ANDS two integer ArrayLists
        if(a==null || b==null){return null;}
        int counta,countb;
        counta=countb=0;
        ArrayList<Integer> out= new ArrayList<Integer>();
        while(counta<a.size() && countb<b.size()){
            int A=a.get(counta);
            int B=b.get(countb);
            if(A==B){out.add(A);counta++;countb++;}
            else if(A>B){countb++;}
            else{counta++;}
        }
        return out;
    }
    public ArrayList<Integer> OrOperator(ArrayList<Integer> a,ArrayList<Integer> b){
        if(a==null){return b;}
        if(b==null){return a;}
        int counta,countb;
        counta=countb=0;
        ArrayList<Integer> out= new ArrayList<Integer>();
        while(counta<a.size() && countb<b.size()){
            int A=a.get(counta);
            int B=b.get(countb);
            if(A==B){out.add(A);counta++;countb++;}
            else if(A>B){out.add(B);countb++;}
            else{out.add(A);counta++;}
        }
        if(a.size()>b.size()){
            while(counta<a.size()){out.add(a.get(counta));counta++;}
        }
        else{while(countb<b.size()){out.add(b.get(countb));countb++;}}

        return out;
    }
    public ArrayList<Integer> AndNotOperator(ArrayList<Integer> a,ArrayList<Integer> b){
        if(a==null){return null;}
        if(b==null){return a;}
        int counta,countb;
        counta=countb=0;
        ArrayList<Integer> out= new ArrayList<Integer>();
        while(counta<a.size() && countb<b.size()){
            int A=a.get(counta);
            int B=b.get(countb);
            if(A==B){counta++;countb++;}
            else if(A>B){countb++;}
            else{out.add(A);counta++;}
        }
        if(a.size()>b.size()){
            while(counta<a.size()){out.add(a.get(counta));counta++;}
        }

        return out;
    }
    
    public ArrayList<Integer> searchParse(String input){
        //takes search query and returns arraylist of index result
        // Split out words
        String[] dupwords=input.split(" ",0);

        //Remove Duplicates to avoid looping through "" alot
        Arrays.sort(dupwords);
        ArrayList<String> words = new ArrayList<>();
        if(dupwords.length==1){words.add(dupwords[0]);}
        else if(dupwords.length==0){return null;}
        else{
            for(int i=0;i<dupwords.length-1;i++){
                if(!dupwords[i].equals(dupwords[i+1])){
                    words.add(dupwords[i]);
                }
            }
            if (!(dupwords[dupwords.length-1]==dupwords[dupwords.length-2])){words.add(dupwords[dupwords.length-1]);}
        }
        
        // Init with first query
        System.out.println(words);
        ArrayList<Integer> out = concord.get(words.get(0));
        for(String word:words){
            if(word.length()==0){continue;}
            else if(word.charAt(0)=='!'){
                String temp="";
                for(int i=1;i<word.length();i++){temp=temp+word.charAt(i);}
                out = AndNotOperator(out,concord.get(temp));
            }
            else if(word.charAt(0)=='+'){
                String temp="";
                for(int i=1;i<word.length();i++){temp=temp+word.charAt(i);}
                out = OrOperator(out,concord.get(temp));
            }
            else{
                out = AndOperater(out, concord.get(word));
            }     
        }
        if(out!=null){
            System.out.println(out);
            if(out.size()==0){return null;}
        }
        return out;
    }
    public static void main(String[] args) {
        SearchService a = new SearchService();
        ArrayList<Integer> out= a.searchParse("hello");
        System.out.println("Query: hello");
        System.out.println(out==null);
        for(int i:out){System.out.println(a.list[i]);}
               
    }
}

