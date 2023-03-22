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
public class Projectile extends Character{
    
    public Projectile(int x, int y){
        super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2),x,y);
    }
    
}
