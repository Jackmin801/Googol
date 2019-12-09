package com.jacksteam.googol.service;

import com.jacksteam.googol.dao.TTTdao;
import com.jacksteam.googol.model.TTT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;



@Service
public class TTTService {
    private final TTTdao tttdao;
    public static int idCounter=0;

    @Autowired
    public TTTService(@Qualifier("fakeDAO")TTTdao tttdao){
        this.tttdao=tttdao;
    }

    public TTT newGame(){
        TTT game = new TTT(idCounter++);
        tttdao.insert(game);
        return game;
    }

    public TTT play(int id,String move){
        String[] temp = move.split(" ",2);
        if(tttdao.update(id, Integer.valueOf(temp[0]), Integer.valueOf(temp[1]))){return tttdao.select(id);}
        else{System.out.println("Invalid Move made on board"+id);return tttdao.select(id);}
    }

    // public String gamePlay(TTT game, int row,int column){
    //     Scanner scan = new Scanner(System.in);
    //     TTT game = new TTT();
    //     game.initializeBoard();
    //     System.out.println("Tic-Tac-Toe!");
    //     do
    //     {
    //         System.out.println("Current board layout:");
    //         game.printBoard();
    //         int row;
    //         int col;
    //         do{
    //             System.out.println("Player " + game.getCurrentPlayerMark() + ", enter an empty row and column to place your mark!");
    //             row = scan.nextInt()-1;
    //             col = scan.nextInt()-1;
    //         }while (!game.placeMark(row, col));
    //         game.changePlayer();
    //     }
    //     while(!game.checkForWin() && !game.isBoardFull());

        
    //     if (game.isBoardFull() && !game.checkForWin())
    //     {
    //         System.out.println("The game was a tie!");
    //     }
    //     else
    //     {
    //         System.out.println("Current board layout:");
    //         game.printBoard();
    //         game.changePlayer();
    //         System.out.println(Character.toUpperCase(game.getCurrentPlayerMark()) + " Wins!");
    //     }
    //     return null;
    // }
}