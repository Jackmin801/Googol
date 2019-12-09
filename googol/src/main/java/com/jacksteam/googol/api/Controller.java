package com.jacksteam.googol.api;

//import com.jacksteam.googol.model.ListElement;
//import com.jacksteam.googol.service.ListElementService;
import com.jacksteam.googol.service.MainParseService;
//import com.jacksteam.googol.service.SearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("jacksteam/googol/api/query")
@RestController
public class Controller{
    //private final ListElementService listElementService;
    //private final SearchService searchService;
    private final MainParseService mainParseService;

    @Autowired
    public Controller(MainParseService mainParseService){
        //this.listElementService = listElementService;
        //this.searchService=searchService;
        this.mainParseService=mainParseService;
    }

    @GetMapping
    public String query(@RequestParam("q") String query){
        //Tictactoe Redirect
        if(query.equalsIgnoreCase("g tictactoe")){return "<head> <meta http-equiv=\"refresh\" content=\"0; URL=http://localhost:8080/jacksteam/googol/api/ttt\" /></head>";}
        
        //Query Parser
        String out ="<title>Googol</title>";
        out += "<style>* {margin:0;padding:0;}a {color:#1e5631;font-weight: bold;}body {background-color:#1e5631;}h4{padding: 0;margin:0;color:#4c9a2a;}header{text-align: center;color:white;background-color: #1a4531;display:grid;grid-template-columns:1fr 4fr;}.container{background-color: #a4de02;padding: 1em;margin:1em;border-radius: 10px;}#form{margin: 7%;}#query{color:#999;padding:1em;width: 80%;}</style>";
        //out+= "<style>a {color:#1e5631;font-weight: bold;}body {background-color:#1e5631;}h4{padding:0;margin:0;color:#4c9a2a;}header{text-align:center;color:white;}.container{background-color:#a4de02;padding:1em;margin:1em;}#query{color:#999;padding:1em;width: 80%;}</style>";
        //out +="<style>a {color:#292929;font-weight: bold;}body {background-color:#1b1b1b;}h4{padding:0;margin:0;color:#808080;}header{text-align:center;color:white;}.container{background-color:#ffa31a;padding:1em;margin:1em;}#query{color:#999;padding:1em;width: 80%;}</style>";
        out +="<header><img src=\"https://i.imgur.com/54lUTMx.png\" height=\"200\" width=\"200\"><form id=\"form\" action=\"http://localhost:8080/jacksteam/googol/api/query\" method=\"GET\"><input type=\"text\" placeholder=\"What are you looking for?\" id=\"query\" name=\"q\" onkeypress=\"querySubmit\"></form></header><body>";
        //System.out.println("\n\n"+query+" \n\n");
        out += mainParseService.parse(query);

        out +="</body>";
        return out;
    }

    //@GetMapping
    public String testicle(){
        String out ="<title>Googol</title>";
        out+= "<style>a {color:#1e5631;font-weight: bold;}body {background-color:#1e5631;}h4{padding:0;margin:0;color:#4c9a2a;}header{text-align:center;color:white;}.container{background-color:#a4de02;padding:1em;margin:1em;}#query{color:#999;padding:1em;width: 80%;}</style>";
        //out +="<style>a {color:#292929;font-weight: bold;}body {background-color:#1b1b1b;}h4{padding:0;margin:0;color:#808080;}header{text-align:center;color:white;}.container{background-color:#ffa31a;padding:1em;margin:1em;}#query{color:#999;padding:1em;width: 80%;}</style>";
        out +="<header><img src=\"https://i.imgur.com/54lUTMx.png\" height=\"200\" width=\"200\"><form id=\"form\" action=\"http://localhost:8080/jacksteam/googol/api/query\" method=\"GET\"><input type=\"text\" placeholder=\"What are you looking for?\" id=\"query\" name=\"q\" onkeypress=\"querySubmit\"></form></header><body>";
       
        out += "<div class=\"container\"><h1>------------- </h1><h1>| o | o | x | </h1><h1>-------------</h1><h1>| o | x | - | </h1><h1>-------------</h1><h1>| o | x | x | </h1><h1>-------------</h1></div>";

        out +="</body>";
        return out;
    }

}