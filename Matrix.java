package test;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import static java.lang.System.out;
import static java.lang.System.in;

public class Matrix {
	private int rows;
	private int columns;
	private List<List<Float>> array;

	private void swapSizes() {
		rows = rows ^ columns;
		columns = rows ^ columns;
		rows = rows ^ columns;
	}

	protected Matrix(int rows, int columns, List<List<Float>> array) {
		this.rows = rows;
		this.columns = columns;
		this.array = array;
	}

	protected float getElement(int row, int column) {
		return array.get(row).get(column);
	}

	public Matrix(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
	}

	public Matrix fill() {
		array = new ArrayList<>();
		List<Float> tempRow;
		for (int row = 0; row < rows; row++) {
			tempRow = new ArrayList<>();
			for (int column = 0; column < columns; column++) {
				tempRow.add(KeyboardGetter.nextFloat(String.format("%d row, %d column: ", row + 1, column + 1)));
			}
			this.array.add(tempRow);
		}
		return this;
	}

	public Matrix multiplByNum(float number) {
		List<List<Float>> array = new ArrayList<>();
		List<Float> tempRow;
		for (List<Float> row : this.array) {
			tempRow = new ArrayList<>();
			for (float num : row) {
				tempRow.add(num * number);
			}
			array.add(tempRow);
		}
		this.array = array;
		return this;
	}

	public Matrix transpose() {
		List<List<Float>> array = new ArrayList<>();
		List<Float> tempRow;
		for (int column = 0; column < columns; column++) {
			tempRow = new ArrayList<>();
			for (int row = 0; row < rows; row++) {
				tempRow.add(this.array.get(row).get(column));
			}
			array.add(tempRow);
		}
		swapSizes();
		this.array = array;
		return this;
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	@Override
	public String toString() {
		if (rows == 0 || columns == 0)
			return "";
		String string = "";
		for (List<Float> row : array) {
			for (float number : row) {
				string += number + " ";
			}
			string += "\n";
		}
		return string;
	}
}

class Operations {
	private static final int NEGATIVE = -1;
	private static final int POSITIVE = 1;

	private static Matrix getSum(Matrix matrix1, Matrix matrix2, int factor) {
		if (matrix1.getRows() != matrix2.getRows() || matrix1.getColumns() != matrix2.getColumns())
			return null;
		List<List<Float>> array = new ArrayList<>();
		List<Float> tempRow;
		for (int row = 0; row < matrix1.getRows(); row++) {
			tempRow = new ArrayList<>();
			for (int column = 0; column < matrix1.getColumns(); column++) {
				tempRow.add(matrix1.getElement(row, column) + matrix2.getElement(row, column) * factor);
			}
			array.add(tempRow);
		}
		return new Matrix(matrix1.getRows(), matrix1.getColumns(), array);
	}

	public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
		return getSum(matrix1, matrix2, POSITIVE);
	}

	public static Matrix getDiff(Matrix matrix1, Matrix matrix2) {
		return getSum(matrix1, matrix2, NEGATIVE);
	}

	public static Matrix getMultipl(Matrix matrix1, Matrix matrix2) {
		if (matrix1.getColumns() != matrix2.getRows())
			return null;
		List<List<Float>> array = new ArrayList<>();
		List<Float> tempRow;
		float temp;
		for (int row = 0; row < matrix1.getRows(); row++) {
			tempRow = new ArrayList<>();
			for (int column = 0; column < matrix2.getColumns(); column++) {
				temp = 0F;
				for (int i = 0; i < matrix1.getColumns(); i++) {
					temp += matrix1.getElement(row, i) * matrix2.getElement(i, column);
				}
				tempRow.add(temp);
			}
			array.add(tempRow);
		}
		return new Matrix(matrix1.getRows(), matrix2.getColumns(), array);
	}
}

class KeyboardGetter {
	@SuppressWarnings("resource")
	public static float nextFloat(String msg) {
		float value;
		try {
			out.println(msg);
			value = (new Scanner(in)).nextFloat();
		} catch (InputMismatchException e) {
			displayError();
			value = nextFloat(msg);
		}
		return value;
	}

	private static void displayError() {
		out.println("[ERROR] Enter a float value!");
	}
}
