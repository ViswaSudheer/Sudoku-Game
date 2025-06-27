package sp.javasudokuprojects.computationLogic;

import sp.javasudokuprojects.problemDomain.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static sp.javasudokuprojects.problemDomain.SudokuGame.GRID_BOUNDARY;

public class GameGenerator {
    //public static int toBeRemoved;
    public static int[][] getNewGameGrid(){
        return unsolvedGame(getSolvedGame());

    }
    private static int[][] getSolvedGame() {
        Random random=new Random(System.currentTimeMillis());
        int [][]newGrid=new int[GRID_BOUNDARY][GRID_BOUNDARY];
        for(int i=1;i<GRID_BOUNDARY;i++){
            int allocations=0;
            int interrupt=0;
            List<Coordinates> allocTracker=new ArrayList<>();
            int attempts=0;
            while(allocations<GRID_BOUNDARY){
                if(interrupt>200){
                    allocTracker.forEach(coord->{
                        newGrid[coord.getX()][coord.getY()]=0;
                    });
                    interrupt=0;
                    allocations=0;
                    allocTracker.clear();
                    attempts++;
                    if(attempts>500){
                        clearArray(newGrid);
                        attempts=0;
                        i=1;
                        break;
                    }
                }
                int xCoordinate=random.nextInt(GRID_BOUNDARY);
                int yCoordinate=random.nextInt(GRID_BOUNDARY);
                if(newGrid[xCoordinate][yCoordinate]==0){
                    newGrid[xCoordinate][yCoordinate]=i+1;
                    if(GameLogic.sudokuIsInvalid(newGrid)){
                        newGrid[xCoordinate][yCoordinate]=0;
                        interrupt++;
                    }else{
                        allocTracker.add(new Coordinates(xCoordinate,yCoordinate));
                        allocations++;
                    }
                }
            }
        }
        return newGrid;
    }

    private static void clearArray(int[][] newGrid) {
        for(int i=0;i<GRID_BOUNDARY;i++){
            for(int j=0;j<GRID_BOUNDARY;j++){
                newGrid[i][j]=0;
            }
        }
    }

    private static int[][] unsolvedGame(int[][] solvedGame) {
        Random random=new Random(System.currentTimeMillis());
        boolean solvable=false;
        int[][] solvableArray=new int[GRID_BOUNDARY][GRID_BOUNDARY];
        while(!solvable){
            SudokuUtilities.copySudokuArrayValues(solvedGame,solvableArray);
            int index=0;
            while(index<40){
                int xCoordinates=random.nextInt(GRID_BOUNDARY);
                int yCoordinates=random.nextInt(GRID_BOUNDARY);
                if(solvableArray[xCoordinates][yCoordinates]!=0){
                    solvableArray[xCoordinates][yCoordinates]=0;
                    index++;
                }
            }
            int[][] toBeSolved= new int[GRID_BOUNDARY][GRID_BOUNDARY];
            SudokuUtilities.copySudokuArrayValues(solvableArray,toBeSolved);
            solvable=SudokuSolver.puzzleIsSolvable(toBeSolved);
        }
        return solvableArray;
    }



}
