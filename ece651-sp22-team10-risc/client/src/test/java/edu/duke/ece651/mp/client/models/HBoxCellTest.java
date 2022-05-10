package edu.duke.ece651.mp.client.models;

import edu.duke.ece651.mp.common.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;


@ExtendWith(ApplicationExtension.class)
class HBoxCellTest {
    @Start
    private void start(Stage stage) {
    }
        @Test
    public void HBoxCell_test(){
        new HBoxCell(1, 1,1, new Color("Red"));
    }

}