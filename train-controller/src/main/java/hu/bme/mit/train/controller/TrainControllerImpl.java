package hu.bme.mit.train.controller;

import hu.bme.mit.train.interfaces.TrainController;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TrainControllerImpl implements TrainController  {
	private int step = 5;
	private int referenceSpeed = 0;
	private int speedLimit = 0;
	public ReferenceSpeedChange runReferenceSpeedChange;
	private boolean run=false;
	public static Logger LOGGER = Logger.getLogger(TrainControllerImpl.class.getName());

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
			runReferenceSpeedChange = new ReferenceSpeedChange("Joystick handler thread");
			setRun(true);
		}
	}
	private void setRun(boolean b) {
		this.run = b;
	}

	public class ReferenceSpeedChange implements Runnable{
		private Thread joystickThread;
		private String threadName;

		ReferenceSpeedChange(String name) {
			threadName = name;
			LOGGER.log(Level.FINE,"{0} created",threadName);
		}
		@Override
		public void run() {
			LOGGER.log(Level.FINE,"{0} started",threadName);
			if (joystickThread == null) {
				joystickThread = new Thread (this, threadName);
				joystickThread.start();
			}
			try {
				while (run){
					followSpeed();
					LOGGER.log(Level.FINE,"Joystick input handled");
					Thread.sleep(1000);
				}
			} catch (InterruptedException e){
				LOGGER.log(Level.FINE,"Thread interrupted.");
				Thread.currentThread().interrupt();
			}
		}
	}
}