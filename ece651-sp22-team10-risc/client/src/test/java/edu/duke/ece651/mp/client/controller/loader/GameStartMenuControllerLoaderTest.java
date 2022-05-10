package edu.duke.ece651.mp.client.controller.loader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class GameStartMenuControllerLoaderTest {
    @Test
    void getGameTopLevelControllerLoader() {
        GameStartMenuControllerLoader.getGameTopLevelControllerLoader();
    }
}


