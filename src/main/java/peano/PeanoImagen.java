package peano;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import peano.gui.PeanoFrame;

public class PeanoImagen {
    private static final Logger logger = LogManager.getRootLogger();

    public static void main(String[] args) {
        logger.info("PeanoImagen");
        PeanoFrame frame = new PeanoFrame();
    }
}
