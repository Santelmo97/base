package hu.bme.mit.train.controller;

import hu.bme.mit.train.interfaces.TrainController;

import java.util.Timer;
import java.util.TimerTask;

public class TrainControllerImpl implements TrainController {

	private int step = 5;
	private int referenceSpeed = 0;
	private int speedLimit = 0;
	private boolean run=false;
	public runReferenceSpeedChange referenceSpeedChange;

	@Override
	public void followSpeed() {
		if (referenceSpeed < 0) {
			referenceSpeed = 0;
		} else {
		    if(referenceSpeed+step > 0) {
                referenceSpeed += step;
            } else {
		        referenceSpeed = 0;
            }
		}

		enforceSpeedLimit();
	}

	@Override
	public int getReferenceSpeed() {
		return referenceSpeed;
	}

	@Override
	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
		enforceSpeedLimit();
		
	}

	private void enforceSpeedLimit() {
		if (referenceSpeed > speedLimit) {
			referenceSpeed = speedLimit;
		}
	}

	@Override
	public void setJoystickPosition(int joystickPosition) {
		this.step = joystickPosition;
		if(referenceSpeedChange==null){
			referenceSpeedChange=new runReferenceSpeedChange("Thread 1");
			referenceSpeedChange.run();
		}
	}
	public void setRun(boolean b){
		this.run=b;
	}
	public class runReferenceSpeedChange implements Runnable
	{
		private Thread t;
		private String threadName;

		runReferenceSpeedChange( String name) {
			threadName = name;
			System.out.println(threadName + "created" );
		}
		@Override
		public void run() {
			setRun(true);
			if(t==null){
				t=new Thread(this,threadName);
				t.start();
			}
			try {
				while (run){
					followSpeed();
					System.out.println("Joystick position handled, speed increased");
					Thread.sleep(1000);
				}
			} catch (InterruptedException e){
				setRun(false);
				System.out.println("Thread interrupted");
				Thread.currentThread().interrupt();
			}
		}

	}
}
