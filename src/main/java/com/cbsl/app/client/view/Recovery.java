package com.cbsl.app.client.view;

import com.cbsl.app.client.world.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Recovery {
    Image recoverImage;
    List<RecoverView> recoverViews;
    Recovery(int id){
        recoverViews = new ArrayList<>();
        try{
            if(id > 0){
                recoverImage = new Image("com/cbsl/app/client/view/images/recoverbro.png");
                for(int i = 1; i <= 7; i++){
                    TimeUnit.MILLISECONDS.sleep(10);
                    int site = Integer.parseInt(Main.Locker.getSite(i));
                    if(site != 0){
                        int X = site/10;
                        int Y = site%10;
                        RecoverView recoverView = new RecoverView(recoverImage, X, Y);
                        new Thread(recoverView).start();
                        recoverViews.add(recoverView);
                    }
                }
            }
            else{
                recoverImage = new Image("com/cbsl/app/client/view/images/recoverdemon.png");
                for(int i = -1; i >= -7; i--){
                    TimeUnit.MILLISECONDS.sleep(10);
                    int site = Integer.parseInt(Main.Locker.getSite(i));
                    if(site != 0){
                        int X = site/10;
                        int Y = site%10;
                        new Thread(new RecoverView(recoverImage, X, Y)).start();
                    }
                }
            }
        }catch (NullPointerException | InterruptedException e){
            System.out.println("No such image file!");
            e.printStackTrace();
        }
    }

    //判断是否还有必要存在
    boolean getStatus(){
        try{
            boolean tmp = true;
            for(RecoverView recoverView:recoverViews){
                if(!recoverView.alive)
                    Main.gameStage.framePane.getChildren().remove(recoverView);
                else
                    tmp = false;
            }
            return tmp;
        }catch (NullPointerException e){
            return false;
        }
    }

    public class RecoverView  extends ImageView implements Runnable{
        private final double UNIT = 80;
        private boolean alive;
        RecoverView(Image image,int x, int y){
            super(image);
            alive = true;
            this.setX((x - 1)*80);
            this.setY((y - 1)*80 + 140);
            this.setSmooth(true);
            this.setCache(true);
            this.setFitWidth(UNIT);
            this.setFitHeight(UNIT);
            this.setVisible(true);
            Main.gameStage.framePane.getChildren().add(this);
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
