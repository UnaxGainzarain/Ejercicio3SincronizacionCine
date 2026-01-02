package ejercicio3Sincronizacion;

import java.util.ArrayList;
import java.util.List;

public class Cine {
	private List<Cliente> cola = new ArrayList<>(); // Cola única (V1)

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
