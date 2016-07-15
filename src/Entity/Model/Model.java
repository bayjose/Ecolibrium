/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.Model;

import Core.Game;
import Entity.Entity;
import Entity.EnumEntity;
import Graphics.Display;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Bailey
 */
public class Model extends Entity{
    private Mesh mesh;

    public Model(int x, int y) {
        super(x, y, EnumEntity.MODEL);
        this.mesh = new Mesh();
        this.display = new Display(1, 1, 1, 1, Color.BLACK);
    }

    @Override
    public void tick() {
        this.mesh.rotateMesh(new Point(0, 0), 1);
    }

    @Override
    public void render(Graphics g) {
        mesh.render(this.getBounds().x, this.getBounds().y, g);
    }
}
