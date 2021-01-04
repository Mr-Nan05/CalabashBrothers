package com.cbsl.app.client.protocol;

import com.cbsl.app.server.NetServer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

//public class Packet <T>
public class Packet {
    private String SrcIP;
    private String DesIP;
    private String SerIP;
    private int SrcPort;
    private int DesPort;
    private String Data;

    byte[] dataBuf = new byte[1024];
    //private List<String> dataList;

    Packet(int port, String serIP, String data){
        SrcPort = port;
        SerIP = serIP;
        Data = data;
    }

    Packet(String src_ip, String des_ip, int src_port, int des_port, String data) {
        SrcIP = src_ip;
        DesIP = des_ip;
        SrcPort = src_port;
        DesPort = des_port;
        Data = data;
    }

    String getSrcIP() {
        return SrcIP;
    }

    String getDesIP() {
        return DesIP;
    }

    int getSrcPort() {
        return SrcPort;
    }

    int getDesPort() {
        return DesPort;
    }
    //List<String> getData(){return dataList;}

    DatagramPacket getSendPacket() {
        ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
        DataOutputStream dataOutStream = new DataOutputStream(byteOutStream);
        DatagramPacket sendPacket = null;
        try {
            /*
            dataOutStream.writeInt(DesPort);
            dataOutStream.writeUTF(SrcIP);
            dataOutStream.writeUTF(DesIP);
             */
            dataOutStream.writeInt(SrcPort);
            dataOutStream.writeUTF(Data);
            byte[] dataBuf = byteOutStream.toByteArray();
            InetSocketAddress serverAddress = new InetSocketAddress(SerIP, NetServer.UDP_PORT);
            sendPacket = new DatagramPacket(dataBuf, dataBuf.length, serverAddress);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sendPacket;
    }
}
