package edu.duke.ece651.mp.common;

import edu.duke.ece651.mp.common.*;

import java.util.List;

/*
 * This interface conducts the order operation.
 */
public interface Actioner {
    public void performAction(Message order, RiscMap map);
}
