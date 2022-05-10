package edu.duke.ece651.mp.client.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class UnitTableRowModel {
    private final StringProperty type;
    private final IntegerProperty num;
    private final IntegerProperty bonus;

    public UnitTableRowModel(){
        type = new SimpleStringProperty("Chopper");
        bonus = new SimpleIntegerProperty(0);
        num = new SimpleIntegerProperty(1);
    }

    public UnitTableRowModel(String type, int numUnits, int bonus){
        this.type = new SimpleStringProperty(type);
        this.num = new SimpleIntegerProperty(numUnits);
        this.bonus = new SimpleIntegerProperty(bonus);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }


    public int getNum() {
        return num.get();
    }

    public IntegerProperty numProperty() {
        return num;
    }

    public int getBonus() {
        return bonus.get();
    }

    public IntegerProperty bonusProperty() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus.set(bonus);
    }

  public List<String> toStringArrayList() {
    List<String> res = new ArrayList<>();
    res.add(type.get());
    res.add(Integer.toString(bonus.get()));
    res.add(Integer.toString(num.get()));
    return res;
  }

}
