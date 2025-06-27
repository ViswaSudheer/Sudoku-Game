package sp.javasudokuprojects.userInterface;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sp.javasudokuprojects.constants.GameState;
import sp.javasudokuprojects.problemDomain.Coordinates;
import sp.javasudokuprojects.problemDomain.SudokuGame;

import java.util.HashMap;

public class UserInterfaceImpl implements IUserInterfaceContract.View , EventHandler<KeyEvent> {
    private final Stage stage;
    private final Group root;
    private HashMap<Coordinates,SudokuTextField> textFieldCoordinates;
    private IUserInterfaceContract.EventListener listener;
    private static final double WINDOW_Y=732;
    private static final double WINDOW_X=668;
    private static final double BOARD_PADDING=50;
    private static final double BOARD_X_AND_Y=576;
    private static final Color WINDOW_BACKGROUND_COLOR=Color.rgb(0,13,136);
    private static final Color BOARD_BACKGROUND_COLOR=Color.rgb(2,242,241);
    private static final String SUDOKU="SUDOKU";



    public UserInterfaceImpl(Stage stage) {
        this.stage = stage;
        this.root = new Group();
        this.textFieldCoordinates=new HashMap<>();
        initializeUserInterface();
        //stage.show();
    }

    private void initializeUserInterface() {
        drawBackGround(root);
        drawTitle(root);
        drawSudokuBoard(root);
        drawTextField(root);
        drawGridLines(root);
        stage.show();
    }

    private void drawGridLines(Group root) {
        int xAndY=114;
        int index=0;
        while(index<8){
            int thickness;
            if(index==2 ||index==5) thickness=5;
            else thickness=2;
            Rectangle verticalLine=getLine(xAndY+64*index,BOARD_PADDING,BOARD_X_AND_Y,thickness);
            Rectangle horizontalLine=getLine(BOARD_PADDING,xAndY+64*index,thickness,BOARD_X_AND_Y);
            root.getChildren().addAll(verticalLine,horizontalLine);
            index++;
        }
    }

    private Rectangle getLine(double x, double y, double height, double width) {
        Rectangle line=new Rectangle();
        line.setX(x);
        line.setY(y);
        line.setHeight(height);
        line.setWidth(width);
        line.setFill(Color.BLACK);
        return line;
    }

    private void drawTextField(Group root) {
        final int xOrigin=50;
        final int yOrigin=48;
        final int xAndYDelta=64;
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                int x=xOrigin+i*xAndYDelta;
                int y=yOrigin+j*xAndYDelta;
                SudokuTextField tile=new SudokuTextField(i,j);
                styleSudokuTile(tile,x,y);
                tile.setOnKeyPressed(this);
                textFieldCoordinates.put(new Coordinates(i,j),tile);
                root.getChildren().add(tile);
            }
        }
    }

    private void styleSudokuTile(SudokuTextField tile ,double x,double y) {
        Font numberFont=new Font(32);
        tile.setFont(numberFont);
        tile.setAlignment(Pos.CENTER);
        tile.setLayoutX(x);
        tile.setLayoutY(y);
        tile.setPrefHeight(64);
        tile.setPrefWidth(64);
    }

    private void drawSudokuBoard(Group root) {
        Rectangle bg=new Rectangle();
        bg.setX(BOARD_PADDING);
        bg.setY(BOARD_PADDING);
        bg.setWidth(BOARD_X_AND_Y);
        bg.setHeight(BOARD_X_AND_Y);
        bg.setFill(BOARD_BACKGROUND_COLOR);
        root.getChildren().addAll(bg);
    }

    private void drawTitle(Group root) {
        Text title=new Text(235,690,SUDOKU);
        title.setFill(Color.AQUA);
        Font titleFont=new Font(43);
        title.setFont(titleFont);
        root.getChildren().add(title);
    }

    private void drawBackGround(Group root) {
        Scene scene=new Scene(root,WINDOW_X,WINDOW_Y);
        scene.setFill(WINDOW_BACKGROUND_COLOR);
        stage.setScene(scene);
    }



    @Override
    public void setListener(IUserInterfaceContract.EventListener listener) {
        this.listener=listener;
    }

    @Override
    public void updateSquare(int x, int y, int input) {
        SudokuTextField tile=textFieldCoordinates.get(new Coordinates(x,y));
        String value=Integer.toString(input);
        if(value.equals("0")) value="";
        tile.textProperty().setValue(value);
    }

    @Override
    public void updateBoard(SudokuGame game) {
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                TextField tile=textFieldCoordinates.get(new Coordinates(i,j));
                String value=Integer.toString(game.getCopyOfGridState()[i][j]);
                if(value.equals("0")) value="";
                tile.setText(value);
                if(game.getGameState()== GameState.NEW){
                    if(value.equals("")){
                        tile.setStyle("-fx-opacity: 1;");
                        tile.setDisable(false);
                    }else{
                        tile.setStyle("-fx-opacity: 0.8;");
                        tile.setDisable(true);
                    }
                }
            }
        }
    }

    @Override
    public void showDialog(String message) {
        Alert dialogue=new Alert(Alert.AlertType.CONFIRMATION,message, ButtonType.OK);
        dialogue.showAndWait();
        if(dialogue.getResult()==ButtonType.OK) listener.onDialogClick();
    }

    @Override
    public void showError(String message) {
        Alert dialogue=new Alert(Alert.AlertType.ERROR,message, ButtonType.OK);
        dialogue.showAndWait();
    }

    @Override
    public void handle(KeyEvent event) {
        if(event.getEventType()==KeyEvent.KEY_PRESSED){
            if(event.getText().matches("[1-9]")){
                int value=Integer.parseInt(event.getText());
                handleInput(value,event.getSource());
            }
            else if(event.getCode()== KeyCode.BACK_SPACE){
                handleInput(0,event.getSource());
            }else{
                ((TextField) event.getSource()).setText("");
            }
        }
        event.consume();
    }

    private void handleInput(int value, Object source) {
        listener.onSudokuInput(((SudokuTextField) source).getX(), ((SudokuTextField) source).getY(),value );
    }
}
