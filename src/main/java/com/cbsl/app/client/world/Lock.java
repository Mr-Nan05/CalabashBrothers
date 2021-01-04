package com.cbsl.app.client.world;

import static java.lang.Math.abs;

public class Lock {
    private int X;
    private int Y;
    private boolean LOCK;
    //用来标记被占用的位置
    private int [][] MARK = new int[10][7];
    public Lock(){
        LOCK = false;
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 7; j++)
                MARK[i][j] = 0;
    }

    public boolean isLocked(){
        return LOCK;
    }

    public void lock(){LOCK = true;}
    public void unLock(){LOCK = false;}

    public boolean occupy(int id, int x, int y){
        if(LOCK)
            return false;
        else if(MARK[x - 1][y - 1] == 0){
            if(id < 0){
                if(x < 6)
                    return false;
                else{
                    MARK[x - 1][y - 1] = id;
                    return true;
                }
            }
            else{
                if(x > 5)
                    return false;
                else{
                    MARK[x - 1][y - 1] = id;
                    return true;
                }
            }

        }
        else return false;
    }

    //如果移动的目标位置是空闲的就成功占用并返回True，否则拒绝占用并返回False
    public boolean occupy(int id, int srcX, int srcY, int desX, int desY){
        if(LOCK)
            return false;
        else if(MARK[desX - 1][desY - 1] == 0){
            MARK[desX - 1][desY - 1] = id;
            MARK[srcX - 1][srcY - 1] = 0;
            return true;
        }
        else return false;
    }
    //移动成功之后将原本占用的位置释放
    void release(int x, int y){
        MARK[x - 1][y - 1] = 0;
    }
    //控制角色进行移动
    public boolean move(int id, int srcX, int srcY, int desX, int desY){
        if(LOCK)
            return false;
        else if(occupy(id, desX, desY)) {
            release(srcX, srcY);
            Main.gameStage.setMove(abs(id), desX, desY);
            Main.gameStage.addBackAction(new int[]{abs(id), srcX, srcY, desX, desY});
            return true;
        }
        else return false;
    }

    public String getSite(int id){
        String tmpStr = "";
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 7; j++)
                if(MARK[i][j] == id)
                    return tmpStr + (i + 1) + (j + 1);

        return "00";
    }

    public int getValue(int x, int y){
        return MARK[x][y];
    }
}
