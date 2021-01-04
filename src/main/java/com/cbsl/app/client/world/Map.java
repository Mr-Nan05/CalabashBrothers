//package com.cbsl.app.client.world;
//
//import com.cbsl.app.client.action.Bullet;
//import com.cbsl.app.client.creature.Creature;
//
//public class Map {
//    public static int LENGTH=10;
//    public static int WIDTH=7;
//
//    private Grid[][] grids;
//
//    public Map(){
//        grids=new Grid[LENGTH][WIDTH];
//    }
//
//    public void addCreature(Creature c,int xpos,int ypos){
//        grids[xpos][ypos].addCreature(c);
//    }
//
//}
//
//class Grid{
//    Creature creature;
//    Bullet bullet;
//    private boolean cflag;
//    private boolean bflag;
//
//    public Grid(){
//        cflag=false;
//        bflag=false;
//    }
//
//    public void addCreature(Creature c){
//        this.creature=c;
//        cflag=true;
//    }
//
//    public Creature getCreature(){
//        if(cflag) return creature;
//        else return null;
//    }
//
//    public void removeCreature(){
//        cflag=false;
//    }
//
//    public void addBullet(Bullet b){
//        this.bullet=b;
//        bflag=true;
//    }
//
//    public Bullet getBullet(){
//        if(bflag) return bullet;
//        else return null;
//    }
//
//    public void removeBullet(){
//        bflag=false;
//    }
//
//
//}
