package hu.bme.mit.train.controller;

import hu.bme.mit.train.interfaces.TrainController;

import java.util.Timer;
import java.util.TimerTask;

public class TrainControllerImpl implements TrainController {

	private int step = 5;
	private int referenceSpeed = 0;
	private int speedLimit = 0;
	private Timer timer = new Timer();
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
		runReferenceSpeedChange();
	}
	public void setRun(){
		this.run=!this.run;
	}
	private void runReferenceSpeedChange()
	{
		setRun();
		TimerTask joystickTask=new TimerTask() {
			@Override
			public void run() {
				followSpeed();
				System.out.println("Joystick position handled");
			}
		};
		while(run){
			timer.scheduleAtFixedRate(joystickTask,0,1000);
		}
		timer.cancel();
		System.out.println("System joystick handling shutdown");

	}
}
