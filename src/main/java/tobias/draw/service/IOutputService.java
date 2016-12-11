package tobias.draw.service;

/**
 * Created by Tobias on 11/12/16.
 *
 * Interface provided for future extensibility. For example writing output to a file or a logging appender.
 */
public interface IOutputService {

    void output(String output);

}
