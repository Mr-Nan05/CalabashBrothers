package com.cbsl.app.client.view;


import com.cbsl.app.client.world.Main;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class DraggableView extends ImageView {
    private int X;
    private int Y;
    private int ID;//即所在Creature的ID用来获取移动信息
    private boolean onClicked = false;
    private final double UNIT = 80;
    public DraggableView(Image image){
        super(image);
        Init();
    }

    public DraggableView(int id, Image image){
        super(image);
        ID = id;
        Init();
    }

    void Init(){
        this.setSmooth(true);
        this.setPickOnBounds(true);
        this.setCache(true);
        this.setCursor(Cursor.HAND);
        this.setFitWidth(UNIT);
        this.setFitHeight(UNIT);

        this.setOnMouseClicked((event)->{
            onClicked = !onClicked;
        });

        this.setOnMouseMoved((event)->{
            if(onClicked)
                onMove(event);
        });

        this.setOnMouseDragged((event)->{
            onClicked = true;
            onMove(event);
        });

        this.setOnMouseReleased((event)->{
            if(onClicked){
                overMove(event);
            }
        });
    }

    void onMove(javafx.scene.input.MouseEvent event){
        if(event.getX() < 40)
            this.setX(0);
        else if(event.getX() > 760)
            this.setX(720);
        else this.setX(event.getX() - 40);

        if(event.getY() < 180)
            this.setY(140);
        else if(event.getY() > 660)
            this.setY(620);
        else this.setY(event.getY() - 40);
    }

    void overMove(MouseEvent event){
        double x = event.getX();
        double y = event.getY();
        int desX;
        int desY;
        if(x < 40)
            desX = 1;
        else if(x > 760)
            desX = 10;
        else desX = (int)(x/80f) + 1;

        if(y < 180)
            desY = 1;
        else if(y > 660)
            desY = 7;
        else desY = (int)((y - 140)/80f) + 1;

        //TODO:移动成功则发送数据报，并扣除一个动作值，不成功则不扣除
        if(Main.Locker.move(ID, X, Y, desX, desY)){
            setXY(desX, desY);
            //移动成功，发送数据报，用来测试
            //Main.netClient.dataSend(ID +" MOVE " + X + ' ' + Y);

        }
        else setXY(X, Y);

    }

    public void setXY(int x, int y){
        if(x < 1)
            X = 1;
        else if (x > 10)
            X = 10;
        else X = x;

        if(y < 1)
            Y = 1;
        else if (y > 10)
            Y = 10;
        else Y = y;

        this.setX((X - 1) * 80);
        this.setY((Y - 1) * 80 + 140);
    }
}