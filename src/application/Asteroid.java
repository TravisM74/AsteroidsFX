/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.util.Random;

/**
 *
 * @author Travis
 */
public class Asteroid extends Character{
    private int size;
    private double rotation;
    
    public Asteroid(int x,int y, int size){
        super(new PolygonFactory().createPolygon(size),x,y);
        this.size = size;
        Random random = new Random();
        
        super.getCharacter().setRotate(random.nextInt(360));
        
        int acceleration = 1 + random.nextInt(10);
        for(int i = 0; i < acceleration;i++){
            accelerate();
        }
        this.rotation = 0.5 - random.nextDouble();
    }
    
    public int getSize(){
        return this.size;
    }
    public void setSize(int size){
        this.size = size;
    }
    
    @Override
    public void move(){
        super.move();
        super.getCharacter().setRotate(super.getCharacter().getRotate() + rotation);
    }
    
}
