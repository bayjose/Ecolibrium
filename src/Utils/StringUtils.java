/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Core.Game;
import java.io.File;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Bayjose
 */
public class StringUtils {
    
    private static String path = "";
    private static Random r = new Random();
    private static LoadedFile lastFile = new LoadedFile("", new String[]{});
    
    public static String[] CombineArrays(String[] base, String[] add){
        String[] temp = new String[base.length+add.length];
        for(int i=0; i<base.length; i++){
            temp[i]=base[i];
        }
        for(int i=0; i<add.length; i++){
            temp[i+base.length]=add[i];
        }
                
        return temp;
    }
    
    public static String[] addLine(String[] base, String add){
        String[] temp = new String[base.length+1];
        for(int i=0; i<base.length; i++){
            temp[i] = base[i];
        }
        temp[base.length]=add;
        return temp;
    }
    
    public static String unify(String[] data){
        String out = "";
        for(int i=0; i<data.length; i++){
            out+=data[i];
        }
        return out;
    }
    
    public static String randomLine(String[] in){
        int index = (int)(in.length * Math.random());
        return in[index];
    }
    
    public static String[] loadData(String path){
        if(path.equals(StringUtils.lastFile.id)){
            return StringUtils.lastFile.data.clone();
        }
        LinkedList<String> data = new LinkedList<String>();
        try {
            Scanner in = new Scanner(new File(StringUtils.getAbsPath()+path));
            do{
                data.add(in.nextLine());
            }while(in.hasNext());
        } catch (Exception e) {
            StringUtils.path = "cpu";
            try{
                Scanner in2 = new Scanner(new File(StringUtils.getAbsPath()+path));
                while(true){
                    try{
                        String line = in2.nextLine();
                        if(line!=null){
                            data.add(line);
                        }
                    }catch(NoSuchElementException e3){
                        break;
                    }
                }
                if(data.size() == 0){
                    in2.close();
                    System.out.println("No \\n detected, passing direct line");
                    in2 = new Scanner(new File(StringUtils.getAbsPath()+path));
                    String specialLine = in2.nextLine();
//                    while(in2.hasNext()){
//                        specialLine = specialLine+in2.next();
//                    }
                    System.out.println("Built line:"+specialLine);
                    data.add(specialLine);
                }
                in2.close();
            }catch(Exception e2){
                e2.printStackTrace();
            }
        }
        String[] outData = new String[data.size()];
        for(int i=0; i<outData.length; i++){
            outData[i] = data.get(i);
        }
        lastFile = new LoadedFile(path, outData);
        return outData;
    }
    
    public static String[] forceLoadData(String path){
        LinkedList<String> data = new LinkedList<String>();
        try {
            Scanner in = new Scanner(new File(StringUtils.getAbsPath()+path));
            do{
                data.add(in.nextLine());
            }while(in.hasNext());
            in.close();
        } catch (StackOverflowError e) {
            
        }catch (Exception e) {
            StringUtils.path = "cpu";
            try{
                String[] deconstructed = StringUtils.getAbsPath().split("/");
                String actualPath = "";
                for(int i = 0; i<deconstructed.length-2; i++){
                    actualPath = actualPath+deconstructed[i];
                }
                actualPath = actualPath+"/";
                Scanner in = new Scanner(new File(actualPath+path));
                do{
                data.add(in.nextLine());
                }while(in.hasNext());
                in.close();
            }catch(Exception e2){
                e2.printStackTrace();
            }
        }
        String[] outData = new String[data.size()];
        for(int i=0; i<outData.length; i++){
            outData[i] = data.get(i);
        }
        return outData;
    }
    
    public static void saveData(String path, String[] data){

        try {
            PrintWriter p = new PrintWriter(new File(StringUtils.getAbsPath()+path));
            for(int i = 0; i<data.length; i++){
                p.println(data[i]);
            }
            p.close();
        } catch (Exception e) {
            try{
                PrintWriter p2 = new PrintWriter(new File(StringUtils.getAbsPath()+path));
                for (int i = 0; i < data.length; i++) {
                    p2.println(data[i]);
                }
                p2.close();
            }catch(Exception e2){
//                e2.printStackTrace();
            }
        }
    }
    
    public static String[] loadUrl(String urlpath){
        String[] out = new String[]{};
        try {
            URL path = new URL(urlpath);
            Scanner scanner = new Scanner(path.openStream());
            for(boolean b=true; b==true;){
                String tmpData = scanner.nextLine();
                if(!tmpData.isEmpty()){
                    System.out.println(tmpData);
                    out = StringUtils.addLine(out, tmpData);
                }else{
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return out;
    }
    
    public static void printAllLines(String[] data){
        System.out.println("Printing:"+data.toString()+"-------------");
        for(int i=0; i<data.length; i++){
            System.out.println(data[i]);
        }
    }
    
    public static String getAbsPath(){
        String absPath;
        if(path.equals("cpu")){
            try {
                absPath = StringUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
                absPath = absPath.replace("/build/classes/", "/");
                absPath = absPath.substring(0, absPath.lastIndexOf("/"));
                absPath = absPath+"/";
            } catch (URISyntaxException ex) {
                ex.printStackTrace();
                absPath = "";
            }
        }else{
            absPath = "";
        }
        return absPath;
    }

    public static int countMatches(String data, String string) {
        int index = 0;
        for(int i=0; i<data.length(); i++){
            if((data.charAt(i)+"").equals(string)){
                index++;
            }
        }
        return index;
    }
    
    public static String removeFrontSpacing(String in){
        String out = in.replaceAll("\t", "");
        while(out.startsWith(" ")){
            out = out.replaceFirst(" ", "");
        }
        return out+"";
    }
    public static String[] Encrypt(String seed, String[] data){
        Random rand = new Random(seed.hashCode());
        String[] out = new String[]{};
        for(int i = 0; i< data.length; i++){
            String tmp = "";
            for(int j = 0; j< data[i].length(); j++){
                tmp = tmp+(char)((int)data[i].charAt(j) + (rand.nextInt(1024)-512))+"";
            }
            out = StringUtils.addLine(out, tmp);
            System.out.println(tmp);
        }
        return out;
    }
    public static String[] Decrypt(String seed, String[] data){
        Random rand = new Random(seed.hashCode());
        String[] out = new String[]{};
        for(int i = 0; i< data.length; i++){
            String tmp = "";
            for(int j = 0; j< data[i].length(); j++){
                tmp = tmp+(char)((int)data[i].charAt(j) - (rand.nextInt(1024)-512))+"";
            }
            out = StringUtils.addLine(out, tmp);
            System.out.println(tmp);
        }
        return out;
    }
    
    public static String genRandomExtension(int numCharacters){
        String[] characters = new String[26];
        characters[0] = "a";
        characters[1] = "b";
        characters[2] = "c";
        characters[3] = "d";
        characters[4] = "e";
        characters[5] = "f";
        characters[6] = "g";
        characters[7] = "h";
        characters[8] = "i";
        characters[9] = "j";
        characters[10] = "k";
        characters[11] = "l";
        characters[12] = "m";
        characters[13] = "n";
        characters[14] = "o";
        characters[15] = "p";
        characters[16] = "q";
        characters[17] = "r";
        characters[18] = "s";
        characters[19] = "t";
        characters[20] = "u";
        characters[21] = "v";
        characters[22] = "w";
        characters[23] = "x";
        characters[24] = "y";
        characters[25] = "z";
        
        String out = "";
        for(int i = 0; i < numCharacters; i++){
            out = out + characters[(int)(Math.floor(Math.random() * 26))];
        }
        
        return out;
    }
}

class LoadedFile{
    public String id;
    public String[] data;
    
    public LoadedFile(String id, String[] data){
        this.id = id;
        this.data = data;
    }
}
