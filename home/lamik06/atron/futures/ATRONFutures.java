package atron.futures;

import atron.spot.ISunTRONAPI;



public abstract class ATRONFutures extends Thread implements IATRONFutures{
	Thread threadFuture;
	ICommand command;
	protected ISunTRONAPI atronAPI;
	
	public void setTimeOut(int timeInSec){}
	abstract public void waitForCompletion();
	
	public void onCompletion(ICommand command) {
		this.command = command;
		start();
	}
	public void  run() {
		waitForCompletion();
		command.execute();
	}
	/*
	 * Test of wait() for multiple futures
	 */
	public static void wait(ATRONFutures f,ATRONFutures f1){
		f.onCompletion(new ICommand(){public void execute(){}}); 
		f1.onCompletion(new ICommand(){public void execute(){}});
//		while (f.getState() != Thread.State.TERMINATED || f1.getState() != Thread.State.TERMINATED){
//		}
		while (f.isAlive() || f1.isAlive()){
			yield();
		}
	}
}
