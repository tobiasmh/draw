package tobias.draw;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tobias.draw.configuration.DrawingConfiguration;

/**
 * Created by Tobias on 11/12/16.
 *
 * Default Spring test for checking that the configuration is valid and loads.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@Import(value = { DrawingConfiguration.class })
public class DrawingApplicationContextTest {

    @Test
    public void contextLoads() {}

}
