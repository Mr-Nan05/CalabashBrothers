package com.cbsl.app.client.world;

import com.cbsl.app.client.action.*;
import com.cbsl.app.client.creature.Creature;
import com.cbsl.app.client.creature.brothers.*;
import com.cbsl.app.client.creature.demons.*;
import javafx.application.Platform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map;

public class World {
    //Map map;

    private boolean SIDE;//阵营，0表示葫芦娃，1表示妖精
    public static int NUM=7;//每方角色总数
    private boolean[] clife={true,true,true,true,true,true,true};//己方每个角色的存活状态
    private int myalive=7;//己方剩余角色数
    private int opalive=7;//对方剩余角色数
    private boolean myrecoverflag=true;//己方是否可以群体回复
    private boolean oprecoverflag=true;//对方是否可以群体回复

    private Map<Integer, Creature> BROS;//己方角色
    private Map<Integer, Creature> DEMONS;//对方角色
    private Map<Integer, Creature> SELF;//己方角色
    private Map<Integer, Creature> OPPO;//对方角色

    private Type type;//操纵的是葫芦娃还是妖怪
    private Order order;//出手顺序
    public int round;//当前回合数

    private ArrayList<String> record;//记录每回合双方的操作

    String mychoice;//某回合己方的操作
    String opchoice;//某回合对方的操作

    String time;//文件名中的时间
    String filename;//文件名

    World(){
        BROS=new HashMap<>();
        DEMONS=new HashMap<>();
        boysInit(BROS);
        demonsInit(DEMONS);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        time=df.format(new Date());
        filename=time+".txt";
        File file =new File(filename);
        try{
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //双方的阵营初始化
    public void setType(Type type){
        this.type = type;
        if(type == Type.BROTHER){
            SELF=BROS;
            OPPO=DEMONS;
            order=Order.FIRST;
        }else{
            SELF=DEMONS;
            OPPO=BROS;
            order=Order.LAST;
        }
    }

    void boysInit(Map<Integer, Creature> boysSide){
        boysSide.put(1, new RedBro());
        boysSide.put(2, new OrangeBro());
        boysSide.put(3, new YellowBro());
        boysSide.put(4, new GreenBro());
        boysSide.put(5, new CyanBro());
        boysSide.put(6, new BlueBro());
        boysSide.put(7, new PurpleBro());
    }

    void demonsInit(Map<Integer, Creature> demonsSide){
        demonsSide.put(-1, new SnakeDemon());
        demonsSide.put(-2, new ScorpionDemon());
        demonsSide.put(-3, new CrocodileDemon());
        demonsSide.put(-4, new EagleDemon());
        demonsSide.put(-5, new ButterflyDemon());
        demonsSide.put(-6, new SpiderDemon());
        demonsSide.put(-7, new ToadDemon());
    }

    Map<Integer, Creature> getSELF(){
        return SELF;
    }

    Map<Integer, Creature> getOPPO(){
        return OPPO;
    }

    Map<Integer, Creature> getBROS(){
        return BROS;
    }

    Map<Integer, Creature> getDEMONS(){
        return DEMONS;
    }

    //联机游玩的main函数
    public void mainConnected(){
        round=1;
        record=new ArrayList<>();

        //initCreature();
        loadGameInterface();

        while(myalive>0&&opalive>0){
            //TODO 判断出手权
            //order=?


            mychoice=this.type.toString()+" "+this.order.toString()+" ; ";
            //调用选择命令的逻辑

            //传出

            //TODO 通信获得对方的选择
            String opchoice=null;

            //displayBattle(round,mychoice,opchoice);

            record.add("round "+round+" :");
            //record.add(mychoice);
            record.add(opchoice);

            round++;
        }


    }

    //本地复现的main函数
    public void mainLocal(ArrayList<String> record){
        this.record=record;

        //initCreature();
        loadGameInterface();

        for(int i=0;i<record.size();++i){
            String[] temp=record.get(i).split(" ");
            if(temp[0]=="round"){
                round=Integer.parseInt(temp[1]);
                continue;
            }

            displayBattle(round,record.get(i),record.get(++i));
        }

    }

    public void connect(){

    }

    //TODO 加载游戏界面
    public static void loadGameInterface(){

    }

    //记录初始回合信息：
    public void initBattle(){
        String result="round 0 :";
        result+="Brother has id 1 id 2 id 3 id 4 id 5 id 6 id 7 ; Demon has id -1 id -2 id -3 id -4 id -5 id -6 id -7 ; .";
        record.add(result);
    }

    //初始化双方
    public void initCreature(){
        int id=0,x=0,y=0;
        Creature[] bros=new Creature[NUM];
        RedBro bro1=new RedBro();
        bros[id++]=bro1;
        OrangeBro bro2=new OrangeBro();
        bros[id++]=bro2;
        YellowBro bro3=new YellowBro();
        bros[id++]=bro3;
        GreenBro bro4=new GreenBro();
        bros[id++]=bro4;
        CyanBro bro5=new CyanBro();
        bros[id++]=bro5;
        BlueBro bro6=new BlueBro();
        bros[id++]=bro6;
        PurpleBro bro7=new PurpleBro();
        bros[id++]=bro7;

        id=0;x=9;y=0;
        Creature[] demons=new Creature[NUM];
        SnakeDemon demon1=new SnakeDemon();
        demons[-(id++)]=demon1;
        ScorpionDemon demon2=new ScorpionDemon();
        demons[-(id++)]=demon2;
        CrocodileDemon demon3=new CrocodileDemon();
        demons[-(id++)]=demon3;
        EagleDemon demon4=new EagleDemon();
        demons[-(id++)]=demon4;
        ButterflyDemon demon5=new ButterflyDemon();
        demons[-(id++)]=demon5;
        SpiderDemon demon6=new SpiderDemon();
        demons[-(id++)]=demon6;
        ToadDemon demon7=new ToadDemon();
        demons[-(id++)]=demon7;

    }

    //TODO 根据选择行为的文本重现本回合战斗
    /*文本规则：（每个单词、数字和符号之间都有空格）
    * 每方每回合的操作之前要加一句 BROTHER[/DEMON] FIRST[/LAST] ;
    * 某一回合移动——round n : 1 MOVE x y ;
    * 某一回合攻击——round n : 1 ATTACK ;
    * 某一回合发大招——round n : 1 SPECIALATTACK ;
    * 某一回合回复——round n : 1 RECOVER ;
     *  */
    public void displayBattle(int round,String player1,String player2){
        //将两个玩家总指令分割成多条指令
        String[] player1choice=player1.split(";");
        String[] player2choice=player2.split(";");

        //得到两个玩家的类型和出手权
        Type type1=Type.valueOf(player1choice[0].split(" ")[0]);
        Type type2=Type.valueOf(player2choice[0].split(" ")[0]);
        Order order1=Order.valueOf(player1choice[0].split(" ")[1]);
        Order order2=Order.valueOf(player2choice[0].split(" ")[1]);

        if(order1==Order.FIRST){
            praseString(player1choice[1]);
            praseString(player2choice[1]);
            praseString(player2choice[2]);
            praseString(player1choice[2]);
            praseString(player2choice[3]);
            praseString(player1choice[3]);
            praseString(player2choice[4]);
            praseString(player1choice[4]);
            praseString(player2choice[5]);
        }else if(order2==Order.FIRST){
            praseString(player2choice[1]);
            praseString(player1choice[1]);
            praseString(player1choice[2]);
            praseString(player2choice[2]);
            praseString(player1choice[3]);
            praseString(player2choice[3]);
            praseString(player1choice[4]);
            praseString(player2choice[4]);
            praseString(player1choice[5]);
        }

    }

    public void praseString(String command){
        String[] parts=command.split(" ");
        int id=Integer.parseInt(parts[0]);
        String operation=parts[1];
        switch (operation){
            case "MOVE":
                int x=Integer.parseInt(parts[2]);
                int y=Integer.parseInt(parts[3]);
                creatureMove(id,x,y);
                break;
            case "ATTACK":
                creatureAttack(id);
            case "SPECIALATTACK":
                creatureSpecialAttack(id);
                break;
            case "RECOVER":
                creatureRecovery(id);
                break;
        }
    }

    //假设id依然存活,真实id
    public void move(int id, int desX, int desY){
        int site = Integer.parseInt(Main.Locker.getSite(id));
        int srcX = site/10;
        int srcY = site%10;
        setPos(id, srcX, srcY, desX, desY);
    }

    //id代表己方序号，不代表真实id，左边从1开始
    public void setPos(int id, int srcX, int srcY, int desX, int desY){
        int temp=id;
        if(type == Type.DEMON){
            temp = -id;
        }
        try{
            if(Main.Locker.occupy(id, srcX, srcY, desX, desY)){
                SELF.get(temp).getImageView().setXY(desX, desY);
            }else Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


    public Map<Integer, Creature> setPos(int id, int x, int y,Map<Integer, Creature> hit){
        try{
            if(Main.Locker.occupy(id, x, y)){
                hit.get(id).getImageView().setXY(x, y);
            }else Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return hit;
    }

    private void creatureMove(int id,int x,int y){
        if(SELF.containsKey(id)){
            SELF.get(id).setXPos(x);
            SELF.get(id).setYPos(y);
        }else if(OPPO.containsKey(id)){
            OPPO.get(id).setXPos(x);
            OPPO.get(id).setYPos(y);
        }
        //图形界面变化
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                move(id,x,y);
            }
        });

    }
    private void creatureAttack(int id){
        int damage,x,y;
        Dir dir;
        if(SELF.containsKey(id)){
            damage=SELF.get(id).getPower();
            x=SELF.get(id).getXPos();
            y=SELF.get(id).getYPos();
            dir=(type==Type.BROTHER)?Dir.R:Dir.L;
            OPPO=bulletAction(damage,x,y,dir,OPPO);
        }else if(OPPO.containsKey(id)){
            damage=OPPO.get(id).getPower();
            x=OPPO.get(id).getXPos();
            y=OPPO.get(id).getYPos();
            dir=(type==Type.BROTHER)?Dir.L:Dir.R;
            SELF=bulletAction(damage,x,y,dir,SELF);
        }
        //图形界面变化
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Main.gameStage.attack(id);
            }
        });

    }

    private Map<Integer, Creature> bulletAction(int damage,int x,int y,Dir dir,Map<Integer, Creature> hit){
        Bullet bullet=new Bullet(damage,x,y,dir);

        /*while(true){
            if(dir==Dir.R&&posHasCreature(x+1,y,hit)){
                x++;
                Set<Integer> keySet = hit.keySet();
                Iterator<Integer> it =keySet.iterator();
                while(it.hasNext()){
                    Integer key=it.next();
                    Creature value = hit.get(key);
                    if(value.getAlive()&&value.getXPos()==x&&value.getYPos()==y) {
                        hit=bulletHit(key,hit,damage);
                        bullet.destroy();
                        break;
                    }
                }
            }
            else if(dir==Dir.L&&posHasCreature(x-1,y,hit)){
                x--;
                Set<Integer> keySet = hit.keySet();
                Iterator<Integer> it =keySet.iterator();
                while(it.hasNext()){
                    Integer key=it.next();
                    Creature value = hit.get(key);
                    if(value.getAlive()&&value.getXPos()==x&&value.getYPos()==y) {
                        hit=bulletHit(key,hit,damage);
                        bullet.destroy();
                        break;
                    }
                }
            }
            else{
                bullet.move();
            }
            if(bullet.destroy())
                break;
        }*/

        int key=posHasCreature(x,y,hit);
        if(key!=0){
            hit=bulletHit(key,hit,damage);
        }

        return hit;
    }

    private Map<Integer, Creature> bulletHit(Integer key,Map<Integer, Creature> hit,int damage){
        int defense=hit.get(key).getDefense();
        hit.get(key).beInjuried((damage/defense)*300);
        if(!hit.get(key).getAlive()){
            if(getSELF().containsKey(key)){
                myalive-=1;
            }
            else if(getOPPO().containsKey(key)){
                opalive-=1;
            }
        }
        return hit;
    }


    private void creatureSpecialAttack(int id){
        int damage,x,y;

        if(SELF.containsKey(id)){
            damage=SELF.get(id).getPower()*3;
            y=SELF.get(id).getYPos();
            OPPO=specialAttackWork(damage,y,OPPO);
        }else if(OPPO.containsKey(id)){
            damage=OPPO.get(id).getPower()*3;
            y=OPPO.get(id).getYPos();
            SELF=specialAttackWork(damage,y,SELF);
        }
        //图形界面变化
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Main.gameStage.specialAttack(id);
            }
        });

    }

    private Map<Integer, Creature> specialAttackWork(int damage,int y,Map<Integer, Creature> hit){
        //在第y行产生特效


        Set<Integer> keySet = hit.keySet();
        Iterator<Integer> it =keySet.iterator();
        while(it.hasNext()) {
            Integer key = it.next();
            Creature value = hit.get(key);
            if(value.getAlive()&&value.getYPos()==y){
                int defense=hit.get(key).getDefense();
                hit.get(key).beInjuried((damage/defense)*300);
                if(!hit.get(key).getAlive()){
                    if(getSELF().containsKey(key)){
                        myalive-=1;
                    }
                    else if(getOPPO().containsKey(key)){
                        opalive-=1;
                    }
                }
            }
        }

        return hit;
    }

    private void creatureRecovery(int id){
        if(SELF.containsKey(id)){
            myrecoverflag=false;
            SELF=recovery(SELF);
        }else if(OPPO.containsKey(id)){
            oprecoverflag=false;
            OPPO=recovery(OPPO);
        }
        //图形界面
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Main.gameStage.recovery(id);
            }
        });

    }

    private Map<Integer, Creature> recovery(Map<Integer, Creature> hit){
        Set<Integer> keySet = hit.keySet();
        Iterator<Integer> it =keySet.iterator();
        while(it.hasNext()) {
            Integer key = it.next();
            Creature value = hit.get(key);
            if(value.getAlive()){
                value.recovery();
            }
        }

        return hit;
    }

    //返回这一行被子弹打中的敌方单位的id，若本行没有地方单位则返回0
    private int posHasCreature(int xpos,int ypos,Map<Integer, Creature> hit){
        Integer result=0;
        int distance=11;

        Set<Integer> keySet = hit.keySet();
        Iterator<Integer> it =keySet.iterator();
        while(it.hasNext()){
            Integer key = it.next();
            Creature value = hit.get(key);
            if(value.getYPos()==ypos){
                if(Math.abs(value.getXPos()-xpos)<distance){
                    distance=Math.abs(value.getXPos()-xpos);
                    result=key;
                }
            }
        }
        return result;
    }

    public void transList2String(List<String> act){

        mychoice=this.type.toString()+" "+this.order.toString()+" ; ";
        opchoice=((this.type==Type.BROTHER)?"DEMON":"BROTHER")+ " "+((this.order==Order.FIRST)?"LAST":"FIRST")+" ; ";
        this.order=(this.order==Order.FIRST)?Order.LAST:Order.FIRST;

        for(String s:act){
            int id=s.charAt(0)-'0';
            if(this.type==Type.DEMON)
                id=-id;

            String action;
            switch (s.charAt(s.length()-1)-'0'){
                case 0:
                    action="SPECIALATTACK";
                    mychoice+=Integer.toString(id)+" "+action+" ; ";
                    break;
                case 1:
                    action="ATTACK";
                    mychoice+=Integer.toString(id)+" "+action+" ; ";
                    break;
                case 2:
                    action="RECOVER";
                    mychoice+=Integer.toString(id)+" "+action+" ; ";
                    break;
                case 3:
                    action="MOVE ";
                    int x=s.charAt(1)-'0';
                    int y=s.charAt(2)-'0';
                    if(s.length()>4){
                        x=10;
                        y=s.charAt(3)-'0';
                    }
                    action+=Integer.toString(x)+" "+Integer.toString(y);
                    mychoice+=Integer.toString(id)+" "+action+" ; ";
                    break;
            }

        }

        mychoice+='\n';

        try{
            FileWriter filewritter = new FileWriter(filename,true);
            if(round>1)
                filewritter.write('\n');
            filewritter.write("round "+(round++)+" :");
            filewritter.write(mychoice);
            filewritter.write(opchoice);
            filewritter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Main.netClient.dataSend(mychoice);

    }

    public int renewBattleMessage(String command){
        String[] parts=command.split(" ");
        int id=Integer.parseInt(parts[0]);
        if(OPPO.containsKey(id)){
            try{
                FileWriter filewritter = new FileWriter(filename,true);
                filewritter.write(command);
                filewritter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String operation=parts[1];
        switch (operation){
            case "MOVE":
                int x=Integer.parseInt(parts[2]);
                int y=Integer.parseInt(parts[3]);
                creatureMove(id,x,y);
                break;
            case "ATTACK":
                creatureAttack(id);
            case "SPECIALATTACK":
                creatureSpecialAttack(id);
                break;
            case "RECOVER":
                creatureRecovery(id);
                break;
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Main.gameStage.updateBloodBars(SELF);
                Main.gameStage.updateBloodBars(OPPO);
            }
        });


        if(myalive<=0) {
            if(this.type==Type.BROTHER)
                return -1;
            else
                return 1;
        }
        else if(opalive<=0) {
            if(this.type==Type.BROTHER)
                return 1;
            else
                return -1;
        }
        else return 0;
    }


}
