package com.cbsl.app.client.world;

import com.cbsl.app.client.action.Dir;
import com.cbsl.app.client.action.Type;
import com.cbsl.app.client.protocol.NetClient;
import com.cbsl.app.client.view.GameStage;
import com.cbsl.app.client.view.ConnectStage;
import com.cbsl.app.client.view.OverStage;
import com.cbsl.app.client.view.StartStage;
import com.cbsl.app.server.NetServer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Main  extends Application{
    public static final double WIDTH = 800;
    public static final double HEIGHT = 700;
    public static NetClient netClient;
    public static Pane mainPane;//Main,将所有界面放入
    public static Pane startPane;//开始界面
    public static Pane connectPane;//连接服务器界面
    public static Pane gamePane;//游戏主界面
    public static Pane overPane;//游戏结束界面
    public static GameStage gameStage;
    public static OverStage overStage;

    public Scene mainScene;

    //public Map<Integer, Creature> creatures;
    public static World world;


    public static Lock Locker = new Lock();

    public static void main(String[] args){
        launch(args);
    }


    @Override
    public void start(javafx.stage.Stage primaryStage) throws Exception{
        Init();

        primaryStage.setTitle("Calabash Brothers VS Demons");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    void Init(){
        //world = new World(Type.BROTHER);
        world = new World();
        overStage = new OverStage();
        gameStage = new GameStage();
        gameStage.addImageView(world.getBROS());
        gameStage.addImageView(world.getDEMONS());

        mainPane = new Pane();
        startPane = new StartStage().getPane();
        connectPane = new ConnectStage().getPane();
        overPane = overStage.getPane();
        gamePane = gameStage.getPane();

        //游戏结束后将GameStage设置为不可见，然后将胜利者的Type传入overStage
        startPane.setVisible(true);
        mainPane.getChildren().add(overPane);
        mainPane.getChildren().add(gamePane);
        mainPane.getChildren().add(connectPane);
        mainPane.getChildren().add(startPane);
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
    }

}
