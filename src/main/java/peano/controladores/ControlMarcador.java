package peano.controladores;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import peano.objetos.Imagen;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class ControlMarcador implements MouseMotionListener {
    private static final Logger logger = LogManager.getRootLogger();
    private Imagen modelo;

    public ControlMarcador(Imagen modelo) {
        this.modelo = modelo;
    }
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        logger.info("Punto en la imagen en la posicion ({},{})",
                mouseEvent.getX(), mouseEvent.getY());
        modelo.punto(mouseEvent.getX(), mouseEvent.getY(), 32);
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }
}
