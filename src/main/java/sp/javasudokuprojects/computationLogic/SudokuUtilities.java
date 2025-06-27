package sp.javasudokuprojects.computationLogic;

import static sp.javasudokuprojects.problemDomain.SudokuGame.GRID_BOUNDARY;

public class SudokuUtilities {
    public static void copySudokuArrayValues(int [][]oldArray,int [][]newArray){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                newArray[i][j]=oldArray[i][j];
            }
        }
    }

    public static int[][] copyToNewArray(int[][] gridState) {
        int newArray[][]=new int[GRID_BOUNDARY][GRID_BOUNDARY];
        for(int i=0;i<GRID_BOUNDARY;i++){
            for(int j=0;j<GRID_BOUNDARY;j++){
                newArray[i][j]=gridState[i][j];
            }
        }
        return newArray;
    }
}
