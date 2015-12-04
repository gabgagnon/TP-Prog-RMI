package ca.csf.client;

import java.io.IOException;

import ca.csf.server.IServer;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;

public class MyClient {

	public static void main(String... args) throws IOException{
		CallHandler callHandler = new CallHandler();
		Client client = new Client("127.0.0.1", 12345, callHandler);
		IServer myServiceCaller = client.getGlobal(IServer.class);
		//Obtention du Proxy		
		myServiceCaller.sayHello("Frank");
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Client done");
		client.close();
	}
	
}
