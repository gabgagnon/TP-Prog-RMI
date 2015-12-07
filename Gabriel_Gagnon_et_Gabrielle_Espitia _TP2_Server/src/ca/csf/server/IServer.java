package ca.csf.server;

import ca.csf.client.ClientController;
import ca.csf.client.MyServerObserver;
import ca.csf.client.View;

public interface IServer
{
public void sayHello(String helloFromWho);
public void addServListener(ServListener clientProxy);
public void registerObserver(ClientController clientController);
public void addModelObserver(MyServerObserver view);
}