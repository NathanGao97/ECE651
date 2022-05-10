package edu.duke.ece651.mp.common;

import java.util.List;

/*
 * This class implements from Actioner interface to allow
 * operations on Unit Upgrade orders.
 */
public class UnitUpgradeActioner implements Actioner {
    /*
     * This function takes an order list and do operations on
     * unit upgrade orders.
     */
    public void performAction(Message unitUpgradeOrder, RiscMap map) {
        performUnitUpgrade(unitUpgradeOrder, map);
    }

    protected void performUnitUpgrade(Message unitUpgradeOrder, RiscMap map) {
        AbstractUnitFactory unitFactory = new V1UnitFactory();
        Territory source = map.getTerritoryByName(unitUpgradeOrder.getSource());
        List<Color> colorList= map.getColorList();
        Color myColor = null;
        for (Color color : colorList) {
            if (color.equals(unitUpgradeOrder.getOwner())) {
                myColor = color;
                break;
            }
        }
        List<Unit> currentUnits = unitUpgradeOrder.getAllocatedUnits();
        String currentUnit = currentUnits.get(0).getType();
        String targetUnit = unitUpgradeOrder.getTarget();
        int currentCost = map.unitLevelUpTable.get(currentUnit);
        int targetCost = map.unitLevelUpTable.get(targetUnit);
        int num = currentUnits.size();
        int cost = (targetCost - currentCost) * num;
        myColor.updateSanityResource(-cost);
        for (Unit curr : currentUnits) {
          source.removeUnit(curr);
        }
        List<Unit> newUnits = unitFactory.createUnitsByType(targetUnit, myColor, num);
        for (Unit newUnit : newUnits) {
          source.addUnit(newUnit);
        }
    }
}

