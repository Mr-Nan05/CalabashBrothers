package com.cbsl.app.client.creature.brother;

import com.cbsl.app.client.protocol.NetClient;

public class CalabashBrothers {
    public static void main(String[] args){
        NetClient clientSide = new NetClient();

        RedBoy redBoy = new RedBoy();
        OrangeBoy orangeBoy = new OrangeBoy();
        YellowBoy yellowBoy = new YellowBoy();

        redBoy.setClient(clientSide.getClient());
        orangeBoy.setClient(clientSide.getClient());
        yellowBoy.setClient(clientSide.getClient());

        redBoy.send("Who is that? It is RedBoy!");

    }
}