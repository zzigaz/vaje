package com.mygdx.game.Logic;

import com.mygdx.assets.RegionNames;
import com.mygdx.game.CellActor;

public class Figures {
    private String name;
    private int blackOrWhite;
    private int figureNumber;

    public  Figures(String name, int blackOrWhite, int figureNumber){
        this.blackOrWhite = blackOrWhite;
        this.figureNumber = figureNumber;
        this.name = setName(name);
        System.out.println(this.figureNumber);

    }
    public int getBlackOrWhite(){
        return this.blackOrWhite;
    }
    public String getName(){
        return this.name;
    }
    public String setName(String name){
        return this.name = name+ String.valueOf(this.figureNumber);
    }

    public boolean Logic(CellActor prejsnji, CellActor novi){
        boolean trueOrFalse = false;
        //Beli
        if(this.blackOrWhite == 0)
            trueOrFalse = logikaBeli(prejsnji,novi);
        if(this.blackOrWhite == 1)
            trueOrFalse = logikaCrni(prejsnji,novi);
        return trueOrFalse;
    }

    private boolean logikaBeli(CellActor prejsnji, CellActor novi){
        System.out.println("Beli");

        if(figureNumber >= 48 && figureNumber <=55) {
            if (novi.getTextureRegion().equals(RegionNames.CELL_EMPTY)) {
            } else {
                if (novi.x == prejsnji.x - 1 && novi.y == prejsnji.y - 1)
                    return true;
                if (novi.x == prejsnji.x + 1 && novi.y == prejsnji.y - 1)
                    return true;
            }
            String b = novi.getName();
            char B = novi.getName().charAt(0);
            if (novi.x == prejsnji.x && novi.y == prejsnji.y - 1 && B != 'w' && B != 'b')
                return true;
            if (novi.x == prejsnji.x && novi.y == prejsnji.y - 2 && B != 'w' && B != 'b')
                return true;
        }
        if(figureNumber == 57 || figureNumber == 62){
                if(novi.x == prejsnji.x-2 && novi.y == prejsnji.y-1)
                    return true;
                if(novi.x == prejsnji.x-1 && novi.y == prejsnji.y-2)
                    return true;
                if(novi.x == prejsnji.x+1 && novi.y == prejsnji.y-2)
                    return true;
                if(novi.x == prejsnji.x+2 && novi.y == prejsnji.y-1)
                    return true;
            if(novi.x == prejsnji.x-2 && novi.y == prejsnji.y+1)
                return true;
            if(novi.x == prejsnji.x-1 && novi.y == prejsnji.y+2)
                return true;
            if(novi.x == prejsnji.x+1 && novi.y == prejsnji.y+2)
                return true;
            if(novi.x == prejsnji.x+2 && novi.y == prejsnji.y+1)
                return true;
        }
        if(figureNumber == 56 ||figureNumber == 63){
            for(int i = 1; i <= 8; i++){
                if(novi.x == prejsnji.x+i && novi.y == prejsnji.y)
                    return true;
                if(novi.x == prejsnji.x && novi.y == prejsnji.y+i)
                    return true;
                if(novi.x == prejsnji.x && novi.y == prejsnji.y-i)
                    return true;
                if(novi.x == prejsnji.x-i && novi.y == prejsnji.y)
                    return true;
            }
        }
        if(figureNumber == 58 ||figureNumber == 61) {
            for(int i = 1; i <= 8; i++){
                if(novi.x == prejsnji.x+i && novi.y == prejsnji.y+i)
                    return true;
                if(novi.x == prejsnji.x-i && novi.y == prejsnji.y+i)
                    return true;
                if(novi.x == prejsnji.x-i && novi.y == prejsnji.y-i)
                    return true;
                if(novi.x == prejsnji.x+i && novi.y == prejsnji.y-i)
                    return true;
            }
        }
        if(figureNumber == 60) {
            if (novi.x == prejsnji.x && novi.y == prejsnji.y - 1)
                return true;
            if (novi.x == prejsnji.x && novi.y == prejsnji.y + 1)
                return true;
            if (novi.x == prejsnji.x-1 && novi.y == prejsnji.y - 1)
                return true;
            if (novi.x == prejsnji.x-1 && novi.y == prejsnji.y + 1)
                return true;
            if (novi.x == prejsnji.x-1 && novi.y == prejsnji.y)
                return true;
            if (novi.x == prejsnji.x+1 && novi.y == prejsnji.y - 1)
                return true;
            if (novi.x == prejsnji.x+1 && novi.y == prejsnji.y + 1)
                return true;
            if (novi.x == prejsnji.x+1 && novi.y == prejsnji.y)
                return true;
        }
        if(figureNumber ==59){
            for(int i = 1; i <= 8; i++){
                if(novi.x == prejsnji.x+i && novi.y == prejsnji.y)
                    return true;
                if(novi.x == prejsnji.x && novi.y == prejsnji.y+i)
                    return true;
                if(novi.x == prejsnji.x && novi.y == prejsnji.y-i)
                    return true;
                if(novi.x == prejsnji.x-i && novi.y == prejsnji.y)
                    return true;
                if(novi.x == prejsnji.x+i && novi.y == prejsnji.y+i)
                    return true;
                if(novi.x == prejsnji.x-i && novi.y == prejsnji.y+i)
                    return true;
                if(novi.x == prejsnji.x-i && novi.y == prejsnji.y-i)
                    return true;
                if(novi.x == prejsnji.x+i && novi.y == prejsnji.y-i)
                    return true;
            }
        }
            return false;
    }
    private boolean logikaCrni(CellActor prejsnji, CellActor novi){
        System.out.println("Crni");
        if(figureNumber >= 8 && figureNumber <=15) {
            if (novi.getTextureRegion().equals(RegionNames.CELL_EMPTY)) {
            } else {
                if (novi.x == prejsnji.x - 1 && novi.y == prejsnji.y + 1)
                    return true;
                if (novi.x == prejsnji.x + 1 && novi.y == prejsnji.y + 1)
                    return true;
            }
            String b = novi.getName();
            char B = novi.getName().charAt(0);
            if (novi.x == prejsnji.x && novi.y == prejsnji.y + 1 && B != 'b' && B != 'w')
                return true;
            if (novi.x == prejsnji.x && novi.y == prejsnji.y + 2 && B != 'b' && B != 'w')
                return true;
        }
        if(figureNumber == 1 || figureNumber == 6){
            if(novi.x == prejsnji.x-2 && novi.y == prejsnji.y+1)
                return true;
            if(novi.x == prejsnji.x-1 && novi.y == prejsnji.y+2)
                return true;
            if(novi.x == prejsnji.x+1 && novi.y == prejsnji.y+2)
                return true;
            if(novi.x == prejsnji.x+2 && novi.y == prejsnji.y+1)
                return true;

            if(novi.x == prejsnji.x-2 && novi.y == prejsnji.y-1)
                return true;
            if(novi.x == prejsnji.x-1 && novi.y == prejsnji.y-2)
                return true;
            if(novi.x == prejsnji.x+1 && novi.y == prejsnji.y-2)
                return true;
            if(novi.x == prejsnji.x+2 && novi.y == prejsnji.y-1)
                return true;
        }
        if(figureNumber == 0 ||figureNumber == 7){
            for(int i = 1; i <= 8; i++){
                if(novi.x == prejsnji.x+i && novi.y == prejsnji.y)
                    return true;
                if(novi.x == prejsnji.x && novi.y == prejsnji.y+i)
                    return true;
                if(novi.x == prejsnji.x && novi.y == prejsnji.y-i)
                    return true;
                if(novi.x == prejsnji.x-i && novi.y == prejsnji.y)
                    return true;
            }
        }
        if(figureNumber == 2 ||figureNumber == 5) {
            for(int i = 1; i <= 8; i++){
                if(novi.x == prejsnji.x+i && novi.y == prejsnji.y+i)
                    return true;
                if(novi.x == prejsnji.x-i && novi.y == prejsnji.y+i)
                    return true;
                if(novi.x == prejsnji.x-i && novi.y == prejsnji.y-i)
                    return true;
                if(novi.x == prejsnji.x+i && novi.y == prejsnji.y-i)
                    return true;
            }
        }
        if(figureNumber == 3) {
            if (novi.x == prejsnji.x && novi.y == prejsnji.y - 1)
                return true;
            if (novi.x == prejsnji.x && novi.y == prejsnji.y + 1)
                return true;
            if (novi.x == prejsnji.x-1 && novi.y == prejsnji.y - 1)
                return true;
            if (novi.x == prejsnji.x-1 && novi.y == prejsnji.y + 1)
                return true;
            if (novi.x == prejsnji.x-1 && novi.y == prejsnji.y)
                return true;
            if (novi.x == prejsnji.x+1 && novi.y == prejsnji.y - 1)
                return true;
            if (novi.x == prejsnji.x+1 && novi.y == prejsnji.y + 1)
                return true;
            if (novi.x == prejsnji.x+1 && novi.y == prejsnji.y)
                return true;
        }
        if(figureNumber ==4){
            for(int i = 1; i <= 8; i++){
                if(novi.x == prejsnji.x+i && novi.y == prejsnji.y)
                    return true;
                if(novi.x == prejsnji.x && novi.y == prejsnji.y+i)
                    return true;
                if(novi.x == prejsnji.x && novi.y == prejsnji.y-i)
                    return true;
                if(novi.x == prejsnji.x-i && novi.y == prejsnji.y)
                    return true;
                if(novi.x == prejsnji.x+i && novi.y == prejsnji.y+i)
                    return true;
                if(novi.x == prejsnji.x-i && novi.y == prejsnji.y+i)
                    return true;
                if(novi.x == prejsnji.x-i && novi.y == prejsnji.y-i)
                    return true;
                if(novi.x == prejsnji.x+i && novi.y == prejsnji.y-i)
                    return true;
            }
        }

        return false;
    }
}
