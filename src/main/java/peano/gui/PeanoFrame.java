package peano.gui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import peano.controladores.ControlMarcador;
import peano.objetos.Imagen;
import peano.objetos.MatrizTransformacion;
import peano.objetos.Peano;

import javax.swing.*;
import java.awt.*;

public class PeanoFrame extends JFrame {
    private static final Logger logger = LogManager.getRootLogger();
    private Imagen modelo;
    private Peano algoritmoPeano;
    public PeanoFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.getContentPane().setLayout(new BorderLayout());

        crearImagenYPeano();
        crearPanelComandos();
        crearMenuOpciones();

        this.pack();
        this.setVisible(true);
    }

    private void crearImagenYPeano() {
        logger.info("Crea una imagen de 512x512 y el Peano");
        modelo = new Imagen(512,512, 0x00FFFFFF);

        PeanoPanel panelBase = new PeanoPanel(modelo);
        panelBase.addMouseMotionListener(new ControlMarcador(modelo));
        this.getContentPane().add(panelBase, BorderLayout.WEST);
        logger.info("Panel izquierdo observa la imagen base");

        algoritmoPeano = new Peano(modelo);
        PeanoPanel panelResultado = new PeanoPanel(algoritmoPeano.getResultado());
        this.getContentPane().add(panelResultado, BorderLayout.CENTER);
        logger.info("Panel derecho observa la imagen resultado");
    }

    private void crearPanelComandos() {
        JPanel pnlComandos = new JPanel();
        pnlComandos.setLayout(new BorderLayout());

        String[] items = {"1", "2", "3", "4", "5", "6"};
        JComboBox cmbIteraciones = new JComboBox<>(items);
        pnlComandos.add(cmbIteraciones, BorderLayout.WEST);

        JButton btn = new JButton("Peano");
        btn.addActionListener(e -> {
            String item = (String) cmbIteraciones.getSelectedItem();
            algoritmoPeano.hacerPeano(Integer.valueOf(item).intValue());
        });
        pnlComandos.add(btn, BorderLayout.EAST);

        getContentPane().add(pnlComandos, BorderLayout.SOUTH);
        logger.info("Panel de comandos creado en la parte sur");
    }

    private void crearMenuOpciones() {
        JMenuBar menuBar = new JMenuBar();

        JMenu mnu = new JMenu("Archivo");
        JMenuItem item = new JMenuItem("Nuevo");
        item.addActionListener(e -> {
            modelo.limpiar(0x00FFFFFF);
        });
        mnu.add(item);

        mnu.addSeparator();

        item = new JMenuItem("Salir");
        item.addActionListener(e -> {
            System.exit(0);
        });
        mnu.add(item);

        menuBar.add(mnu);

        this.setJMenuBar(menuBar);
        logger.info("Menu de opciones creado");
    }
}
