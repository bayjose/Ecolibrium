/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Core.Game;
import Core.Handler;
import Core.Input.Keyboard;
import Graphics.SpriteSheet;
import World.TileConstants;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Bailey
 */
public class Player extends Entity{
    
    final int walkSpeed = 4;
    final int sprintSpeed = (int)(walkSpeed*2.6);
    
    SpriteSheet assets = new SpriteSheet("Character");

    public Player(int x, int y) {
        super(x, y, EnumEntity.PLAYER);
    }

    @Override
    public void onAdd() {
        this.allocateGraphics("shell.png");
        this.display.width = TileConstants.size;
        this.display.height = (int)(1.5*TileConstants.size);
    }

    @Override
    public void onRemove() {
        
    }

    @Override
    public void tick() {
        
        int speed = this.walkSpeed;
        if(Keyboard.SHIFT){
            speed = this.sprintSpeed;
        }
        

        Rectangle rect = this.getBounds();
        int deltaX = 0;
        int deltaY = 0;

        if(Keyboard.W){
            deltaY-=speed;
        }
        if(Keyboard.A){
            deltaX-=speed;
        }
        if(Keyboard.S){
            deltaY+=speed;
        }
        if(Keyboard.D){
            deltaX+=speed;
        }

        Handler.cam.x+=deltaX;
        Handler.cam.y+=deltaY;



        this.x = Handler.cam.x+Game.WIDTH/2;
        this.y = Handler.cam.y+Game.HEIGHT/2;
    }

    @Override
    public void render(Graphics g) {
        if(!Keyboard.bool_0){
            this.display.render(x-Handler.cam.x, y-Handler.cam.y, g);
        }
    }
    
}
