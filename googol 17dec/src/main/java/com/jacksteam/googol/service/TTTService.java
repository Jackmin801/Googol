package com.jacksteam.googol.service;

import com.jacksteam.googol.dao.TTTdao;
import com.jacksteam.googol.model.TTT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;



@Service
public class TTTService {
    /**
     * //Variables
     * tttdao - TTT DAO duh
     * idCounter - counter to keep track of ids used 
     * 
     * //Methods
     * newGame() - returns a new game with unique id
     * play(id, move) - finds the board with this id and makes the move
     * 
     */
    private final TTTdao tttdao;
    public static int idCounter=0;
    private boolean legal;

    @Autowired
    public TTTService(@Qualifier("fakeDAO")TTTdao tttdao){
        this.tttdao=tttdao;
        this.legal=true;
    }

    public TTT newGame(){
        TTT game = new TTT(idCounter++);
        tttdao.insert(game);
        return game;
    }

    public TTT play(int id,String move){
        String[] temp = move.split(" ",2);
        if(tttdao.update(id, Integer.valueOf(temp[0]), Integer.valueOf(temp[1]))){legal=true;return tttdao.select(id);}
        else{System.out.println("Invalid Move made on board"+id);legal=false;return tttdao.select(id);}
    }

    public boolean getLegal() {
        return legal;
    }

    

}

