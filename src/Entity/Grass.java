/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Entity.Model.Link;
import Entity.Model.Mesh;
import Entity.Model.Point;
import Entity.Model.Texture;
import World.TileConstants;
import java.awt.Graphics;

/**
 *
 * @author Bailey
 */
public class Grass extends Entity{

    private Mesh mesh;
    private int maxTicks = 120;
    private int ticks = (int)(Math.random() * maxTicks);
        
    private final float wiggle = 15.0f/(60.0f * 4);
    
    public Grass(int x, int y) {
        super(x, y, EnumEntity.GRASS);
        
        this.mesh = new Mesh();
        
        Point[] p = new Point[]{
            new Point(0,0),
            new Point(8,0),
            new Point(16,0),
            new Point(24,0),
            new Point(32,0),
            new Point(40,0),
            new Point(48,0),
            new Point(56,0),
            new Point(64,0),
        };
        
        Link[] l = new Link[]{
//            new Link(0,1),
//            new Link(1,2),
//            new Link(2,3),
//            new Link(3,4),
//            new Link(4,5),
//            new Link(5,6),
//            new Link(6,7),
        };
        
        Texture[] t = new Texture[]{
            new Texture(p[0], new String[]{"entity/grass_0.png","entity/grass_1.png","entity/grass_2.png","entity/grass_3.png","entity/grass_4.png"}).scale(2.0f, 2.0f).offset(0, -32).extraRotation((float)(Math.random()*50)-25.0f),
            new Texture(p[1], new String[]{"entity/grass_0.png","entity/grass_1.png","entity/grass_2.png","entity/grass_3.png","entity/grass_4.png"}).scale(2.0f, 2.0f).offset(0, -32).extraRotation((float)(Math.random()*50)-25.0f),
            new Texture(p[2], new String[]{"entity/grass_0.png","entity/grass_1.png","entity/grass_2.png","entity/grass_3.png","entity/grass_4.png"}).scale(2.0f, 2.0f).offset(0, -32).extraRotation((float)(Math.random()*50)-25.0f),
            new Texture(p[3], new String[]{"entity/grass_0.png","entity/grass_1.png","entity/grass_2.png","entity/grass_3.png","entity/grass_4.png"}).scale(2.0f, 2.0f).offset(0, -32).extraRotation((float)(Math.random()*50)-25.0f),
            new Texture(p[4], new String[]{"entity/grass_0.png","entity/grass_1.png","entity/grass_2.png","entity/grass_3.png","entity/grass_4.png"}).scale(2.0f, 2.0f).offset(0, -32).extraRotation((float)(Math.random()*50)-25.0f),
            new Texture(p[5], new String[]{"entity/grass_0.png","entity/grass_1.png","entity/grass_2.png","entity/grass_3.png","entity/grass_4.png"}).scale(2.0f, 2.0f).offset(0, -32).extraRotation((float)(Math.random()*50)-25.0f),
            new Texture(p[6], new String[]{"entity/grass_0.png","entity/grass_1.png","entity/grass_2.png","entity/grass_3.png","entity/grass_4.png"}).scale(2.0f, 2.0f).offset(0, -32).extraRotation((float)(Math.random()*50)-25.0f),

        };
        
        this.mesh.setVerticies(p);
        this.mesh.setConnections(l);
        this.mesh.setTextures(t);
    }

    @Override
    public void tick() {
        if(this.ticks<this.maxTicks){
            if(this.ticks<(this.maxTicks/2)){
                for(int i = 0; i < 6; i++){
                    this.mesh.textures[i].addRotation(wiggle);
                }
            }else{
                for(int i = 0; i < 6; i++){
                    this.mesh.textures[i].addRotation(-wiggle);
                }
            }
            this.ticks++;
        }else{
            this.ticks = 0;
        }
    }

    @Override
    public void render(Graphics g) {
        this.mesh.render(this.getBounds().x, this.getBounds().y+TileConstants.size, g);
    }
    
}
