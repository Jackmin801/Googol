package par;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Query{
    public static HashMap<String,ArrayList<Integer>> load(){
        try{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("concord500.ser"));

        //private Map<String, String> someMap = (HashMap<String, String>)getApplicationContext().getBean("someMap");
        //HashMap<String,ArrayList<Integer>> concord =null;
        HashMap<String,ArrayList<Integer>> concord = (HashMap<String,ArrayList<Integer>>) in.readObject();
        
        in.close();
        return concord;
        }catch(Exception ex){
            System.out.println(ex);
            return null;
        }
        
    }
    public static void main(String[] args) {
        HashMap<String,ArrayList<Integer>> concord = load();
        String[] list=App.loadlink(500);

        ArrayList<Integer> temp;
        System.out.println("Query: shopping");
        try{
            temp = concord.get("shopping");
            for(int i:temp){System.out.println(list[i]);}
        }catch(Exception e){
            System.out.println("NULL");
        }

        System.out.println("Query: porn");
        try{
        temp = concord.get("gay");
        for(int i:temp){System.out.println(list[i]);}
        }catch(Exception e){
            System.out.println("NULL");
        }

        System.out.println("Query: java");
        temp = concord.get("java");
        try{
            temp = concord.get("java");
            for(int i:temp){System.out.println(list[i]);}
            }catch(Exception e){
                System.out.println("NULL");
            }
        
            System.out.println("Query: banana");
            try{
                temp = concord.get("banana");
                for(int i:temp){System.out.println(list[i]);}
            }catch(Exception e){
                System.out.println("NULL");
            }
    }
}