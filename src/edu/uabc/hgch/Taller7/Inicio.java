package edu.uabc.hgch.Taller7;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Inicio extends JPanel  implements ActionListener,FocusListener
{
    private App app;
    private Font font;
    private JTextField username_tf;
    private JTextField correo_tf;
    private JPasswordField pass_tf;
    private JButton iniciar_btn;
    private JButton registrar_btn;
	public GridBagConstraints gbc;
    
	public Inicio(App app)
	{
		super();
        this.app = app;
        setLayout(new GridBagLayout());
         // Crear un objeto GridBagConstraints
        gbc = new GridBagConstraints();

        // Configurar el valor por defecto para los GridBagConstraints
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Los componentes se expandirán en dirección horizontal
        //MARGENES
        gbc.insets.left = 10;
        gbc.insets.right = 10;
        gbc.insets.top = 10;
        //TAMAÑO DE LETRA
        font = new Font("Nuevo",Font.PLAIN,18);
        JLabel temp;
        
        // Etiqueta USERNAME
        gbc.gridy = 0; // Fila 0
        gbc.gridx = 0; // Fila 0, columna 0
        gbc.gridwidth = 1; // Un solo espacio horizontal
        temp = new JLabel("USERNAME:");
        temp.setFont(font);
        add(temp, gbc);
        // Campo de texto USERNAME
        gbc.gridy = 0; // Fila 0
        gbc.gridx = 1; // Columna 1
        gbc.gridwidth = 2; // Ocupa dos columnas
        username_tf = new JTextField(20);
        username_tf.setText("");
        username_tf.setFont(font);
        username_tf.addFocusListener(this);
        add(username_tf, gbc);        
               
        
        // Etiqueta PASSWORD
        gbc.gridy = 2; // Fila 2
        gbc.gridx = 0; // Columna 0
        gbc.gridwidth = 1; // Un solo espacio horizontal
        temp = new JLabel("PASSWORD:");
        temp.setFont(font);
        add(temp, gbc);
        // Campo de texto PASSWORD
        gbc.gridy = 2; // Fila 2
        gbc.gridx = 1; // Columna 1
        gbc.gridwidth = 2; // Ocupa dos columnas
        pass_tf = new JPasswordField(20);
        pass_tf.setText("");
        pass_tf.setFont(font);
        pass_tf.addFocusListener(this);
        add(pass_tf, gbc);
    
        // Botón de CANCELAR
        gbc.gridy = 3; // Fila 3
        gbc.gridx = 0; // Columna 0
        gbc.gridwidth = 1; // El botón ocupará las 1 columnas
        gbc.anchor = GridBagConstraints.WEST; // Ubica el botón a la izquierda
        gbc.fill = GridBagConstraints.NONE; // No ajusta el tamaño
        registrar_btn = new JButton("Registrar");
        registrar_btn.setFont(font);
        registrar_btn.setActionCommand("Registrar");
        registrar_btn.addActionListener(this);//ACCION DEL BOTON
		registrar_btn.addFocusListener(this);
        add(registrar_btn, gbc);
        
        // Botón de Registrar
        gbc.gridy = 3; // Fila 3
        gbc.gridx = 2; // Columna 2
        gbc.gridwidth = 1; // El botón ocupará las 1 columnas
        gbc.anchor = GridBagConstraints.EAST; // Ubica el botón la derecha
        gbc.fill = GridBagConstraints.NONE; // No ajusta el tamaño
        iniciar_btn = new JButton("Iniciar");
        iniciar_btn.setFont(font);
        iniciar_btn.setActionCommand("Iniciar");
        iniciar_btn.addActionListener(this); //ACCION DEL BOTON
		iniciar_btn.addFocusListener(this);
        add(iniciar_btn, gbc);
		   
        
	}
	
	public void defaultFocus()
	{
		setVisible(false);
		setVisible(true);
		username_tf.requestFocus();
	}	
   
    @Override
    public void actionPerformed(ActionEvent e)
    {         
        
        String command = e.getActionCommand();
        
        if(command.equals( iniciar_btn.getActionCommand() ) )
        {
            System.out.printf("\n INICIAR"); 
        }
        else if(command.equals( registrar_btn.getActionCommand() ) )
        {
            System.out.printf("\n REGISTRAR"); 
			app.setRegistro();
        }
    }
    
    public void focusGained(FocusEvent e)
    { 
        if(username_tf.isFocusOwner())
            System.out.printf("\n CAMPO USERNAME SELECIONADO");
        if(pass_tf.isFocusOwner())
            System.out.printf("\n CAMPO PASSWORD SELECIONADO");
        if(registrar_btn.isFocusOwner())
            System.out.printf("\n BOTON REGISTRAR SELECIONADO");
        if(iniciar_btn.isFocusOwner())
            System.out.printf("\n BOTON INICIAR SELECIONADO");
                   
    }
    
    public void focusLost(FocusEvent e)
    {
        //System.out.printf("\n FOCUS LOST: %s",e);
    }
}