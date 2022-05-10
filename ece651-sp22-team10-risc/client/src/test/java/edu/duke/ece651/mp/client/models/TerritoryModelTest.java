package edu.duke.ece651.mp.client.models;

import edu.duke.ece651.mp.common.V1MapFactory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TerritoryModelTest {

    @Test
    void getTerritoryModelList() {
        V1MapFactory mapFactory = new V1MapFactory();
        List<String> visibleTerritory = new ArrayList<>();
        List<TerritoryModel> territoryModelList = TerritoryModel.getTerritoryModelList(mapFactory.createThreePlayerMap().getTerritoryList(), visibleTerritory);
        System.out.println(territoryModelList.size());
    }

    @Test
    void getTerritoryModelByName() {
    }

    @Test
    void updateTerritoryModels() {
    }

    @Test
    void nameProperty() {
    }

    @Test
    void sizeProperty() {
    }

    @Test
    void getRgbColor() {
    }
}