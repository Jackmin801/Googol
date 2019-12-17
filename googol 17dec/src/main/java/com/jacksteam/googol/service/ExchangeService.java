package com.jacksteam.googol.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Scanner;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class ExchangeService{
    /**
     * //Variables
     * rates - Hashmap of currencies converting to MYR
     * 
     * //Methods
     * update(String filename) creates serialized hashmap with file name filename
     * load(String filename) Loads/returns hashmap of exchange rates from filename
     * exch(String a,String cur1,String cur1) 
     */
    //Var Hash Map rates 
    private HashMap<String,Double> rates;

    public ExchangeService (){
        this.rates = load("exchange.ser");
    }

    public HashMap<String,Double> update(String filename){
        //Outputs Hashmap of conversion rates
        try{
            Scanner in = new Scanner(new FileInputStream("CurrencyList.txt"));
            HashMap<String,Double> out=new HashMap<String,Double>();
            out.put("MYR", 1.00);
            String url;

            while(in.hasNextLine()){
                String currency = in.nextLine();
                try {
                    //Construct the link
                    url="https://www.google.com/search?q=" + currency + "+to+MYR";
                    //Get Request
                    Document doc = Jsoup.connect(url).get();
                    //Zone down the attribute
                    String rate = doc.getElementById("knowledge-currency__updatable-data-column").getElementsByTag("div").attr("data-exchange-rate");
                    out.put(currency, Double.valueOf(rate));
                    System.out.println("Success!");
                } catch (HttpStatusException e) {
                    int a =e.getStatusCode();
                    System.out.println(a);
                    System.out.println("Cannot find exchange rate for "+currency);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }

            int count=0;
            for (String i : out.keySet()) {
                System.out.println(i+": "+out.get(i));
                if(count>10 ){break;}
                count++;
            }
            try {
                ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(filename));
                writer.writeObject(out);
                writer.close();
                System.out.println("Serialized data is saved in "+filename);
             } catch (Exception i) {
                // Print error
                i.printStackTrace();
             }


            in.close();
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }




    }
    public HashMap<String,Double> load(String filename){
        //loads exchangerates into hashmap
        //e.g. RateUpdate.load("exchange.ser");
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
    
            HashMap<String,Double> exchangeRate = (HashMap<String,Double>) in.readObject();
    
            // Success message
            System.out.println("Loaded "+filename);
            int count=0;
            for (String i : exchangeRate.keySet()) {
                System.out.println(i+": "+exchangeRate.get(i));
                if(count>10 ){break;}
                count++;
            }
            
            in.close();
            return exchangeRate;      
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String exch(String a,String cur1,String cur2){
        //Recieves a cur1 to cur2
        //e.g. 23.45 USD
        //convert a to double
        double A=0;
        double ans=0;
        try{A=Double.valueOf(a);}
        catch(Exception e){return "Invalid number parse \n Please input in the format: x \"amount\" %currency1% to %currency2%";}
        //cur1 to Base(MYR)
        try {
        double B = A*rates.get(cur1.toUpperCase());
        //Base(MYR) to cur2
        ans = B/rates.get(cur2.toUpperCase());
        } catch (Exception e) {
            return "Invalid currency parse \n Please input in the format: x %amount% %currency1% to %currency2%";
        }

        return a+" "+cur1.toUpperCase()+" = "+String.format("%.2f", ans)+" "+cur2.toUpperCase();
    }
    public static void main(String[] args) {
        ExchangeService a = new ExchangeService();
        a.update("exchange.ser");
    }
}