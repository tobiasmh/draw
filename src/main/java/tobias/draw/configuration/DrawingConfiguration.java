package tobias.draw.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tobias.draw.controller.*;
import tobias.draw.service.*;

/**
 * Created by Tobias on 11/12/16.
 *
 */

@Configuration
public class DrawingConfiguration {

    @Bean
    public ICreateCanvasService createCanvasService() { return new CreateCanvasService(); }

    @Bean
    public IDrawLineService drawLineService() { return new DrawLineService(); }

    @Bean
    public IDrawRectangleService drawRectangleService() { return new DrawRectangleService(); }

    @Bean
    public IFillCanvasService fillCanvasService() { return new FillCanvasService(); }

    @Bean
    public ICreateCanvasController createCanvasController() { return new CreateCanvasController(createCanvasService(), outputService()); }

    @Bean
    public IDrawLineController drawLineController() { return new DrawLineController(drawLineService(), outputService()); }

    @Bean
    public IDrawRectangleController drawRectangleController() { return new DrawRectangleController(drawRectangleService(), outputService()); }

    @Bean
    public IFillCanvasController fillCanvasController() { return new FillCanvasController(fillCanvasService(), outputService()); }

    @Bean
    public IOutputService outputService() {
        return new SystemConsoleOutputService();
    }

}
