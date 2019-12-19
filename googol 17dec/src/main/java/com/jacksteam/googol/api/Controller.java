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
    private final MainParseService mainParseService;

    @Autowired
    public Controller(MainParseService mainParseService){
        this.mainParseService=mainParseService;
    }

    @GetMapping
    public String query(@RequestParam("q") String query){
        //Tictactoe Redirects
        if(query.equalsIgnoreCase("g tictactoe")){return "<head> <meta http-equiv=\"refresh\" content=\"0; URL=http://localhost:8080/jacksteam/googol/api/ttt\" /></head>";}
        if(query.equalsIgnoreCase("g tictactoe bot")){return "<head> <meta http-equiv=\"refresh\" content=\"0; URL=http://localhost:8080/jacksteam/googol/api/ttt/bot\" /></head>";}
        if(query.equalsIgnoreCase("g tictactoe bot wait")){return "<head> <meta http-equiv=\"refresh\" content=\"0; URL=http://localhost:8080/jacksteam/googol/api/ttt/bot/wait\" /></head>";}
        
        //Documentation redirect
        if(query.equalsIgnoreCase("Milocat Help!")){return "<head> <meta http-equiv=\"refresh\" content=\"0; URL=http://localhost:8080/documentation.html\" /></head>";}

        //Deafault page redirects
        if(query.equalsIgnoreCase("c")){return "<head> <meta http-equiv=\"refresh\" content=\"0; URL=http://localhost:8080/calc.html\" /></head>";}
        if(query.equalsIgnoreCase("x")){return "<head> <meta http-equiv=\"refresh\" content=\"0; URL=http://localhost:8080/exchange.html\" /></head>";}
        if(query.equalsIgnoreCase("b")){return "<head> <meta http-equiv=\"refresh\" content=\"0; URL=http://localhost:8080/binomial.html\" /></head>";}
        if(query.equalsIgnoreCase("k")){return "<head> <meta http-equiv=\"refresh\" content=\"0; URL=http://localhost:8080/kmap.html\" /></head>";}

        //Payload Construction
        String out = "<html>";
        //Head
        out +="<head><title>Googol</title>";
        out +="<link rel=\"icon\" href=\"http://localhost:8080/tabicon.png\">";
        out += "<style>* {margin:0;padding:0;}a {color:#1e5631;font-weight: bold;}body {background-color:#1e5631;}h4{padding: 0;margin:0;color:#4c9a2a;}header{background-image:url(http://localhost:8080/background.png);display:grid;grid-template-columns:1fr 4fr;}.container{background-color: #a4de02;padding: 1em;margin:1em;border-radius: 10px;}#form{margin: 7%;}#query{color:#999;padding:1em;width: 80%;}</style></head>";

        //Body
        out += "<body>";
            //out +="<header><img src=\"http://localhost:8080/googologo.png\" height=\"200\" width=\"200\"><form id=\"form\" action=\"http://localhost:8080/jacksteam/googol/api/query\" method=\"GET\"><input type=\"text\" placeholder=\"What are you looking for?\" id=\"query\" name=\"q\" onkeypress=\"querySubmit\"></form></header>";
            out += "<header><a href=\"http://localhost:8080\"><img src=\"http://localhost:8080/googologo.png\" height=\"200\" width=\"200\"></a><form id=\"form\" action=\"http://localhost:8080/jacksteam/googol/api/query\" method=\"GET\"><input type=\"text\" placeholder=\"What are you looking for?\" value =\""+query+"\"id=\"query\" name=\"q\" onkeypress=\"querySubmit\"></form></header>";
            out += "<div class =\"maincontainer\">";
                out += mainParseService.parse(query);
            out += "</div>";
        out += "</body>";

        out += "</html>";
        return out;
    }

}