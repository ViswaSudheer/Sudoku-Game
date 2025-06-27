package sp.javasudokuprojects.computationLogic;

import sp.javasudokuprojects.problemDomain.Coordinates;

import static sp.javasudokuprojects.problemDomain.SudokuGame.GRID_BOUNDARY;

public class SudokuSolver {

    public static boolean puzzleIsSolvable(int[][] puzzle) {
        Coordinates[] emptyCells = typeWriterEnumerate(puzzle);
        int index = 0;
        int input;

        while (index < emptyCells.length && emptyCells[index] != null) {
            Coordinates current = emptyCells[index];
            input = puzzle[current.getX()][current.getY()] + 1;
            boolean foundValidInput = false;

            while (input <= GRID_BOUNDARY) {
                puzzle[current.getX()][current.getY()] = input;
                if (!GameLogic.sudokuIsInvalid(puzzle)) {
                    foundValidInput = true;
                    index++;
                    break;
                }
                input++;
            }

            if (!foundValidInput) {
                puzzle[current.getX()][current.getY()] = 0;
                index--;
                if (index < 0) {
                    return false;
                }
            }
        }

        return index == emptyCells.length || emptyCells[index] == null;
    }

    private static Coordinates[] typeWriterEnumerate(int[][] puzzle) {
        Coordinates[] emptyCells = new Coordinates[40];
        int iterator = 0;

        for (int i = 0; i < GRID_BOUNDARY; i++) {
            for (int j = 0; j < GRID_BOUNDARY; j++) {
                if (puzzle[i][j] == 0) {
                    emptyCells[iterator] = new Coordinates(i, j);
                    iterator++;
                    if (iterator == 40) {
                        return emptyCells;
                    }
                }
            }
        }

        return emptyCells;
    }
}
