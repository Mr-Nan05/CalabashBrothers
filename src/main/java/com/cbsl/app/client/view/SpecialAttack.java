package com.cbsl.app.client.view;

import com.cbsl.app.client.action.Dir;
import com.cbsl.app.client.world.Main;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//TODO:解决自动移动，Lock改成int型二维数组，在run函数内判断如果lock位置是与自己相反的符号，则自行销毁
//根据Dir判断自己的符号，自行销毁后可以发出一个信号alive置为False
public class SpecialAttack{
    private int X;
    private int Y;
    private int ID;
    private List<SpecialAttackView> attackViews;

    //默认是葫芦娃，方向向右
    public SpecialAttack(int id){
        super();
        ID = id;
        attackViews = new ArrayList<>();
        int site = Integer.parseInt(Main.Locker.getSite(id));
        X = site/10;
        Y = site%10;
        attack();
    }

    void attack(){
        int selfValue = Main.Locker.getValue(X - 1, Y - 1);
        for(int i = 0; i < 10; i++){
            int oppoValue = Main.Locker.getValue(i, Y - 1);
            if(selfValue * oppoValue < 0){
                SpecialAttackView attackView = new SpecialAttackView(i, Y - 1);
                attackViews.add(attackView);
                new Thread(attackView).start();
            }
        }
    }

    //判断是否还有必要存在
    boolean getStatus(){
        try{
            boolean tmp = true;
            for(SpecialAttackView attackView:attackViews){
                if(!attackView.alive)
                    Main.gameStage.framePane.getChildren().remove(attackView);
                else
                    tmp = false;
            }
            return tmp;
        }catch (NullPointerException e){
            e.printStackTrace();
            return false;
        }
    }

    public class SpecialAttackView  extends ImageView implements Runnable{
        private final double UNIT = 80;
        private boolean alive;
        SpecialAttackView(int x, int y){
            alive = true;
            this.setX(x*80);
            this.setY(y*80 + 140);
            Init();
        }

        void Init(){
            this.setSmooth(true);
            this.setCache(true);
            this.setFitWidth(UNIT);
            this.setFitHeight(UNIT);
            this.setVisible(true);
            try{
                if(ID > 0){
                    this.setImage(new Image("com/cbsl/app/client/view/images/specialattackbro.png"));
                }
                else{
                    this.setImage(new Image("com/cbsl/app/client/view/images/specialattackdemon.png"));
                }

                Main.gameStage.framePane.getChildren().add(this);
            }catch (NullPointerException e){
                System.out.println("No such image file!");
                e.printStackTrace();
            }
        }

        @Override
        public void run(){
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
                this.setVisible(false);
                alive = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
