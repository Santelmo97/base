import hu.bme.mit.train.controller.TrainControllerImpl;
import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.user.TrainUserImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TrainTachographTest {
    TrainController tc;
    TrainSensor ts;
    TrainUser tu;
    TrainTachograph tt;

    @Before
    public void before(){
        tt=new TrainTachograph();
        tc=new TrainControllerImpl();
        tu=new TrainUserImpl(tc);
    }

    @Test
    public void CollectionHasElement(){
        tt.add(tu.getJoystickPosition(),tc.getReferenceSpeed());
        Assert.assertTrue((tt.getSize() > 0));
    }
}
