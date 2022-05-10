package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class MessageTest {
  @Test
  public void test_mess() {

    Message mess1 = new Message();
    assertEquals(mess1.getType(), "");

    assertEquals(mess1.getOwner(), null);   
    assertEquals(mess1.getSource(), "");   
    assertEquals(mess1.getDest(), "");   
    assertEquals(mess1.getAllocatedUnits(), new ArrayList<>());   
    
    Message mess2 = new Message("attack", null, "source", "dest", new ArrayList<>());                 
    assertEquals(mess2.getType(), "attack");             
    assertEquals(mess2.getOwner(), null);            
    assertEquals(mess2.getSource(), "source");           
    assertEquals(mess2.getDest(), "dest");             
    assertEquals(mess2.getAllocatedUnits(), new ArrayList<>());   

    Message mess3 = new Message("");
    assertEquals(mess1, mess3);
    assertNotEquals(mess1, mess2);

    Color owner = new Color("Red");
    ArrayList<Unit> units = new ArrayList<>();
    units.add(new BasicUnit(owner));
    Message mess4 = new Message("attack", owner, "source", "dest", units);
    Message mess5 = new Message("attack", new Color("Red"), "source", "dest", new ArrayList<>());
    assertEquals("(Red): attack, source, dest, 1 units", mess4.toString());
    assertEquals(mess4, new Message("attack", owner, "source", "dest", units));
    assertNotEquals(mess4, new Color("Red"));
  }

  @Test
  public void test_new_constructors() {
    Message mess1 = new Message("unitUpgrade", new Color("Red"), "Chopper", null, "Zoro");
    assertEquals("Zoro", mess1.getTarget());
    Message mess2 = new Message("techUpgrade", new Color("Red"));
  }
}
