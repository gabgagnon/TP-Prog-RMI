package ca.csf.server;

public class IllegalModelOperationException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public IllegalModelOperationException(String msg)
	{
		super(msg);
	}
}
