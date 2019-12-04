/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.Scanner;

/**
 *
 * @author zhien
 */
public class Tictactoe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner s = new Scanner(System.in);
        TTT game1 = new TTT();
        game1.newGame();
        do{
            game1.currentGame();
            int row;
            int column;
            System.out.println("Player "+game1.getCurrentMark()+", please enter an empty row and column to place your mark");
            row = s.nextInt();
            column = s.nextInt();
            game1.placeMark(row, column);
            game1.changeMark();
        }while(!game1.checkWin()&&!game1.isFull());
        
        if(game1.isFull()&&!game1.checkWin()){
            System.out.println("It's a tie!");
        }
        else{
            game1.currentGame();
            System.out.println(game1.getCurrentMark()+" Wins!");
        }
        }
        
    }
    

