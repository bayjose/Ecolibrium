/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Core.Game;
import Core.Handler;
import Graphics.Display;
import Graphics.SpriteBinder;
import PhysicsEngine.Point2D;
import PhysicsEngine.PrebuiltBodies;
import PhysicsEngine.RigidBody;
import PhysicsEngine.RigidUtils;
import Utils.StringUtils;
import World.TileConstants;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Bailey
 */
public abstract class Entity {
    public int x;
    public int y;
    public int width = TileConstants.size;
    public int height = TileConstants.size;
    
    public int renderOffsetX = 0;
    public int renderOffsetY = 0;
    public int renderWidth = width;
    public int renderHeight = height;
    
    private String id=StringUtils.genRandomExtension(8);
    public Display display = new Display(1, 1, 1, 1, Color.BLACK);
    
    private String channel="entities";
    
    private boolean alwaysRender = false;
    
    private EnumEntity type;
    
    public Entity(int x, int y, EnumEntity type){
        this.x = x;
        this.y = y;
        this.type = type;
    }
    
    public String getID(){
        return this.id;
    }
    
    public void setID(String id){
        this.id = id;
    }
    
    public EnumEntity getType(){
        return this.type;
    }
    
    public boolean shouldAlwaysRender(){
        return this.alwaysRender;
    }
    
    public void setAlwaysRender(boolean b){
        this.alwaysRender = b;
    }
    
    public abstract void tick();
    public abstract void render(Graphics g);
    
    public void tickInSpace(int x, int y){
        return;
    }
    
    public void onAdd(){
        return;
    };
    
    public void onRemove(){
        return;
    };
    
    public void onClick(Rectangle rect){
        return;
    }

    public void allocateGraphics(String name){
        this.display = SpriteBinder.loadSprite(name);
        this.display.width = this.width;
        this.display.height = this.height;
    }
    
    public Entity setGraphics(String name){
        this.display = SpriteBinder.loadSprite(name);
        this.display.width = this.width;
        this.display.height = this.height;
        return this;
    }
    
    public Rectangle getBounds(){
        return new Rectangle(this.x-(this.display.width)-(Handler.cam.x), this.y-(this.display.height)-(Handler.cam.y), this.width, this.height);
    }
    
    public Rectangle getRenderBox(){
        return new Rectangle((this.x-(this.display.width)-(Handler.cam.x))+this.renderOffsetX,
                             (this.y-(this.display.height)-(Handler.cam.y))+this.renderOffsetY, this.renderWidth, this.renderHeight);
    }
}
