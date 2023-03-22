/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;


import javafx.scene.shape.Polygon;

/**
 *
 * @author Travis
 */
public class Ship extends Character {
    
    public Ship(int x, int y){
        super (new Polygon(-5,-5,10,0,-5,5),x,y);
      
    }
    
    
}
