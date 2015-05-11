package com.pcgtcg.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.pcg.pcgtcg;

public class Server extends NetworkManager implements Runnable{

	private SocketHints socketHints;
	
	
	public Server()
	{
		super();
	}
	
	public void run()
	{
	    try
	    {
    		socketHints = new SocketHints();
    		socketHints.connectTimeout = 400000;
    		System.out.println("Connecting to this IP: " +  masterServerIP);
    		socket = Gdx.net.newClientSocket(Protocol.TCP, masterServerIP, 2000, socketHints);
    		
    		connected = true;
            buffer = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
    		initialized = true;
	    }
	    catch (Exception ex)
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
}
