package com.cbsl.app.client.protocol;

import com.cbsl.app.server.NetServer;

import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;

public class PerClient {
    private int UDP_PORT;//客户端的UDP端口号
    private int serverPort;//服务器转发客户但UDP包的UDP端口
    private String serverIP;//服务器IP地址
    private DatagramSocket dataSocket = null;//客户端的UDP套接字


    public PerClient(int port) {
        UDP_PORT = port;
        serverIP = NetServer.IP;
        serverPort = NetServer.UDP_PORT;
        serverConnect();
    }

    public PerClient(String desIP, int desPort, int port) {
        UDP_PORT = port;
        serverIP = desIP;
        serverPort = desPort;
        serverConnect();
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

        void dataParse(){
            ByteArrayInputStream byteInStream = new ByteArrayInputStream(dataBuf, 0,  receivePacket.getLength());
            DataInputStream inputStream = new DataInputStream(byteInStream);
            try{
                int srcPort = inputStream.readInt();
                String data = inputStream.readUTF();
                System.out.println(data);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //可以改成boolean返回值，判断发送失败并重发
    public void dataSend(String data) throws NullPointerException{
        Packet packet = new Packet(UDP_PORT, serverIP, data);
        try {
            DatagramPacket sendPacket = packet.getSendPacket();
            dataSocket.send(sendPacket);
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
