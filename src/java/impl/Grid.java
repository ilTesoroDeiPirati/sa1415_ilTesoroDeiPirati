package impl;

import java.util.ArrayList;
import java.util.List;

import interfaces.IGrid;
import interfaces.IPirate;

public class Grid implements IGrid {
	
	private Cell[][] grid;
	private int lines, column;
	
	public Grid(int lines, int column){
		this.lines = lines;
		this.column = column;
		grid = new Cell[lines+1][column+1];
		
		for(int i = 1; i <= lines; i++){
			for(int j = 1; j <= column; j++){
				grid[i][j] = new Cell(new Position(i, j));
			}
		}
	}
	
	public void setPirate(Position pos, Pirate pirate) {
		grid[pos.getX()][pos.getY()].setPirate(pirate);
	}

	public boolean isFree(Position cell) {
		return grid[cell.getX()][cell.getY()].isFree();
	}
	
	public Pirate getPirate(Position position){
		return grid[position.getX()][position.getY()].getPirate();
	}
	
	public void printPosition() {
		
	}

	public List<IPirate> allPiratePosition() {
		List<IPirate> allPirate = new ArrayList<IPirate>();
		for(int i = 1; i <= lines; i++){
			for(int j = 1; j <= column; j++){
				if(!grid[i][j].isFree()){
					allPirate.add(grid[i][j].getPirate());
				}
			}
		}
		return allPirate;
	}

}
