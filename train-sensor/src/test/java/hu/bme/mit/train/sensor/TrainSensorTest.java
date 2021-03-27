package hu.bme.mit.train.sensor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.user.TrainUserImpl;
import hu.bme.mit.train.controller.TrainControllerImpl;
import static org.mockito.Mockito.*;

public class TrainSensorTest {
    TrainSensor sensor;
    TrainUser usr;
    TrainController ctr;
    
    @Before
    public void before() {
        ctr = mock(TrainControllerImpl.class);
        usr = mock(TrainUserImpl.class);
        sensor = new TrainSensorImpl(ctr,usr);
        when(ctr.getReferenceSpeed()).thenReturn(130);
    }
    /*
    @Test
    public void FirstLabTest() {
        sensor.overrideSpeedLimit(130);
        Assert.assertEquals(130, sensor.getSpeedLimit());
    }
    */

    //Tests the absolute upper margin. Alarm value should be true.
    @Test
    public void AlarmTriggerOverMargin(){
        sensor.overrideSpeedLimit(690);
        verify(usr).setAlarmState(true);
    }
    //Tests the absolute lower margin. Alarm value should be true.
    @Test
    public void AlarmTriggerUnderMargin(){
        sensor.overrideSpeedLimit(-42);
        verify(usr).setAlarmState(true);
    }
    //Tests the relative margin. Alarm value should be true.
    @Test
    public void AlarmTriggerUnderPercent(){
        sensor.overrideSpeedLimit(23);
        verify(usr).setAlarmState(true);
    }
    //Test if the alarm is called in case of correct value. Alarm should be called 0 times.
    @Test
    public void AlarmTriggerCorrectValue(){
        sensor.overrideSpeedLimit(190);
        verify(usr, times(0)).setAlarmState(true);
    }
}
