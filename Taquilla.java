package ejercicio3Sincronizacion;

public class Taquilla implements Runnable {
	private String nombre;
	public Taquilla(String nombre) {
		this.nombre = nombre;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
            while(true) {
                // Simular venta (2000-3000ms)
                Thread.sleep(2000); // 
                System.out.println(nombre + " está libre esperando clientes...");
            }
        } catch (InterruptedException e) { e.printStackTrace(); }
    }
}


