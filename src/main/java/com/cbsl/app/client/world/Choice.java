package com.cbsl.app.client.world;

import com.cbsl.app.client.action.Order;
import com.cbsl.app.client.action.Type;
import com.cbsl.app.client.creature.Creature;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Choice {
    Order order;
    Type type;
    int step;
    Creature[] self;
    Creature[] oppo;

    String curcommond;
    ArrayList<String> command;//

    public Choice(Order order, Type type){
        this.order=order;
        this.type=type;
        step=(order==Order.FIRST)?4:5;
    }

    public String makeChoice(Creature[] self,Creature[] oppo,boolean recoverflag){
        this.self=self;
        this.oppo=oppo;
        command=new ArrayList<>();

        //如果是先手，则中上方第五组两个框上会显示两个X

        String result=type.toString()+" "+order.toString()+" ; ";
        //KeyEvent e=new KeyEvent();
        for(int i=0;i<step;++i){
            //中上方第i+1组的两个框边缘出现高亮特效

            while(true){

                //选择角色，每点击左上角某个角色的头像 中上方的高亮组左框便会显示该角色的头像
                int id;


                String operation;
                //select operation


                //点击确认
                boolean comfirm;
                /*if(comfirm) {
                    result+=Integer.toString(id)+" "+operation+" ; ";
                    break;
                }*/
            }

        }

        return result;
    }







    //返回1表示已完成所有命令选择立即结束，返回0表示完成当前命令选择，返回-1表示删除上一个命令
    /*public int oneStepChioce(KeyEvent e){
//        int key;
//        while(true){
//            Creature cur;
//            key=e.getKeyCode();
//            switch(key){
//                case KeyEvent.VK_1:
//                    cur=self[0];
//                    break;
//                case KeyEvent.VK_2:
//                    cur=self[1];
//                    break;
//                case KeyEvent.VK_3:
//                    cur=self[2];
//                    break;
//                case KeyEvent.VK_4:
//                    cur=self[3];
//                    break;
//                case KeyEvent.VK_5:
//                    cur=self[4];
//                    break;
//                case KeyEvent.VK_6:
//                    cur=self[5];
//                    break;
//                case KeyEvent.VK_7:
//                    cur=self[6];
//                    break;
//                case KeyEvent.VK_ENTER:
//                    return 1;
//                case KeyEvent.VK_BACK_SPACE:
//                    return -1;
//                default:
//                    continue;
//            }
//
//            String action;
//            key=e.getKeyCode();
//            switch (key){
//                case KeyEvent.VK_UP:
//                    if(!posHasCreature(cur.xpos,cur.ypos)&&cur.ypos-1>=0){
//                        action="up";
//                        curcommond="id "+cur.getId()+" to "+action+" ; ";
//                        return 0;
//                    }
//                    else continue;
//                case KeyEvent.VK_DOWN:
//                    if(!posHasCreature(cur.xpos,cur.ypos)&&cur.ypos+1<=6){
//                        action="down";
//                        curcommond="id "+cur.getId()+" to "+action+" ; ";
//                        return 0;
//                    }
//                    else continue;
//                case KeyEvent.VK_LEFT:
//                    if(!posHasCreature(cur.xpos,cur.ypos)&&cur.xpos-1>=0){
//                        action="left";
//                        curcommond="id "+cur.getId()+" to "+action+" ; ";
//                        return 0;
//                    }
//                    else continue;
//                case KeyEvent.VK_RIGHT:
//                    if(!posHasCreature(cur.xpos,cur.ypos)&&cur.xpos+1<=9){
//                        action="right";
//                        curcommond="id "+cur.getId()+" to "+action+" ; ";
//                        return 0;
//                    }
//                    else continue;
//                case KeyEvent.VK_J:
//                    action="attack";
//                    curcommond="id "+cur.getId()+" to "+action+" ; ";
//                    return 0;
//                case KeyEvent.VK_K:
//                    if(!cur.getSpecialAttackFlag()){
//                        action="specialattack";
//                        curcommond="id "+cur.getId()+" to "+action+" ; ";
//                        return 0;
//                    }
//                    else continue;
//                case KeyEvent.VK_ENTER:
//                    return 1;
//                case KeyEvent.VK_BACK_SPACE:
//                    return -1;
//                default:continue;
//
//            }
//        }
        return 0;
    }*/

    private boolean posHasCreature(int xpos,int ypos){
        for(Creature c:self){
            if(c.getXPos()==xpos&&c.getYPos()==ypos)
                return true;
        }
        for(Creature c:oppo){
            if(c.getXPos()==xpos&&c.getYPos()==ypos)
                return true;
        }
        return false;
    }

}
