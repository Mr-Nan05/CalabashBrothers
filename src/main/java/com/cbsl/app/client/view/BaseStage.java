package com.cbsl.app.client.view;

import com.cbsl.app.client.world.Main;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class BaseStage {
    protected static final double WIDTH = Main.WIDTH;
    protected static final double HEIGHT = Main.HEIGHT;
    protected Pane framePane;
    protected Image BGImage;

    BaseStage(){
        framePane = new Pane();
        framePane.setPrefWidth(WIDTH);
        framePane.setPrefHeight(HEIGHT);
        //framePane.setVisible(false);
    }

    void setBGImage(){
        BackgroundSize BGSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        framePane.setBackground(new Background(new BackgroundImage(BGImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BGSize)));
    }

    void setBtnLayout(Button btn, int layoutX, int layoutY, int width, int height){
        btn.setLayoutX(layoutX);
        btn.setLayoutY(layoutY);
        btn.setPrefWidth(width);
        btn.setPrefHeight(height);
    }

    void setLabelLayout(Label label, int layoutX, int layoutY, int width, int height){
        label.setLayoutX(layoutX);
        label.setLayoutY(layoutY);
        label.setPrefWidth(width);
        label.setPrefHeight(height);
    }

    void setTextLayout(TextField text, int layoutX, int layoutY, int width, int height){
        text.setLayoutX(layoutX);
        text.setLayoutY(layoutY);
        text.setPrefWidth(width);
        text.setPrefHeight(height);
    }

    public Pane getPane(){
        return framePane;
    }
}
