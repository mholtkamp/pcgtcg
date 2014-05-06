package com.pcgtcg.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.SocketHints;

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
		socket = Gdx.net.newClientSocket(Protocol.TCP, "192.168.1.118", 5151, socketHints);
		
		connected = true;
        buffer = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
		
		while(true)
		{
			
		}
	}
}
