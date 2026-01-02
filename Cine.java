package ejercicio3Sincronizacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random; // Necesario para elegir cola al azar
import java.util.concurrent.Semaphore;

public class Cine {
    // CAMBIO V2: En lugar de una sola cola, tenemos una lista de colas
    private List<List<Cliente>> colas; 
    private int maxPorCola; // Para controlar el límite V2
    
    private Semaphore semaforoAforo;
    private int entradasVendidas = 0;
    private int clientesSinEntrada = 0;
    private int clientesPerdidosCola = 0; // Nuevo contador para la V2
    
    public Cine(int totalAsientos, int numColas, int maxPorCola) {
        this.maxPorCola = maxPorCola;
        
        // Inicializamos el semáforo (Correcto, igual que tenías)
        this.semaforoAforo = new Semaphore(totalAsientos); // [cite: 242]
        
        // CAMBIO V2: Inicializamos las múltiples colas
        colas = new ArrayList<>();
        for (int i = 0; i < numColas; i++) {
            colas.add(new ArrayList<>());
        }
    }
    
    // Este método lo tenías perfecto, lo dejamos igual
    public boolean intentarVenderEntrada() {
        if (semaforoAforo.tryAcquire()) { 
            synchronized (this) {
                entradasVendidas++;
            }
            return true;
        } else {
            synchronized (this) {
                clientesSinEntrada++;
            }
            return false;
        }
    }
    
    // CAMBIO V2: Gestión de entrada a colas múltiples con límite
    public synchronized boolean ponerseEnCola(Cliente c) { // [cite: 162]
        // Elegimos una cola al azar (Simulación de cliente que elige fila)
        Random rand = new Random();
        int indiceCola = rand.nextInt(colas.size());
        
        // Obtenemos la cola específica
        List<Cliente> colaElegida = colas.get(indiceCola);
        
        // Verificamos si cabe gente (Requisito: "Sólo pueden haber 10 personas")
        if (colaElegida.size() < maxPorCola) {
            colaElegida.add(c);
            System.out.println("Cliente " + c.getId() + " entra en la cola " + indiceCola);
            
            // Usamos notifyAll() en lugar de notify() por seguridad al haber múltiples taquillas
            notifyAll(); // 
            return true; // Pudo entrar
        } else {
            // Si la cola está llena, el cliente se va
            System.out.println("Cliente " + c.getId() + " se marcha (Cola " + indiceCola + " llena).");
            clientesPerdidosCola++;
            return false; // No pudo entrar
        }
    }

    // CAMBIO V2: La taquilla debe buscar en todas las colas
    public synchronized Cliente atenderCliente() throws InterruptedException {
        // Mientras TODAS las colas estén vacías, esperamos
        while (todasVacias()) {
            wait(); //  Bloquea el hilo hasta que notify() avise
        }
        
        // Buscamos la primera cola que tenga gente
        for (List<Cliente> unaCola : colas) {
            if (!unaCola.isEmpty()) {
                return unaCola.remove(0); // Sacamos al cliente
            }
        }
        return null; // No debería llegar aquí si el while funciona bien
    }
    
    // Método auxiliar para verificar si no hay nadie en ninguna cola
    private boolean todasVacias() {
        for (List<Cliente> unaCola : colas) {
            if (!unaCola.isEmpty()) {
                return false; // Hay alguien
            }
        }
        return true; // Todas vacías
    }
    
    // Opcional: Para ver estadísticas completas al final
    public synchronized void mostrarEstadisticas() {
        System.out.println("Entradas vendidas: " + entradasVendidas);
        System.out.println("Clientes sin entrada (Aforo): " + clientesSinEntrada);
        System.out.println("Clientes que se fueron (Cola llena): " + clientesPerdidosCola);
    }
}