package tobias.draw;

import org.mockito.ArgumentCaptor;
import tobias.draw.service.IOutputService;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Tobias on 11/12/16.
 *
 * Some functions that shared across the controller and application test classes.
 */
public abstract class BaseTest {

    protected IOutputService outputService = mock(IOutputService.class);

    protected void sendToConsole(String... s) {

        ByteArrayInputStream in = new ByteArrayInputStream(String.join("\r", s).getBytes());
        System.setIn(in);
    }

    protected List<String> getOutput() {

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.outputService, atLeastOnce()).output(argumentCaptor.capture());

        return argumentCaptor.getAllValues();
    }

    protected String getLastOutput() {

        List<String> output = getOutput();
        int size = output.size();
        if (size > 0) {
            return  output.get(size-1);
        }
        return "";
    }

}
