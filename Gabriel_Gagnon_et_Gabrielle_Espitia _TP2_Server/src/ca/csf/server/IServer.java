package ca.csf.server;

import ca.csf.client.ClientController;

public interface IServer
{
public void sayHello(String helloFromWho);
public void addServListener(ServListener clientProxy);
public void registerObserver(ClientController clientController);
}