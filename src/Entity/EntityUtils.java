/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author Bailey
 */
public class EntityUtils {
    
    public static Entity create(EnumEntity entity, String[] data){
        if(entity.equals(EnumEntity.WIRE)){
            return new EntityWire(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
        }
        if(entity.equals(EnumEntity.BATTERY)){
            return new EntityBattery(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
        }
        if(entity.equals(EnumEntity.BUTTON)){
            return new EntityButton(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
        }
        if(entity.equals(EnumEntity.SPEAKER)){
            return new EntitySpeaker(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
        }
        if(entity.equals(EnumEntity.RADIO_TRANSCIEVER)){
            return new EntityTransciever(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
        }
        
        System.out.println("Entity was not recognised:"+entity.name());
        return new EntityWire(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
    }
}
