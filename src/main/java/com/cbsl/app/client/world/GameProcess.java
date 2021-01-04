package com.cbsl.app.client.world;

import com.cbsl.app.client.action.Type;

import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.KeyEvent;

public class GameProcess {
    boolean gameflag=false;//开始游戏的标志
    boolean loadflag=false;//从文件加载游戏的标志

    public void main(){
        beginInterface();
        while(gameflag){
            proceedInterface();
            endInterface();
        }
    }

    //游戏开始界面
    void beginInterface(){
        while(!gameflag){
            loadBeginInterface();
            //监听键盘，L调用loadFromFile()方法，回车则置gameflag为true
        }
    }

    //游戏进行界面
    void proceedInterface(){
        //
        //World world=new World();
        //world.mainConnected();

        gameflag=false;
    }

    //游戏结束界面
    void endInterface(){
        while(!gameflag){
            loadEndInterface();
            //监听键盘，L调用loadFromFile()方法，R则置gameflag为true
        }
    }

    //TODO 从文件读记录并复现
    void loadFromFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

            File file=chooser.getSelectedFile();
            //World world=new World(Type.BROTHER);
            ArrayList<String> record=new ArrayList<>();

            try{
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line="";
                while ((line = reader.readLine()) != null) {
                    record.add(line);
                }
                //world.mainLocal(record);
                reader.close();

            }catch (IOException e){
                e.printStackTrace();
            }

        } else {
            System.out.println("No file is selected!");
        }


    }

    //加载开始界面
    void loadBeginInterface(){

    }

    //加载结束界面
    void loadEndInterface(){

    }

    //对开始界面键盘释放的监听
    void beginKeyReleased(KeyEvent e){
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_ENTER:
                gameflag=true;
                break;
            case KeyEvent.VK_L:
                loadflag=true;
                break;
        }
    }

    //对结束界面键盘释放的监听

}
