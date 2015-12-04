package ca.csf.server;

import java.io.IOException;

import net.sf.lipermi.exception.LipeRMIException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Server;

public class MyServer extends Server implements IServer {

	public MyServer() {
		
		CallHandler callHandler = new CallHandler();
		try {
			callHandler.registerGlobal(IServer.class, this);
			this.bind(12345, callHandler);
			
			while(true){
				
				try {
					Thread.sleep(500);
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}				
		} catch (LipeRMIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void sayHello(String helloFromWho) {
		System.out.println(helloFromWho);
		
	}
	
	public static void main(String... arg) 
	{
		new MyServer();
	}
}
