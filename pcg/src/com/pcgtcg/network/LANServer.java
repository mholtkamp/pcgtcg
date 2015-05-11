package com.pcgtcg.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;

public class LANServer extends NetworkManager implements Runnable{

    private ServerSocket serverSocket;
    private ServerSocketHints serverSocketHint;
    
    
    public LANServer()
    {
        super();
    }
    
    public void run()
    {
        try
        {
            System.out.println("Server running");
            serverSocketHint = new ServerSocketHints();
            serverSocketHint.acceptTimeout = 0;
            serverSocket = Gdx.net.newServerSocket(Protocol.TCP, 2000, serverSocketHint);
            socket = serverSocket.accept(null);
            
            connected = true;
            buffer = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
        }
        catch(Exception ex)
        {
            
        }
        
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
    
    public void close()
    {
        if(serverSocket != null)
            serverSocket.dispose();
        super.close();
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
