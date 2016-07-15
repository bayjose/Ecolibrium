/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Item;

import Entity.EntityCircuit;
import Entity.EnumEntity;

/**
 *
 * @author Bailey
 */
public abstract class ItemCircuit extends Item{

    public EnumEntity circuit;
    
    public ItemCircuit(String name, EnumEntity circuit, String image) {
        super(name, image);
        this.circuit = circuit;
    }
    
}
