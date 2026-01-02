package ejercicio3Sincronizacion;
import java.util.concurrent.Semaphore;

public class SimulacionCine {
	// Configuración V2
    static final int NUM_TAQUILLAS = 2;
    static final int AFORO_TOTAL = 200;
    static final int NUM_COLAS = 4;
    static final int MAX_PERSONAS_COLA = 10;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Cine cine = new Cine(AFORO_TOTAL, 0, 0);
        System.out.println("Cine inaugurado con aforo: " + AFORO_TOTAL);

	}

}
