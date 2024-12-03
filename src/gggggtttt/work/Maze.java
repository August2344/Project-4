package gggggtttt.work;

import java.io.*;
import java.util.*;

public class Maze {
    private String[][] actualMaze;
    private String name="";
    private String start="";
    private String end="";
    
    public Maze(List<List<String>> g) throws Exception {
        try{
        if (g.get(0).size()==0||g.get(1).size()==0||g.get(2).size()==0){
            throw new InvalidMazeException("skbidi");  
        }
        for (int j=0; j<g.get(0).size();j++){
            name+=g.get(0).get(j);
        }
        name+="\n";
        
        for (int j=g.get(1).indexOf(":")+1; j<g.get(1).size();j++){
            
            start+=g.get(1).get(j);

        }
        String hope=start;

        start=hope.substring(hope.indexOf("-")+1)+"-"+hope.substring(0,hope.indexOf("-"));

        
        for (int j=g.get(2).indexOf(":")+1; j<g.get(2).size();j++){
            
            end+=g.get(2).get(j);
        }

        
        int maxSize=g.get(3).size();
        actualMaze=new String[g.size()-3][maxSize];
        for (int h=3;h<g.size();h++){
            for (int width=0;width<g.get(h).size();width++){
                String curChar=g.get(h).get(width);
                if (!(curChar.equals("W")||curChar.equals("P"))){
                   throw new InvalidMazeException("bigwskib");
                }
                actualMaze[h-3][width]=curChar;

            }

            int curSize=g.get(h).size();
            if (curSize!=maxSize){
               throw new InvalidMazeException("NOT RECTANGLE!!!!");
              
            }
                
        }


        
        
        
        
    
    }catch (InvalidMazeException b){
        throw b;
    }
    
    }
    public String mazeName(){
        return name;
    }
    public String start(){
        return start;
    }
    public String[][] actualMaze(){
        return actualMaze;
    }
    public String m(int x, int y){
        return actualMaze[y][x];

    }

}