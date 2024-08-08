// Software: Sudoku Solver
// Author: Hy Truong Son
// Major: BSc. Computer Science
// Class: 2013 - 2016
// Institution: Eotvos Lorand University
// Email: sonpascal93@gmail.com
// Website: http://people.inf.elte.hu/hytruongson/
// Copyright (c) 2015. All rights reserved.

package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import logic.SudokuSolver;

public class SudokuGUI extends JFrame {
    
    private final int marginFrame = 60;
    private final int heightComponent = 90;
    
    private final int widthFrame, heightFrame;
    private final int nTableRows, nTableColumns;
    private final int nCellRows, nCellColumns;
    private final int widthSquare, heightSquare;
    
    private final JButton squareButton[][];
    private final JButton solveButton;
    private JTextArea inputText;
    private SudokuSolver solver = null;
    
    public SudokuGUI(String titleFrame, final int widthFrame, final int heightFrame, final int nTableRows, final int nTableColumns, final int nCellRows, final int nCellColumns) {
        this.widthFrame = widthFrame;
        this.heightFrame = heightFrame;
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
        
        solver = new SudokuSolver(nTableRows, nTableColumns, nCellRows, nCellColumns);
        
        squareButton = new JButton[nTableRows][nTableColumns];
        for (int rowIndex = 0; rowIndex < nTableRows; ++rowIndex) {
            for (int columnIndex = 0; columnIndex < nTableColumns; ++columnIndex) {
                int x = columnIndex * widthSquare + marginFrame * (columnIndex / nCellColumns);
                int y = rowIndex * heightSquare + marginFrame * (rowIndex / nCellRows);
                
                squareButton[rowIndex][columnIndex] = new JButton();
                squareButton[rowIndex][columnIndex].setBounds(x, y, widthSquare, heightSquare);
                squareButton[rowIndex][columnIndex].setText(new String());
                squareButton[rowIndex][columnIndex].putClientProperty("rowIndex", rowIndex);
                squareButton[rowIndex][columnIndex].putClientProperty("columnIndex", columnIndex);
                squareButton[rowIndex][columnIndex].addActionListener(squareButtonAL);
                
                add(squareButton[rowIndex][columnIndex]);
            }
        }
        
        int x = heightFrame + marginFrame;
        int y = marginFrame;
        int w = widthFrame - heightFrame - 2 * marginFrame;
        
        solveButton = new JButton("Solve");
        solveButton.setBounds(x, y, w, heightComponent);
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Integer> solution = solver.findSolution();
                if (solution == null) {
                    JOptionPane.showMessageDialog(null, "There is NO solution!");
                } else {
                    String title = "Solution for table " +
                            Integer.toString(nTableRows) + " x " +
                            Integer.toString(nTableColumns) + " (cell " +
                            Integer.toString(nCellRows) + " x " +
                            Integer.toString(nCellColumns) + ") - Hy Truong Son";
                    new SolutionGUI(title, widthFrame, heightFrame, nTableRows, nTableColumns, nCellRows, nCellColumns, solution);
                }
            }
        });
        add(solveButton);
        
        x = heightFrame;
        y += marginFrame + heightComponent;
        w = widthFrame - heightFrame;
        int h = heightFrame - y;
        
        inputText = new JTextArea();
        inputText.setText(getCurrentInput());
        inputText.setBounds(x, y, w, h);
        inputText.setEditable(false);
        add(inputText);
        
        setVisible(true);
    }
    
    private String getCurrentInput() {
        String ret = "Input:\n";
        for (int rowIndex = 0; rowIndex < nTableRows; ++rowIndex) {
            if ((rowIndex > 0) && (rowIndex % nCellRows == 0)) {
                ret += "\n";
            }
            for (int columnIndex = 0; columnIndex < nTableColumns; ++columnIndex) {
                if ((columnIndex > 0) && (columnIndex % nCellColumns == 0)) {
                    ret += "  ";
                }
                int value = solver.getValueAt(rowIndex, columnIndex);
                ret += Integer.toString(value);
            }
            ret += "\n";
        }
        return ret;
    }
    
    private ActionListener squareButtonAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton)(e.getSource());
            int rowIndex = (int)(button.getClientProperty("rowIndex"));
            int columnIndex = (int)(button.getClientProperty("columnIndex"));
            
            solver.setNextValueAt(rowIndex, columnIndex);
            int value = solver.getValueAt(rowIndex, columnIndex);
            if (value == 0) {
                button.setText(new String());
            } else {
                button.setText(Integer.toString(value));
            }
            inputText.setText(getCurrentInput());
        }
    };
    
}
