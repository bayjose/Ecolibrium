/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import Camera.Camera;
import Core.Input.Keyboard;
import Entity.EntityCircuitboard;
import Entity.EntityManager;
import Entity.EntityPalmTree;
import Entity.Grass;
import Entity.Model.Model;
import Entity.Player;
import Graphics.Gui.GuiManager;
import Graphics.Gui.GuiWorldMap;
import Graphics.Gui.SpitesheetMaker;
import Item.MouseItem;
import World.World;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Bailey
 */
public class Handler {
    
    public static Camera cam = new Camera(0, 0, 0);
    public static World world;
    private static GuiManager guiManager;
    public static Player player;
    
    public boolean debounce = false;
    
    private final int camSpeed = 4;
    int x = 0;
    int y = 0;
    
    
    public void init(){
        player =new Player(Game.WIDTH/2, Game.HEIGHT/2);
        this.world = new World(""+Math.random());
        this.guiManager = new GuiManager();
        GuiManager.add("map", new GuiWorldMap());
        EntityManager.addEntity(player);
        EntityManager.addEntity(new EntityCircuitboard(cam.x+(Game.WIDTH/2), cam.y+(Game.HEIGHT/2)));
    }
    
    public void tick(){
        this.guiManager.tick();
        EntityManager.tickAllEntities();
        MouseItem.tick();
        
        if(Keyboard.Q){
            if(debounce == false){
                EntityManager.addEntity(new Grass(cam.x+(Game.WIDTH/2), cam.y+(Game.HEIGHT/2)));
                debounce = true;
            }
        }else{
            debounce = false;
        }
        if(Keyboard.E){
            EntityManager.addEntity(new EntityCircuitboard(cam.x+(Game.WIDTH/2), cam.y+(Game.HEIGHT/2)));
            
//            this.world = new World(""+Math.random());
        }
        
//          int speed = 4;
//        if(Keyboard.SHIFT){
//            speed = 26;
//        }
//        
        if(Keyboard.T){
            GuiManager.openThis(new SpitesheetMaker("Fonts/main/font.png"));
            System.out.println("Here");
        }
//        if(Keyboard.A){
//            this.x-=speed;
//        }
//        if(Keyboard.S){
//            this.y+=speed;
//        }
//        if(Keyboard.D){
//            this.x+=speed;
//        }
//        
//        Handler.cam.x = x;
//        Handler.cam.y = y;
    }
    
    public void render(Graphics g){
        //Game rendering
        Graphics2D g2d = (Graphics2D)g;
        g.translate(Game.WIDTH/2, Game.HEIGHT/2);
            g.translate(-cam.x - Game.WIDTH/2, -cam.y - Game.HEIGHT/2);
                this.world.renderTiles(g);
            g.translate(cam.x + Game.WIDTH/2, cam.y + Game.HEIGHT/2);
        g.translate(-Game.WIDTH/2, -Game.HEIGHT/2);
        //Entity
        EntityManager.renderAllEntities(g);
//        g.setColor(Color.yellow);
//        g.drawRect(Game.getScreen().x, Game.getScreen().y, Game.getScreen().width, Game.getScreen().height);
        //Ui
        this.guiManager.render(g);
        MouseItem.render(g);
    }
    
    public static void onClick(Rectangle rect){
//                    world.intersectsTile(rect);
        if(guiManager.onClick(rect)){
            EntityManager.clickAllEntities(rect);
        }else{
            if(EntityManager.intersects(rect)){
                System.out.println("Intersection");
                EntityManager.clickAllEntities(rect);
            }else{
                MouseItem.onClick(rect);
            }
        }
    }
}
