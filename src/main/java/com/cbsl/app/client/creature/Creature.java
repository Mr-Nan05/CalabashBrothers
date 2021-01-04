package com.cbsl.app.client.creature;

import com.cbsl.app.client.action.*;
import com.cbsl.app.client.view.DraggableView;
import javafx.scene.image.Image;

public class Creature {
    protected int id;//生物id
    protected String name;//生物名

    protected int hp;//血量
    protected int resthp;//剩余血量
    protected int power;//攻击力
    protected int defense;//防御力
    protected boolean specialattackflag;//是否发动过大招
    protected boolean alive;//是否存活
    protected DraggableView imageView;
    protected int X;//横坐标
    protected int Y;//纵坐标

    public Creature(){
        this.specialattackflag=false;
        this.alive=true;
    }

    public DraggableView getImageView(){
        return imageView;
    }

    //返回受到伤害后是否存活
    public boolean beInjuried(int injury){
        resthp-=injury;
        //血量条减少

        if(resthp<=0) alive=false;
        return alive;
    }

    public void recovery(){
        this.resthp+=0.5*this.hp;

        //血量条增加
    }

    public void specialAttack(){
        specialattackflag=true;
    }

    public String getName(){
        return this.name;
    }

    public int getId(){
        return this.id;
    }

    public int getHP(){
        return this.hp;
    }

    public int getRestHP(){
        return this.resthp;
    }

    public int getPower(){
        return this.power;
    }

    public int getDefense(){
        return this.defense;
    }

    public int getXPos(){
        return this.X;
    }

    public int getYPos(){
        return this.Y;
    }

    public boolean getSpecialAttackFlag(){
        return this.specialattackflag;
    }

    public boolean getAlive(){
        return this.alive;
    }

    public void setXPos(int x){
        this.X=x;
    }

    public void setYPos(int y){
        this.Y=y;
    }

}


