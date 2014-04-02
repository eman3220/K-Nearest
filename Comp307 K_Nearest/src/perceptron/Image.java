package perceptron;

import java.util.ArrayList;

public class Image {
	private int row;
	private int col;
	private int[][] signs;

	public Image(int row, int col, int[] signs) {
		this.row = row;
		this.col = col;
		this.signs = new int[row][col];
		readSigns(signs);
		printSigns();
	}

	private void printSigns() {
		for (int colC = 0; colC < this.col; colC++) {
			for (int rowC = 0; rowC < this.row; rowC++) {
				System.out.print(this.signs[rowC][colC]);
			}
			System.out.println();
		}
		System.out.println();
	}

	private void readSigns(int[] signs2) {
		int x = 0;
		int y = 0;
		for (int b : signs2) {
			this.signs[x][y] = b;
			x++;
			if (x >= row) {
				x = 0;
				y++;
			}
		}

	}
}
