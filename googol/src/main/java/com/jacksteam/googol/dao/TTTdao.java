package com.jacksteam.googol.dao;

import com.jacksteam.googol.model.TTT;

public interface TTTdao{
    public int insert(TTT game);
    public TTT select(int id);
    public int delete(int id);
    public boolean update(int id,int row,int col);
}