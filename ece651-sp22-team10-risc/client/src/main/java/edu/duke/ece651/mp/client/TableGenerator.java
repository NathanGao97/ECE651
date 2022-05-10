package edu.duke.ece651.mp.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TableGenerator {

    private int PAD_SIZE = 2;
    private String NEW_LINE = "\n";
    private String JOINT = "+";
    private String V_SPLIT = "|";
    private String H_SPLIT = "-";

    private void fillSpace(StringBuilder sB, int length)
    {
        for (int i = 0; i < length*33/20; i++) {
            sB.append(" ");
        }
    }

    private void createRowLine(StringBuilder sB,int headersListSize, Map<Integer,Integer> colMaxWMapp)
    {
        for (int i = 0; i < headersListSize; i++) {
            if(i == 0)
            {
                sB.append(JOINT);
            }

            for (int j = 0; j < colMaxWMapp.get(i) + PAD_SIZE * 2 ; j++) {
                sB.append(H_SPLIT);
            }
            sB.append(JOINT);
        }
    }


    private Map<Integer,Integer> getMaxWidth(List<String> headersList, List<List<String>> rowsList)
    {
        Map<Integer,Integer> colMaxWMapp = new HashMap<>();

        for (int colIdx = 0; colIdx < headersList.size(); colIdx++) {
            colMaxWMapp.put(colIdx, 0);
        }

        for (int colIdx = 0; colIdx < headersList.size(); colIdx++) {

            if(headersList.get(colIdx).length() > colMaxWMapp.get(colIdx))
            {
                colMaxWMapp.put(colIdx, headersList.get(colIdx).length());
            }
        }


        for (List<String> row : rowsList) {

            for (int colIdx = 0; colIdx < row.size(); colIdx++) {

                if(row.get(colIdx).length() > colMaxWMapp.get(colIdx))
                {
                    colMaxWMapp.put(colIdx, row.get(colIdx).length());
                }
            }
        }

        for (int colIdx = 0; colIdx < headersList.size(); colIdx++) {

            if(colMaxWMapp.get(colIdx) % 2 != 0)
            {
                colMaxWMapp.put(colIdx, colMaxWMapp.get(colIdx) + 1);
            }
        }


        return colMaxWMapp;
    }

    private int getCellPadd(int cellIdx,int len,Map<Integer,Integer> colMaxWMapp,int cellPaddingSize)
    {
        if(len % 2 != 0)
        {
            len++;
        }

        if(len < colMaxWMapp.get(cellIdx))
        {
            cellPaddingSize = cellPaddingSize + (colMaxWMapp.get(cellIdx) - len) / 2;
        }

        return cellPaddingSize;
    }

    private void fillCell(StringBuilder sB,String cell,int cellIdx,Map<Integer,Integer> colMaxWMapp)
    {

        int cellPaddingSize = getCellPadd(cellIdx, cell.length(), colMaxWMapp, PAD_SIZE);

        if(cellIdx == 0)
        {
            sB.append(V_SPLIT);
        }

        fillSpace(sB, cellPaddingSize);
        sB.append(cell);
        if(cell.length() % 2 != 0)
        {
            sB.append(" ");
        }
        fillSpace(sB, cellPaddingSize);
        sB.append(V_SPLIT);
    }
    public String generateTable(List<String> headersList, List<List<String>> rowsList,int... overRiddenHeaderHeight)
    {
        StringBuilder sB = new StringBuilder();

        int rowHeight = overRiddenHeaderHeight.length > 0 ? overRiddenHeaderHeight[0] : 1;

        Map<Integer,Integer> colMaxWMapp = getMaxWidth(headersList, rowsList);

        sB.append(NEW_LINE);
        sB.append(NEW_LINE);
        createRowLine(sB, headersList.size(), colMaxWMapp);
        sB.append(NEW_LINE);


        for (int hIdx = 0; hIdx < headersList.size(); hIdx++) {
            fillCell(sB, headersList.get(hIdx), hIdx, colMaxWMapp);
        }

        sB.append(NEW_LINE);

        createRowLine(sB, headersList.size(), colMaxWMapp);


        for (List<String> row : rowsList) {

            for (int i = 0; i < rowHeight; i++) {
                sB.append(NEW_LINE);
            }

            for (int cellIdx = 0; cellIdx < row.size(); cellIdx++) {
                fillCell(sB, row.get(cellIdx), cellIdx, colMaxWMapp);
            }

        }

        sB.append(NEW_LINE);
        createRowLine(sB, headersList.size(), colMaxWMapp);
        sB.append(NEW_LINE);
        sB.append(NEW_LINE);

        return sB.toString();
    }
}