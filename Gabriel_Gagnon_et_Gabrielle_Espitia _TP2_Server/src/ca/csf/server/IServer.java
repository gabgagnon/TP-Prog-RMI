package ca.csf.server;

import net.sf.lipermi.net.Server;

public interface IServer
{
public void sayHello(String helloFromWho);
public void addServListener(ServListener serv);
}