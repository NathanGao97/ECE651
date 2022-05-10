package edu.duke.ece651.mp.common;

import edu.duke.ece651.mp.common.*;

import java.util.List;

/*
 * This class implements from Actioner interface to allow
 * operations on Technology Level Upgrade orders.
 */
public class TechUpgradeActioner implements Actioner {
    /*
     * This function takes an order list and do operations on
     * technology level upgrade orders.
     */
    public void performAction(Message techUpgradeOrder, RiscMap map) {
        performTechUpgrade(techUpgradeOrder, map);
    }

    protected void performTechUpgrade(Message techUpgradeOrder, RiscMap map) {
        List<Color> colorList= map.getColorList();
        Color myColor = null;
        for (Color color : colorList) {
            if (color.equals(techUpgradeOrder.getOwner())) {
                myColor = color;
                break;
            }
        }
        int techLevel = myColor.getTechLevel();
        int cost = map.technologyLevelUpTable.get(techLevel);
        myColor.updateSanityResource(-cost);
        myColor.upgradeTechLevel();
    }
}