/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics.Gui;

import Core.Input.Keyboard;
import Item.Item;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.HashMap;

/**
 *
 * @author Bailey
 */
public class GuiManager {
    
    private static HUD hud;
    private static Gui mainGui = null;
    private static boolean toClose = false;
    private static HashMap<String, Gui> guis = new HashMap<String, Gui>();
    
    public GuiManager(){
        this.hud = new HUD();
    }
    
    private boolean realease = true;
    
    public static void addItem(Item item){
        if(hud != null){
            hud.getContainer().slots[hud.getContainer().slots.length-1].setItem(item);
        }
    }
    
    public static HUD getHud(){
        return hud;
    }
    
    public void tick(){
        if(Keyboard.ESC){
            this.close();
        }
        if(Keyboard.M){
            if(this.realease){
                this.openByName("map");
                this.realease = false;
            }
        }else{
            this.realease = true;
        }
        
        
        //tick stuff
        this.hud.tick();
        if(this.mainGui!=null){
            this.mainGui.tick();
        }
    }
    
    public static void add(String name, Gui gui){
        GuiManager.guis.put(name, gui);
    }
    
    public void openByName(String name){
        if(this.mainGui==null){
            if(guis.containsKey(name)){
                this.mainGui = guis.get(name);
                mainGui.onOpen();
            }
        }else{
            if(this.mainGui.equals(guis.get(name))){
                this.close();
            }
        }
    }
    
    public static void openThis(Gui gui){
        if(mainGui!=null){
            if(mainGui.equals(gui)){
                if(mainGui.canBeOveridden){
                    close();
                }
            }else{
                mainGui = gui;    
            }
        }else{
            mainGui = gui;
        }
        mainGui.onOpen();
    }
    
    
    public static void close(){
        if(mainGui!=null){
            if(toClose==false){
                toClose = true;
            }
        }
        
    }
    
    public void render(Graphics g){
        this.hud.render(g);
        if(this.toClose){
            this.mainGui.onClose();
            this.mainGui = null;
            this.toClose = false;
        }
        if(this.mainGui!=null){
            this.mainGui.render(g);
        }
    }
    
    public boolean onClick(Rectangle rect){
        boolean toReturn = false;
        if(mainGui!=null){
            toReturn = toReturn||mainGui.onClick(rect);
        }
        toReturn = toReturn||hud.onClick(rect);
        return toReturn;
    }
    
    public static Gui getMain(){
        return mainGui;
    }
}
