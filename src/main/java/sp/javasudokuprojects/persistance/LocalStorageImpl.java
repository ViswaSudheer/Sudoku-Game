package sp.javasudokuprojects.persistance;

import sp.javasudokuprojects.problemDomain.IStorage;
import sp.javasudokuprojects.problemDomain.SudokuGame;

import java.io.*;

public class LocalStorageImpl implements IStorage {

    // Define the file to store game data
    private static File GAME_DATA = new File(System.getProperty("user.home"), "game-data.txt");


    @Override
    public void updateGameData(SudokuGame game) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(GAME_DATA);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(game);
        } catch (IOException e) {
            throw new IOException("Unable to access game data", e);
        }
    }


    @Override
    public SudokuGame getGameData() throws IOException {
        try (FileInputStream fis = new FileInputStream(GAME_DATA);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (SudokuGame) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Class not found", e);
        }
    }
}
