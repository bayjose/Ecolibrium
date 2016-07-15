/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.Model;

import Core.Input.Keyboard;
import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

/**
 *
 * @author Bailey
 */
public class Mesh {
    
    public Point[] verticies = new Point[]{};
    public Point[] initialVerticies = new Point[]{};
    public Link[] connections = new Link[]{};
    public Texture[] textures = new Texture[]{};
    
    public Mesh setVerticies(Point[] p){
        this.verticies = p;
        this.initialVerticies = p;
        return this;
    }
    
    public Mesh setConnections(Link[] l){
        this.connections = l;
        return this;
    }
    
    public Mesh setTextures(Texture[] t){
        this.textures = t;
        return this;
    }
    
    public void rotateMesh(Point origin, float angle){
       double s = Math.sin(Math.toRadians(angle));
       double c = Math.cos(Math.toRadians(angle));
       for (Point verticy : verticies) {
//            Point verticy = verticies[1];
            verticy.x -= origin.x;
            verticy.y -= origin.y;
            double newX = (verticy.x * c) - (verticy.y * s);
            double newY = (verticy.x * s) + (verticy.y * c);
            verticy.x = (newX + origin.x);
            verticy.y =(newY + origin.y);
            verticy.setTextureRotation((verticy.getTextureRotation()+angle)%360);
        }
    }
    
    public void rotatePoint(int p, float angle){
        this.verticies = null;
       this.verticies = this.initialVerticies.clone();
       HashMap<Integer, Integer> visited = new HashMap<>();
       Point origin = this.verticies[p];
       subRotation(origin, p, angle, visited);
    }
    
    private void subRotation(Point origin, int p, float angle, HashMap<Integer, Integer> visited){
       double s = Math.sin(Math.toRadians(angle));
       double c = Math.cos(Math.toRadians(angle));
       
       visited.put(p, p);
       
       for (int i = 0; i < this.connections.length; i++) {
            Point verticy = null;
            int aux = 0;
            
            if(this.connections[i].first == p){
                verticy = this.verticies[this.connections[i].second];
                aux = this.connections[i].second;
            }
            if(this.connections[i].second == p){
                verticy = this.verticies[this.connections[i].first];
                aux = this.connections[i].first;
            }
            
            if(aux>p){
                if(!visited.containsKey(aux)){
                    if(verticy != null){
                        verticy.x -= origin.x;
                        verticy.y -= origin.y;
                        double newX = (verticy.x * c) - (verticy.y * s);
                        double newY = (verticy.x * s) + (verticy.y * c);
                        verticy.x = (newX + origin.x);
                        verticy.y =(newY + origin.y);
                        verticy.setTextureRotation((verticy.getTextureRotation()+angle)%360);

                        subRotation(origin, aux, angle, visited);
                    }
                }
            }
        }
    }
    
    public void render(int x, int y, Graphics g){
        
        if(Keyboard.bool_1){
            g.setColor(Color.blue);
            for (Link connection : connections) {
                g.drawLine((int)(verticies[connection.first].x+x), (int)(verticies[connection.first].y+y), (int)(verticies[connection.second].x+x), (int)(verticies[connection.second].y+y));
            }
        }
        
        for (Texture texture : textures) {
            texture.render(x, y, g);
        }

        
    }
    
}
