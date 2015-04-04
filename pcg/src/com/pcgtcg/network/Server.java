package com.pcgtcg.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;

public class Server extends NetworkManager implements Runnable{

	private ServerSocket serverSocket;
	private ServerSocketHints serverSocketHint;
	
	
	public Server()
	{
		super();
	}
	
	public void run()
	{
		System.out.println("Server running");
		serverSocketHint = new ServerSocketHints();
		serverSocketHint.acceptTimeout = 0;
		serverSocket = Gdx.net.newServerSocket(Protocol.TCP, 2000, serverSocketHint);
		socket = serverSocket.accept(null);
		
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
	
	public void close()
	{
		if(serverSocket != null)
			serverSocket.dispose();
		super.close();
	}
	
}
