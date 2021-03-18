package hu.bme.mit.train.controller;

import hu.bme.mit.train.interfaces.TrainController;

public class TrainControllerImpl implements TrainController  {
	private int step = 0;
	private int referenceSpeed = 0;
	private int speedLimit = 0;
	public referenceSpeedChange runReferenceSpeedChange;

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
	public void setSpeedLimit(int speedLimit) { // no issues found..
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
		if(runReferenceSpeedChange == null){
			runReferenceSpeedChange = new referenceSpeedChange("Tread1");
			runReferenceSpeedChange.start();
		}
	}


	public class referenceSpeedChange implements Runnable{
		private Thread t;
		private String threadName;
		private boolean run;

		referenceSpeedChange( String name) {
			threadName = name;
			System.out.println("Creating " +  threadName );
		}
		@Override
		public void run() {
			run = true;
			try {
				while (run){
					followSpeed();
					System.out.println("Speed set");
					Thread.sleep(1000); // setting speed every second
				}
			} catch (InterruptedException e){
				System.out.println("Thread " +  threadName + " interrupted.");
				Thread.currentThread().interrupt();
			}

		}
		public void start(){
			System.out.println("Starting " +  threadName );
			if (t == null) {
				t = new Thread (this, threadName);
				t.start ();
			}
		}

		public void setRun(boolean b) {
			run =b;
		}
	}

}