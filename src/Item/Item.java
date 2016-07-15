/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import static Core.Handler.cam;
import Entity.EntityManager;
import Entity.EnumEntity;
import Entity.ItemEntity;
import Graphics.Display;
import Graphics.SpriteBinder;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Bailey
 */
public abstract class Item {
    
    //Item Definitions
    public static Item shell = new ItemShell("shell.png");
    public static Item stick = new BasicItem("stick", "bar.png");
    public static Item chest = new ItemChest();
    public static Item test = new ItemShell("heightMap.png");
    public static Item wire = new ItemWire(EnumEntity.WIRE);
    public static Item circuit_board_empty = new ItemCircuitBoard();
    public static Item battery = new ItemBattery();
    public static Item button = new ItemCircuitComponent("Button", EnumEntity.BUTTON, "entity/button_core.png");
    public static Item speaker = new ItemCircuitComponent("Speaker", EnumEntity.SPEAKER, "entity/speaker.png");
    public static Item transciever = new ItemCircuitComponent("Transciever", EnumEntity.RADIO_TRANSCIEVER, "entity/transciever_core.png");
    
    //Item
    protected String name;
    protected Display display;
    protected float weight = 1.0f;
    
    public Item(String name, String image){
        this.display = SpriteBinder.loadSprite(image);
        this.display.width = 32;
        this.display.height = 32;
        this.name = name;
    }
    
    public abstract void tick();
    public abstract void render(int x, int y, Graphics g);
    public abstract void onInteract(Rectangle rect);
    
    public int onClick(Rectangle rect, int quantity){
        EntityManager.addEntity(new ItemEntity(cam.x+rect.x, cam.y+rect.y, this));
        return quantity-1;
    };
    
    public Display getDisplay(){
        return this.display;
    }
    
    public String getName(){
        return this.name;
    }
   
}
