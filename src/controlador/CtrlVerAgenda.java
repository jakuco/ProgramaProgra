/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import modelo.Edificio;
import vista.VistaPrincipal;

/**
 *
 * @author davqi
 */
public class CtrlVerAgenda implements ActionListener{
    private VistaPrincipal ventanaUsuario;
    private Map<String,Edificio> edificios;

    public CtrlVerAgenda(VistaPrincipal ventanaUsuario) {
        this.ventanaUsuario = ventanaUsuario;
        edificios = new HashMap<String,Edificio>();
        cargarEdificios();
        cargarComboEdificios();
        this.ventanaUsuario.verAgendaBtn.addActionListener(this);
        this.ventanaUsuario.edificios.addActionListener(this);
    }
    
    public void cargarEdificios(){
        File archEdificios = new File("edificios");
        try{
            FileInputStream fis = new FileInputStream(archEdificios);
            ObjectInputStream ois = new ObjectInputStream(fis);
            edificios = (Map<String, Edificio>) ois.readObject();
            ois.close();
        }
        catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "No se ha encontrado el archivo edificios", "Error", 2);
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error al leer el archivo edificios", "Error", 2);
        }
        catch(ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, "Los archivos no son compatibles", "Error", 2);
        }
    }
    
    public void cargarComboEdificios(){
        
    }
    
    public void cargarSalas(){
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ventanaUsuario.edificios) cargarSalas();
    }

}
