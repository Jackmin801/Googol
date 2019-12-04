/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

/**
 *
 * @author zhien
 */
public class TTT {
    private char move[][];
    private char currentMark;
    String getCurrentMark;

    public TTT() {
        move = new char[3][3];
        currentMark = 'o';
        newGame();
    }
    
    public char getCurrentMark() {
        return currentMark;
    }
    
    public void newGame(){
        for (int i = 0; i<=2; i++){
            for (int j = 0; j<=2; j++){
                move[i][j]='-';
            }
        }
}
    public void currentGame(){
        System.out.println("-------------");
        for (int i = 0; i<=2; i++){
            System.out.print("| ");
            for (int j = 0; j<=2; j++){
                System.out.print(move[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");   
    }
    }
    
    public boolean placeMark(int a, int b){
        if(a>=0&&a<3){
            if(b>=0&&b<3){
                if (move[a][b] == '-'){
                    move[a][b] = currentMark;
                    return true;
                }}}
               return false;   
    }
    
    public void changeMark(){
        if(currentMark=='o'){
            currentMark='x';
        }
        else
            currentMark='o';
    }
    
    public boolean isFull(){
        for (int i = 0; i<=2; i++){
            for (int j = 0; j<=2; j++){
                if (move[i][j]=='-'){
                    return false;
                }}
            }
    return true;
    }
    
    public boolean checkSame(char a, char b, char c){
        if((a!='-')&&(a==b)&&(b==c)){
            return true;}
        return false;
        }
        
    public boolean checkRows(){
        for (int i = 0; i<=2; i++){
            if(checkSame(move[i][0],move[i][1],move[i][2])){
                return true;
            }
    }return false;
    }
    
    public boolean checkColumns(){
        for (int i = 0; i<=2; i++){
            for (int j = 0; j<=2; j++){
                if(checkSame(move[0][j],move[1][j],move[2][j])){
                    return true;
                }   
                }}return false;
            }
    
    public boolean checkDiagonals(){
        for (int i = 0; i<=2; i++){
            for (int j = 0; j<=2; j++){
                if((checkSame(move[0][0],move[1][1],move[2][2]))||(checkSame(move[0][2],move[1][1],move[2][0]))){
                    return true;
                }
            }
    }return false;
    }
    
    public boolean checkWin(){
        if(checkRows()||checkColumns()||checkDiagonals()){
            return true;
        }return false;
    }
    }
        





    

