package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class Main extends Application {
	private List<Asteroid>newAsteroids;
	private List<Asteroid>asteroids;
	private Asteroid asteroid;
	private int  numberOfAsteroids = 3;
	public static int HEIGHT = 640;
	public static int WIDTH = 800;
	private Random random;
	
	@Override
	public void start(Stage primaryStage) throws IOException{
		try {
			
			BorderPane root = new BorderPane();
			int asteroidSize = 40;
	        int projectileLimit = 5;
			        
	        Pane pane = new Pane();
	        pane.setPrefSize(WIDTH,HEIGHT);
	        Ship ship = new Ship(WIDTH /2,HEIGHT /2);
	        random = new Random();
	        root.setCenter(pane);
	        
	        Scene scene = new Scene(root, WIDTH,HEIGHT);
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        
	        Text text = new Text(19,29,"Points: 0");
	        AtomicInteger points = new AtomicInteger();
	        pane.getChildren().add(text);
	        
	        List<Projectile>projectiles= new ArrayList<>();
	        
	        asteroids = new ArrayList<>();
	        newAsteroids = new ArrayList<>();
	        for (int i = 0 ; i < numberOfAsteroids; i++){
	            System.out.println("buildign starting roids #"+ i);
	            Asteroid asteroid = new Asteroid(random.nextInt(WIDTH /3),random.nextInt(HEIGHT),asteroidSize);
	            asteroids.add(asteroid);
	        }
	        
	        
	        
	        
	        pane.getChildren().add(ship.getCharacter());
	        asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));
	        
	        //Scene scene = new Scene(pane);
	        
	        Map<KeyCode,Boolean> pressedKeys = new HashMap<>();
	        
	        scene.setOnKeyPressed(event -> {
	            pressedKeys.put(event.getCode(), Boolean.TRUE);
	        });
	        scene.setOnKeyReleased(event ->{
	            pressedKeys.put(event.getCode(),Boolean.FALSE);
	        });
	        
	        Point2D movement = new Point2D(1,0);
	        
	        
	        new AnimationTimer(){
	            
	            @Override
	            public void handle(long now){
	                if (pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
	                    ship.turnLeft();
	                }
	                if (pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
	                    ship.turnRight();
	                }
	                if (pressedKeys.getOrDefault(KeyCode.UP ,false)){
	                    ship.accelerate();
	                }
	                if(pressedKeys.getOrDefault(KeyCode.SPACE, false) && projectiles.size() < projectileLimit){
	                    Projectile projectile = new Projectile((int) ship.getCharacter().getTranslateX(),(int) ship.getCharacter().getTranslateY());
	                    projectile.getCharacter().setRotate(ship.getCharacter().getRotate());
	                    projectiles.add(projectile);
	                    
	                    projectile.accelerate();
	                    projectile.setMovement(projectile.getMovement().normalize().multiply(3));
	                    
	                    pane.getChildren().add(projectile.getCharacter());
	                }
	                
	                ship.move();
	                asteroids.forEach(asteroid -> asteroid.move());
	                projectiles.forEach(projectile -> projectile.move());
	                
	                
	                projectiles.forEach(projectile ->{
	                    asteroids.forEach(asteroid -> {
	                        if(projectile.collide(asteroid)){
	                            projectile.setDead();
	                            asteroid.setDead();
	                            asteroid.setSize(asteroid.getSize() -5);
	                            System.out.println(asteroid.getSize());
	                            text.setText("Points: "+ points.addAndGet(50));
	                            if (asteroid.getSize() > 5){
	                                asteroidReduction(asteroid);
	                               
	                            }
	                        }
	                    });
	                });
	                projectiles.stream().filter(projectile -> !projectile.alive())
	                        .forEach(projectile -> pane.getChildren().remove(projectile.getCharacter()));
	                projectiles.removeAll(projectiles.stream()
	                        .filter(projectile -> !projectile.alive())
	                        .collect(Collectors.toList()));
	                
	                 asteroids.stream().filter(asteroid -> !asteroid.alive())
	                        .forEach(asteroid -> pane.getChildren().remove(asteroid.getCharacter()));
	                asteroids.removeAll(asteroids.stream()
	                        .filter(asteroid -> !asteroid.alive())
	                        .collect(Collectors.toList()));
	                
	                asteroids.addAll(newAsteroids);
	                newAsteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));
	                newAsteroids.clear();
	                
	                asteroids.forEach(asteroid -> {
	                    if (ship.collide(asteroid)){
	                        stop();
	                    }
	                });
	                if (asteroids.size() == 0){
	                    numberOfAsteroids++;
	                    for (int i = 0 ; i < numberOfAsteroids; i++){
	                        System.out.println("building starting roids #"+ i);
	                        Asteroid asteroid = new Asteroid(random.nextInt(WIDTH /3),random.nextInt(HEIGHT),asteroidSize);
	                        asteroids.add(asteroid);
	                    }
	                    asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));
	                }
	            }
	        }.start();
	        
	       
	       
			
			primaryStage.setTitle("Asteroids");    
		    primaryStage.setScene(scene);
		    
		    primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
	}
			    
			    public void createAsteroid(int x,int y,int size){
			        
			            asteroids.add(new Asteroid(x,y,size));
			            
			    }
			    public void asteroidReduction(Asteroid asteroid){
			       Random rand = new Random();
			       double x = asteroid.getCharacter().getTranslateX();
			       double y = asteroid.getCharacter().getTranslateY();
			       int intX = (int) x;
			       int intY= (int) y;
			       for (int i = 0;i < rand.nextInt(5); i++){
			            Asteroid newAsteroid = new Asteroid(intX,intY,asteroid.getSize()-5);
			            System.out.println(newAsteroid);
			            newAsteroids.add(newAsteroid);
			            
			       }
			        System.out.println(newAsteroids);
			    }
			            
	
	public static void main(String[] args) {
		launch(args);
	}
}
