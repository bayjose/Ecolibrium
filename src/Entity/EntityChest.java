/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Core.Handler;
import Graphics.Display;
import Graphics.Gui.Gui;
import Graphics.Gui.GuiChest;
import Graphics.Gui.GuiManager;
import Graphics.SpriteBinder;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Bailey
 */
public class EntityChest extends Entity{

    GuiChest gui;
    Display open;
    
    public EntityChest(int x, int y) {
        super(x, y, EnumEntity.CHEST);
        this.display = SpriteBinder.loadSprite("chest.png");
        this.gui = new GuiChest();
        this.width = (24 * 6);
        this.height = (16 * 6);
        this.display.width = this.width;
        this.display.height = this.height;
        
        this.open = SpriteBinder.loadSprite("water.png");
        this.open.width = this.width;
        this.open.height = this.height;
    }

    @Override
    public void tick() {
        
    }

    @Override
    public void render(Graphics g) {
        if(GuiManager.getMain()!=null){
            if(GuiManager.getMain().equals(this.gui)){
                this.open.render(this.x+(-Handler.cam.x)-(this.display.width/2), this.y+(-Handler.cam.y)-(this.display.height/2), g);
            }else{
                this.display.render(this.x+(-Handler.cam.x)-(this.display.width/2), this.y+(-Handler.cam.y)-(this.display.height/2), g);
            }
        }else{
            this.display.render(this.x+(-Handler.cam.x)-(this.display.width/2), this.y+(-Handler.cam.y)-(this.display.height/2), g);
        }
    }
    
    @Override
    public void onClick(Rectangle rect) {
        if(rect.intersects(this.getBounds())){
            GuiManager.openThis(this.gui);
        }
    }
    
}
