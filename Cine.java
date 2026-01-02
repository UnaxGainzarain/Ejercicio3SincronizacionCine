package ejercicio3Sincronizacion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Cine {
	private List<Cliente> cola = new ArrayList<>(); 

	private Semaphore semaforoAforo;
	private int entradasVendidas = 0;
    private int clientesSinEntrada = 0;
	
    public Cine(int totalAsientos, int numColas, int maxPorCola) {

        // Creamos el semáforo con el número de permisos (asientos) disponibles
        // Según el PDF: "Crear una instancia de Semaphore con un número inicial de permisos"
        this.semaforoAforo = new Semaphore(totalAsientos); // [cite: 240, 242]
    }
    
    public boolean intentarVenderEntrada() {
        // Intentamos adquirir un permiso.
        // El método tryAcquire() es una variante de acquire() que no bloquea si no hay permisos,
        // sino que devuelve false inmediatamente. Es ideal para decir "está lleno".
        
        if (semaforoAforo.tryAcquire()) { 
            // Si entra aquí, es que el semáforo restó 1 al contador de asientos.
            synchronized (this) { // Sincronizamos solo para sumar al contador estadística
                entradasVendidas++;
            }
            return true; // Venta exitosa
            
        } else {
            // Si tryAcquire devuelve false, es que el semáforo está a 0 (Cine lleno).
            synchronized (this) {
                clientesSinEntrada++;
            }
            return false; // No se pudo vender
        }
    }
    
    // El cliente llama a esto
    public synchronized void ponerseEnCola(Cliente c) { // 
        cola.add(c);
        System.out.println("Cliente " + c.getId() + " en cola.");
        notify(); // Avisa a una taquilla dormida 
    }

    // La taquilla llama a esto
    public synchronized Cliente atenderCliente() throws InterruptedException {
        while (cola.isEmpty()) {
            wait(); // Espera a que llegue alguien 
        }
        return cola.remove(0);
    }
    
}
