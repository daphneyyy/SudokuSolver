/**
 * Copyright (c) 2023, Xuewen Yang. All rights reserved.
 * Author: Xuewen Yang
 * Email: xuewenyang1@gmail.com
 * References: None
 */

public class Sudoku {

    /* Declare magic numbers */
    private static final int SIZE = 9;
    private static final int FACTOR = 3;

    private final int[][] grid;

    public Sudoku() {
        this.grid = new int[SIZE][SIZE];
    }

    public int[][] getGrid() {
        return this.grid;
    }

    public void setGridPoint(int x, int y, int val) {
        this.grid[x][y] = val;
    }

    public int getGridPoint(int row, int col) {
        return this.grid[row][col];
    }

    public void setLine(int row, String[] values) {
        for (int i = 0; i < values.length; i++) {
            int val = Integer.parseInt(values[i]);
            setGridPoint(row, i, val);
        }
    }

    public void printSudoku() {
        for (int row = 0; row < SIZE; row++) {
            StringBuilder str = new StringBuilder();
            for (int col = 0; col < SIZE; col++) {
                if (col != 0 && col % FACTOR == 0) {
                    str.append("| ");
                }
                if (this.grid[row][col] != 0) {
                    str.append(this.grid[row][col]);
                } else {
                    str.append(" ");
                }
                if (col != SIZE - 1) {
                    str.append(" ");
                }
            }
            System.out.println(str);
            if (row != SIZE - 1 && (row + 1) % FACTOR == 0) {
                System.out.println("---------------------");
            }
        }
    }
}
