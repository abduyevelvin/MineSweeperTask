package com.example.impl;

import java.util.ArrayList;

import com.example.interf.MineSweeper;

public class MineSweeperImpl implements MineSweeper {

	private char noMine;

	private char mineChar;

	private char lineBreak;

	private char[][] mineMatrix;

	private int rowSize;

	@SuppressWarnings("unused")
	private int colSize;
	
	public MineSweeperImpl(){
		this.mineChar = '*';
		this.noMine = '.';
		this.lineBreak = '\n';
	}

	@Override
	public void setMineField(String mineField) throws IllegalArgumentException {

		String[] rows;

		if (!mineField.matches("^[" + this.noMine + this.mineChar + this.lineBreak + "]+$")) {
			throw new IllegalArgumentException(
					"mineField string must contain: " + this.mineChar + ", " + this.noMine + ", " + this.lineBreak);
		}

		rows = mineField.split(String.valueOf(this.lineBreak));

		this.rowSize = rows.length;
		this.mineMatrix = new char[this.rowSize][];

		for (int i = 0; i < rows.length; i++) {
			if (i > 0 && rows[i].length() != rows[i - 1].length()) {
				throw new IllegalArgumentException("All the rows must be of " + "the same length.");
			}
			this.mineMatrix[i] = rows[i].toCharArray();
		}

		this.colSize = this.mineMatrix[0].length;

	}

	@Override
	public String getHintField() throws IllegalStateException {

		String hintField = "";

		if (this.mineMatrix == null) {
			throw new IllegalStateException("Minefield has not been " + "initialized yet.");
		}

		int last = this.rowSize - 1;
		for (int r = 0; r < this.rowSize; r++) {
			for (int c = 0; c < this.colSize; c++) {
				if (this.mineMatrix[r][c] == this.mineChar) {
					hintField += this.mineChar;
				} else {
					hintField += Character.forDigit(this.getNeighborMineCount(r, c), 10);
				}
			}
			if (r != last) {
				hintField = hintField + "\\n";
			}
		}

		return hintField;
	}

	private ArrayList<Character> getNeighbors(int row, int col) throws IllegalArgumentException {
		ArrayList<Character> neighbours = new ArrayList<Character>();

		if (row > this.rowSize - 1 || row < 0 || col > this.colSize - 1 || col < 0) {
			throw new IllegalArgumentException("The cell must be inside the " + "matrix boundaries.");
		}

		int rowLowerBound = (row - 1 >= 0) ? row - 1 : row;
		int rowUpperBound = (row + 1 <= this.rowSize - 1) ? row + 1 : row;
		int columnLowerBound = (col - 1 >= 0) ? col - 1 : col;
		int columnUpperBound = (col + 1 <= this.colSize - 1) ? col + 1 : col;

		/* Loop over the cells of the sub-matrix, ignore the cell's itself. */
		for (int r = rowLowerBound; r <= rowUpperBound; r++) {
			for (int c = columnLowerBound; c <= columnUpperBound; c++) {
				if (c != col || r != row) {
					neighbours.add(this.mineMatrix[r][c]);
				}
			}
		}

		return neighbours;
	}

	private int getNeighborMineCount(int row, int col) {
		int count = 0;

		for (char c : this.getNeighbors(row, col)) {
			if (c == this.mineChar) {
				count++;
			}
		}

		return count;
	}

}
