package com.cbsl.app.client.view;

import com.cbsl.app.client.action.Dir;
import com.cbsl.app.client.action.Type;
import com.cbsl.app.client.world.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.concurrent.TimeUnit;

//TODO:解决自动移动，Lock改成int型二维数组，在run函数内判断如果lock位置是与自己相反的符号，则自行销毁
//根据Dir判断自己的符号，自行销毁后可以发出一个信号alive置为False
public class BulletAttack extends ImageView implements Runnable{
    private int X;
    private int Y;
    private int ID;
    private Dir D;
    private Type TYPE;
    private double timer;
    private boolean alive;//判断实例任务是否完成，如果已经完成则进行垃圾回收

    private final double UNIT = 80;
    //默认是葫芦娃，方向向右
    public BulletAttack(int x, int y){
        super();
        D = Dir.R;
        X = x;
        Y = y;
        alive = true;
        timer = System.currentTimeMillis();

        Init();
    }
    public BulletAttack(Type type, int x, int y){
        super();
        TYPE = type;
        X = x;
        Y = y;
        alive = true;
        timer = System.currentTimeMillis();

        Init();
    }

    public BulletAttack(Dir dir, int x, int y){
        super();
        D = dir;
        X = x;
        Y = y;
        alive = true;
        timer = System.currentTimeMillis();

        Init();
    }

    public BulletAttack(int id){
        super();
        ID = id;
        alive = true;
        timer = System.currentTimeMillis();

        int site = Integer.parseInt(Main.Locker.getSite(id));
        X = site/10;
        Y = site%10;
        Init();
    }

    void Init(){
        this.setSmooth(true);
        this.setCache(true);
        this.setFitWidth(UNIT);
        this.setFitHeight(UNIT);
        this.setVisible(true);
        this.setY((Y - 1)*80 + 140);
        try{
            this.setX((X - 1)*80 + 60);
            if(ID > 0){
                this.setImage(new Image("com/cbsl/app/client/view/images/attackbro.png"));
            }
            else{
                this.setImage(new Image("com/cbsl/app/client/view/images/attackdemon.png"));
            }
        }catch (NullPointerException e){
            System.out.println("No such image file!");
            e.printStackTrace();
        }
    }

    boolean getStatus(){

        return alive;
    }

    @Override
    public void run(){
        while(alive){
            try {
                double now = System.currentTimeMillis();
                double realX = this.getX();
                int siteX = (int)(realX/80f) + 1;
                if(siteX <= 0||siteX > 10||now - timer > 1600){
                    this.setVisible(false);
                    alive = false;
                    break;
                }
                int selfValue = Main.Locker.getValue(X - 1, Y - 1);
                int oppoValue = Main.Locker.getValue(siteX - 1, Y - 1);

                if(selfValue*oppoValue < 0){
                    this.setVisible(false);
                    alive = false;
                }
                if(ID > 0)
                    this.setX(realX + 20);
                else
                    this.setX(realX - 20);
                TimeUnit.MILLISECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
