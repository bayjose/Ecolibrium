/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics.Gui;

import Core.Handler;
import Core.Input.MouseInput;
import Core.Input.MousePositionLocator;
import Graphics.Display;
import Graphics.Pixel;
import Graphics.SpriteBinder;
import Graphics.font.FontBook;
import Item.Item;
import Item.MouseItem;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Bailey
 */
public abstract class Slot extends Gui{

    protected Item item = null;
    protected int quantity = 0;
    protected Display slot;
 
    private int pickupDisplay = 0;
    private final int maxPickupDelay  = 30;
    
    public Slot(int x, int y) {
        super(x, y, 112, 112);
        this.slot = SpriteBinder.loadSprite("gui/slot.png");
        this.slot.width = this.bounds.width;
        this.slot.height = this.bounds.height;
    }

    @Override
    public void tick() {
        if(this.item!=null){
            this.item.tick();
        }
    }
    
    public int getCount(){
        return this.quantity;
    }
    
    public Item getItem(){
        return this.item;
    }

    public Slot setCount(int i){
        this.quantity = i;
        return this;
    }
    
    public Slot setItem(Item i){
        this.item = i;
        return this;
    }
    
    public boolean canPutItem(Item i){
        return true;
    }
    
    public void onPlace(){
        return;
    }
    
    public void onRemove(){
        return;
    }
    
    
    @Override
    public void render(Graphics g) {
        this.slot.render(this.bounds.x+ this.bounds.width/2, this.bounds.y+ this.bounds.height/2, g);
        if(this.item!=null){
            Graphics2D g2d = (Graphics2D)g;
            float scale = 2.5f;
            g2d.scale(scale, scale);
                this.item.render((int)((this.bounds.x + this.bounds.width/2)/scale), (int)((this.bounds.y + this.bounds.height/2)/scale), g);
                g.setColor(Color.white);
                g.drawString(""+this.quantity, (int)((this.bounds.x + this.bounds.width/2)/scale), (int)((this.bounds.y + this.bounds.height/2)/scale));
            g2d.scale(1/scale, 1/scale);
            
            if(MousePositionLocator.MouseLocation.intersects(bounds)){
                FontBook.font.drawString(this.item.getName(), (int)((this.bounds.x)), (int)((this.bounds.y)), g);
            }
            
        }
        
    }

    @Override
    public boolean onClick(Rectangle rect) {
        if(rect.intersects(this.bounds)){
            if(!MouseInput.IsRightClick){
                if(this.item==null && MouseItem.item != null){
                    if(this.canPutItem(MouseItem.item)){
                        this.setItem(MouseItem.item);
                        this.onPlace();
                        this.quantity = MouseItem.count;
                        MouseItem.item = null;
                        MouseItem.count = 0;
                    }
                    return true;
                }
                if(this.item!=null && MouseItem.item == null){
                    MouseItem.item = this.item;
                    MouseItem.count = this.quantity;
                    this.onRemove();
                    this.item = null;
                    this.quantity = 0;
                    return true;
                }
                if(this.item == MouseItem.item){
                    if(MouseItem.item!=null){
                        this.quantity += MouseItem.count;
                        this.onPlace();
                        MouseItem.item = null;
                        MouseItem.count -= 1;
                        return true;
                    }
                }
            }else{
               if(this.item==null && MouseItem.item != null){
                    if(this.canPutItem(MouseItem.item)){
                        this.setItem(MouseItem.item);
                        this.onPlace();
                        this.quantity = 1;
                        MouseItem.count -= 1;
                        if(MouseItem.count<=0){
                            MouseItem.item = null;
                        }
                    }
                    return true;
                } 
                if(this.item == MouseItem.item){
                    if(MouseItem.item!=null){
                        this.quantity += 1;
                        this.onPlace();
                        MouseItem.count -= 1;
                        if(MouseItem.count<=0){
                            MouseItem.item = null;
                        }
                        return true;
                    }
                 } 
            }
        }
        return false;
    }
    
}
