package hu.bme.mit.train.sensor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.user.TrainUserImpl;
import hu.bme.mit.train.controller.TrainControllerImpl;

public class TrainSensorTest {
    TrainSensor sensor;
    TrainUser usr;
    TrainController ctr;
    
    @Before
    public void before() {
        ctr = new TrainControllerImpl();
        usr = new TrainUserImpl(ctr);
        sensor = new TrainSensorImpl(ctr,usr);
    }

    @Test
    public void ThisIsAnExampleTestStub() {
        sensor.overrideSpeedLimit(130);
        Assert.assertEquals(130, sensor.getSpeedLimit());
    }
}
