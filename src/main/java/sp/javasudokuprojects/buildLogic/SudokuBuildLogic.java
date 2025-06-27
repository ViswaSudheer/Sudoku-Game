package sp.javasudokuprojects.buildLogic;

import sp.javasudokuprojects.computationLogic.GameLogic;
import sp.javasudokuprojects.persistance.LocalStorageImpl;
import sp.javasudokuprojects.problemDomain.IStorage;
import sp.javasudokuprojects.problemDomain.SudokuGame;
import sp.javasudokuprojects.userInterface.IUserInterfaceContract;
import sp.javasudokuprojects.userInterface.Logic.ControlLogic;

import java.io.IOException;

public class SudokuBuildLogic {
    public static void build(IUserInterfaceContract.View userInterface) throws IOException {
        SudokuGame initialState;
        IStorage storage=new LocalStorageImpl();
        try{
            initialState=storage.getGameData();
        }catch (IOException e){
            initialState= GameLogic.getNewGame();
            storage.updateGameData(initialState);
        }
        IUserInterfaceContract.EventListener uiLogic =new ControlLogic(storage,userInterface);
        userInterface.setListener(uiLogic);
        userInterface.updateBoard(initialState);
    }
}
