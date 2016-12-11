package tobias.draw.service;

/**
 * Created by Tobias on 11/12/16.
 *
 * A simple wrapper provided to provide for future extensibility.
 */
public class SystemConsoleOutputService implements IOutputService {

    @Override
    public void output(String output) {
        System.out.println(output);
    }
}
