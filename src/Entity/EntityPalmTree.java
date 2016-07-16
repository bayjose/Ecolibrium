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
import Graphics.Display;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Bailey
 */
public class EntityPalmTree extends Entity{

    private Mesh mesh;

    private int maxTicks = 120;
    private int ticks = (int)(Math.random() * maxTicks);
        
    private final float wiggle = 8.0f/(60.0f * 4);
    
    public EntityPalmTree(int x, int y) {
        super(x, y, EnumEntity.PALM_TREE);
        //testing
        super.setAlwaysRender(true);
        
        this.mesh = new Mesh();
        
        Point[] p = new Point[]{
            new Point(0,0),
            new Point(0,-80),
            new Point(0,-150),
            new Point(0,-210),
            new Point(0,-260),
            new Point(0,-300),
            new Point(0,-330),
        };
        
        Link[] l = new Link[]{
            new Link(0,1),
            new Link(1,2),
            new Link(2,3),
            new Link(3,4),
            new Link(4,5),
            new Link(5,6),
        };
        
        Texture[] t = new Texture[]{
            new Texture(p[0], "entity/PalmTreeBase.png").scale(2.0f, 2.0f),
            new Texture(p[1], "entity/PalmTreeBase.png").scale(1.8f, 1.8f),
            new Texture(p[2], "entity/PalmTreeBase.png").scale(1.6f, 1.6f),
            new Texture(p[3], "entity/PalmTreeBase.png").scale(1.4f, 1.4f),
            new Texture(p[4], "entity/PalmTreeBase.png").scale(1.2f, 1.2f),
            new Texture(p[5], "entity/PalmTreeBase.png").scale(1.0f, 1.0f),
            new Texture(p[6], "entity/PalmTreeBase.png").scale(0.8f, 0.8f),
            new Texture(p[6], "entity/PalmTreeLeaf.png").scale(3.0f, 3.0f).offset(-80, 0).extraRotation(-35.0f),
            new Texture(p[6], "entity/PalmTreeLeaf.png").scale(3.0f, 3.0f).offset(-80, 0).extraRotation(35.0f),
            new Texture(p[6], "entity/PalmTreeLeaf.png").scale(3.0f, 3.0f).offset(-80, 0).extraRotation(145.0f),
            new Texture(p[6], "entity/PalmTreeLeaf.png").scale(3.0f, 3.0f).offset(-80, 0).extraRotation(215.0f),
        };
        
        this.mesh.setVerticies(p);
        this.mesh.setConnections(l);
        this.mesh.setTextures(t);
        
        float base = 18.0f;
        float angle = (float)(Math.random() * base)-(base/2);
        
        for(int i = 0; i < 6; i++){
            this.mesh.rotatePoint(i, angle);
        }

        
    }

    @Override
    public void tick() {
        if(this.ticks<this.maxTicks){
            if(this.ticks<(this.maxTicks/2)){
                for(int i = 0; i < 6; i++){
                    this.mesh.rotatePoint(i, this.wiggle);
                }
            }else{
                for(int i = 0; i < 6; i++){
                    this.mesh.rotatePoint(i, -this.wiggle);
                }
            }
            this.ticks++;
        }else{
            this.ticks = 0;
        }
    }

    @Override
    public void render(Graphics g) {
        this.mesh.render(this.getBounds().x, this.getBounds().y, g);
    }
    
}
