package com.jacksteam.googol.service;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import com.jacksteam.googol.model.ListElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListElementService{
    /**
     * //Variables
     * String[] list - list of links arranged by order of popularity
     * 
     * //Methods
     * markupify(ListElement e) returns String of html formatted ListElement
     * loadlink(int n) retusn String[] or the first n links
     * genFromIndex(int i) generates the ListELement of website of index i
     */
    private final String[] list;
    private final ArrayList<String> titlelist;
    @Autowired
    public ListElementService(){
        this.list = loadlink(3000);
        this.titlelist = loadtitle("titlelist3k.ser");
    }

    public String markupify(ListElement e){
    //Takes in ListElement and turns it into html div container
    /*
    <div class="container">
        <a href="https://spectrum.um.edu.my/course/view.php?id=151">Teo Richie's cats</a>
        <h4>www.richielovesneko.com</h4>
        <p>Lorem, ipsum dolor sit amet consectetur adipisicing elit. Nisi voluptatibus, pariatur non temporibus dolorum ipsum officia perspiciatis nostrum, illo voluptates esse aut et incidunt corrupti omnis. Quo ex rerum repellendus.</p>
    </div>
    */
        String out = "<div class=\"container\"><a href=\"";
        out += e.getLink();
        out += "\">";
        out += e.getTitle();
        out += "</a><h4>";
        out += e.getLink();
        out += "</h4><p>";
        out += e.getExcerpt();
        out += "</p></div>";

        return out;
    }

    public ArrayList<String> loadtitle(String filename){
        //Attempts to load titles from filename
        try{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));

        ArrayList<String> titlelist = (ArrayList<String>) in.readObject();

        // Success message
        System.out.println("Loaded "+filename);
        for(int i=0;i<3;i++){System.out.println(titlelist.get(i));}
        System.out.println("...");
        
        in.close();
        return titlelist;
        }catch(Exception ex){
            //Print error return null
            System.out.println(ex);
            return null;
        }
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

    public ListElement genFromIndex(int i){
        return new ListElement(titlelist.get(i), list[i], "   ");
    }

    public static void main(String[] args) {
        ListElementService a = new ListElementService();
        a.genFromIndex(2900);
    }
}