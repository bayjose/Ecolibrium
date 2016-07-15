/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Core.Game;
import Core.Handler;
import Graphics.Display;
import Graphics.Gui.GuiManager;
import Item.Item;
import Utils.DistanceCalculator;
import World.TileConstants;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Bailey
 */
public class ItemEntity extends Entity{

    private Item item;
    private int count = 1;
    private int rotation = 0;
    
    public ItemEntity(int x, int y, Item item) {
        super(x, y, EnumEntity.ITEM);
        this.width = 64;
        this.height = 64;
        this.x+=this.width/2;
        this.y+=this.height/2;
        this.item = item;
    }

    @Override
    public void onAdd() {
        this.display = new Display(this.width, this.height, this.item.getDisplay().pWidth, this.item.getDisplay().pHeight, this.item.getDisplay().pixels);    
        this.display.width = this.width;
        this.display.height = this.height;
        this.rotation = (int)(360 * Math.random());
    }

    @Override
    public void onRemove() {
        
    }
    
    @Override
    public void onClick(Rectangle rect) {
        if(rect.intersects(this.getBounds())){
            this.item.onInteract(rect);
        }
    }

    @Override
    public void tick() {
        this.item.tick();
        if(DistanceCalculator.CalculateDistance(Handler.cam.x+Game.WIDTH/2, Handler.cam.y+Game.HEIGHT/2, x, y)<=TileConstants.size*5){
            GuiManager.addItem(item);
            EntityManager.removeEntity(this.getID());
        }
    }

    @Override
    public void render(Graphics g) {
        this.display.render(this.x+(-Handler.cam.x)-(this.display.width/2), this.y+(-Handler.cam.y)-(this.display.height/2), g, rotation);
    }
    
}
