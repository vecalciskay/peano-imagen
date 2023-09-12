package peano.objetos;

import java.util.Arrays;

public class Vector2dConDireccion {
    private double[] vector;
    private DireccionFigura direccion;

    public Vector2dConDireccion(double x, double y) {
        vector = new double[2];
        vector[0] = x;
        vector[1] = y;
        direccion = DireccionFigura.ARRIBA;
    }

    public Vector2dConDireccion(double x, double y, DireccionFigura direccion) {
        vector = new double[2];
        vector[0] = x;
        vector[1] = y;
        this.direccion = direccion;
    }

    public double[] getVector() {
        return vector;
    }

    public double getX() {
        return vector[0];
    }

    public double getY() {
        return vector[1];
    }

    public DireccionFigura getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionFigura direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Vector2{" +
                "vector=" + Arrays.toString(vector) +
                '}';
    }
}
