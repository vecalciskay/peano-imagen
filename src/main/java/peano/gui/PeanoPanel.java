package peano.gui;

import peano.objetos.Imagen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PeanoPanel extends JPanel implements PropertyChangeListener {
    private Imagen modelo;
    public PeanoPanel(Imagen modelo) {
        this.modelo = modelo;
        modelo.addObserver(this);
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(512,512);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage rsm = new BufferedImage(
                modelo.getAncho(), modelo.getAlto(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rsm.createGraphics();

        modelo.dibujar(0, 0, g2d);
        g.drawImage(rsm, 0, 0, null);
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        repaint();
    }
}
