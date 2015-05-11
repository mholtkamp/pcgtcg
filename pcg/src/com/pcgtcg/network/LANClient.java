package com.pcgtcg.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.SocketHints;
import com.pcg.pcgtcg;

public class LANClient extends NetworkManager implements Runnable{
    
    private SocketHints socketHints;
    
    public LANClient()
    {
        super();
    }

    public void run()
    {
        socketHints = new SocketHints();
        socketHints.connectTimeout = 400000;
        System.out.println("Connecting with this IP: " +  pcgtcg.menu.connectIP);
        socket = Gdx.net.newClientSocket(Protocol.TCP, pcgtcg.menu.connectIP, 2000, socketHints);
        
        connected = true;
        buffer = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
        
        while(!finished)
        {
            try
            {
                Thread.sleep(5);
            }
            catch (Exception ex)
            {
                System.out.println(ex);
            }
        }
    }
    
    public void send(String str)
    {
        String msg = str + "\n";
        try
        {
            socket.getOutputStream().write(msg.getBytes());
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
    }
}
