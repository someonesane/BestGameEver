import com.sun.jdi.connect.spi.TransportService;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.swing.plaf.ComponentInputMapUIResource;

public class BestGameEver extends Application {
    static float speed = 3;
    boolean bPlayer1IsPressingDownKey = false;
    boolean bPlayer1IsPressingUpKey = false;
    boolean bPlayer2IsPressingUpKey = false;
    boolean bPlayer2IsPressingDownKey = false;
    float directionX = 1;
    float directionY = 1;
    float ballSpeed = 1;

    @Override
    public void start(Stage stage) throws Exception {
        Circle ball = new Circle(0, 0, 10);
        Pane canvas = new Pane();
        canvas.getChildren().addAll(ball);


        double rand = Math.random();
        if (rand < 0.5) directionX *= -1;
        rand = Math.random();
        if (rand < 0.5) directionY *= -1;


        Rectangle player1 = new Rectangle(0, 0, 10, 50);
        Rectangle player2 = new Rectangle(275, 0, 10, 50);
        canvas.getChildren().addAll(player1, player2);

        Scene scene = new Scene(canvas);
        stage.setScene(scene);
        stage.setHeight(400);
        stage.setWidth(400);

        player1.setTranslateY(stage.getHeight() / 2 - player1.getHeight() / 2);
        player2.setTranslateY(stage.getHeight() / 2 - player2.getHeight() / 2);
        System.out.println(player1.getTranslateY());
        System.out.println(player2.getTranslateY());

        ball.setTranslateX(stage.getWidth() / 2);
        ball.setTranslateY(stage.getHeight() / 2);

        stage.show();


        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.Z || keyEvent.getCode() == KeyCode.W) {
                    bPlayer1IsPressingUpKey = true;
                }
                if (keyEvent.getCode() == KeyCode.S) {
                    bPlayer1IsPressingDownKey = true;
                }
                if (keyEvent.getCode() == KeyCode.UP) {
                    bPlayer2IsPressingUpKey = true;
                }
                if (keyEvent.getCode() == KeyCode.DOWN) {
                    bPlayer2IsPressingDownKey = true;
                }
            }
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.Z || keyEvent.getCode() == KeyCode.W) {
                    bPlayer1IsPressingUpKey = false;
                }
                if (keyEvent.getCode() == KeyCode.S) {
                    bPlayer1IsPressingDownKey = false;
                }
                if (keyEvent.getCode() == KeyCode.UP) {
                    bPlayer2IsPressingUpKey = false;
                }
                if (keyEvent.getCode() == KeyCode.DOWN) {
                    bPlayer2IsPressingDownKey = false;
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    // check if game is over
                    if (ball.getTranslateX() < 0 || ball.getTranslateX() > stage.getWidth() - 20) {
                        System.out.println("GAME OVER-------------------");
                        break;
                    }


                    // apply input
                    if (bPlayer1IsPressingUpKey) {
                        player1.setTranslateY(player1.getTranslateY() - speed);
                    }
                    if (bPlayer1IsPressingDownKey) {
                        player1.setTranslateY(player1.getTranslateY() + speed);
                    }
                    if (bPlayer2IsPressingUpKey) {
                        player2.setTranslateY(player2.getTranslateY() - speed);
                    }
                    if (bPlayer2IsPressingDownKey) {
                        player2.setTranslateY(player2.getTranslateY() + speed);
                    }

                    // checking for ball collisions
                    if (ball.getBoundsInParent().intersects(player1.getBoundsInParent())) {
                        directionX *= -1;
                    }
                    if (ball.getBoundsInParent().intersects(player2.getBoundsInParent())) {
                        directionX *= -1;
                    }
                    if (ball.getTranslateY() < ball.getRadius() || ball.getTranslateY() > stage.getHeight() - 50)
                        directionY *= -1;

                    // update ball location
                    ball.setTranslateX(ball.getTranslateX() + directionX * ballSpeed);
                    ball.setTranslateY(ball.getTranslateY() + directionY * ballSpeed);


                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
