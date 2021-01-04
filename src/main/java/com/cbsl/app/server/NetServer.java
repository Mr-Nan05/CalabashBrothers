package com.cbsl.app.server;
import com.cbsl.app.client.protocol.Packet;
import org.checkerframework.checker.units.qual.C;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
public class NetServer {
    public static int ID = 100;//id号的初始序列

    public static final int TCP_PORT = 5001;//TCP端口号
    public static final int UDP_PORT = 55555;//转发客户端数据的UDP端口号
    public static final String IP = "127.0.0.1";

    private static ServerSocket serverSocket = null;

    private static ArrayList<ObjClient> clients = new ArrayList<>();

    public static void main(String[] args){
        NetServer netServer = new NetServer();
        new Thread(new UDPThread()).start();
        netServer.serverStart();

        while (true){
            netServer.TCPConnect();
        }
    }

    //给客户配专属TCP套接字
    void TCPConnect(){
        Socket TCPSocket = null;
        int clientUDPPort = 0;
        try {
            TCPSocket = serverSocket.accept();//给客户但分配专属TCP套接字
            System.out.println("A client has connected...");

            DataInputStream dis = new DataInputStream(TCPSocket.getInputStream());
            clientUDPPort = dis.readInt();//记录客户端UDP端口
            String clientIp = TCPSocket.getInetAddress().getHostAddress();
            System.out.println(clientIp);
            ObjClient client = new ObjClient(clientIp, clientUDPPort);//创建Client对象
            clients.add(client);//添加进客户端容器

            DataOutputStream dos = new DataOutputStream(TCPSocket.getOutputStream());
            dos.writeInt(UDP_PORT);//告诉客户端自己的UDP端口号

        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(TCPSocket != null) TCPSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //启动TCP套接字，监听客户端连接
    void serverStart(){
        try {
            serverSocket = new ServerSocket(TCP_PORT);//在TCP欢迎套接字上监听客户端连接
            System.out.println("NetServer has started...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class ObjClient{
        String IP;
        String name;
        int UDP_PORT;
        int id;

        public ObjClient(String ipAddr, int UDP_PORT) {
            this.IP = ipAddr;
            this.UDP_PORT = UDP_PORT;
        }
    }

    private static class UDPThread implements Runnable{

        private byte[] buf = new byte[1024];
        private DatagramSocket dataSocket = null;
        private DatagramPacket dataPacket = new DatagramPacket(buf, buf.length);

        @Override
        public void run() {
            //创建发送数据的UDP Socket
            setDataSocket();

            while (dataSocket != null){
                try {
                    //接收数据到dataPacket
                    dataReceive();
                    //服务器向每一个目标元素广播
                    dataSend();
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        //创建发送数据的UDP Socket
        void setDataSocket(){
            try{
                dataSocket = new DatagramSocket(UDP_PORT);
            }catch (SocketException e) {
                e.printStackTrace();
            }
        }

        //接收数据到dataPacket
        void dataReceive(){
            try{
                dataSocket.receive(dataPacket);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        //服务器向每一个目标元素广播
        void dataSend(){
            try{
                for(ObjClient client : clients){
                    dataPacket.setSocketAddress(new InetSocketAddress(client.IP, client.UDP_PORT));
                    dataSocket.send(dataPacket);
                    System.out.println("Server has sent a packet to" + client.IP + client.UDP_PORT);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
