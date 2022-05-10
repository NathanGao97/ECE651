package edu.duke.ece651.mp.client.models;
import edu.duke.ece651.mp.client.TableGenerator;
import edu.duke.ece651.mp.common.Unit;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

public class UnitTableModel {

    private static UnitTableModel unitTableModel = null;

    private final ObservableList<Unit> allyUnits;

    private final ObservableList<UnitTableRowModel> rows;

    //when allyUnits added all the new units, we can change the numAllyUnits property
    private UnitTableModel() {
        allyUnits = FXCollections.observableArrayList();
        rows = FXCollections.observableArrayList();
    }

    public static UnitTableModel getUnitTableModel() {
        if (unitTableModel == null) {
            unitTableModel = new UnitTableModel();
        }
        return unitTableModel;
    }

    public void updateAllyUnits(List<Unit> newUnits) {
        this.allyUnits.clear();
        this.allyUnits.addAll(newUnits);

        updateUnitsInfoRows();
    }

    //update the rows if there is updateAllyUnits() is called
    private void updateUnitsInfoRows() {
        HashMap<String, Integer> unitTypeToNum = new HashMap<>();
        HashMap<String, Integer> unitTypeToBonus = new HashMap<>();
        for (Unit unit : allyUnits) {
            unitTypeToNum.put(unit.getType(), unitTypeToNum.getOrDefault(unit.getType(), 0) + 1);
            unitTypeToBonus.put(unit.getType(), unit.getBonus());
        }

        rows.clear();
        for (String unitType : unitTypeToNum.keySet()) {
            rows.add(new UnitTableRowModel(unitType, unitTypeToNum.get(unitType), unitTypeToBonus.get(unitType)));
        }

    }

    public ObservableList<UnitTableRowModel> rowsProperty() {
        return rows;
    }

//  public String drawTable(String[][] table) {
//    String borderRow = Arrays.stream(table[0])
//      // border row between rows
//      .map(str -> "-".repeat(str.length()))
//      .collect(Collectors.joining("+", "+", "+\n"));
//    return Arrays.stream(table)
//      // table row with borders between cells
//      .map(row -> Arrays.stream(row)
//           .collect(Collectors.joining("|", "|", "|\n")))
//      .collect(Collectors.joining(borderRow, borderRow, borderRow));
//  }


    @Override
    public String toString(){
        List<String> headers = new ArrayList<>();
        headers.add("Type");
        headers.add("Bonus");
        headers.add("Num");
        List<List<String>> temp = new ArrayList<>();
        for (UnitTableRowModel row : rows) {
            temp.add(row.toStringArrayList());
        }
        TableGenerator tableGenerator = new TableGenerator();
        return tableGenerator.generateTable(headers, temp);
    }

}
