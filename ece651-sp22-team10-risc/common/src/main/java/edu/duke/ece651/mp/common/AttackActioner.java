package edu.duke.ece651.mp.common;

import java.util.List;

/*
 * This class implements Actioner interface to allow operations on
 * attack orders.
 */
public class AttackActioner implements Actioner {
    /*
     * This function perform operations on attack order.
     */
    @Override
    public void performAction(Message attackOrder, RiscMap map) {
        performAttack(attackOrder, map);
    }
    protected void performAttack(Message attackOrder, RiscMap map) {
        Territory src = map.getTerritoryByName(attackOrder.getSource());
        Territory dest = map.getTerritoryByName(attackOrder.getDest());
        List<Unit> movingUnits = attackOrder.getAllocatedUnits();
        for(Unit unit : movingUnits) {
            src.removeUnit(unit);
            dest.addUnit(unit);
        }
        for (Color color : map.getColorList()) {
          if (color.equals(attackOrder.getOwner())) {
            color.updateFoodResource(-1*attackOrder.getAllocatedUnits().size());
          } 
        }
    }
}
