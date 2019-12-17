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

    @Autowired
    public TTTController(TTTService tttService){
        this.tttService=tttService;
    }

    @GetMapping
    public String tttgamestart(){
        String out ="<head><title>Tic-tac-toe!</title><link rel=\"icon\" href=\"https://i.imgur.com/TiKXS1h.png\"></head>";
        out +="<style>.nested {padding-left: 20%;padding-right: 20%;display:grid;grid-template-columns:repeat(3, 1fr);grid-auto-columns: 200px;grid-auto-rows: 200px;grid-gap:1em;}</style>";
        out += "<style>* {margin:0;padding:0;}a {color:#1e5631;font-weight: bold;}body {background-color:#1e5631;}h4{padding: 0;margin:0;color:#4c9a2a;}header{text-align: center;color:white;background-color: #1a4531;display:grid;grid-template-columns:1fr 4fr;}.container{text-align: center;background-color: #a4de02;padding: 1em;margin:1em;border-radius: 50px;}#form{margin: 7%;}#query{color:#999;padding:1em;width: 80%;}</style>";
        //out+= "<style>a {color:#1e5631;font-weight: bold;}body {background-color:#1e5631;}h4{padding:0;margin:0;color:#4c9a2a;}header{text-align:center;color:white;}.container{background-color:#a4de02;padding:1em;margin:1em;}#query{color:#999;padding:1em;width: 80%;}</style>";
        //out +="<style>a {color:#292929;font-weight: bold;}body {background-color:#1b1b1b;}h4{padding:0;margin:0;color:#808080;}header{text-align:center;color:white;}.container{background-color:#ffa31a;padding:1em;margin:1em;}#query{color:#999;padding:1em;width: 80%;}</style>";
        out +="<header><img src=\"https://i.imgur.com/54lUTMx.png\" height=\"200\" width=\"200\"><form id=\"form\" action=\"http://localhost:8080/jacksteam/googol/api/query\" method=\"GET\"><input type=\"text\" placeholder=\"What are you looking for?\" id=\"query\" name=\"q\" onkeypress=\"querySubmit\"></form></header><body>";
    
        TTT game = tttService.newGame();
        if(game.checkForWin() || game.isBoardFull()){
            out+="<div class=\"container\"><h1>Game Over!</h1></div>";
        }else{
            out += "<div class=\"container\"><h1>"+Character.toUpperCase(game.getCurrentPlayerMark()) + ", enter an empty row and column to place your mark!"+"</h1></div>";
        }

        out+="<div class=\"nested\">";
        char[] uwu = game.printBoard();
        int counter=0;
        for(char i:uwu){
            out+="<a href=\"http://localhost:8080/jacksteam/googol/api/ttt/";
            out+=game.getId() +"?m=";
            out+=(counter/3)+"+"+(counter%3)+"\">";
            if(i=='x'){out+="<img src=\"https://i.imgur.com/10Rc9Er.png\" alt=\"milo\" height=\"100%\" width=\"100%\">";}
            else if(i=='o'){out+= "<img src=\"https://i.imgur.com/uP8t5WN.png\" alt=\"milo\" height=\"100%\" width=\"100%\">";}
            else{out+="<img src=\"https://i.imgur.com/ucDRkZa.png\" alt=\"milo\" height=\"100%\" width=\"100%\">";}
            out+="</a>";
            counter++;
        }

        out +="</div></body>";
                 
        return out;
    }

    @GetMapping("/{id}")
    public String tttgame(@RequestParam("m")String move,@PathVariable int id){
        String out ="<head><title>Tic-tac-toe</title><link rel=\"icon\" href=\"https://i.imgur.com/TiKXS1h.png\"></head>";
        out +="<style>.nested {padding-left: 20%;padding-right: 20%;display:grid;grid-template-columns:repeat(3, 1fr);grid-auto-columns: 200px;grid-auto-rows: 200px;grid-gap:1em;}</style>";
        out += "<style>* {margin:0;padding:0;}a {color:#1e5631;font-weight: bold;}body {background-color:#1e5631;}h4{padding: 0;margin:0;color:#4c9a2a;}header{text-align: center;color:white;background-color: #1a4531;display:grid;grid-template-columns:1fr 4fr;}.container{text-align: center;background-color: #a4de02;padding: 1em;margin:1em;border-radius: 50px;}#form{margin: 7%;}#query{color:#999;padding:1em;width: 80%;}</style>";
        //out+= "<style>a {color:#1e5631;font-weight: bold;}body {background-color:#1e5631;}h4{padding:0;margin:0;color:#4c9a2a;}header{text-align:center;color:white;}.container{background-color:#a4de02;padding:1em;margin:1em;}#query{color:#999;padding:1em;width: 80%;}</style>";
        //out +="<style>a {color:#292929;font-weight: bold;}body {background-color:#1b1b1b;}h4{padding:0;margin:0;color:#808080;}header{text-align:center;color:white;}.container{background-color:#ffa31a;padding:1em;margin:1em;}#query{color:#999;padding:1em;width: 80%;}</style>";
        out +="<header><img src=\"https://i.imgur.com/54lUTMx.png\" height=\"200\" width=\"200\"><form id=\"form\" action=\"http://localhost:8080/jacksteam/googol/api/query\" method=\"GET\"><input type=\"text\" placeholder=\"What are you looking for?\" id=\"query\" name=\"q\" onkeypress=\"querySubmit\"></form></header><body>";
    
        TTT game = tttService.play(id,move);
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

        out+="<div class=\"nested\">";
        char[] uwu = game.printBoard();//xxo---xo-
        int counter=0;
        for(char i:uwu){
            if(!game.getGameOver()){out+="<a href=\"http://localhost:8080/jacksteam/googol/api/ttt/";
            out+=game.getId() +"?m=";
            out+=(counter/3)+"+"+(counter%3)+"\">";}
            if(i=='x'){out+="<img src=\"https://i.imgur.com/10Rc9Er.png\" alt=\"milo\" height=\"100%\" width=\"100%\">";}
            else if(i=='o'){out+= "<img src=\"https://i.imgur.com/uP8t5WN.png\" alt=\"milo\" height=\"100%\" width=\"100%\">";}
            else{out+="<img src=\"https://i.imgur.com/ucDRkZa.png\" alt=\"milo\" height=\"100%\" width=\"100%\">";}
            if(!game.getGameOver()){out+="</a>";}
            counter++;
        }

        out +="</div></body>";
                 
        return out;
        
    }
   

}