package com.cbsl.app.client.view;

import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

import java.awt.*;

public class StartStage extends BaseStage{
    Button startBtn;
    Button playbackBtn;

    public StartStage(){
        super();
        BGImage = new Image("com/cbsl/app/client/view/images/startBG.png");
        startBtn = new Button("开始游戏");
        playbackBtn = new Button("查看回放");

        setBtnLayout(startBtn, 268, 190, 265, 50);
        setBtnLayout(playbackBtn, 268, 332, 265, 50);

        framePane.getChildren().add(startBtn);
        framePane.getChildren().add(playbackBtn);

        startBtn.setOnAction(event -> {
            framePane.setVisible(false);
        });
        setBGImage();
    }

}
