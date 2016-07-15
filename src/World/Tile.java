/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package World;

import Core.Handler;
import Core.Input.Keyboard;
import Graphics.ColorPixel;
import Graphics.Display;
import Graphics.Pixel;
import Graphics.PixelUtils;
import Graphics.SpriteBinder;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/**
 *
 * @author Bailey
 */
public class Tile {
    private Display display;
    private int x;
    private int y;
    private float height = 0;
    
    private float[] heights;
    
    public Tile(int x, int y, int color){
        this.x = x;
        this.y = y;
        
        final int noise = 15;
        
        Pixel[] pixels = new Pixel[ TileConstants.tileLOD *  TileConstants.tileLOD];
        for(int i = 0; i<pixels.length; i++){
            Random r = new Random(i*(x+(y*65536)));
            int[] rgb = PixelUtils.getRGBA(color);
            int offset = r.nextInt(noise) - (int)Math.floor(noise/2);
            rgb[0] = rgb[0] + offset;
            rgb[1] = rgb[1] + offset;
            rgb[2] = rgb[2] + offset;
            
            for(int j = 0; j<rgb.length; j++){
                if(rgb[j]>255){
                   rgb[j] = 255; 
                }
                if(rgb[j]<0){
                   rgb[j] = 0; 
                }
            }
            
            pixels[i] = new ColorPixel(PixelUtils.makeColor(rgb));            
        }
        
        this.display = new Display(0, 0, TileConstants.tileLOD, TileConstants.tileLOD, pixels);
        this.display.width = TileConstants.size;
        this.display.height = TileConstants.size;
        
        
        this.heights = new float[TileConstants.tileLOD*TileConstants.tileLOD];
        for(int i = 0; i < this.heights.length; i++){
            this.heights[i] = 0;
        }
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
    public void shrink(){
        for(int j = 0; j < TileConstants.tileLOD; j++){
            for(int i = 0; i < TileConstants.tileLOD; i++){
                if(this.heights[i+(j * TileConstants.tileLOD)]>0){
                    this.heights[i+(j * TileConstants.tileLOD)] -= 0.01f;
                }
            }
        }
    }
    
    public void pushHeightBufferToDisplay(){
        Display heightMap = Handler.world.heightMap;
        for(int j = 0; j < TileConstants.tileLOD; j++){
            for(int i = 0; i < TileConstants.tileLOD; i++){
                int color = heightMap.getPixelColor((int)Math.ceil(this.linearInterpolate(heightMap.width, 0, this.heights[i+(j * TileConstants.tileLOD)])), 0);
                
                int noise = 15;
                Random r = new Random();
                int[] rgb = PixelUtils.getRGBA(color);
                int offset = r.nextInt(noise) - (int) Math.floor(noise / 2);
                rgb[0] = rgb[0] + offset;
                rgb[1] = rgb[1] + offset;
                rgb[2] = rgb[2] + offset;

                for (int l = 0; l < rgb.length; l++) {
                    if (rgb[l] > 255) {
                        rgb[l] = 255;
                    }
                    if (rgb[l] < 0) {
                        rgb[l] = 0;
                    }
                }
                this.setPixel(i, j, new Color(PixelUtils.makeColor(rgb)));
            }
        }
        this.update();
    }
    
    public Tile setHeight(float height){
        this.height = height;
        return this;
    }
    
    public void setHeight(int x, int y, float data){
         this.heights[x+(y * TileConstants.tileLOD)] = data;
    }
    
    public float getHeight(){
        return this.height;
    }
    
    public float getHeight(int x, int y){
        return this.heights[x+(y * TileConstants.tileLOD)];
    }
    
    public void setPixel(int x, int y, Color color){
        this.display.setPixelColor(x, y, PixelUtils.makeColor(new int[]{color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()}));
    }
    
    public void update(){
        this.display.update();
    }
    
    public void tick(){
        
    }
    
    public boolean Collides(Rectangle rect){
        if(rect.intersects(new Rectangle(x-(TileConstants.size/2), y-(TileConstants.size/2), TileConstants.size, TileConstants.size))){
            return true;
        }
        return false;
    }
    
    public void render(Graphics g){
        display.render(x, y, g);
        if(Keyboard.bool_1){
            g.setColor(Color.blue);
            g.drawRect(x-(TileConstants.size/2), y-(TileConstants.size/2), TileConstants.size, TileConstants.size);
            g.drawString(""+this.height, x-(TileConstants.size/2), y);
        }

    }
    
    public float linearInterpolate(float start, float end, float progress){
        //the second - the first * .5 + the first
        return ((end-start)*progress)+start;
    }
}
