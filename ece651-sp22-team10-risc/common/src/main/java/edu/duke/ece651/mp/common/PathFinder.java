package edu.duke.ece651.mp.common;

import java.util.List;
import java.util.ArrayList;
import java.lang.Math;
import edu.duke.ece651.mp.common.*;

public class PathFinder {
    public int findMinimalCost(List<Territory> nextTerritoryList, List<Territory> visitedTerritoryList, Territory dest, int num, int cost, List<Territory> path) {
        if (nextTerritoryList.size() == 0) {
            return Integer.MAX_VALUE;
        }
        Territory next = nextTerritoryList.get(0);
        nextTerritoryList.remove(0);
        if (next.equals(dest)) {
            path.add(next);
            return cost + num * next.getSize();
        }
        else if (visitedTerritoryList.contains(next) || !next.getOwner().equals(dest.getOwner())) {
            return findMinimalCost(nextTerritoryList, visitedTerritoryList, dest, num, cost, path);
        }
        else {
            List<Territory> notUsedNextList = new ArrayList<>();
            for (Territory terr : nextTerritoryList) {
                notUsedNextList.add(terr);
            }

            List<Territory> usedNextList = new ArrayList<> ();
            for (Territory terr : next.getNeighbourTerritories()) {
                usedNextList.add(terr);
            }

            List<Territory> notUsedVisitedList = new ArrayList<> ();
            for (Territory terr: visitedTerritoryList) {
                notUsedVisitedList.add(terr);
            }

            List<Territory> usedVisitedList = new ArrayList<> ();
            for (Territory terr: visitedTerritoryList) {
                usedVisitedList.add(terr);
            }
            usedVisitedList.add(next);

            int notVisitedCost = cost;
            int visitedCost = cost + num * next.getSize();
            List<Territory> usedPath = new ArrayList<> ();
            for (Territory terr: path) {
                usedPath.add(terr);
            }

            usedPath.add(next);
            return Math.min(findMinimalCost(notUsedNextList, notUsedVisitedList, dest, num, notVisitedCost, path),
                    findMinimalCost(usedNextList, usedVisitedList, dest, num, visitedCost, usedPath));
        }
    }


    public int getMinimalCost(RiscMap myMap, Territory source, Territory dest, int num) {
        List<Territory> nextTerritoryList = new ArrayList<> ();
        List<Territory> visitedTerritoryList = new ArrayList<> ();
        visitedTerritoryList.add(source);
        for (Territory territory : myMap.getTerritoryList()) {
            if (territory.equals(source)) {
              for (Territory terr : territory.getNeighbourTerritories()) {
                nextTerritoryList.add(terr);
              }
              break;
            }
        }
        int cost = 0;
        List<Territory> path = new ArrayList<> ();
        path.add(source);
        int ans = findMinimalCost(nextTerritoryList, visitedTerritoryList, dest, num, cost, path);
        return ans;
    }
}
