/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import Entity.EntityCircuitboard;
import Graphics.Gui.GuiCircuitboard;
import Graphics.Gui.GuiManager;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Bailey
 */
public class ItemCircuitBoard extends Item{

    private GuiCircuitboard board;
    
    public ItemCircuitBoard() {
        super("Blank Circuit Board", "sand.png");
        board = new GuiCircuitboard();
    }
    
    public ItemCircuitBoard(GuiCircuitboard board) {
        super("Populated Circuit Board", "sand.png");
        this.board = board;
    }

    @Override
    public void tick() {
        
    }

    @Override
    public void render(int x, int y, Graphics g) {
        this.display.render(x, y, g);
    }

    @Override
    public void onInteract(Rectangle rect) {
        GuiManager.openThis(this.board);
    }
    
}
