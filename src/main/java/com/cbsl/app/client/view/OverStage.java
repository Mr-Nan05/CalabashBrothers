package com.cbsl.app.client.view;

import com.cbsl.app.client.action.Type;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.text.Font;

public class OverStage extends BaseStage{
    Label gameOver;
    Label winner;

    public OverStage(){
        super();
        BGImage = new Image("com/cbsl/app/client/view/images/startBG.png");
        gameOver = new Label("Game Over!");
        gameOver.setAlignment(Pos.CENTER);
        gameOver.setFont(Font.font(40));

        winner = new Label("Do Not Know Winner！");
        winner.setAlignment(Pos.CENTER);
        winner.setFont(Font.font(40));

        setLabelLayout(gameOver, 267, 136, 267, 126);
        setLabelLayout(winner, 175, 292, 450, 181);

        framePane.getChildren().add(gameOver);
        framePane.getChildren().add(winner);

        setBGImage();
    }
    //游戏结束后将GameStage设置为不可见，然后将胜利者的Type传入此函数
    public void setWinner(Type type){
        if(type == Type.BROTHER)
            winner.setText("Calabash Brothers Win!");
        else winner.setText("Demons Win!");
    }
}
