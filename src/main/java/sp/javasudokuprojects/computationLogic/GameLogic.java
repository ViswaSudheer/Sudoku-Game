package sp.javasudokuprojects.computationLogic;
import java.util.*;
import sp.javasudokuprojects.constants.GameState;
import sp.javasudokuprojects.constants.Rows;
import sp.javasudokuprojects.problemDomain.SudokuGame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static sp.javasudokuprojects.problemDomain.SudokuGame.GRID_BOUNDARY;

public class GameLogic {


    public static SudokuGame getNewGame() {
        return new SudokuGame(GameState.NEW,GameGenerator.getNewGameGrid());
    }
    public static GameState checkForCompletion(int[][] grid) {
        if(sudokuIsInvalid(grid)) return GameState.ACTIVE;
        if(tilesAreNotFilled(grid)) return GameState.ACTIVE;
        return GameState.COMPLETE;
    }
    public static boolean sudokuIsInvalid(int[][] grid) {
        if(rowsAreInvalid(grid)) return true;
        if(columnsAreInvalid(grid)) return true;
        if(squaresAreInvalid(grid)) return true;
        return false;
    }

    public static boolean squaresAreInvalid(int[][] grid) {
        if(rowOfSquaresIsInvalid(Rows.TOP,grid)) return true;
        if(rowOfSquaresIsInvalid(Rows.MIDDLE,grid)) return true;
        if(rowOfSquaresIsInvalid(Rows.BOTTOM,grid)) return true;
        return false;
    }

    public static boolean rowOfSquaresIsInvalid(Rows value, int[][] grid) {
        return switch (value) {
            case TOP -> squareIsInvalid(0, 0, grid) || squareIsInvalid(0, 3, grid) || squareIsInvalid(0, 6, grid);
            case MIDDLE -> squareIsInvalid(3, 0, grid) || squareIsInvalid(3, 3, grid) || squareIsInvalid(3, 6, grid);
            case BOTTOM -> squareIsInvalid(6, 0, grid) || squareIsInvalid(6, 3, grid) || squareIsInvalid(6, 6, grid);
            default -> false;
        };
    }


    public static boolean squareIsInvalid(int x, int y, int[][] grid) {
        int xEnd=x+3;
        int yEnd=y+3;
        ArrayList<Integer> square=new ArrayList<>();
        for (int i = x; i < xEnd; i++) {
            for (int j = y; j < yEnd; j++) {
                square.add(grid[i][j]);
            }
        }
        return collectionsHasRepeats(square);
    }

    public static boolean collectionsHasRepeats(ArrayList<Integer> square) {
        for(int i=1;i<=GRID_BOUNDARY;i++){
            if(Collections.frequency(square,i)>1) return true;
        }
        return false;
    }


    public static boolean rowsAreInvalid(int[][] grid) {
        for(int x=0;x<GRID_BOUNDARY;x++){
            ArrayList<Integer> row=new ArrayList<>();
            for(int y=0;y<GRID_BOUNDARY;y++){
                row.add(grid[x][y]);
            }
            if(collectionsHasRepeats(row)) return true;

        }
        return false;
    }

    public static boolean columnsAreInvalid(int[][] grid) {
        for(int y=0;y<GRID_BOUNDARY;y++){
            ArrayList<Integer> col=new ArrayList<>();
            for(int x=0;x<GRID_BOUNDARY;x++){
                col.add(grid[x][y]);
            }
            if(collectionsHasRepeats(col)) return true;

        }
        return false;
    }

    public static boolean tilesAreNotFilled(int[][] grid) {
        for(int i=0;i<GRID_BOUNDARY;i++){
            for(int j=0;j<GRID_BOUNDARY;j++){
                if(grid[i][j]==0) return true;
            }
        }
        return false;
    }
}
