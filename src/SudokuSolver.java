import java.util.Scanner;

/**
 * Copyright (c) 2023, Xuewen Yang. All rights reserved.
 * Author: Xuewen Yang
 * Email: xuewenyang1@gmail.com
 * References: None
 */

public class SudokuSolver {
    private static final String START = "Please enter the sudoku that needs be solved(one line at a time, no space between digits, use 0 for placeholder): ";
    private static final String NEXT = "Enter next row:";
    private static final String INVALID_LENGTH = "Invalid length! Please enter again:";
    private static final String INVALID_VALUE = "Invalid value! Please enter again:";
    private static final int SIZE = 9;

    private final Sudoku sd;
    private int line;

    public SudokuSolver() {
        this.sd = new Sudoku();
        this.line = 0;
    }

    public boolean readLine(int row) {
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        boolean result = true;
        String[] vals = str.split("");
        if (vals.length == 9) {
            boolean valid = true;
            for (int i = 0; i < vals.length; i++) {
                if (str.charAt(i) < 48 || str.charAt(i) > 57) {
                    valid = false;
                    break;
                }
            }
            if (!valid) {
                System.out.println(INVALID_VALUE);
                result = readLine(row);
            } else {
                this.sd.setLine(row, vals);
                this.line += 1;
            }
        } else if (str.compareTo("n") == 0) {
            result = false;
        } else {
            System.out.println(INVALID_LENGTH);
            result = readLine(row);
        }
        return result;
    }

    public boolean solve() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (this.sd.getGridPoint(row, col) == 0) {
                    return putNumber(row, col);
                }
            }
        }
        return true;
    }

    public boolean putNumber(int row, int col) {
        for (int i = 1; i <= SIZE; i++) {
            if (isSafe(row, col, i)) {
                this.sd.setGridPoint(row, col, i);
                if (solve()) {
                    return true;
                } else {
                    this.sd.setGridPoint(row, col, 0);
                }
            }
        }
        return false;
    }

    public boolean isSafe(int row, int col, int val) {
        return checkRowCol(row, col, val) && checkBox(row, col, val);
    }

    // Return false if the val exists
    public boolean checkRowCol(int row, int col, int val) {
        for (int i = 0; i < SIZE; i++) {
            if (this.sd.getGridPoint(row, i) == val) {
                return false;
            }
            if (this.sd.getGridPoint(i, col) == val) {
                return false;
            }
        }
        return true;
    }

    // Return false if the val exists
    public boolean checkBox(int row, int col, int val) {
        int[] idxList = getEdgesIdx(row, col);
        int xLeft = idxList[0];
        int xRight = idxList[1];
        int yLeft = idxList[2];
        int yRight = idxList[3];
        // check the box
        for (int r = xLeft; r < xRight + 1; r++) {
            for (int c = yLeft; c < yRight + 1; c++) {
                if (this.sd.getGridPoint(r, c) == val) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[] getEdgesIdx(int row, int col) {
        int xLeft;
        int xRight;
        int yLeft;
        int yRight;
        if (row % 3 == 0) {
            xLeft = row;
            xRight = row + 2;
        } else if (row % 3 == 1) {
            xLeft = row - 1;
            xRight = row + 1;
        } else {
            xLeft = row - 2;
            xRight = row;
        }
        if (col % 3 == 0) {
            yLeft = col;
            yRight = col + 2;
        } else if (col % 3 == 1) {
            yLeft = col - 1;
            yRight = col + 1;
        } else {
            yLeft = col - 2;
            yRight = col;
        }
        return new int[]{xLeft, xRight, yLeft, yRight};
    }

    public static void main(String[] args) {
        SudokuSolver sds = new SudokuSolver();
        System.out.println(START);
        boolean check = sds.readLine(sds.line);
        while (check && sds.line < 9) {
            System.out.println(NEXT);
            check = sds.readLine(sds.line);
        }
        if (check) {
            sds.sd.printSudoku();
            if (sds.solve()) {
                System.out.println("Answer is:");
                sds.sd.printSudoku();
            } else {
                System.out.println("Unsolvable");
            }
        }
    }
}
