/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Core.Game;
import Core.Input.Keyboard;
import Utils.StringUtils;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Bailey
 */
public class EntityManager {
    private static HashMap<String, Entity> entities = new HashMap<String, Entity>();
    private static LinkedList<Entity> toAdd = new LinkedList<Entity>();
    private static LinkedList<String> toRemove = new LinkedList<String>();
    
    public static void addEntity(Entity entity){
        entity.onAdd();
        entity.setID(StringUtils.genRandomExtension(8));
        toAdd.add(entity);
    }
    
    public static void removeEntity(String id){
        if(entities.containsKey(id)){
            entities.get(id).onRemove();
            entities.put(id, null);
            toRemove.add(id);
        }
    }
    
    public static int size(){
        return entities.size();
    }
    
    private static void cleanUp(){
        //To see how many entities were removed this render call
        for(int i = 0; i < toRemove.size(); i++){
            if(entities.containsKey(toRemove.get(i))){
                entities.remove(toRemove.get(i));
            }
        }
        toRemove.clear();
    }
    
    private static void Synch(){
        if(toAdd.size() == 0){
            return;
        }
        for(int i = 0; i < toAdd.size(); i++){
            entities.put(toAdd.get(i).getID(), toAdd.get(i));
        }
        toAdd.clear();
    }
    
    public static void tickAllEntities(){
        for(Entity entity: EntityManager.entities.values()){
//            if(entity.getBounds().intersects(Game.getScreen())){
                entity.tick();
//            }
        }
    }
    
    public static void clickAllEntities(Rectangle rect){
        for(Entity entity: EntityManager.entities.values()){
            if(entity.getBounds().intersects(Game.getScreen())){
                entity.onClick(rect);
            }
        }
    }
    
    public static void renderAllEntities(Graphics g){
        Synch();
        boolean flag = false;
        for(Entity entity: EntityManager.entities.values()){
            if(entity!=null){
                if(entity.getBounds().intersects(Game.getScreen()) || entity.shouldAlwaysRender()){
                    entity.render(g);
                }
                if(Keyboard.bool_2){
                    g.setColor(Color.ORANGE);
                    g.drawRect(entity.getBounds().x, entity.getBounds().y, entity.getBounds().width, entity.getBounds().height);
                }
            }else{
                flag = true;
            }
        }
        if(flag){
            cleanUp();
        }
    }
    
    public static boolean intersects(Rectangle rect){
        for(Entity entity: EntityManager.entities.values()){
            if(entity.getBounds().intersects(Game.getScreen())){
                if(entity.getBounds().intersects(rect)){
                    return true;
                }
            }
        }
        return false;
    }
    
}
