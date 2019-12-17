package com.jacksteam.googol.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TTTBotService{
    /**
     * //Variables
     * buffer - stores the possible moves of the next layer
     * scores - stores the scores of the possible moves of the next layer
     * 
     * //Methods
     * bestmove(char[] flat,boolean axe) - recieves flat representation of the board and a bool of whether or not it is playing x
     *                                      returns the best possible move for itself
     * boardBabies(char[][] board, boolean axe) - recieves the board and whether or not it is player x, returns arraylist of all possible boards with 1 move made
     * checkWin(board) - bool for whether or not someone won
     * checkWinner(board) - returns char of whos the winner, returns - if no one won yet
     * checkTie(board) - bool:board got tie mou?
     * minimax(board,depth,axe?,alpha,beta) - recieves the board state, depth to search,are u AXE?,highest current known edge(alpha),
     *                                        lowest current known edge(beta) then runs minimax algorithm with alpha beta pruning
     * 
     * //Minimax
     * A tree battle between maxboii and minboii where maxboii wants the score to be as high as possible while minboii wants 
     * the score to be as low as possible. And they are all masters of game theory and expect their opponents to make the best
     * move because theyll be worse of if they dont anyways. 
     * Alpha Beta pruning is a way of pruning branches that cannot possibly change the outcome by removing a branch if it is bound to
     * have a value that the player making the move would never pick since they have a better option on the same layer
     */
//maxboii is x, minboii is o
    private ArrayList<char[][]> buffer;
    //ArrayList<String> realbuffer = new ArrayList<>();
    private ArrayList<Integer> scores;
    @Autowired
    public TTTBotService(){

    }

    public String bestmove(char[] flat,boolean axe){
        char[][] board = new char[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                board[i][j]=flat[i*3+j];
            }
        }
        if (checkWin(board) || checkTie(board)){return "0 0";}

        //clear the buffer and scores
        this.buffer = new ArrayList<>();
        this.scores = new ArrayList<>();

        //Get best Score and find this board
        int bestScore = minimax(board, 10, axe, -120, 120) ;
        char[][] uwu = buffer.get(scores.indexOf(bestScore));

        //System.out.println("Do");
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(uwu[i][j]!=board[i][j]){return i+" "+j;}
            }
        }
        return null;
    }

    public ArrayList<char[][]> boardBabies(char[][] board,boolean axe){
        
        ArrayList<char[][]> out = new ArrayList<>();
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(board[i][j]=='-'){
                    char[][] boardCopy=new char[3][3];
                    for(int k=0;k<3;k++){
                        for(int l=0;l<3;l++){
                            boardCopy[k][l]=board[k][l];
                        }
                    }

                    if(axe){boardCopy[i][j]='x';
                    out.add(new char[3][3]);
                    for(int k=0;k<3;k++){
                        for(int l=0;l<3;l++){
                            out.get(out.size()-1)[k][l]=boardCopy[k][l];
                        }
                    }}
                   
                    else{boardCopy[i][j]='o';
                    out.add(new char[3][3]);
           
                    for(int k=0;k<3;k++){
                        for(int l=0;l<3;l++){
                            out.get(out.size()-1)[k][l]=boardCopy[k][l];
                        }
                    }}
                }
            }
        }

        return out;
    }

    public boolean checkWin(char[][] board){
        if(checkWinner(board)!='-'){return true;}else{return false;}
    }
    public char checkWinner(char[][] board){
        //rows
        for(int i=0;i<3;i++){
            if(board[i][0]=='-' || board[i][1]=='-' || board[i][2]=='-'){continue;}
            else if(board[i][0]==board[i][1] && board[i][1]==board[i][2]){return board[i][0];}
        }
        //columns
        for(int i=0;i<3;i++){
            if(board[0][i]=='-' || board[1][i]=='-' || board[2][i]=='-'){continue;}
            else if(board[0][i]==board[1][i] && board[1][i]==board[2][i]){return board[0][i];}
        }
        //diagonals
        if(!(board[0][0]=='-' || board[1][1]=='-' || board[2][2]=='-')){
            if(board[0][0]==board[1][1] && board[1][1]==board[2][2]){return board[1][1];}
        }
        if(!(board[2][0]=='-' || board[1][1]=='-' || board[0][2]=='-')){
            if(board[2][0]==board[1][1] && board[1][1]==board[0][2]){return board[1][1];}
        }
        return '-';
    }
    public boolean checkTie(char[][] board){
        //Is board full?
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){if(board[i][j]=='-'){return false;}}
        }
        //Did someone win? yes?->no tie nuubs    no?->true
        if(checkWin(board)){System.out.println("ping");return false;}else{return true;}
    }

    public int minimax(char[][] board, int depth, boolean maxboii, int alpha,int beta){
        // Leaf?
        if (depth==0 || checkWin(board) || checkTie(board)){
            if(depth==9){
                buffer.add(new char[3][3]);
                    for(int k=0;k<3;k++){
                        for(int l=0;l<3;l++){
                            buffer.get(buffer.size()-1)[k][l]=board[k][l];
                        }
                    }
                    if(checkWinner(board)=='x'){
                        scores.add(100+depth);
                    }else if(checkWinner(board)=='o'){
                        scores.add(-100-depth);
                    }else{
                        scores.add(0);
                    }
            }
            if(checkWinner(board)=='x'){
                return 100+depth;
            }else if(checkWinner(board)=='o'){
                return -100-depth;
            }else{
                return 0;
            }
        }

        if(maxboii){
            int maxEval=-100;
            ArrayList<char[][]> children = boardBabies(board,true);
            for(char[][] child:children){
                int eval = minimax(child, depth-1, false, alpha, beta);
                if(eval>maxEval){maxEval=eval;}
                if(eval>alpha){alpha=eval;}
                if(beta<=alpha){break;}
            }

            if(depth==9){
                buffer.add(new char[3][3]);
                    for(int k=0;k<3;k++){
                        for(int l=0;l<3;l++){
                            buffer.get(buffer.size()-1)[k][l]=board[k][l];
                        }
                    }
                scores.add(maxEval);
            }
            return maxEval;
        }
        else{
            int minEval=100;
            ArrayList<char[][]> children = boardBabies(board,false);
            for(char[][] child:children){
                int eval = minimax(child, depth-1, true, alpha, beta);
                if(eval<minEval){minEval=eval;}
                if(eval<beta){beta=eval;}
                if(beta<=alpha){break;}
            }

            if(depth==9){
                buffer.add(new char[3][3]);
                    for(int k=0;k<3;k++){
                        for(int l=0;l<3;l++){
                            buffer.get(buffer.size()-1)[k][l]=board[k][l];
                        }
                    }
                scores.add(minEval);
            }


            return minEval;
        }

    }


    public static void main(String[] args) {
        TTTBotService bot = new TTTBotService();
        char[] a={'o','o','x','x','x','-','o','-','o'};

        String k =bot.bestmove(a, true);
        System.out.println(k);
//true makes u x
        // int bestScore = bot.minimax(test, 10, true, -120, 120) ;
        // char[][] uwu = bot.buffer.get(bot.scores.indexOf(bestScore));

        // System.out.println("buufer here");
        // //ArrayList<char[][]> babies = bot.boardBabies(test,false);
        // int count=0;
        // for(char[][] baby:bot.buffer){
        //     for(int i=0;i<3;i++){
        //         for(int j=0;j<3;j++){
        //             System.out.print(baby[i][j]);
        //         }
        //         System.out.println();
        //     }
        //     System.out.println(bot.scores.get(count++));
        //     System.out.println("=================");
  
        // }

        // System.out.println("Do");
        // for(int i=0;i<3;i++){
        //     for(int j=0;j<3;j++){
        //         System.out.print(uwu[i][j]);
        //     }
        //     System.out.println();
        // }

    }
}