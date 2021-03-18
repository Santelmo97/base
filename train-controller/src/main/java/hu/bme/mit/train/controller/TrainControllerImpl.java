package hu.bme.mit.train.controller;

import hu.bme.mit.train.interfaces.TrainController;

public class TrainControllerImpl implements TrainController  {
	private int step = 5;
	private int referenceSpeed = 0;
	private int speedLimit = 0;
	public referenceSpeedChange runReferenceSpeedChange;
	private boolean run=false;
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
		if(runReferenceSpeedChange == null){
			runReferenceSpeedChange = new referenceSpeedChange("Tread1");
			setRun(true);
		}
	}
	public void setRun(boolean b) {
		this.run = b;
	}

	public class referenceSpeedChange implements Runnable{
		private Thread joystickThread;
		private String threadName;

		referenceSpeedChange( String name) {
			threadName = name;
			System.out.println(threadName + "created");
		}
		@Override
		public void run() {
			System.out.println(threadName + "started");
			if (joystickThread == null) {
				joystickThread = new Thread (this, threadName);
				joystickThread.start();
			}
			try {
				while (run){
					followSpeed();
					System.out.println("Joystick input handled");
					Thread.sleep(1000);
				}
			} catch (InterruptedException e){
				System.out.println("Thread interrupted.");
				Thread.currentThread().interrupt();
			}
		}
	}
}