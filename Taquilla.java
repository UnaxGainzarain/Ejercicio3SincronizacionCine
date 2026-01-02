package ejercicio3Sincronizacion;

import java.util.Random;

public class Taquilla implements Runnable {
    private Cine cine;
    private String nombre;
    private Random generator;

    public Taquilla(Cine cine, String nombre) {
        this.cine = cine;
        this.nombre = nombre;
        this.generator = new Random();
    }

    @Override
    public void run() {
        System.out.println(nombre + " abierta y esperando clientes.");

        try {
            // El bucle se ejecuta hasta que el hilo principal llame a interrupt()
            while (!Thread.currentThread().isInterrupted()) {
                
                // 1. Intentar coger un cliente de la cola.
                // Si no hay nadie, este método dejará al hilo esperando (wait)
                Cliente cliente = cine.atenderCliente();

                if (cliente != null) {
                    System.out.println(nombre + " atendiendo al Cliente " + cliente.getId());

                    // 2. Simular el tiempo de venta (Sleep)
                    // Calculamos un tiempo aleatorio entre el MÍN y MÁX configurados en el Main
                    int tiempoVenta = generator.nextInt(
                        SimulacionCine.TIEMPO_VENTA_MAX - SimulacionCine.TIEMPO_VENTA_MIN + 1
                    ) + SimulacionCine.TIEMPO_VENTA_MIN;

                    Thread.sleep(tiempoVenta);

                    // 3. Intentar finalizar la venta (comprobar semáforo de aforo)
                    if (cine.intentarVenderEntrada()) {
                        System.out.println( nombre + " ha vendido una entrada al Cliente " + cliente.getId());
                    } else {
                        System.out.println( nombre + " no pudo vender. CINE LLENO. Cliente " + cliente.getId() + " se queda sin entrada.");
                    }
                }
            }
        } catch (InterruptedException e) {
            // Esta excepción salta cuando el Main llama a interrupt() mientras el hilo duerme o espera
            System.out.println(nombre + " cerrando caja (Interrupción recibida).");
        }
    }
}