/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics.Gui;

import Core.Game;
import Core.Input.Keyboard;
import Graphics.Display;
import Graphics.SpriteBinder;
import Item.Item;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Bailey
 */
public class HUD extends Gui{

    private Display Food;
    private Display Water;
    private Display Environment;
    
    private Container slots;
    
    public HUD() {
        super(0, 0, Game.WIDTH, Game.HEIGHT);
        Food = SpriteBinder.loadSprite("bar.png"); 
        Water = SpriteBinder.loadSprite("bar.png");
        Water.width = (8*4);
        Water.height = (146*4);
        Environment = SpriteBinder.loadSprite("heightMap.png");
        
        int slotsX = 32;
        int slotsY = 32;
        
        this.slots = new Container(slotsX, slotsY, 1, 6);
        this.slots.slots[0].setItem(Item.wire);
        this.slots.slots[0].setCount(1000);
        this.slots.slots[2].setItem(Item.battery);
        this.slots.slots[2].setCount(100);
        this.slots.slots[3].setItem(Item.button);
        this.slots.slots[3].setCount(100);
        this.slots.slots[4].setItem(Item.stick);
        this.slots.slots[4].setCount(100);
        this.slots.slots[5].setItem(Item.transciever);
        this.slots.slots[5].setCount(100);
        
        this.slots.slots[1].setItem(Item.chest);
        this.slots.slots[1].setCount(12);
        
    }

    @Override
    public void tick() {
        Food.update();
        Water.update();
        Environment.update();
        
        this.slots.tick();
    }

    @Override
    public void render(Graphics g) {
//        this.Food.render(0, 0, g);
//        this.Water.render(8, 480, g);
//        this.Environment.render(0, 0, g);
        
//        g.setColor(Color.green);
        
        if(!Keyboard.bool_0){
            this.slots.render(g);
        }
    }

    public Container getContainer(){
        return this.slots;
    }
    
    @Override
    public boolean onClick(Rectangle rect) {
        boolean toReturn = false;
        toReturn = toReturn||this.slots.onClick(rect);
        return toReturn;
    }
    
}
