package edu.uabc.hgch.Taller7;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class App extends JFrame 
{
    private Inicio panelPrincipal;
    private Registro panelSegundario;    
    
    public App(String label)
    {
        super(label);  
        setLayout(new GridLayout(1,1));
        setSize(1080,720);
        setMinimumSize(new Dimension(640,480));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           
        
        panelPrincipal = new Inicio(this);
        panelSegundario = new Registro(this);        
        setInicio();
        setVisible(true);
    }    
    
    public void setInicio()
	{		
		getContentPane().setVisible(false);
		setContentPane(panelPrincipal);
		panelPrincipal.defaultFocus();
		getContentPane().setVisible(true);	
	}    
	
	
    public void setRegistro()
	{		
		getContentPane().setVisible(false);
		setContentPane(panelSegundario);	
		panelSegundario.defaultFocus();
		getContentPane().setVisible(true);	
	}    
	    
}