package com.pcgtcg.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.pcg.pcgtcg;



public class NetworkManager implements Runnable{
	
	protected boolean connected;
	protected BufferedReader buffer;
	protected Socket socket;

	private List<String> addresses;
	public NetworkManager()
	{
		connected = false;
		addresses = new ArrayList<String>();
        
		// BEGIN code borrowed from http://www.gamefromscratch.com/post/2014/03/11/LibGDX-Tutorial-10-Basic-networking.aspx
		try
        {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            for(NetworkInterface ni : Collections.list(interfaces)){
                for(InetAddress address : Collections.list(ni.getInetAddresses()))
                {
                    if(address instanceof Inet4Address){
                        addresses.add(address.getHostAddress());
                    }
                }
            }
        } 
        catch (SocketException e) {
            e.printStackTrace();
        }
		
        String ipAddress = new String("");
        for(String str:addresses)
        {
            ipAddress = ipAddress + str + "\n";
        }
        //END borrowed Code

        System.out.println(ipAddress);
	}
	
	public boolean isConnected()
	{
		return connected;
	}
	
	public void run()
	{
		System.out.println("Wrong run called");
	}

	public void send(String str)
	{
		str += "\n";
		try
		{
			socket.getOutputStream().write(str.getBytes());
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		
	}
	
	public void poll()
	{
		try
		{
			if(buffer.ready())
			{
				String sig = buffer.readLine();
				String com = sig.split("[.]", 2)[0];
				String params;
				try
				{
					params = sig.split("[.]",2)[1];
				}
				catch(ArrayIndexOutOfBoundsException ex)
				{
					params = "none";
				}
				
				
				//BEGIN Debug
				System.out.println("Command: " + com);
				System.out.println("Params: " + params);
				// END Debug
				
				if(com.equals("DECKONE"))
				{
					pcgtcg.game.exeDECKONE(params.replaceAll("[.]", ""));
				}
				else if(com.equals("DECKTWO"))
				{
					pcgtcg.game.exeDECKTWO(params.replaceAll("[.]", ""));				
				}
				else if(com.equals("FIRSTTURN"))
				{
					pcgtcg.game.exeFIRSTTURN(params.replaceAll("[.]", ""));
				}
				else if(com.equals("DRAW"))
				{
					pcgtcg.game.exeDRAW();
				}
				else if(com.equals("FDRAW"))
				{
					pcgtcg.game.exeFDRAW();
				}
				else if(com.equals("ENDTURN"))
				{
					pcgtcg.game.exeENDTURN();
				}
				else if(com.equals("SUMMON"))
				{
					pcgtcg.game.exeSUMMON(params.replaceAll("[.]", ""));
				}
				else if(com.equals("SET"))
				{
					pcgtcg.game.exeSET(params.replaceAll("[.]", ""));
				}
				else if(com.equals("KILL"))
				{
					pcgtcg.game.exeKILL(params.replaceAll("[.]", ""));
				}
				else if(com.equals("SKILL"))
				{
					pcgtcg.game.exeSKILL(params.replaceAll("[.]", ""));
				}
				else if(com.equals("TOGGLE"))
				{
					pcgtcg.game.exeTOGGLE(params.replaceAll("[.]", ""));
				}
				else if(com.equals("DAMAGE"))
				{
					pcgtcg.game.exeDAMAGE(params.replaceAll("[.]", ""));
				}
				else if(com.equals("SDAMAGE"))
				{
					pcgtcg.game.exeSDAMAGE(params.replaceAll("[.]", ""));
				}
			
				
				
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		
	}
	
	public void close()
	{
		socket.dispose();
		
	}
	
	public List<String> getIP()
	{
		return addresses; 
		
	}
}
