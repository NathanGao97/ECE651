package edu.duke.ece651.mp.client.controller;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import javafx.application.Platform;
import edu.duke.ece651.mp.client.controller.MoveAttackPopUpHelper;


@ExtendWith(ApplicationExtension.class)
public class MoveAttackPopUpHelperTest {
    @Test
    public void test_popUpSelectAttack() throws IOException {

        Platform.runLater(() -> {
            try{
                MoveAttackPopUpHelper moveAttackPopUpHelper = new MoveAttackPopUpHelper();
                moveAttackPopUpHelper.popUpMoveAttackSelect("Chopper");
            }
            catch(Exception e){
            }
        });

    }

}
