package Pong;
/**
 *  This program uses JavaFx to implement a pong game
 *  using the mouse as an controller and a secondary player
 *  for the player to play aganst.
 *
 *  !!!!!!  MUST USE JDK 8 TO RUN PROGRAM !!!!!!
 *
 * @author  Alvin Mai
 * @version 1.0
 * @Final   05/10/2019
 */
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PongMain extends Application
{
    // sets stage size
    private static final int windowWidth = 800;
    private static final int windowHight = 600;
    // sets the player paddle also mirros oponent
    private static final int playerWidth = 15;
    private static final int playerHight = 100;
    // ball size and speed - ball speed will increase
    private static final double ball = 15;
    private int ballYSpeed = 1;
    private int ballXSpeed = 1;
    // position of paddles/players
    private double playerOneYPos = windowHight / 2;
    private double playerTwoYPos = windowHight / 2;
    // position of ball
    private double ballPosX = windowWidth/2;
    private double ballPosY = windowHight/2;
    //Score
    private int playerOneScore = 0;
    private int playerTwoScore = 0;
    // boolean for game start
    private boolean gameStarted;
    //user position
    private int playerOneXPos = 0;
    // program opponent position
    private double playerTwoXPos = windowWidth - playerWidth;

    private void run(GraphicsContext gc)
    {
        // to set colour of stage and paddles (set font is for font of text)
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, windowWidth, windowHight);
        gc.setFill(Color.GREEN);
        gc.setFont(Font.font(25));

        //set handler for ball speed and position
        if(gameStarted)
        {
            // sets ball speed to its position
            ballPosX+=ballXSpeed;
            ballPosY+=ballYSpeed;

            // this if else statment will handle oponet paddle positioning
            if(ballPosX < windowWidth - windowHight  / 4)
            {
                playerTwoYPos = ballPosY - playerHight / 2;
            }
            else
           {
               playerTwoYPos =  ballPosY > playerTwoYPos + playerHight / 2 ?playerTwoYPos += 1: playerTwoYPos - 1;
            }
            gc.fillOval(ballPosX, ballPosY, ball, ball);
        }
        // to set click to start after score or start
        else
        {
          gc.setStroke(Color.YELLOW);
          gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("Click to Start", windowWidth / 2, windowHight / 2);
            ballPosX = windowWidth / 2;
            ballPosY = windowHight / 2;
            ballXSpeed = new Random().nextInt(2) == 0 ? 1: -1;
            ballYSpeed = new Random().nextInt(2) == 0 ? 1: -1;
        }
        //ball y speed
        if(ballPosY>windowHight||ballPosY<0)ballYSpeed*=-1;
        //Score paddle 2
        if(ballPosX< playerOneXPos - playerWidth)
        {
           //this adds one to player two score
            playerTwoScore++;
            //pauses game then restarts stage when paddle two scores
            gameStarted=false;
        }
        //Score paddle 1 (player)
        if(ballPosX> playerTwoXPos + playerWidth)
        {
            // this adds to player one score
            playerOneScore++;
            //pauses game then restarts stage when paddle one scores
            gameStarted=false;
        }
        // this if statement is the handler for all getter above - final speed modifier
        if( ((ballPosX + ball > playerTwoXPos) && ballPosY >= playerTwoYPos && ballPosY <= playerTwoYPos + playerHight) ||
                ((ballPosX < playerOneXPos + playerWidth) && ballPosY >= playerOneYPos && ballPosY <= playerOneYPos + playerHight))
        {
            ballYSpeed += 1 * Math.signum(ballYSpeed);
            ballXSpeed += 1 * Math.signum(ballXSpeed);
            ballXSpeed *= -1;
            ballYSpeed *= -1;
        }
        gc.fillText(playerOneScore + "\t\t\t\t\t\t\t\t" + playerTwoScore, windowWidth / 2, 100);
        gc.fillRect(playerTwoXPos, playerTwoYPos, playerWidth, playerHight);
        gc.fillRect(playerOneXPos, playerOneYPos, playerWidth, playerHight);
    }
    // this statment will run the app that allows for the window
    public void start(Stage stage) throws Exception {
        // this names the window to pong game
        stage.setTitle("Pong Game");
        // this canvas statment will allow for window width and hight
        Canvas canvas = new Canvas(windowWidth, windowHight);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);
        // this statment will allow for use of mouse
        canvas.setOnMouseMoved(e ->  playerOneYPos  = e.getY());
        canvas.setOnMouseClicked(e ->  gameStarted = true);
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
        tl.play();
    }
}