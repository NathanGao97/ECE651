package edu.duke.ece651.mp.client.models;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.junit.jupiter.api.Test;

import jdk.jfr.Timestamp;

public class UnitTableRowModelTest {
  @Test
  public void testConstructor() {
    UnitTableRowModel model = new UnitTableRowModel();
    assertNotEquals(null, model);

    model = new UnitTableRowModel("Chopper", 0, 1);
    assertNotEquals(null, model);
  }


  @Test
  public void testGetType() {
    UnitTableRowModel model = new UnitTableRowModel("Chopper", 0, 1);
    assertEquals("Chopper", model.getType());
  }

  @Test
  public void testSetType() {
    UnitTableRowModel model = new UnitTableRowModel("Chopper", 0, 1);
    model.setType("Nami");
    assertEquals("Nami", model.getType());
  }

  @Test
  public void testGetNum() {
    UnitTableRowModel model = new UnitTableRowModel("Chopper", 0, 1);
    assertEquals(0, model.getNum());
  }

  @Test
  public void testBonus() {
    UnitTableRowModel model = new UnitTableRowModel("Chopper", 0, 1);
    assertEquals(1, model.getBonus());
    model.setBonus(10);
    assertEquals(10, model.getBonus());
  }

  @Test
  public void testTypeProperty() {
    UnitTableRowModel model = new UnitTableRowModel("Chopper", 0, 1);
    assertEquals("Chopper", model.typeProperty().get());
    assertEquals(0, model.numProperty().get());
  }

  @Test
  public void testToStringArrayList() {
    UnitTableRowModel model = new UnitTableRowModel("Chopper", 0, 1);
    assertEquals(3, model.toStringArrayList().size());
  }
}
