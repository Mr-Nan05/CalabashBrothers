package com.cbsl.app.client.view;

import com.cbsl.app.client.action.Dir;
import com.cbsl.app.client.action.Type;
import com.cbsl.app.client.creature.Creature;
import com.cbsl.app.client.world.Main;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.abs;

//游戏对战模块
//TODO:移动图标获取显示相应的操作以及显示对应的action在对应的位置
public class GameStage extends BaseStage{
    int actionCounter;//计步器
    Type type;
    Button finishChoose;
    List<BulletAttack> bullets;
    List<SpecialAttack> specialAttacks;
    List<Recovery> recoveries;
    List<Button> chooseButtons;//选择头像的button
    List<Button> cancelButtons;//取消选择的button
    List<ChosenView> chosenViews;//放置被选择的头像图片
    List<ChosenView> actionViews;//放置动作图片
    List<Image> headImages;//所有的头像Image
    List<Image> actionImages;//所有动作的Image
    List<String> actionMessages;//记录所有的信息，每条消息是4~5个数字，分别代表id序号、位置、执行动作(
                                // 0-3分别代表大招、普攻、恢复、移动)
    List<BloodBar> bloodBars;
    List<int []> backActions;


    public GameStage(){
        super();
        BGImage = new Image("com/cbsl/app/client/view/images/mybackground.png");
    }

    public GameStage(Type type){
        super();
        this.type = type;
        if(type == Type.BROTHER)
            BGImage = new Image("com/cbsl/app/client/view/images/mybackground.png");
        else
            BGImage = new Image("com/cbsl/app/client/view/images/mybackground.png");
    }

    void setType(Type type){
        this.type = type;
        Initialize();
        setBackGroundImage(type);
    }

    void Initialize(){
        actionCounter = 0;
        bullets = new ArrayList<>();
        specialAttacks = new ArrayList<>();
        recoveries = new ArrayList<>();
        backActions = new ArrayList<>();
        initButtons();//初始化所有的按钮
        initBloodBars();//初始化血量条
        setHeadViews();//初始化已选择头像的位置
        setHeadImage();//初始化所有头像
        setActionImages();//初始化所有动作
        setButtons();//设置按钮对应的操作
        setActionMessages();//初始化五个动作message为“”


        BackgroundSize BGSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        framePane.setBackground(new Background(new BackgroundImage(BGImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BGSize)));

        framePane.setOnKeyPressed(event -> {
            switch (event.getCode()){
                case W:setAction(0);
                    break;
                case Q:setAction(1);
                    break;
                case E:setAction(2);
                    break;
            }
        });

        //Test
        Button testBt1 = new Button("testBt1");
        framePane.getChildren().add(testBt1);
        testBt1.setOnMouseClicked(event -> {
            attack(3);
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            attack(-7);
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            specialAttack(-7);
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            recovery(-3);
        });

        //Test2
        Button testBt2 = new Button("testBt2");
        testBt2.setLayoutX(0);
        testBt2.setLayoutY(25);
        framePane.getChildren().add(testBt2);
        testBt2.setOnMouseClicked(event -> {
            attack(-4);
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            attack(3);
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            specialAttack(5);
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            recovery(3);
        });
    }

    //产生普攻的函数，使用单独的线程进行
    public void attack(int id){
        BulletAttack bullet = new BulletAttack(id);
        new Thread(bullet).start();
        bullets.add(bullet);
        framePane.getChildren().add(bullet);

        for(BulletAttack b:bullets){
            if(!b.getStatus()){
                framePane.getChildren().remove(b);
            }
        }
    }

    public void specialAttack(int id){
        specialAttacks.add(new SpecialAttack(id));

        for(int i = 0; i < specialAttacks.size(); i++){
            if(!specialAttacks.get(i).getStatus()){
                specialAttacks.remove(specialAttacks.get(i));
                i--;
            }
        }
    }
    public void recovery(int id){
        recoveries.add(new Recovery(id));

        for(int i = 0; i < recoveries.size(); i++){
            if(!recoveries.get(i).getStatus()){
                recoveries.remove(recoveries.get(i));
                i--;
            }
        }
    }

    //初始化血量条
    public void initBloodBars(){
        bloodBars = new ArrayList<>();
        for(int i = 1; i <= 7; i++){
            BloodBar bloodBar = new BloodBar(i);
            bloodBars.add(bloodBar);
            framePane.getChildren().add(bloodBar);
        }
    }
    //更新血量条
    public void updateBloodBars(Map<Integer, Creature> self){
        for(Integer id:self.keySet()){
            int i = abs(id);
            double restHP = self.get(id).getRestHP();
            double fullHP = self.get(id).getHP();
            bloodBars.get(i - 1).setProgress(restHP/fullHP);
        }
    }

    public void addBackAction(int[] action){
        backActions.add(action);
    }
    //将所有的移动操作都退回
    void runBackActions(){
        for(ChosenView chosenView:chosenViews){
            chosenView.cancel();
            framePane.getChildren().remove(chosenView);
        }

        for(ChosenView actionView:actionViews){
            actionView.cancel();
            framePane.getChildren().remove(actionView);
        }

        for(Button cancelButton:cancelButtons){
            framePane.getChildren().remove(cancelButton);
        }

        for(int i = backActions.size() - 1; i >= 0; i--){
            int[] action = backActions.get(i);
            int id = action[0];
            int desX = action[1];
            int desY = action[2];
            int srcX = action[3];
            int srcY = action[4];

            Main.world.setPos(id, srcX, srcY, desX, desY);
        }
        backActions.clear();
    }

    void setActionMessages(){
        actionMessages = new ArrayList<>();
        for(int i = 0; i < 5; i++)
            actionMessages.add("");
    }

    public void addImageView(Map<Integer, Creature> creMap){
        //放进Pane中显示
        for(Creature creature:creMap.values()){
            int x = creature.getXPos();
            int y = creature.getYPos();
            while (true){
                try{
                    if(Main.Locker.occupy(creature.getId(), x, y)){
                        creature.getImageView().setXY(x,y);
                        break;
                    }else Thread.sleep(100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            framePane.getChildren().add(creature.getImageView());
        }
    }

    void initButtons(){
        chooseButtons = new ArrayList<>();
        cancelButtons = new ArrayList<>();
        setChooseButton(new Button(), 14, 8, 57, 57);
        setChooseButton(new Button(), 88, 8, 57, 57);
        setChooseButton(new Button(), 162, 8, 57, 57);
        setChooseButton(new Button(), 237, 8, 57, 57);
        setChooseButton(new Button(), 14, 73, 57, 57);
        setChooseButton(new Button(), 88, 73, 57, 57);
        setChooseButton(new Button(), 162, 73, 57, 57);

        setCancelButton(new Button(), 322, 8, 57, 57);
        setCancelButton(new Button(), 443, 8, 57, 57);
        setCancelButton(new Button(), 567, 8, 57, 57);
        setCancelButton(new Button(), 322, 73, 57, 57);
        setCancelButton(new Button(), 443, 73, 57, 57);
    }

    void setButtons(){
        setFinishChoose();//设置结束按钮
        for(int i = 0; i < 7; i ++){
            Image tmpImage = headImages.get(i);
            chooseButtons.get(i).setOnMouseClicked(event -> {
                if(Main.Locker.isLocked())
                    return;
                if(++actionCounter >= 5)
                    Main.Locker.lock();//计步器步数加一

                for(ChosenView view:chosenViews){
                    if(!view.isChosen()){
                        view.choose();
                        view.setImage(tmpImage);
                        int id = headImages.indexOf(tmpImage) + 1;
                        String site;
                        if(type == Type.BROTHER)
                            site= Main.Locker.getSite(id);
                        else
                            site = Main.Locker.getSite(-id);

                        actionMessages.set(chosenViews.indexOf(view),id + site);
                        framePane.getChildren().add(view);
                        framePane.getChildren().add(cancelButtons.get(chosenViews.indexOf(view)));
                        break;
                    }
                }
            });
        }

        for(int i = 0; i < 5; i++){
            ChosenView tmpHeadView = chosenViews.get(i);
            ChosenView temActionView = actionViews.get(i);
            cancelButtons.get(i).setOnMouseClicked(event -> {
                if(tmpHeadView.isChosen()){
                    actionCounter--;//计步器减一
                    Main.Locker.unLock();

                    tmpHeadView.cancel();
                    temActionView.cancel();
                    framePane.getChildren().remove(tmpHeadView);
                    framePane.getChildren().remove(temActionView);
                    framePane.getChildren().remove(cancelButtons.get(chosenViews.indexOf(tmpHeadView)));
                }
            });
        }
    }

    //TODO:结束按钮，将字符串传递到World中，然后使用World的函数进行调用转发已经字符串解析和存储
    void setFinishChoose(){
        finishChoose = new Button();
        finishChoose.setLayoutX(567);
        finishChoose.setLayoutY(73);
        finishChoose.setPrefWidth(57);
        finishChoose.setPrefHeight(57);
        finishChoose.setOpacity(0);
        finishChoose.setOnMouseClicked(event -> {
            //初始化计步器并解锁
            actionCounter = 0;
            Main.Locker.unLock();

            runBackActions();
            //World中的函数
            Main.world.transList2String(actionMessages);
        });

        framePane.getChildren().add(finishChoose);
    }

    void setActionImages(){
        actionImages = new ArrayList<>();
        if(type == Type.BROTHER){
            actionImages.add(new Image("com/cbsl/app/client/view/images/smallspecialattackbro.png"));
            actionImages.add(new Image("com/cbsl/app/client/view/images/smallattackbro.png"));
            actionImages.add(new Image("com/cbsl/app/client/view/images/smallrecoverbro.png"));
        }else{
            actionImages.add(new Image("com/cbsl/app/client/view/images/smallspecialattackdemon.png"));
            actionImages.add(new Image("com/cbsl/app/client/view/images/smallattackdemon.png"));
            actionImages.add(new Image("com/cbsl/app/client/view/images/smallrecoverdemon.png"));
        }
        actionImages.add(new Image("com/cbsl/app/client/view/images/smallmove.png"));
    }

    //lock
    //设置对应位置的动作图标以及对应字符串
    void setAction(int action){
        for(ChosenView view:actionViews){
            if(!view.isChosen()){
                view.choose();
                view.setImage(actionImages.get(action));
                framePane.getChildren().add(view);
                //设置对应位置的字符串
                int index = actionViews.indexOf(view);
                String message = actionMessages.get(index);
                actionMessages.set(index, message + action);
                break;
            }
        }
    }
    //设置unlock（）
    //id默认从1开始计数
    public void setMove(int id, int x, int y){
        Image tmpImage = headImages.get(id - 1);
        String tmpMsg = Integer.toString(id) + x + y;
        for(ChosenView view:chosenViews){
            if(!view.isChosen()){
                view.choose();
                view.setImage(tmpImage);
                int index = chosenViews.indexOf(view);
                actionMessages.set(index, tmpMsg);
                setAction(3);
                framePane.getChildren().add(view);
                framePane.getChildren().add(cancelButtons.get(chosenViews.indexOf(view)));

                if(++actionCounter >= 5)
                    Main.Locker.lock();
                break;
            }
        }
    }

    void setHeadViews(){
        //正数表示头像，负数表示操作
        chosenViews = new ArrayList<>();
        actionViews = new ArrayList<>();
        chosenViews.add(new ChosenView(1));
        chosenViews.add(new ChosenView(2));
        chosenViews.add(new ChosenView(3));
        chosenViews.add(new ChosenView(4));
        chosenViews.add(new ChosenView(5));
        actionViews.add(new ChosenView(-1));
        actionViews.add(new ChosenView(-2));
        actionViews.add(new ChosenView(-3));
        actionViews.add(new ChosenView(-4));
        actionViews.add(new ChosenView(-5));
    }

    void setChooseButton(Button btn, int x, int y, int w, int h){
        btn.setVisible(true);
        btn.setOpacity(0);
        setBtnLayout(btn, x, y, w, h);
        chooseButtons.add(btn);
        framePane.getChildren().add(btn);
    }

    void setCancelButton(Button btn, int x, int y, int w, int h){
        btn.setVisible(true);
        btn.setOpacity(0);
        setBtnLayout(btn, x, y, w, h);
        cancelButtons.add(btn);
    }

    void setHeadImage(){
        headImages = new ArrayList<>();
        if(type == Type.BROTHER){
            headImages.add(new Image("com/cbsl/app/client/view/images/head_bro1.png"));
            headImages.add(new Image("com/cbsl/app/client/view/images/head_bro2.png"));
            headImages.add(new Image("com/cbsl/app/client/view/images/head_bro3.png"));
            headImages.add(new Image("com/cbsl/app/client/view/images/head_bro4.png"));
            headImages.add(new Image("com/cbsl/app/client/view/images/head_bro5.png"));
            headImages.add(new Image("com/cbsl/app/client/view/images/head_bro6.png"));
            headImages.add(new Image("com/cbsl/app/client/view/images/head_bro7.png"));
        }
        else {
            headImages.add(new Image("com/cbsl/app/client/view/images/head_snake.png"));
            headImages.add(new Image("com/cbsl/app/client/view/images/head_scorption.png"));
            headImages.add(new Image("com/cbsl/app/client/view/images/head_crocodile.png"));
            headImages.add(new Image("com/cbsl/app/client/view/images/head_eagle.png"));
            headImages.add(new Image("com/cbsl/app/client/view/images/head_butterfly.png"));
            headImages.add(new Image("com/cbsl/app/client/view/images/head_spider.png"));
            headImages.add(new Image("com/cbsl/app/client/view/images/head_toad.png"));
        }
    }

    public void setBackGroundImage(Type type){

        if(type == Type.BROTHER)
            BGImage = new Image("com/cbsl/app/client/view/images/mybackground_brother.png");
        else
            BGImage = new Image("com/cbsl/app/client/view/images/mybackground_demon.png");
        BackgroundSize BGSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        framePane.setBackground(new Background(new BackgroundImage(BGImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BGSize)));
    }

    public void gameOver(){
        framePane.setVisible(false);
    }

}
