/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Core.Input;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Bayjose
 */
public class MousePositionLocator implements MouseMotionListener{

    public static Rectangle MouseLocation = new Rectangle(0,0,1,1);
   
    
    public MousePositionLocator(){

    }
    
    public void mouseDragged(MouseEvent e) {
        MouseLocation.x = e.getX();
        MouseLocation.y = e.getY();
    }

    
    public void mouseMoved(MouseEvent e) {
        MouseLocation.x = e.getX();
        MouseLocation.y = e.getY();

    }
    
    
}
