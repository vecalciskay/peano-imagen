package peano.objetos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class Peano {
    private static final Logger logger = LogManager.getRootLogger();
    private Imagen base;
    private Imagen resultado;
    private static final HashMap<DireccionFigura, Vector2dConDireccion> direccionesIzquierda;
    private static final HashMap<DireccionFigura, Vector2dConDireccion> direccionesDerecha;
    private static final HashMap<DireccionFigura, Vector2dConDireccion> direccionesArriba1;
    private static final HashMap<DireccionFigura, Vector2dConDireccion> direccionesArriba2;

    static {
        direccionesIzquierda = new HashMap<>();
        direccionesIzquierda.put(DireccionFigura.ARRIBA,
                new Vector2dConDireccion(0, 1, DireccionFigura.DERECHA));
        direccionesIzquierda.put(DireccionFigura.DERECHA,
                new Vector2dConDireccion(0, 0, DireccionFigura.ABAJO));
        direccionesIzquierda.put(DireccionFigura.ABAJO,
                new Vector2dConDireccion(1, 0, DireccionFigura.IZQUIERDA));
        direccionesIzquierda.put(DireccionFigura.IZQUIERDA,
                new Vector2dConDireccion(1, 1, DireccionFigura.ARRIBA));

        direccionesDerecha = new HashMap<>();
        direccionesDerecha.put(DireccionFigura.ARRIBA,
                new Vector2dConDireccion(1, 1, DireccionFigura.IZQUIERDA));
        direccionesDerecha.put(DireccionFigura.IZQUIERDA,
                new Vector2dConDireccion(1, 0, DireccionFigura.ABAJO));
        direccionesDerecha.put(DireccionFigura.ABAJO,
                new Vector2dConDireccion(0, 0, DireccionFigura.DERECHA));
        direccionesDerecha.put(DireccionFigura.DERECHA,
                new Vector2dConDireccion(0, 1, DireccionFigura.ARRIBA));

        direccionesArriba1 = new HashMap<>();
        direccionesArriba1.put(DireccionFigura.ARRIBA,
                new Vector2dConDireccion(0, 0, DireccionFigura.ARRIBA));
        direccionesArriba1.put(DireccionFigura.IZQUIERDA,
                new Vector2dConDireccion(0, 1, DireccionFigura.IZQUIERDA));
        direccionesArriba1.put(DireccionFigura.ABAJO,
                new Vector2dConDireccion(1, 1, DireccionFigura.ABAJO));
        direccionesArriba1.put(DireccionFigura.DERECHA,
                new Vector2dConDireccion(1, 0, DireccionFigura.DERECHA));

        direccionesArriba2 = new HashMap<>();
        direccionesArriba2.put(DireccionFigura.ARRIBA,
                new Vector2dConDireccion(1, 0, DireccionFigura.ARRIBA));
        direccionesArriba2.put(DireccionFigura.IZQUIERDA,
                new Vector2dConDireccion(0, 0, DireccionFigura.IZQUIERDA));
        direccionesArriba2.put(DireccionFigura.ABAJO,
                new Vector2dConDireccion(0, 1, DireccionFigura.ABAJO));
        direccionesArriba2.put(DireccionFigura.DERECHA,
                new Vector2dConDireccion(1, 1, DireccionFigura.DERECHA));
    }

    public Peano(Imagen base) {
        this.base = base;
        resultado = new Imagen(base.getAncho(), base.getAlto());
    }

    public Imagen getResultado() {
        return resultado;
    }

    public void hacerPeano(int iteraciones) {
        logger.info("hacerPeano({})", iteraciones);
        hacerPeano(0, 0, DireccionFigura.ARRIBA, 1, new MatrizTransformacion(), iteraciones);
    }

    private void hacerPeano(int x, int y, DireccionFigura dir, int divisor, MatrizTransformacion matriz, int n) {
        if (n == 1) {
            Imagen img = new Imagen(10, 10);
            img.copiar(base);
            MatrizTransformacion m1 = new MatrizTransformacion();

            m1.escala(1.0 / divisor, 1.0 / divisor);
            m1.traslacion(-base.getAncho() / divisor / 2, -base.getAlto() / divisor / 2);
            m1.componer(matriz);
            m1.traslacion(base.getAncho() / divisor / 2, base.getAlto() / divisor / 2);

            img.aplicarMatriz(m1);
            img.recortar(divisor);
            resultado.copiar(x, y, img);
            return;
        }
        int divisorNuevo = divisor * 2;

        MatrizTransformacion m1 = new MatrizTransformacion();
        m1.rotacion(-90);
        MatrizTransformacion m2 = new MatrizTransformacion();
        m2.copiar(matriz);
        m2.componer(m1);
        int x1 = x + (int) (direccionesIzquierda.get(dir).getX()) * base.getAncho() / divisorNuevo;
        int y1 = y + (int) (direccionesIzquierda.get(dir).getY()) * base.getAlto() / divisorNuevo;
        DireccionFigura dir1 = direccionesIzquierda.get(dir).getDireccion();
        logger.info("La matriz para el nivel {}, escala {}, cuadrado 1, x,y = ({},{}) \n{}",
                n, divisorNuevo, x1, y1, m2);

        hacerPeano(x1, y1, dir1, divisorNuevo, m2, n - 1);

        m1 = new MatrizTransformacion();
        m1.rotacion(90);
        m2 = new MatrizTransformacion();
        m2.copiar(matriz);
        m2.componer(m1);
        x1 = x + (int) (direccionesDerecha.get(dir).getX()) * base.getAncho() / divisorNuevo;
        y1 = y + (int) (direccionesDerecha.get(dir).getY()) * base.getAlto() / divisorNuevo;
        dir1 = direccionesDerecha.get(dir).getDireccion();
        logger.info("La matriz para el nivel {}, escala {}, cuadrado 1, x,y = ({},{}) \n{}",
                n, divisorNuevo, x1, y1, m2);

        hacerPeano(x1, y1, dir1, divisorNuevo, m2, n - 1);

        x1 = x + (int) (direccionesArriba1.get(dir).getX()) * base.getAncho() / divisorNuevo;
        y1 = y + (int) (direccionesArriba1.get(dir).getY()) * base.getAlto() / divisorNuevo;
        dir1 = direccionesArriba1.get(dir).getDireccion();
        logger.info("La matriz para el nivel {}, escala {}, cuadrado 1, x,y = ({},{}) \n{}",
                n, divisorNuevo, x1, y1, m1);

        hacerPeano(x1, y1, dir1, divisorNuevo, matriz, n - 1);

        x1 = x + (int) (direccionesArriba2.get(dir).getX()) * base.getAncho() / divisorNuevo;
        y1 = y + (int) (direccionesArriba2.get(dir).getY()) * base.getAlto() / divisorNuevo;
        dir1 = direccionesArriba2.get(dir).getDireccion();
        logger.info("La matriz para el nivel {}, escala {}, cuadrado 1, x,y = ({},{}) \n{}",
                n, divisorNuevo, x1, y1, m1);

        hacerPeano(x1, y1, dir1, divisorNuevo, matriz, n - 1);
    }
}
