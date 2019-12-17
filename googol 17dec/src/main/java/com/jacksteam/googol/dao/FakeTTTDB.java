package com.jacksteam.googol.dao;

import java.util.ArrayList;

import com.jacksteam.googol.model.TTT;

import org.springframework.stereotype.Repository;


@Repository("fakeDAO")
public class FakeTTTDB implements TTTdao {

    private ArrayList<TTT> DB;

    public FakeTTTDB(){
        this.DB=new ArrayList<>();
    }

    @Override
    public int insert(TTT game) {
        DB.add(game);
        return 1;
    }

    
    @Override
    public int delete(int id) {
        for(TTT i:DB){
            if(i.getId()==id){DB.remove(i);return 1;}
        }
        return 0;
    }

    @Override
    public boolean update(int id,int row,int col) {
        for(TTT i:DB){
            if(i.getId()==id){
                if(i.placeMark(row, col)){i.changePlayer();return true;}     
                else{return false;}      
            }
        }
        return false;
    }

    @Override
    public TTT select(int id) {
        for(TTT i:DB){
            if(i.getId()==id){return i;}
        }
        return null;
    }


}