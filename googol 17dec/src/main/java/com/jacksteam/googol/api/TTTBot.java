package com.jacksteam.googol.api;

import com.jacksteam.googol.model.TTT;
import com.jacksteam.googol.service.TTTBotService;
import com.jacksteam.googol.service.TTTService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("jacksteam/googol/api/ttt/bot")
@RestController
public class TTTBot{
    private final TTTService tttService;
    private final TTTBotService MiloBot;

    String axe ="<img src=\"http://localhost:8080/Cross.png\" alt=\"X\" length=\"100%\" width=\"100%\">";
    String owe ="<img src=\"http://localhost:8080/Circle.png\" alt=\"O\" length=\"100%\" width=\"100%\">";
    String empty ="<img src=\"http://localhost:8080/none.png\" alt=\"-\" length=\"100%\" width=\"100%\">";

    String head = headConstruct();
    public String headConstruct(){
        String head = "<head>";
        head +="<title>Milo Bot</title>";
        head +="<link rel=\"icon\" href=\"http://localhost:8080/tabicon.png\">";
        head +="<style>* {margin:0;padding:0;}a {color:#1e5631;font-weight: bold;}body {background-color:#1e5631}h4{padding: 0;margin:0;color:#4c9a2a;}header{text-align: center;color:white;background-color: #1a4531;display:grid;grid-template-columns:1fr 4fr;}.container{text-align: center;background-color: #a4de02;padding: 1em;margin:1em;border-radius: 50px;}#form{margin: 7%;}#query{color:#999;padding:1em;width: 80%;}</style>";
        head +="<style>.nested {padding-left:30%;padding-right: 30%;display:grid;grid-template-columns:repeat(3, 1fr);grid-gap:1em;}</style>";
        head +="</head>";

        return head;
    }

    @Autowired
    public TTTBot(TTTService tttService,TTTBotService tttBotService){
        this.tttService=tttService;
        this.MiloBot = tttBotService;

    }

    @GetMapping
    public String tttgamestart(){
        String out = "<html>";
        //Head
        out += head;
        
        //Body
        out += "<body>";
            //Header
            out +="<header><img src=\"http://localhost:8080/googologo.png\" height=\"200\" width=\"200\"><form id=\"form\" action=\"http://localhost:8080/jacksteam/googol/api/query\" method=\"GET\"><input type=\"text\" placeholder=\"What are you looking for?\" id=\"query\" name=\"q\" onkeypress=\"querySubmit\"></form></header>";
    
            //Status Prompt
            TTT game = tttService.newGame();
            char[] uwu = game.printBoard();
            String botmove = MiloBot.bestmove(uwu, true);
            tttService.play(game.getId(), botmove);
            if(game.checkForWin() || game.isBoardFull()){
                out+="<div class=\"container\"><h1>Game Over!</h1></div>";
            }else{
                out += "<div class=\"container\"><h1>"+Character.toUpperCase(game.getCurrentPlayerMark()) + ", enter an empty row and column to place your mark!"+"</h1></div>";
            }

            //Board
            out+="<div class=\"nested\">";
            uwu = game.printBoard();
            int counter=0;
            for(char i:uwu){
                out+="<a href=\"http://localhost:8080/jacksteam/googol/api/ttt/bot/";
                out+=game.getId() +"?m=";
                out+=(counter/3)+"+"+(counter%3)+"\">";
                if(i=='x'){out += axe;}
                else if(i=='o'){out += owe;}
                else{out += empty;}
                out+="</a>";
                counter++;
            }
            out +="</div>";
        out +="</body>";
        
        out += "</html>";
        return out;
    }

    @GetMapping("/wait")
    public String tttgamestartsecond(){
        String out = "<html>";
        //Head
        out += head;

        //Body 
        out += "<body>";
            //Header
            out +="<header><img src=\"http://localhost:8080/googologo.png\" height=\"200\" width=\"200\"><form id=\"form\" action=\"http://localhost:8080/jacksteam/googol/api/query\" method=\"GET\"><input type=\"text\" placeholder=\"What are you looking for?\" id=\"query\" name=\"q\" onkeypress=\"querySubmit\"></form></header>";
        
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
                out+="<a href=\"http://localhost:8080/jacksteam/googol/api/ttt/bot/";
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
            out +="<header><img src=\"http://localhost:8080/googologo.png\" height=\"200\" width=\"200\"><form id=\"form\" action=\"http://localhost:8080/jacksteam/googol/api/query\" method=\"GET\"><input type=\"text\" placeholder=\"What are you looking for?\" id=\"query\" name=\"q\" onkeypress=\"querySubmit\"></form></header><body>";

            //Bots Move
            TTT game = tttService.play(id,move);
            char[] uwu = game.printBoard();
            if(tttService.getLegal()){boolean axe;
            if(game.getCurrentPlayerMark()=='x'){axe=true;}else{axe=false;}
            String botmove = MiloBot.bestmove(uwu, axe);
            tttService.play(game.getId(), botmove);}
            
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
            uwu = game.printBoard();
            int counter=0;
            for(char i:uwu){
                if(!game.getGameOver()){
                    out+="<a href=\"http://localhost:8080/jacksteam/googol/api/ttt/bot/";
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
   

}