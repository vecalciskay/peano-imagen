package peano.objetos;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Imagen implements IDibujador {
    public static final String EVENT_IMAGEN = "IMAGEN";
    private int alto;
    private int ancho;
    private int[][] pixeles;
    private PropertyChangeSupport observado;

    public Imagen(int w, int h) {
        ancho = w;
        alto = h;
        pixeles = new int[ancho][alto];
        observado = new PropertyChangeSupport(this);
    }

    public Imagen(int w, int h, int color) {
        ancho = w;
        alto = h;
        pixeles = new int[ancho][alto];
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                pixeles[i][j] = color;
            }
        }
        observado = new PropertyChangeSupport(this);
    }

    @Override
    public void dibujar(int x, int y, Graphics g) {
        for (int i = x; i < (ancho + x); i++) {
            for (int j = y; j < (alto + y); j++) {
                g.setColor(new Color(pixeles[i-x][j-y]));
                g.drawLine(i,j,i,j);
            }
        }
    }

    public void addObserver(PropertyChangeListener listener) {
        observado.addPropertyChangeListener(listener);
    }

    public void aplicarMatriz(MatrizTransformacion m) {
        int[][] nuevosPixeles = new int[ancho][alto];

        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                Vector2dConDireccion entrada = new Vector2dConDireccion(i,j);
                Vector2dConDireccion salida = m.aplicarMatriz(entrada);
                if ((int)salida.getX() >= 0 &&
                        (int)salida.getX() < ancho &&
                        (int)salida.getY() >= 0 &&
                        (int)salida.getY() < alto) {
                    nuevosPixeles[(int)salida.getX()][(int)salida.getY()] =
                            pixeles[i][j];
                }
            }
        }
        pixeles = nuevosPixeles;
        observado.firePropertyChange(EVENT_IMAGEN,true, false);
    }

    public int getAlto() {
        return alto;
    }

    public int getAncho() {
        return ancho;
    }

    public int[][] getPixeles() {
        return pixeles;
    }

    public void punto(int x, int y, int tamano) {

        for (int i = x - tamano/2; i < x + tamano / 2; i++) {
            for (int j = y - tamano/2; j < y + tamano/2; j++) {
                if (i >= 0 && i < ancho &&
                        j >= 0 && j < alto) {
                    pixeles[i][j] = 0;
                }
            }
        }
        observado.firePropertyChange(EVENT_IMAGEN,true, false);
    }

    public void copiar(Imagen base) {
        this.ancho = base.getAncho();
        this.alto = base.getAlto();
        pixeles = new int[ancho][alto];
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                pixeles[i][j] = base.getPixeles()[i][j];
            }
        }
        observado.firePropertyChange(EVENT_IMAGEN,true, false);
    }

    public void copiar(int x, int y, Imagen img) {
        for (int i = x; i < (img.getAncho() + x); i++) {
            for (int j = y; j < (img.getAlto() + y); j++) {
                if (i >= 0 && i < ancho &&
                        j >= 0 && j < alto) {
                    pixeles[i][j] = img.getPixeles()[i - x][j - y];
                }
            }
        }
        observado.firePropertyChange(EVENT_IMAGEN,true, false);
    }

    public void limpiar(int color) {
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++){
                pixeles[i][j] = color;
            }
        }
        observado.firePropertyChange(EVENT_IMAGEN,true, false);
    }

    public void recortar(int escalaRecorte) {
        int nuevoAncho = ancho / escalaRecorte;
        int nuevoAlto = alto / escalaRecorte;
        int[][] nuevosPixeles = new int[nuevoAncho][nuevoAlto];

        for (int i = 0; i < nuevoAncho; i++) {
            for (int j = 0; j < nuevoAlto; j++) {
                nuevosPixeles[i][j] = pixeles[i][j];
            }
        }
        ancho = nuevoAncho;
        alto = nuevoAlto;
        pixeles = nuevosPixeles;
        observado.firePropertyChange(EVENT_IMAGEN,true, false);
    }
}
