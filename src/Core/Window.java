/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Bayjose
 */
public class Window {
    
    public Window(Game game){
        JFrame frame = new JFrame(Game.NAME);
        Dimension dim = new Dimension(Game.WIDTH, Game.HEIGHT);
        
        game.setPreferredSize(dim);
        game.setMaximumSize(dim);
        game.setMinimumSize(dim);
        
        frame.setSize(dim);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        
        game.start();
    }
}
