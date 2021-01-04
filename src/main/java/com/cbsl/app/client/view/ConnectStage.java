package com.cbsl.app.client.view;

import com.cbsl.app.client.action.Type;
import com.cbsl.app.client.protocol.NetClient;
import com.cbsl.app.client.world.Main;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.List;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

//TODO:选择开始游戏或者读取文件，，，根据阵营选择不同背景，，，没有冲突，，开始界面->连接界面->游戏界面->选择是否结束
public class ConnectStage extends BaseStage{
    private Label serverIP;;
    private Label typeLabel;
    private Image BGImage;
    private Button connect;

    private TextField ipField;
    private TextField typeField;

    private String serverIp;
    private int side;
    private Type type;

    public ConnectStage(){
        super();
        BGImage = new Image("com/cbsl/app/client/view/images/startBG.png");
        typeLabel = new Label("Type:");
        serverIP  = new Label("Server Ip:");
        ipField = new TextField("127.0.0.1");
        typeField = new TextField("0");
        connect = new Button("Connect");

        setLabelLayout(serverIP, 52, 114, 110, 36);
        setLabelLayout(typeLabel, 373, 114, 68, 36);

        setTextLayout(ipField, 162, 114, 150, 36);
        setTextLayout(typeField, 461, 114, 96, 36);

        setBtnLayout(connect, 607, 114, 96, 36);
        setTextLayout(ipField, 162, 114, 150, 36);

        framePane.getChildren().add(typeLabel);
        framePane.getChildren().add(serverIP);
        framePane.getChildren().add(ipField);
        framePane.getChildren().add(typeField);
        framePane.getChildren().add(connect);

        BackgroundSize BGSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        framePane.setBackground(new Background(new BackgroundImage(BGImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BGSize)));

        //点击connect按钮，netClient进行初始化并与服务器进行连接
        connect.setOnAction(event -> {
            framePane.setVisible(false);
            serverIp = ipField.getText();
            side = Integer.parseInt(typeField.getText());
            type = side == 0 ? Type.BROTHER:Type.DEMON;
            System.out.println(serverIp);

            //将得到的Type和Server IP传到其他地方
            Main.world.setType(type);
            Main.gameStage.setType(type);
            Main.netClient = new NetClient(type, serverIp);
            Main.netClient.serverConnect();
        });
    }

    Type getType(){
        return type;
    }

    String getServerIp(){
        return serverIp;
    }
}
