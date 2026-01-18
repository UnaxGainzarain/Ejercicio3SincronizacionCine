# 📘 PSP_Ejercicio3_Cine

Repositorio para el ejercicio de sincronización de hilos de 2 DAM
Módulo Programación Servicios y Procesos

---

## 📑 Índice de contenidos

### 🔹 UT2: **Sincronización de Hilos (Cine V2)**
- **Descripción:** Simulación concurrente de un cine antiguo utilizando monitores, semáforos y gestión de colas múltiples.
- **Clases incluidas:** - **SimulacionCine (Main):** Configuración de parámetros (V2), lanzamiento de hilos y estadísticas finales.
  - **Cine (Monitor):** Recurso compartido. Gestiona las listas de colas y el semáforo de aforo.
  - **Taquilla (Runnable):** Hilo consumidor. Simula el tiempo de venta y atiende a los clientes.
  - **Cliente (Thread):** Hilo productor. Elige una cola aleatoria y desiste si está llena.
- **Conceptos clave:** `Thread`, `Runnable`, `synchronized`, `wait/notify`, `Semaphore`, gestión de recursos compartidos.

---

## 🚀 Cómo ejecutar el proyecto
1. Clona este repositorio:
   ```bash
   git clone [https://github.com/UnaxGainzarain/Ejercicio3SincronizacionCine.git](https://github.com/UnaxGainzarain/Ejercicio3SincronizacionCine.git)
