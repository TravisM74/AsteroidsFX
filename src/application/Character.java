/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;



import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;


/**
 *
 * @author Travis
 */
public abstract class Character {
     
    private Polygon character;
    private Point2D movement;
    private Boolean alive;
    
    public Character(Polygon polygon,int x, int y){
        this.character = polygon;
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);
        this.alive = true;
        
        this.movement = new Point2D(0,0);
        
        
    }
    public Polygon getCharacter(){
        return character;
    }
    public Point2D getMovement(){
        return this.movement;
    }
    public boolean alive(){
        return this.alive;
    }
    public double getX(){
        return this.character.getTranslateX();
    }
     public double getY(){
        return this.character.getTranslateY();
    }
    
    public void setDead(){
        this.alive= false;
    }
    public void setMovement(Point2D move){
        this.movement= move;
    }
    
    public void turnLeft(){
        this.character.setRotate(this.character.getRotate() - 3 );
    }
    public void turnRight(){
        this.character.setRotate(this.character.getRotate() + 3 );
    }
    public void move(){
        this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());
        this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());
        
        if (this.character.getTranslateX() < 0) {
        this.character.setTranslateX(this.character.getTranslateX() + Main.WIDTH);
    }

    if (this.character.getTranslateX() > Main.WIDTH) {
        this.character.setTranslateX(this.character.getTranslateX() % Main.WIDTH);
    }

    if (this.character.getTranslateY() < 0) {
        this.character.setTranslateY(this.character.getTranslateY() + Main.HEIGHT);
    }

    if (this.character.getTranslateY() > Main.HEIGHT) {
        this.character.setTranslateY(this.character.getTranslateY() % Main.HEIGHT);
    }
    }
    public void accelerate() {
        double changeX = Math.cos(Math.toRadians(this.character.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.character.getRotate()));
        
        changeX *= 0.05;
        changeY *= 0.05;

        this.movement = this.movement.add(changeX, changeY);
    }
    
    public boolean collide(Character other){
        Shape collisionArea = Shape.intersect(this.character, other.getCharacter());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }
    
}

    

