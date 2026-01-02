# Ejercicio: Simulación Cine (Hilos y Sincronización)

Este proyecto implementa la **Versión 2** del ejercicio de simulación de un Cine, utilizando programación concurrente en Java. El objetivo es gestionar la venta de entradas mediante taquillas (hilos consumidores) y clientes (hilos productores), controlando el acceso a los recursos compartidos.

## Descripción del Ejercicio

El programa simula el funcionamiento de un cine antiguo con las siguientes características (V2):
* **Múltiples colas:** Existen varias filas para comprar entrada; los clientes eligen una al azar.
* **Capacidad limitada en colas:** Si una cola supera el límite de personas establecido, el cliente se marcha.
* **Tiempo de venta:** Las taquillas tardan un tiempo aleatorio en procesar cada cliente.
* **Control de Aforo:** Se utiliza un **Semáforo** para asegurar que nunca se vendan más entradas que el número total de asientos.

Todo el sistema es **configurable** mediante constantes en el código.

##Implementación Técnica

Para resolver el ejercicio he aplicado los conceptos de la unidad de Procesos e Hilos:

### 1. Gestión de Hilos
**Clase `Cliente`:** Extiende de Thread. Cada cliente es un hilo independiente que intenta acceder a una cola.
**Clase `Taquilla`:** Implementa la interfaz `Runnable. Separa la lógica de la tarea de la ejecución del hilo.

### 2. Sincronización (Monitores)
He utilizado la clase `Cine` como un monitor para proteger los recursos compartidos (las listas de colas):
**`synchronized`**: Utilizado en los métodos que modifican las colas (`ponerseEnCola`, `atenderCliente`) para evitar condiciones de carrera.
**`wait()`**: Las taquillas se bloquean y esperan si no hay clientes en ninguna cola, evitando consumo innecesario de CPp.
**`notifyAll()`**: Cuando un cliente entra en una cola, avisa a las taquillas para que se despierten y lo atiendan.

### 3. Semáforos
He utilizado la clase `java.util.concurrent.Semaphore` para controlar el aforo total del cine.
* Antes de vender una entrada, la taquilla llama a `tryAcquire()` para verificar si quedan asientos libres.
