package com.jacksteam.googol.api;

import com.jacksteam.googol.model.TTT;
import com.jacksteam.googol.service.TTTService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("jacksteam/googol/api/ttt")
@RestController
public class TTTController{
    private final TTTService tttService;

    String axe ="<img src=\"http://localhost:8080/Cross.png\" alt=\"X\" length=\"100%\" width=\"100%\">";
    String owe ="<img src=\"http://localhost:8080/Circle.png\" alt=\"O\" length=\"100%\" width=\"100%\">";
    String empty ="<img src=\"http://localhost:8080/none.png\" alt=\"-\" length=\"100%\" width=\"100%\">";
    String header = "<header><a href=\"http://localhost:8080\"><img src=\"http://localhost:8080/googologo.png\" height=\"200\" width=\"200\"></a><form id=\"form\" action=\"http://localhost:8080/jacksteam/googol/api/query\" method=\"GET\"><input type=\"text\" placeholder=\"What are you looking for?\" id=\"query\" name=\"q\" onkeypress=\"querySubmit\"></form></header>";

    String head = headConstruct();
    public String headConstruct(){
        String head = "<head>";
        head +="<title>Tic-tac-toe!</title>";
        head +="<link rel=\"icon\" href=\"http://localhost:8080/tabicon.png\">";
        head +="<style>* {margin:0;padding:0;}a {color:#1e5631;font-weight: bold;}body {background-color:#1e5631}h4{padding: 0;margin:0;color:#4c9a2a;}header{text-align: center;color:white;background-image:url(http://localhost:8080/background.png);display:grid;grid-template-columns:1fr 4fr;}.container{text-align: center;background-color: #a4de02;padding: 1em;margin:1em;border-radius: 50px;}#form{margin: 7%;}#query{color:#999;padding:1em;width: 80%;}</style>";
        head +="<style>.nested {padding-left:30%;padding-right: 30%;display:grid;grid-template-columns:repeat(3, 1fr);grid-gap:1em;}</style>";
        head +="</head>";

        return head;
    }

    @Autowired
    public TTTController(TTTService tttService){
        this.tttService=tttService;
    }


    @GetMapping
    public String tttgamestartsecond(){
        String out = "<html>";
        //Head
        out += head;

        //Body 
        out += "<body>";
            //Header
            out += header;
            //out +="<header><img src=\"http://localhost:8080/googologo.png\" height=\"200\" width=\"200\"><form id=\"form\" action=\"http://localhost:8080/jacksteam/googol/api/query\" method=\"GET\"><input type=\"text\" placeholder=\"What are you looking for?\" id=\"query\" name=\"q\" onkeypress=\"querySubmit\"></form></header>";
        
            //Status Prompt
            TTT game = tttService.newGame();
            char[] uwu = game.printBoard();
            if(game.checkForWin() || game.isBoardFull()){
                out+="<div class=\"container\"><h1>Game Over!</h1></div>";
            }else{
                out += "<div class=\"container\"><h1>"+Character.toUpperCase(game.getCurrentPlayerMark()) + ", enter an empty row and column to place your mark!"+"</h1></div>";
            }

            //Board
            out+="<div class=\"nested\">";
            int counter=0;
            for(char i:uwu){
                out+="<a href=\"http://localhost:8080/jacksteam/googol/api/ttt/";
                out+=game.getId() +"?m=";
                out+=(counter/3)+"+"+(counter%3)+"\">";
                if(i=='x'){out+=axe;}
                else if(i=='o'){out+= owe;}
                else{out+=empty;}
                out+="</a>";
                counter++;
            }
            out +="</div>";
        out +="</body>";
        
        out += "</html>";
        return out;
    }

    @GetMapping("/{id}")
    public String tttgame(@RequestParam("m")String move,@PathVariable int id){
        String out = "<html>";

        //Head
        out += head;

        //Body
        out += "<body>";
            //Header
            out += header;
            //out +="<header><img src=\"http://localhost:8080/googologo.png\" height=\"200\" width=\"200\"><form id=\"form\" action=\"http://localhost:8080/jacksteam/googol/api/query\" method=\"GET\"><input type=\"text\" placeholder=\"What are you looking for?\" id=\"query\" name=\"q\" onkeypress=\"querySubmit\"></form></header><body>";

            //Bots Move
            TTT game = tttService.play(id,move);
            char[] uwu = game.printBoard();
            
            
            //Status Prompt
            if(game.checkForWin() || game.isBoardFull()){
                game.toggleGameOver();
                if (game.isBoardFull() && !game.checkForWin()){
                    out+="<div class=\"container\"><h1>The game was a tie!</h1></div>";
                }else{
                    game.changePlayer();           
                    out+="<div class=\"container\"><h1>"+Character.toUpperCase(game.getCurrentPlayerMark()) + " Wins!</h1></div>";
                }
            }else{
                out += "<div class=\"container\"><h1>"+Character.toUpperCase(game.getCurrentPlayerMark()) + ", enter an empty row and column to place your mark!"+"</h1></div>";
            }

            //Board
            out+="<div class=\"nested\">";
            int counter=0;
            for(char i:uwu){
                if(!game.getGameOver()){
                    out+="<a href=\"http://localhost:8080/jacksteam/googol/api/ttt/";
                    out+=game.getId() +"?m=";
                    out+=(counter/3)+"+"+(counter%3)+"\">";
                }
                if(i=='x'){out +=axe;}
                else if(i=='o'){out += owe;}
                else{out += empty;}
                out+="</a>";
                if(!game.getGameOver()){counter++;}
            }
            out +="</div>";
        out +="</body>";
        
        out += "</html>";
        return out;
        
    }
   


    // //@GetMapping
    // public String tttgamestart(){
    //     String out = "<html>";

    //     out+="<body>";
    //     out +="<header><img src=\"https://i.imgur.com/54lUTMx.png\" height=\"200\" width=\"200\"><form id=\"form\" action=\"http://localhost:8080/jacksteam/googol/api/query\" method=\"GET\"><input type=\"text\" placeholder=\"What are you looking for?\" id=\"query\" name=\"q\" onkeypress=\"querySubmit\"></form></header>";
    
    //     TTT game = tttService.newGame();
    //     if(game.checkForWin() || game.isBoardFull()){
    //         out+="<div class=\"container\"><h1>Game Over!</h1></div>";
    //     }else{
    //         out += "<div class=\"container\"><h1>"+Character.toUpperCase(game.getCurrentPlayerMark()) + ", enter an empty row and column to place your mark!"+"</h1></div>";
    //     }

    //     out+="<div class=\"nested\">";
    //     char[] uwu = game.printBoard();
    //     int counter=0;
    //     for(char i:uwu){
    //         out+="<a href=\"http://localhost:8080/jacksteam/googol/api/ttt/";
    //         out+=game.getId() +"?m=";
    //         out+=(counter/3)+"+"+(counter%3)+"\">";
    //         if(i=='x'){out+="<img src=\"https://i.imgur.com/10Rc9Er.png\" alt=\"milo\" height=\"100%\" width=\"100%\">";}
    //         else if(i=='o'){out+= "<img src=\"https://i.imgur.com/uP8t5WN.png\" alt=\"milo\" height=\"100%\" width=\"100%\">";}
    //         else{out+="<img src=\"https://i.imgur.com/ucDRkZa.png\" alt=\"milo\" height=\"100%\" width=\"100%\">";}
    //         out+="</a>";
    //         counter++;
    //     }

    //     out +="</div></body>";
                 
    //     return out;
    // }

    // //@GetMapping("/{id}")
    // public String tttgames(@RequestParam("m")String move,@PathVariable int id){
    //     String out ="<head><title>Tic-tac-toe</title><link rel=\"icon\" href=\"https://i.imgur.com/TiKXS1h.png\"></head>";
    //     out +="<style>.nested {padding-left: 20%;padding-right: 20%;display:grid;grid-template-columns:repeat(3, 1fr);grid-auto-columns: 200px;grid-auto-rows: 200px;grid-gap:1em;}</style>";
    //     out += "<style>* {margin:0;padding:0;}a {color:#1e5631;font-weight: bold;}body {background-color:#1e5631;}h4{padding: 0;margin:0;color:#4c9a2a;}header{text-align: center;color:white;background-image:url(http://localhost:8080/background.png);display:grid;grid-template-columns:1fr 4fr;}.container{text-align: center;background-color: #a4de02;padding: 1em;margin:1em;border-radius: 50px;}#form{margin: 7%;}#query{color:#999;padding:1em;width: 80%;}</style>";
    //     //out+= "<style>a {color:#1e5631;font-weight: bold;}body {background-color:#1e5631;}h4{padding:0;margin:0;color:#4c9a2a;}header{text-align:center;color:white;}.container{background-color:#a4de02;padding:1em;margin:1em;}#query{color:#999;padding:1em;width: 80%;}</style>";
    //     //out +="<style>a {color:#292929;font-weight: bold;}body {background-color:#1b1b1b;}h4{padding:0;margin:0;color:#808080;}header{text-align:center;color:white;}.container{background-color:#ffa31a;padding:1em;margin:1em;}#query{color:#999;padding:1em;width: 80%;}</style>";
    //     out +="<header><img src=\"https://i.imgur.com/54lUTMx.png\" height=\"200\" width=\"200\"><form id=\"form\" action=\"http://localhost:8080/jacksteam/googol/api/query\" method=\"GET\"><input type=\"text\" placeholder=\"What are you looking for?\" id=\"query\" name=\"q\" onkeypress=\"querySubmit\"></form></header><body>";
    
    //     TTT game = tttService.play(id,move);
    //     if(game.checkForWin() || game.isBoardFull()){
    //         game.toggleGameOver();
    //         if (game.isBoardFull() && !game.checkForWin()){
    //             out+="<div class=\"container\"><h1>The game was a tie!</h1></div>";
    //         }else{
    //             game.changePlayer();
    //             out+="<div class=\"container\"><h1>"+Character.toUpperCase(game.getCurrentPlayerMark()) + " Wins!</h1></div>";
    //         }
    //     }else{
    //         out += "<div class=\"container\"><h1>"+Character.toUpperCase(game.getCurrentPlayerMark()) + ", enter an empty row and column to place your mark!"+"</h1></div>";
    //     }

    //     out+="<div class=\"nested\">";
    //     char[] uwu = game.printBoard();//xxo---xo-
    //     int counter=0;
    //     for(char i:uwu){
    //         if(!game.getGameOver()){out+="<a href=\"http://localhost:8080/jacksteam/googol/api/ttt/";
    //         out+=game.getId() +"?m=";
    //         out+=(counter/3)+"+"+(counter%3)+"\">";}
    //         if(i=='x'){out+="<img src=\"https://i.imgur.com/10Rc9Er.png\" alt=\"milo\" height=\"100%\" width=\"100%\">";}
    //         else if(i=='o'){out+= "<img src=\"https://i.imgur.com/uP8t5WN.png\" alt=\"milo\" height=\"100%\" width=\"100%\">";}
    //         else{out+="<img src=\"https://i.imgur.com/ucDRkZa.png\" alt=\"milo\" height=\"100%\" width=\"100%\">";}
    //         if(!game.getGameOver()){out+="</a>";}
    //         counter++;
    //     }

    //     out +="</div></body>";
                 
    //     return out;
        
    // }
   

}