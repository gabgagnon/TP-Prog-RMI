package ca.csf.server;

import ca.csf.client.MyServerObserver;

public interface IServer
{
public void sayHello(String helloFromWho);
public void addServListener(ServListener clientProxy);
public int addPlayerObserver(MyServerObserver view);
public int getNbRows();
public int getNbColumns();
public void columnClicked(int columnIndex);
public void gameClosed();
public void resign();
}