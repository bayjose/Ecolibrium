/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics.Gui;

import Entity.EntityCircuit;
import Item.Item;
import Item.ItemCircuit;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Bailey
 */
public class Container {
    public Slot[] slots;
    int x;
    int y;
    int width;
    int height;
    
    private EnumSlotType type = EnumSlotType.BASIC;
    
    public Container(int x, int y, int width, int height){
        this.slots = new Slot[width*height];
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        for(int j = 0; j< this.height; j++){
            for(int i = 0; i< this.width; i++){
                this.slots[i+(j * this.width)] = new BasicSlot(x+(i*((112)+(2*6))), y+(j*((112)+(2*6))));
            }
        }
    }
    
    public Container(int x, int y, int width, int height, EnumSlotType type){
        this.slots = new Slot[width*height];
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        this.type = type;
        
        if(type.equals(EnumSlotType.BASIC)){
            for(int j = 0; j< this.height; j++){
                for(int i = 0; i< this.width; i++){
                    this.slots[i+(j * this.width)] = new BasicSlot(x+(i*((112)+(2*6))), y+(j*((112)+(2*6))));
                }
            }
        }
    }
    
    public void putInSlot(int x, int y, Item item){
        if(x>=0 && x < this.width){
            if(y>=0 && y < this.height){
                this.slots[x+(y * this.width)].canPutItem(item);
            }
        }
    }
    
    public int getWidth(){
        return this.width;
    }
    
    public int getHeight(){
        return this.height;
    }
    
    public void render(Graphics g){
        for(int i = 0; i< this.slots.length; i++){
            this.slots[i].render(g);
        }
    }
    
    public void tick(){
        for(int i = 0; i< this.slots.length; i++){
            this.slots[i].tick();
        }
    }
    
    public boolean collides(Rectangle rect){
        if(rect.intersects(new Rectangle(this.x, this.y, this.width*(64), this.height*(64)))){
            return true;
        }
        return false;
    }
    
    public boolean onClick(Rectangle rect){
        boolean toReturn = false;
        for(int j = 0; j < this.height; j++){
            for(int i = 0; i< this.width; i++){
                toReturn = toReturn || this.slots[i+(j * this.width)].onClick(rect);
            }
        }
        
        return toReturn;
    }
}
