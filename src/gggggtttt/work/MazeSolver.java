package gggggtttt.work;

import java.io.*;
import java.util.*;
import java.lang.*;


public class MazeSolver {
    private String solution="";
    private Maze maze;
    private String usedStrings="";
    private ArrayList<String> waitSpot=new ArrayList<String>();
    private static boolean first=true;
    public MazeSolver(){
        Maze maze;
    }
    public void readMaze(String txt) throws Exception{

        try {

            FileReader in=new FileReader(txt);

            BufferedReader br=new BufferedReader(in);

            String curLet;
            int curRow=0;
            String let;
            List<List<String>> bigList= new ArrayList<List<String>>();
            ArrayList<String> indexZeroArrayList=new ArrayList<String>();
            bigList.add(indexZeroArrayList);
            while((curLet=br.readLine())!=null){
                for (int i=0;i<curLet.length();i++){
                    let=curLet.substring(i,i+1);
                    if ((!let.equals(","))&&(!(let.equals(" ")))){
                        bigList.get(curRow).add(let);
                    }
        
                }
                curRow++;
                bigList.add(new ArrayList<String>());
                
            }
            bigList.remove(bigList.size()-1);

            this.maze=new Maze(bigList);
            
        } catch(InvalidMazeException e) {
            throw e;
            
        }
        
        
    }
    public void solveMaze(){
        solution+=maze.mazeName();

        String startDir=availablePaths(Integer.parseInt(maze.start().substring(0,maze.start().indexOf("-"))),Integer.parseInt(maze.start().substring(maze.start().indexOf("-")+1)));
        String half="";
        int firstX=Integer.parseInt(maze.start().substring(0,maze.start().indexOf("-")));
        int firstY=Integer.parseInt(maze.start().substring(maze.start().indexOf("-") + 1));
        half += firstY+"-"+firstX+"\n"+subSolve(startDir,firstX,firstY);
        int moves=countOf("-",half);
        solution+="Moves: "+moves+"\nStart\n";
        solution+=half;


    }
    public String availablePaths(int x,int y){
        return up(x,y)+right(x,y)+down(x,y)+left(x,y);

    }
    public String up(int x,int y) {
        if (y == 0) {
            return "";
        }
        if (maze.m(x, y - 1).equals("P")) {
            return "U";
        }
        return "";
    }
    public String down(int x,int y){
        if (y==maze.actualMaze().length-1) {
            return "";
        }
        if (maze.m(x, y+1).equals("P")) {
            return "D";
        }
        return "";
    }
    public String left(int x,int y){
        if (x==0){
            return "";
        }

        if (maze.m(x-1, y).equals("P")) {
            return "L";
        }
        return "";
    }
    public String right(int x,int y){
        if (x==maze.actualMaze()[0].length-1) {
            return "";
        }
        if (maze.m(x+1,y).equals("P")) {
            return "R";
        }
        return "";
    }

    public String subSolve(String GT,int x, int y){
        String t=availablePaths(x,y);

        if (waitSpot.contains(x+"-"+y+"-"+GT)){
            return "Mission Failed";
        }


        if (availablePaths(x,y).length()==2&&t.contains(GT)||first){
            first=false;
            if (GT.equals("U")){
                return "" + y + "-" + (x) + "\n" + subSolve(GT,x,y-1);
            } else if (GT.equals("D")){
                return "" + y + "-" + (x) + "\n" + subSolve(GT,x,y+1);
            } else if (GT.equals("L")){
                return "" + (y) + "-" + x + "\n" + subSolve(GT,x-1,y);
            } else {
                return "" + (y) + "-" + x + "\n" + subSolve(GT,x+1,y);
            }

        } else if (t.length()==2){
            t=t.replace(Opposite(GT),"");
            t=t.replace(GT,"");
            if (t.equals("U")){
                return "" +y +"-"+ x+ "\n"+subSolve(t,x,y-1);
            } else if (t.equals("D")){
                return "" +y +"-"+ x+  "\n"+subSolve(t,x,y+1);
            } else if (t.equals("L")){
                return "" +y +"-"+ x+ "\n"+ subSolve(t,x-1,y);
            } else if (t.equals("R")){
                return "" +y +"-"+ x+  "\n"+subSolve(t,x+1,y);
            } else {

            }

        }else if (availablePaths(x,y).length()==1) {
            if (x==maze.actualMaze()[0].length-1||x==0) {
                return "" + y + "-" + x + "\n";
            } else if (y==0||y==maze.actualMaze().length-1){
                return "" + y + "-" + x + "\n";
            } else {
                return "Mission Failed";
            }
        } else {
            waitSpot.add((x)+"-"+(y+1)+"-"+Opposite(GT));
            waitSpot.add((x+1)+"-"+(y)+"-"+Opposite(GT));
            waitSpot.add((x-1)+"-"+(y)+"-"+Opposite(GT));
            waitSpot.add((x)+"-"+(y-1)+"-"+Opposite(GT));

            String s1="";
            String s2="";
            String s3="";
            String s4="";

            s1="-".repeat(maze.actualMaze()[0].length*maze.actualMaze().length);
            s2="-".repeat(maze.actualMaze()[0].length*maze.actualMaze().length);
            s3="-".repeat(maze.actualMaze()[0].length*maze.actualMaze().length);
            s4="-".repeat(maze.actualMaze()[0].length*maze.actualMaze().length);

            t=t.replace(Opposite(GT),"");

            if (t.contains("R")){
                s1 = ""+y+"-"+x+"\n"+subSolve("R",x+1,y);
            }
            if (t.contains("L")){
                s2 = ""+y+"-"+x+"\n"+subSolve("L",x-1,y);
            }

            if (t.contains("U")){
                s3 = ""+y+"-"+x+"\n"+subSolve("U",x,y-1);
            }
            if (t.contains("D")){
                s4 = ""+y+"-"+x+"\n"+subSolve("D",x,y+1);
            }

            if (s1.contains("Mission Failed")){
                s1="-".repeat(maze.actualMaze()[0].length*maze.actualMaze().length);
            }
            if (s2.contains("Mission Failed")){
                s2="-".repeat(maze.actualMaze()[0].length*maze.actualMaze().length);
            }
            if (s3.contains("Mission Failed")){
                s3="-".repeat(maze.actualMaze()[0].length*maze.actualMaze().length);
            }
            if (s4.contains("Mission Failed")){
                s4="-".repeat(maze.actualMaze()[0].length*maze.actualMaze().length);
            }
            String correct=s1;
            if (countOf("-",s1)<countOf("-",s2)&&countOf("-",s1)<countOf("-",s3)&&countOf("-",s1)<countOf("-",s4)&&countOf("-",s1)!=0){
                return s1;
            } else if (countOf("-",s2)<countOf("-",s1)&&countOf("-",s2)<countOf("-",s3)&&countOf("-",s2)<countOf("-",s4)&&countOf("-",s2)!=0){
                return s2;
            } else if (countOf("-",s3)<countOf("-",s1)&&countOf("-",s3)<countOf("-",s4)&&countOf("-",s3)<countOf("-",s2)&&countOf("-",s3)!=0){
                return s3;
            } else {
                return s4;
            }


        }

        return "oh shit";
    }
    public String subSubSolve(String dir, int x, int y){
        return "oh shit";
    }
    public String Opposite(String pp){
        if (pp.equals("U")){
            return "D";
        } else if (pp.equals("D")){
            return "U";
        } else if (pp.equals("R")){
            return "L";
        } else if (pp.equals("L")){
            return "R";
        }
        return "oh shit";
    }
    public int countOf(String l,String big){
        int count=0;
        String b=big;
        int RI=-1;
        for (int j=0;j<big.length()-1;j++){
            if ((RI=b.indexOf("-"))!=-1){
                count++;
                b=b.substring(0,RI)+b.substring(RI+1);
            }
        }
        return count;
    }
    public void writeSolution(String txt){
        solution+="end";
        try {
            File myFile = new File(txt);
            FileWriter fw = new FileWriter(txt);
            fw.write(solution);
            fw.close();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    
    
    
    
}