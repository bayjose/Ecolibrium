/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics.Gui;

import Core.Input.Keyboard;
import Core.Game;
import Core.Input.MouseInput;
import Core.Input.MousePositionLocator;
import Entity.Entity;
import Entity.EntityCircuit;
import Entity.EntityUtils;
import Entity.EntityWire;
import Entity.EnumEntity;
import Entity.WireConnectionPoint;
import Graphics.Display;
import Graphics.SpriteBinder;
import Item.ItemCircuit;
import Item.ItemWire;
import Item.MouseItem;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author Bailey
 */
public class GuiCircuitboard extends Gui{

    Display display;
    private LinkedList<EntityCircuit> entities = new LinkedList<EntityCircuit>();
    
    private boolean movingEntity = false;
    private EntityCircuit moving = null;
    private boolean placingWire = false;
    private EntityWire wire = null;
    
    public GuiCircuitboard() {
        super((28 * 6), (4 * 6), Game.WIDTH, Game.HEIGHT);
        this.display = SpriteBinder.loadSprite("gui/circuitboard.png");
        this.display.width  = 12 * 64;
        this.display.height  = 12 * 64;
                
        super.canBeOveridden = false;
    }

    @Override
    public void tick() {
        for(EntityCircuit circuit:this.entities){
            circuit.resetPower();
        }
        //initial power set from power sources
        for(EntityCircuit circuit:this.entities){
            circuit.tick();
        }

        HashMap<String, WireConnectionPoint> checked = new HashMap<String, WireConnectionPoint>();
        HashMap<String, WireConnectionPoint> toCheck = new HashMap<String, WireConnectionPoint>();
        //find powereed nodes in all nodes
        for(EntityCircuit entity : this.entities){
            for(int i = 0; i < entity.getConnections().length; i++){
                if(entity.getConnections()[i].isPowered()){
                   toCheck.put(entity.getConnections()[i].getID(), entity.getConnections()[i]);
                }
            }
        }
        //for all powered nodes
         //repeat untill all have been checked
        while(!toCheck.isEmpty()){
            updatePower(checked, toCheck);
        }
        
        //return
        if(this.movingEntity){
            if(this.moving!= null){
                this.moving.x = MousePositionLocator.MouseLocation.x;
                this.moving.y = MousePositionLocator.MouseLocation.y;
            }
        }

    }
    
    public void updatePower(HashMap<String, WireConnectionPoint> checked, HashMap<String, WireConnectionPoint> toCheck){
        //target Node
        WireConnectionPoint wcp = (WireConnectionPoint)toCheck.values().toArray()[0];
        toCheck.remove(wcp.getID());
        checked.put(wcp.getID(), wcp);
        
        //find wires connected to the target node
        for(EntityCircuit e : this.entities){
            if(e instanceof EntityWire){
                if(((EntityWire)e).getPT1().getID().equals(wcp.getID())){
                    //power the wire, and power the opposing wire connection point
                    e.tick();
                    WireConnectionPoint newTarget = ((EntityWire) e).getPT2();
                    if(!checked.containsKey(newTarget.getID())){
                        toCheck.put(newTarget.getID(), newTarget);
                        //find entity new node is connected to
                        loop:{
                            for(EntityCircuit e2 : this.entities){
                                if(!(e2 instanceof EntityWire)){
                                    for(int i = 0; i < e2.getConnections().length; i++){
                                        if(e2.getConnections()[i].getID().equals(newTarget.getID())){
                                            //tick the entity that the opposing node connects to
                                            e2.tick();
                                            for(int j = 0; j < e2.getConnections().length; j++){
                                                if(e2.getConnections()[j].isPowered()){
                                                    if(!checked.containsKey(e2.getConnections()[j].getID())){
                                                        toCheck.put(e2.getConnections()[j].getID(), e2.getConnections()[j]);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //tick the entity that the opposing node connects to
                    
                }
                if(((EntityWire)e).getPT2().getID().equals(wcp.getID())){
                    //power the wire, and power the opposing wire connection point
                    e.tick();
                    WireConnectionPoint newTarget = ((EntityWire) e).getPT1();
                    if(!checked.containsKey(newTarget.getID())){
                        toCheck.put(newTarget.getID(), newTarget);
                        //find entity new node is connected to
                        loop:{
                            for(EntityCircuit e2 : this.entities){
                                if(!(e2 instanceof EntityWire)){
                                    for(int i = 0; i < e2.getConnections().length; i++){
                                        if(e2.getConnections()[i].getID().equals(newTarget.getID())){
                                            //tick the entity that the opposing node connects to
                                            e2.tick();
                                            for(int j = 0; j < e2.getConnections().length; j++){
                                                if(e2.getConnections()[j].isPowered()){
                                                    if(!checked.containsKey(e2.getConnections()[j].getID())){
                                                        toCheck.put(e2.getConnections()[j].getID(), e2.getConnections()[j]);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //tick the entity that the opposing node connects to
                    
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        this.display.render(this.bounds.x+(this.display.width/2), this.bounds.y+(this.display.height/2), g);
        
        g.drawRect(this.bounds.x, this.bounds.y, this.display.width, this.display.height);
        
        if(this.movingEntity){
            if(this.moving!= null){
                this.moving.render(g);
            }
        }
        
        for(EntityCircuit circuit:this.entities){
            circuit.render(g);
            if(Keyboard.bool_3){
                g.setColor(Color.orange);
                g.drawRect(circuit.getBounds().x, circuit.getBounds().y, circuit.getBounds().width, circuit.getBounds().height);
            }
        }
    }

   
    @Override
    public boolean onClick(Rectangle rect) {
        if(!MouseInput.IsRightClick){
            if(!this.movingEntity){
                if(rect.intersects(new Rectangle(this.bounds.x, this.bounds.y, this.display.width, this.display.height))){
                    if(MouseItem.item!=null){
                        if(MouseItem.item instanceof ItemWire){
                            loop:{
                                WireConnectionPoint wcp = null;
                                for(int i = 0; i < this.entities.size(); i++){
                                    wcp = this.entities.get(i).canConnect(rect);
                                    if(wcp != null){
                                        break;
                                    }
                                }
                                if(wcp == null){
                                    break loop;
                                }

                                if(placingWire){
                                    if(this.wire.getPT1() != wcp){
                                        this.wire.setPT2(wcp);
                                        placingWire = false;
                                        this.entities.add(wire);
                                        this.wire = null;
                                    }
                                }else{
                                    this.wire = new EntityWire(MouseInput.Mouse.x, MouseInput.Mouse.y);
                                    this.wire.setPT1(wcp);
                                    placingWire = true;
                                }
                            }
                            return true;
                        }
                        this.entities.add((EntityCircuit)EntityUtils.create(((ItemCircuit)MouseItem.item).circuit, new String[]{""+MouseInput.Mouse.x, ""+MouseInput.Mouse.y}));
                        return true;
                    }else{
                        //mouse item is null
                        for(EntityCircuit entity: this.entities){
                            if(rect.intersects(entity.getBounds())){
                                this.moving = entity;
                                this.movingEntity = true;
//                                this.entities.remove(entity);
                                break;
                            }
                        }
                    }
                }else{
                    //interact with items off the board ie hud
                    GuiManager.getHud().onClick(rect);
                    return true;
                }
            }else{
                //moving entity
                loop:{
                    for(EntityCircuit entity: this.entities){
                        if(this.moving.getBounds().intersects(entity.getBounds())){
                            if(this.moving!=entity){
                                break loop;
                            }
                        }
                    }
                    
                    if(!this.moving.getBounds().intersects(new Rectangle(this.bounds.x, this.bounds.y, this.display.width, this.display.height))){
                        this.entities.remove(this.moving);
                    }
                    //return to world
                    this.movingEntity = false;
                    this.moving.updateConnectionPoints();
                    this.moving = null;
                }
            }
        }else{
            for(EntityCircuit circuit:this.entities){
                if(circuit.getBounds().intersects(rect)){
                    circuit.onClick(rect);
                }
            }
        }
        return false;
    }
    
    @Override
    public void onOpen(){
        
    }
    
}
