package com.pcgtcg.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.SocketHints;
import com.pcg.pcgtcg;

public class Client extends NetworkManager implements Runnable{
	
	private SocketHints socketHints;
	
	public Client()
	{
		super();
	}

	public void run()
	{
		socketHints = new SocketHints();
		socketHints.connectTimeout = 400000;
		System.out.println("Connecting to this IP: " +  masterServerIP);
		socket = Gdx.net.newClientSocket(Protocol.TCP, masterServerIP, 2000, socketHints);
		
		connected = true;
        buffer = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
        initialized = true;
		
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
}
