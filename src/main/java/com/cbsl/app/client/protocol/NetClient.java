package com.cbsl.app.client.protocol;

import com.cbsl.app.client.action.Order;
import com.cbsl.app.client.action.Type;
import com.cbsl.app.client.world.Main;
import com.cbsl.app.server.NetServer;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class NetClient {
    private int portNum = 5005;
    private int UDP_PORT;//客户端的UDP端口号
    private String serverIP;
    private int serverPort;
    private DatagramSocket dataSocket;//客户端的UDP套接字
    private String brochoice;
    private String demonchoice;
    private boolean broflag=false;
    private boolean demonflag=false;

    //TODO:将perClient的内容转移至此，Server IP由Connect Stage提供

    //默认构造函数，默认己方为葫芦娃，使用默认端口，服务器默认为本地
    public NetClient(){
        UDP_PORT = 5005;
        UDP_PORT = 5006;
        serverIP = "127.0.0.1";
    }

    public NetClient(Type type){
        //葫芦娃的UDP端口号为5005，妖精客户端UDP端口号5006,服务器ip默认为本地ip
        if(type == Type.BROTHER)
            UDP_PORT = 5005;
        else UDP_PORT = 5006;
    }

    public NetClient(Type type, String ip){
        serverIP = ip;
        if(type == Type.BROTHER)
            UDP_PORT = 5005;
        else UDP_PORT = 5006;
    }

    public void setServerIP(String ip){
        serverIP = ip;
    }

    public PerClient getClient(){
        PerClient client = new PerClient(getPort());
        return client;
    }

    public int getPort(){
        return portNum++;
    }

    /**
     * 与服务器进行TCP连接
     */
    public void serverConnect() {

        Socket tmpSocket = null;
        try {
            dataSocket = new DatagramSocket(UDP_PORT);//创建UDP套接字
            try {
                tmpSocket = new Socket(serverIP, NetServer.TCP_PORT);//创建TCP套接字
            } catch (Exception e) {
                e.printStackTrace();
            }
            DataOutputStream dos = new DataOutputStream(tmpSocket.getOutputStream());
            dos.writeInt(UDP_PORT);//向服务器发送自己的UDP端口号

            DataInputStream dis = new DataInputStream(tmpSocket.getInputStream());
            serverPort = dis.readInt();//获得服务器转发客户端消息的UDP端口号

            System.out.println("connect to server successfully...");
        } catch (IOException e) {
            System.out.println("no server open...");
            e.printStackTrace();
        } finally {
            try {
                if (tmpSocket != null) tmpSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        new Thread(new UDPThread()).start();
    }

    private class UDPThread implements Runnable{
        byte[] dataBuf = new byte[1024];
        DatagramPacket receivePacket = null;

//        @Override
//        public void run() {
//            while (null != dataSocket){
//                receivePacket = new DatagramPacket(dataBuf, dataBuf.length);
//                try {
//                    dataSocket.receive(receivePacket);
//                    TimeUnit.MILLISECONDS.sleep(100);
//                    dataParse();
//
//                } catch (IOException | InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    @Override
    public void run() {
        while (null != dataSocket){
            receivePacket = new DatagramPacket(dataBuf, dataBuf.length);
            try {
                dataSocket.receive(receivePacket);
                TimeUnit.MILLISECONDS.sleep(100);
                dataParse();

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


//        //数据解析，将获取的数据给到其他地方
//        void dataParse(){
//            ByteArrayInputStream byteInStream = new ByteArrayInputStream(dataBuf, 0,  receivePacket.getLength());
//            DataInputStream inputStream = new DataInputStream(byteInStream);
//            try{
//                int srcPort = inputStream.readInt();
//                if(srcPort == UDP_PORT)
//                    System.out.println("receive a packet from myself!");
//                String data = inputStream.readUTF();
//                System.out.println(data);
//            }catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    //数据解析，将获取的数据给到其他地方
    void dataParse(){
        ByteArrayInputStream byteInStream = new ByteArrayInputStream(dataBuf, 0,  receivePacket.getLength());
        DataInputStream inputStream = new DataInputStream(byteInStream);
        try{
            int srcPort = inputStream.readInt();
            if(srcPort == 5005)
                System.out.println("receive a packet from BROTHER!");
            else if(srcPort == 5006)
                System.out.println("receive a packet from DEMON!");
            String data = inputStream.readUTF();

            if(data.split(" ")[0].equals("BROTHER")&&broflag==false){
                brochoice=data;
                broflag=true;
            }
            else if(data.split(" ")[0].equals("DEMON")&&demonflag==false){
                demonchoice=data;
                demonflag=true;
            }


            if(broflag&&demonflag){
                broflag=false;
                demonflag=false;

                //将两个玩家总指令分割成多条指令
                String[] bro=brochoice.split(" ; ");
                String[] demon=demonchoice.split(" ; ");

                //得到两个玩家的类型和出手权
                Type typeb=Type.valueOf(bro[0].split(" ")[0]);
                Type typed=Type.valueOf(demon[0].split(" ")[0]);
                Order orderb=Order.valueOf(bro[0].split(" ")[1]);
                Order orderd=Order.valueOf(demon[0].split(" ")[1]);

                int result=0;
                if(orderb==Order.FIRST){
    //                        Main.world.renewBattleMessage(bro[1]);
    //                        Main.world.renewBattleMessage(demon[1]);
    //                        Main.world.renewBattleMessage(bro[2]);
    //                        Main.world.renewBattleMessage(demon[2]);
    //                        Main.world.renewBattleMessage(bro[3]);
    //                        Main.world.renewBattleMessage(demon[3]);
    //                        Main.world.renewBattleMessage(bro[4]);
    //                        Main.world.renewBattleMessage(demon[4]);
    //                        Main.world.renewBattleMessage(bro[5]);
    //                        Main.world.renewBattleMessage(demon[5]);
                    for(int i=1;i<=5;++i){
                        int flag1= Main.world.renewBattleMessage(bro[i]);
                        if(flag1!=0){
                            result=flag1;
                            break;
                        }

                        int flag2=Main.world.renewBattleMessage(demon[i]);
                        if(flag2!=0){
                            result=flag2;
                            break;
                        }
                    }
                }
                else if(orderd==Order.FIRST){
    //                        Main.world.renewBattleMessage(demon[1]);
    //                        Main.world.renewBattleMessage(bro[1]);
    //                        Main.world.renewBattleMessage(demon[2]);
    //                        Main.world.renewBattleMessage(bro[2]);
    //                        Main.world.renewBattleMessage(demon[3]);
    //                        Main.world.renewBattleMessage(bro[3]);
    //                        Main.world.renewBattleMessage(demon[4]);
    //                        Main.world.renewBattleMessage(bro[4]);
    //                        Main.world.renewBattleMessage(demon[5]);
    //                        Main.world.renewBattleMessage(bro[5]);
                    for(int i=1;i<=5;++i){
                        int flag1=Main.world.renewBattleMessage(demon[i]);
                        if(flag1!=0){
                            result=flag1;
                            break;
                        }

                        int flag2=Main.world.renewBattleMessage(bro[i]);
                        if(flag2!=0){
                            result=flag2;
                            break;
                        }
                    }
                }

                if(result!=0){
                    Main.overStage.setWinner((result==1)?Type.BROTHER:Type.DEMON);
                    Main.gameStage.gameOver();
                }

            }


            System.out.println(data);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
        }


    //可以改成boolean返回值，判断发送失败并重发
    public void dataSend(String data) throws NullPointerException{
        Packet packet = new Packet(UDP_PORT, serverIP, data);
        //System.out.println("try to send to" + serverIP);
        try {
            DatagramPacket sendPacket = packet.getSendPacket();
            dataSocket.send(sendPacket);
            System.out.println("sent a packet to "+ serverIP);
            TimeUnit.MILLISECONDS.sleep(100);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    //可以改成boolean返回值，判断发送失败并重发
    public void dataSend(Packet packet) {
        try {
            DatagramPacket sendPacket = packet.getSendPacket();
            dataSocket.send(sendPacket);
            TimeUnit.MILLISECONDS.sleep(100);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
