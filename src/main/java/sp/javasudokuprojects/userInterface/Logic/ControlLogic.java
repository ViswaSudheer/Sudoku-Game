package sp.javasudokuprojects.userInterface.Logic;

import sp.javasudokuprojects.computationLogic.GameLogic;
import sp.javasudokuprojects.constants.GameState;
import sp.javasudokuprojects.constants.Messages;
import sp.javasudokuprojects.problemDomain.IStorage;
import sp.javasudokuprojects.problemDomain.SudokuGame;
import sp.javasudokuprojects.userInterface.IUserInterfaceContract;

import java.io.IOException;

public class ControlLogic implements IUserInterfaceContract.EventListener {
    private IStorage storage;
    private IUserInterfaceContract.View view;

    public ControlLogic(IStorage storage,IUserInterfaceContract.View view) {
        if (storage == null || view == null) {
            throw new IllegalArgumentException("Storage and view cannot be null");
        }
        this.storage = storage;
        this.view=view;
    }

    @Override
    public void onSudokuInput(int x, int y, int input) {
        try{
            SudokuGame gameData=storage.getGameData();
            int [][]newGridState=gameData.getCopyOfGridState();
            newGridState[x][y]=input;
            gameData=new SudokuGame(GameLogic.checkForCompletion(newGridState),newGridState);
            storage.updateGameData((gameData));
            view.updateSquare(x,y,input);
            if(gameData.getGameState()== GameState.COMPLETE){
                view.showDialog(Messages.GAME_COMPLETE);
            }

        }catch(IOException e){
            e.printStackTrace();
            view.showError(Messages.ERROR);
        }
    }

    @Override
    public void onDialogClick() {
        try {
            SudokuGame newGame = GameLogic.getNewGame();
            storage.updateGameData(newGame);
            view.updateBoard(newGame);
        } catch (IOException e) {
            e.printStackTrace();
            view.showError(Messages.ERROR);
        }
    }
}
