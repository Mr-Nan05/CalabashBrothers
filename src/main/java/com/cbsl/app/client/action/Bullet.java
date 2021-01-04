package com.cbsl.app.client.action;

import javafx.util.Pair;

public class Bullet{
    private int damage;
    private int xpos;
    private int ypos;
    private int sourceid;
    private boolean alive;
    private Dir dir;
    private static int id=1;


    public Bullet(int damage,int xpos,int ypos,Dir dir){
        this.damage=damage;
        this.xpos=xpos;
        this.ypos=ypos;
        this.sourceid=id++;
        this.dir=dir;
        this.alive=true;
    }

    public int attackHit(){
        return this.damage;
    }

    //得到该子弹的坐标
    public Pair<Integer,Integer> getCoordinate(){
        return new Pair<>(this.xpos,this.ypos);
    }

    //子弹朝dir方向移动一个单位
    public Pair<Integer,Integer> move(){
        //撞到墙，毁灭
        if((this.dir==Dir.R&&xpos>=10)||(this.dir==Dir.L&&xpos<=1))
            destroy();
        else if(this.dir==Dir.R) {
            this.xpos++;
            //往右移动的特效
        }
        else if(this.dir==Dir.L){
            this.xpos--;
            //往左移动的特效
        }
        return getCoordinate();
    }

    public Dir getDir(){
        return this.dir;
    }

    public boolean destroy(){
        this.alive=false;
        return !this.alive;
    }

}
