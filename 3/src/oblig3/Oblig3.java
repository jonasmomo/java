/*package oblig3;

import java.util.*;
import hjelpeklasser.*;


public class Oblig3
{

    public static void main(String[] args)
    {
        SBinTre2<Integer> tre = SBinTre2.lagTre();
        
        Integer[]a = {1,2,3,4,5};
        //Integer[]a = {6,5,7,6,7};
        //Integer[]a = {6,6,6,7,7,6,7,7,7};
        //Integer[]a = {6,1,7,3,2,5,4};
        //Integer[]a = {1,2,3,4,5,6,7};
        
        for(int i : a)
            tre.leggInn(i);
        
        boolean ok = false;
        
        System.out.println(tre.toString());
        System.out.println("Antall: "+tre.antall());
        System.out.println("Bladnoder: "+tre.antallIngenBarn());
        System.out.println("EttBarn: "+tre.antallEttBarn());
        System.out.println("ToBarn: "+tre.antallToBarn());
        System.out.println("Høyde: "+tre.høyde());
        ok = (tre.antall() == (tre.antallEttBarn() + tre.antallIngenBarn() + tre.antallToBarn()));
        System.out.println(ok);
        
        System.out.println("-----------------");
        
        System.out.println("maksFjernAlle: "+tre.maksFjernAlle());
        
        System.out.println(tre.toString());
        System.out.println("Antall: "+tre.antall());
        System.out.println("Bladnoder: "+tre.antallIngenBarn());
        System.out.println("EttBarn: "+tre.antallEttBarn());
        System.out.println("ToBarn: "+tre.antallToBarn());
        System.out.println("Høyde: "+tre.høyde());
        ok = (tre.antall() == (tre.antallEttBarn() + tre.antallIngenBarn() + tre.antallToBarn()));
        System.out.println(ok);
        
        //System.out.println(tre.nestMaks());
        
    }
}*/
