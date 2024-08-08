// Software: Sudoku Solver
// Author: Hy Truong Son
// Major: BSc. Computer Science
// Class: 2013 - 2016
// Institution: Eotvos Lorand University
// Email: sonpascal93@gmail.com
// Website: http://people.inf.elte.hu/hytruongson/
// Copyright (c) 2015. All rights reserved.

package gui;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class SolutionGUI extends JFrame {
    
    private final int marginFrame = 50;
    
    private final int nTableRows, nTableColumns;
    private final int nCellRows, nCellColumns;
    private final int widthSquare, heightSquare;
    
    private final JButton squareButton[][];
    private final JTextArea outputText;
    
    public SolutionGUI(String titleFrame, int widthFrame, int heightFrame, int nTableRows, int nTableColumns, int nCellRows, int nCellColumns, ArrayList<Integer> solution) {
        this.nTableRows = nTableRows;
        this.nTableColumns = nTableColumns;
        this.nCellRows = nCellRows;
        this.nCellColumns = nCellColumns;
        
        setTitle(titleFrame);
        setSize(widthFrame + marginFrame, heightFrame + marginFrame);
        setResizable(true);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                
        widthSquare = (heightFrame - (nTableColumns / nCellColumns - 1) * marginFrame) / nTableColumns;
        heightSquare = (heightFrame - (nTableRows / nCellRows - 1) * marginFrame) / nTableRows;
                
        squareButton = new JButton[nTableRows][nTableColumns];
        int count = 0;
        for (int rowIndex = 0; rowIndex < nTableRows; ++rowIndex) {
            for (int columnIndex = 0; columnIndex < nTableColumns; ++columnIndex) {
                int x = columnIndex * widthSquare + marginFrame * (columnIndex / nCellColumns);
                int y = rowIndex * heightSquare + marginFrame * (rowIndex / nCellRows);
                
                squareButton[rowIndex][columnIndex] = new JButton();
                squareButton[rowIndex][columnIndex].setBounds(x, y, widthSquare, heightSquare);
                int value = solution.get(count);
                ++count;
                if (value < 0) {
                    squareButton[rowIndex][columnIndex].setForeground(Color.red);
                } else {
                    squareButton[rowIndex][columnIndex].setForeground(Color.green);
                }
                squareButton[rowIndex][columnIndex].setText(Integer.toString(Math.abs(value)));
                                
                add(squareButton[rowIndex][columnIndex]);
            }
        }
        
        outputText = new JTextArea();
        outputText.setText(getCurrentInput(solution));
        outputText.setBounds(heightFrame, 0, widthFrame - heightFrame, heightFrame);
        outputText.setEditable(false);
        add(outputText);
        
        setVisible(true);
    }
    
    private String getCurrentInput(ArrayList<Integer> solution) {
        String ret = "Solution:\n";
        int count = 0;
        for (int rowIndex = 0; rowIndex < nTableRows; ++rowIndex) {
            if ((rowIndex > 0) && (rowIndex % nCellRows == 0)) {
                ret += "\n";
            }
            for (int columnIndex = 0; columnIndex < nTableColumns; ++columnIndex) {
                if ((columnIndex > 0) && (columnIndex % nCellColumns == 0)) {
                    ret += "  ";
                }
                int value = solution.get(count);
                ++count;
                ret += Integer.toString(Math.abs(value));
            }
            ret += "\n";
        }
        return ret;
    }
    
}
