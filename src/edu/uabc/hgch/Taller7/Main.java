package edu.uabc.hgch.Taller7;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Main
{
    public static void main(String []args)
    {      
        try
        {         
            //CARGAR INTERFAZ DE SISTEMA
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //REVERTIR
            //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch(Exception error)
        {
            System.out.println(error);
        }       
	
		SwingUtilities.invokeLater( () ->		
		{
			App aplicacion = new App("Taller 7");
			
		}); 
    }
}