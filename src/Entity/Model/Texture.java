/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.Model;

import Graphics.Display;
import Graphics.SpriteBinder;
import java.awt.Graphics;

/**
 *
 * @author Bayjose
 */
public class Texture {
    public Point pt;
    public Display display; 
    public int offsetX = 0;
    public int offsetY = 0;
    public float extraRotation = 0.0f;
    
    public Texture(Point pt, String imageID){
        this.pt = pt;
        this.display = SpriteBinder.loadSprite(imageID);
    }
    
    public Texture(Point pt, String[] randImage){
        this.pt = pt;
        int index = (int)Math.floor(Math.random()*randImage.length);
        this.display = SpriteBinder.loadSprite(randImage[index]);
    }
    
    public Texture(Point pt, Display image){
        this.pt = pt;
        this.display = image;
    }
    
    public void render(int x, int y, Graphics g){
        display.render((int)(pt.x + x), (int)(pt.y + y), g, (int)(pt.getTextureRotation()+this.extraRotation), offsetX, offsetY);
    }
    
    public Texture scale(float scaleX, float scaleY){
        this.display.width *= scaleX;
        this.display.height *= scaleY;
        return this;
    }
    
    public Texture offset(int offsetX, int offsetY){
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        return this;
    }
    
    public Texture extraRotation(float rot){
        this.extraRotation = rot;
        return this;
    }
    
    public void addRotation(float rot){
        this.extraRotation+=rot;
    }
}
