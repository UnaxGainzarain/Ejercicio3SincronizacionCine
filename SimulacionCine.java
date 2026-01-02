package ejercicio3Sincronizacion;

import java.util.Random;

public class SimulacionCine {
    // --- PARÁMETROS CONFIGURABLES ---
    static final int NUM_TAQUILLAS = 2;
    static final int TOTAL_ASIENTOS = 200;
    static final int NUM_COLAS = 4;           // V2: Múltiples colas
    static final int MAX_PERSONAS_COLA = 10;  // V2: Límite de personas por cola
    
    // Tiempos en milisegundos
    static final int TIEMPO_SIMULACION = 15000; // Duración total de la simulación
    static final int TASA_LLEGADA_CLIENTES = 500; // Cada cuánto llega un cliente
    
    // Tiempos que tarda la taquilla en vender (se usarán en la clase Taquilla)
    public static final int TIEMPO_VENTA_MIN = 1000;
    public static final int TIEMPO_VENTA_MAX = 2000;

    public static void main(String[] args) {
        // 1. Instanciamos el Recurso Compartido (Cine)
        Cine cine = new Cine(TOTAL_ASIENTOS, NUM_COLAS, MAX_PERSONAS_COLA);
        System.out.println("--- INICIO SIMULACIÓN CINE ---");
        System.out.println("Aforo: " + TOTAL_ASIENTOS + " | Colas: " + NUM_COLAS);

        // 2. Creamos y arrancamos los Hilos Taquilla (Runnable)
        Thread[] hilosTaquillas = new Thread[NUM_TAQUILLAS];
        
        for (int i = 0; i < NUM_TAQUILLAS; i++) {
            Taquilla t = new Taquilla(cine, "Taquilla-" + (i + 1));
            hilosTaquillas[i] = new Thread(t);
            hilosTaquillas[i].start();
        }

        // 3. Generador de Clientes (Hilo Productor en el main)
        long tiempoFin = System.currentTimeMillis() + TIEMPO_SIMULACION;
        int idCliente = 1;

        try {
            while (System.currentTimeMillis() < tiempoFin) {
                // Creamos un nuevo cliente (Thread)
                Cliente c = new Cliente(idCliente++, cine);
                c.start();

                // Esperamos un poco antes de que llegue el siguiente
                Thread.sleep(TASA_LLEGADA_CLIENTES);
            }
            
            System.out.println("\n--- FIN DEL TIEMPO DE ENTRADA. CERRANDO PUERTAS... ---");
            // Damos tiempo a que terminen las últimas ventas
            Thread.sleep(2000); 

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 4. Parar las Taquillas (Interrupción)
        for (Thread t : hilosTaquillas) {
            t.interrupt();
        }

        // 5. Mostrar resultados finales
        try {
            // Pequeña espera para asegurar que los hilos cierran sus mensajes
            Thread.sleep(1000);
            cine.mostrarEstadisticas();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}