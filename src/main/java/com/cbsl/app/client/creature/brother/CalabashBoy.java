package com.cbsl.app.client.creature.brother;

import com.cbsl.app.client.protocol.PerClient;

import java.util.concurrent.TimeUnit;

public class CalabashBoy implements Runnable{
    protected int HP;
    protected int ATK;
    protected int DEF;
    protected int rank;


    protected boolean live;
    protected String name;
    protected String skills;
    protected PerClient perClient;

    CalabashBoy(){
        HP = 100;
        live = true;
    }
    //休息一下，回复HP
    void getWell(){
        if(HP < 90)
            HP += 10;
        else HP = 100;
    }

    void setClient(PerClient client){
        perClient = client;
    }

    void send(String data){
        perClient.dataSend(data);
    }

    void getAttacked(int atk){
        int damage = atk - DEF;
        if(HP > damage)
            HP -= damage;
        else{
            HP = 0;
            live = false;
        }
    }


    int getHP(){
        return HP;
    }

    String getName(){
        return name;
    }

    int getRank(){
        return rank;
    }

    public void useSkill(){
        System.out.println(skills);
    }

    @Override
    public String toString(){
        return "我是" + name + "，我的技能是" + skills;
    }

    public void run(){
        try {
            while (live) {
                System.out.println(name + "剩余血量:" + HP);
                this.getAttacked(46);
                Thread.yield();
                TimeUnit.MILLISECONDS.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted");  //可能被打断
        }
    }

}

class RedBoy extends CalabashBoy{
    RedBoy(){
        ATK = 50;
        DEF = 30;
        name = "红娃";
        rank = 1;
        skills = "力壮术、巨大化";
    }
}

class OrangeBoy extends CalabashBoy{
    OrangeBoy(){
        ATK = 40;
        DEF = 10;
        name = "橙娃";
        rank = 2;
        skills = "千里眼、顺风耳";
    }
}

class YellowBoy extends CalabashBoy{
    YellowBoy(){
        ATK = 50;
        DEF = 45;
        name = "黄娃";
        rank = 3;
        skills = "刀枪不入";
    }
}

class GreenBoy extends CalabashBoy{
    GreenBoy(){
        ATK = 50;
        DEF = 20;
        name = "绿娃";
        rank = 4;
        skills = "火攻、吸纳火焰、霹雳";
    }
}

class CyanBoy extends CalabashBoy{
    CyanBoy(){
        ATK = 50;
        DEF = 20;
        name = "青娃";
        rank = 5;
        skills = "洪击、蓄水、闪电";
    }
}

class BlueBoy extends CalabashBoy{
    BlueBoy(){
        ATK = 50;
        DEF = 20;
        name = "蓝娃";
        rank = 6;
        skills = "隐形";
    }
}

class PurpleBoy extends CalabashBoy{
    PurpleBoy(){
        ATK = 50;
        DEF = 20;
        name = "紫娃";
        rank = 7;
        skills = "神葫芦";
    }
}