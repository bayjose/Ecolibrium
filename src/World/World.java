/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package World;

import Entity.EntityManager;
import Core.Game;
import Core.Handler;
import Core.Input.Keyboard;
import Entity.EntityPalmTree;
import Entity.Grass;
import Entity.ItemEntity;
import Graphics.ColorPixel;
import Graphics.Display;
import Graphics.Pixel;
import Graphics.PixelUtils;
import Graphics.SpriteBinder;
import Utils.DistanceCalculator;
import Utils.StringUtils;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/**
 *
 * @author Bailey
 */
public class World {
    public String seed;
    public final int width;
    public final int height;
    
    private Display draw;
    public final Display heightMap;
    private String[] tileIndex;
    
    //change the world
    int range = 100;
    int islandHeight = 200;
    int islandOffset = 15;
    int totalRange = (range+islandHeight);
    int subDivisions = 5;
    int waterHeight = (int)((0.5)*255);
    int[] heights;
    
    //tiles in the world
    Tile[][] tiles;
    
    final boolean heightTest = false;
        
    public World(String seed){
        this.seed = seed;
        
        int scale = 1;
        int pw = Game.WIDTH/(6*scale);
        int ph = Game.HEIGHT/(6*scale);
        this.width = pw;
        this.height = ph;
        tiles = new Tile[this.width][this.height];
        
        islandHeight += ph/2;
        
        final int subDivisionsX = subDivisions*3;
        final int subDivisionsY = subDivisions*2;
        Pixel[] pixels = new Pixel[pw *ph];
        this.heights = new int[pw *ph];
        this.heightMap = SpriteBinder.loadSprite("heightMap_3.png");
//        this.tileIndex = StringUtils.loadData("tileMap.txt");
        System.out.println("Height map loaded");
        //initialization
        for(int j = 0; j< ph; j++){
            for(int i = 0; i< pw; i++){
                pixels[i+(j * pw)] = new ColorPixel(0).setRGBA(0, 0, 0, 255);
                heights[i+(j+pw)] = 0;
            }
        }
        System.out.println("Tiles initialized");
        float maxHeight = 0;
        if(!heightTest){
            //gen nodes
            for(int j = 0; j<subDivisionsY; j++){
                for(int i = 0; i<subDivisionsX; i++){
                    float value = this.randomPoint(range, i, j);
                    int color = (int)((value / range) * 255);
                    if(((i * (pw/subDivisionsX)) +((j * (ph/subDivisionsY)) * pw))<(pw*ph)){
                        pixels[((i * (pw/subDivisionsX)) +((j * (ph/subDivisionsY)) * pw))] = new ColorPixel(0).setRGBA(color, color, color, 255);
                    }
                }
            }
            //linear interpolation
            //gen nodes
            for(int j = 0; j<subDivisionsY; j++){
                for(int i = 0; i<subDivisionsX; i++){
                    if(((i * (pw/subDivisionsX)) +((j * (ph/subDivisionsY)) * pw))<(pw*ph)){
                        //Next step
                        int pt_00 = this.randomPoint(range, i, j);
                        int pt_01 = this.randomPoint(range, i, j+1);
                        int pt_10 = this.randomPoint(range, i+1, j);
                        int pt_11 = this.randomPoint(range, i+1, j+1);

                        for(float y = 0; y<(ph/subDivisionsY); y++){
                            for(float x = 0; x<(pw/subDivisionsX); x++){
                                if((i+(int)x)<pw){
                                    if((j+(int)y)<ph){
                                        if((((i * (pw/subDivisionsX))+(int)x) +(((j * (ph/subDivisionsY))+(int)y) * pw))<(pw*ph)){
                                            float stepX = (x/(pw/subDivisionsX)); 
                                            float stepY = (y/(ph/subDivisionsY));
                                            float litX = linearInterpolate(pt_00, pt_01, stepY);
                                            float litY = linearInterpolate(pt_10, pt_11, stepY);
                                            int color = (int)linearInterpolate((litX/totalRange)*255, (litY/totalRange)*255, stepX);
                                            pixels[(((i * (pw/subDivisionsX))+(int)x) +(((j * (ph/subDivisionsY))+(int)y) * pw))] = new ColorPixel(0).setRGBA(color, color, color, 255);
                                            this.heights[(((i * (pw/subDivisionsX))+(int)x) +(((j * (ph/subDivisionsY))+(int)y) * pw))] = color;
                                            if(color>maxHeight){
                                                maxHeight = color;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Nodes have been placed");
        //curve to gen island
        for(int y = 0; y<this.height; y++){
            for(int x = 0; x<this.width; x++){
                this.heights[x +(y * this.width)] += linearDecrease(x, y);
                if(this.heights[x +(y * this.width)]<0){
                    this.heights[x +(y * this.width)] = 0;
                }
                if(this.heights[x +(y * this.width)]>maxHeight){
                    maxHeight = this.heights[x +(y * this.width)];
                }
            }   
        }
        System.out.println("Curve added to island");
        //put heightData into the island
        for(int y = 0; y<this.height; y++){
            for(int x = 0; x<this.width; x++){
                int color = (int)((this.heights[x +(y * this.width)] / maxHeight) * 255);
                pixels[x +(y * this.width)]= new ColorPixel(0).setRGBA(color, color, color, 255);
            }   
        }
        System.out.println("Heights written to height buffer");
        //Add The color data based off the height map
        for(int j = 0; j< ph; j++){
            for(int i = 0; i< pw; i++){
//                System.out.println("i:"+i+"/"+pw+" j:"+j+"/"+ph);
                float progress = PixelUtils.getRGBA(pixels[i+(j * pw)].getColor())[0]/255.0f;
                int color = heightMap.getPixelColor((int)Math.ceil(this.linearInterpolate(heightMap.width, 0, progress)), 0);
                if(color == 0){
                    pixels[i +(j * this.width)].setColor(heightMap.getPixelColor(heightMap.width-1, 0));
                }else{
                    pixels[i +(j * this.width)].setColor(color);
                }
                tiles[i][j] = new Tile(i*TileConstants.size,j*TileConstants.size,  pixels[i +(j * this.width)].getColor());
                tiles[i][j].setHeight(progress);
                if(progress == 1){
                    Handler.player.x = i * TileConstants.size;
                    Handler.player.y = j * TileConstants.size;
                    Handler.cam.x = Handler.player.x -Game.WIDTH/2;
                    Handler.cam.y = Handler.player.y -Game.HEIGHT/2;
                }
                if(progress>0.5f){
                    if(Math.random()>0.99){
                        EntityManager.addEntity(new EntityPalmTree(i * TileConstants.size, j * TileConstants.size));
                    }
                }
                if(progress>0.8f){
                    if(Math.random()>0.85){
                        EntityManager.addEntity(new Grass(i * TileConstants.size+(int)(Math.random()*TileConstants.size), j * TileConstants.size+(int)(Math.random()*TileConstants.size)));
                    }
                }
                
            }
        }
        System.out.println("Pixel Data written to Tile Data");
        //refine edges in the actual world by lerping the tile heights
        float lastPercent =0;
        System.out.println("Refining Tiles");
        for(int j = 0; j< ph; j++){
            for(int i = 0; i< pw; i++){
                //Next step
                int pt_00 = 0;
                if((i)>=0){
                    if((j)>=0){
                        pt_00 = (int)((tiles[i][j].getHeight()) * 255);
                    }
                }
                int pt_01 = 0;
                if((i)>=0){
                    if((j+1)<this.height){
                        pt_01 = (int)((tiles[i][j+1].getHeight()) * 255);
                    }
                }
                int pt_10 = 0;
                if((i+1)<this.width){
                    if((j)>=0){
                        pt_10 = (int)((tiles[i+1][j].getHeight()) * 255);
                    }
                }
                int pt_11 = 0;
                if((i+1)<this.width){
                    if((j+1)<this.height){
                        pt_11 = (int)((tiles[i+1][j+1].getHeight()) * 255);
                    }
                }
                for(float y = 0; y < TileConstants.tileLOD; y++){
                    for(float x = 0; x < TileConstants.tileLOD; x++){
                        float stepX = (x/(TileConstants.tileLOD)); 
                        float stepY = (y/(TileConstants.tileLOD));
                        float litX = linearInterpolate(pt_00, pt_01, stepY);
                        float litY = linearInterpolate(pt_10, pt_11, stepY);
//                        int color = (int)linearInterpolate((litX), (litY), stepX);
                        int color = heightMap.getPixelColor((int)Math.ceil(this.linearInterpolate(heightMap.width, 0, (linearInterpolate((litX), (litY), stepX)/255))) , 0);
                        if(color == 0){
                            tiles[i][j].setPixel((int)x, (int)y, new Color(heightMap.getPixelColor(heightMap.width-1, 0)));
                        }else{
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
                            
                            tiles[i][j].setPixel((int)x, (int)y, new Color(PixelUtils.makeColor(rgb)));
                        }
                        //acutaly write the tile pixel height data
                        tiles[i][j].setHeight((int)x, (int)y, (linearInterpolate((litX), (litY), stepX)/255));
                    }
                }
                tiles[i][j].update();
                if((int)(lastPercent*100) != (int)(((j + 1.0f)/(ph + 1.0f))*100)){
                    System.out.println((int)((lastPercent*100)+1)+"%");
                }
                lastPercent = ((j + 1.0f)/(ph + 1.0f));
            }
        }
        System.out.println("100%");
        this.draw = new Display(Game.WIDTH, Game.HEIGHT, pw, ph, pixels);
    }
    
    public int randomPoint(int range, int x, int y){
        return new Random(("seed:"+this.seed+",x:"+x+",y:"+(y*15868000)).hashCode()).nextInt(range);
    }
    
    public float linearInterpolate(float start, float end, float progress){
        //the second - the first * .5 + the first
        return ((end-start)*progress)+start;
    }
    
    public float linearDecrease(int x, int y){
        return (1-(DistanceCalculator.CalculateDistanceF(x, y, this.width/2, this.height/2)/DistanceCalculator.CalculateDistanceF(islandOffset*3, islandOffset*2, this.width/2, this.height/2)))*this.islandHeight;
    }
    
    
    //Graphics
    public void renderMap(Graphics g){
        this.draw.render(Game.WIDTH/2, Game.HEIGHT/2, g);
    }
    
    public void intersectsTile(Rectangle par1){
        Rectangle rect = new Rectangle(par1.x+Handler.cam.x, par1.y+Handler.cam.y, par1.width, par1.height);
        for(int j = 0; j<(Game.HEIGHT/TileConstants.size)+2; j++){
            for(int i = 0; i<(Game.WIDTH/TileConstants.size)+2; i++){
                if((i+(int)Math.ceil(Handler.cam.x/TileConstants.size))<this.width&&(i+(int)Math.ceil(Handler.cam.x/TileConstants.size))>=0){
                    if((j+(int)Math.ceil(Handler.cam.y/TileConstants.size))<this.height&&(j+(int)Math.ceil(Handler.cam.y/TileConstants.size))>=0){ 
                        if(this.tiles[i+(int)Math.ceil(Handler.cam.x/TileConstants.size)][j+(int)Math.floor(Handler.cam.y/TileConstants.size)].Collides(rect)){
                            this.tiles[i+(int)Math.ceil(Handler.cam.x/TileConstants.size)][j+(int)Math.floor(Handler.cam.y/TileConstants.size)].shrink();
                            this.tiles[i+(int)Math.ceil(Handler.cam.x/TileConstants.size)][j+(int)Math.floor(Handler.cam.y/TileConstants.size)].pushHeightBufferToDisplay();
                        }
                    }
                }
            }
        }
    }
    
    public void renderTiles(Graphics g){
        g.setColor(Color.CYAN);
        for(int j = 0; j<(Game.HEIGHT/TileConstants.size)+2; j++){
            for(int i = 0; i<(Game.WIDTH/TileConstants.size)+2; i++){
                if((i+(int)Math.ceil(Handler.cam.x/TileConstants.size))<this.width&&(i+(int)Math.ceil(Handler.cam.x/TileConstants.size))>=0){
                    if((j+(int)Math.ceil(Handler.cam.y/TileConstants.size))<this.height&&(j+(int)Math.ceil(Handler.cam.y/TileConstants.size))>=0){ 
                        Tile temp = this.tiles[i+(int)Math.ceil(Handler.cam.x/TileConstants.size)][j+(int)Math.floor(Handler.cam.y/TileConstants.size)];
                        temp.render(g);
//                        for(int k = 0; k < TileConstants.tileLOD; k++){
//                            for(int l = 0; l < TileConstants.tileLOD; l++){
//                                if(temp.getHeight(k, l)<0.5f){
//                                    g.fillRect(temp.getX()+(k * TileConstants.ppp)-(TileConstants.size/2), temp.getY()+(l * TileConstants.ppp)-(TileConstants.size/2), TileConstants.ppp, TileConstants.ppp);
//                                }
//                            }
//                        }
                    }
                }
            }
        }
    }
}
