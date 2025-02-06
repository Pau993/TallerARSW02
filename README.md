# Programación concurrente, condiciones de carrera, esquemas de sincronización, colecciones sincronizadas y concurrentes

En este laboratorio, analizaremos en detalle la funcionalidad de los métodos wait(), notify() y notifyAll(). 
Estudiaremos su comportamiento en distintos escenarios, identificando su propósito y utilidad dentro de la sincronización de hilos en programación concurrente. Además, aprenderemos a aplicarlos 
de manera eficiente para optimizar la ejecución de procesos y mejorar el rendimiento en los casos planteados.

Java.
JUnit.
Threads y sincronización con wait(), notify(), notifyAll().

## Descripción de las aplicaciones 📖

# Juego de la Serpiente con Elementos Adicionales

Esta aplicación es una implementación del clásico juego de la serpiente, desarrollada en Java,
con un enfoque en la simulación de movimiento autónomo y la interacción con diferentes elementos dentro de un tablero.

# Cálculo de primos

Esta aplicación implementa la generación de números primos, contando cuántos números primos se crean en un período de 5 segundos. 
Utiliza hilos para optimizar el proceso y completa la implementación de la funcionalidad.

## Comenzando 🚀

Las siguientes instrucciones le permitirán obtener una copia del proyecto en funcionamiento.

### Tecnologías usadas ⚙️

* [Java](https://www.java.com/es/) : Lenguaje de programación robusto para backend y aplicaciones empresariales.

### Instalación 📦

Realice los siguientes pasos para clonar el proyecto en su máquina local.

```
git clone https://github.com/Pau993/TallerARSW02.git
cd TallerARSW02
git checkout main
mvn clean compile
```

### Ejecutando la aplicación ⚙️

Para abrir los archivos y ejecutarlo siga la siguiente instrucción.
Dentro de main encontrará el archivo con el programa de primos y dentro de la rama Snake encontrará el programa del juego de la serpiendte

https://github.com/user-attachments/assets/40c25085-cff8-4962-96df-861f5230b95c

Siguiendo las instrucciones podrá clonar el repositorio y ejecutar los programas satisfactoriamente.

## Características principales: ⚙️

1. Validación de Objetos Nulos
Antes de realizar cualquier operación con objetos (como invocar métodos o acceder a propiedades),
valida que estos no sean nulos. Esto es crucial cuando trabajas con arrays o colecciones que pueden contener
referencias no inicializadas.

2. Manejo de Excepciones
Aunque no es ideal depender únicamente de excepciones,
puedes incluir bloques try-catch para capturar y manejar errores inesperados.

3. Singleton Seguro (Para SnakeApp)
Si estás utilizando el patrón Singleton para la clase SnakeApp, asegúrate de implementarlo
 correctamente para garantizar que siempre exista una instancia válida.

4. Robustez y Control de Calidad
Implementa pruebas y verificaciones para garantizar que los elementos clave del juego estén en un estado
válido antes de comenzar el juego:

Comprueba que todas las serpientes estén inicializadas.
Verifica que el tablero tenga dimensiones correctas.
Asegúrate de que los métodos del juego se ejecutan en el orden esperado.
## Muestra de la aplicación 🧩

https://github.com/user-attachments/assets/c4e7d1fb-52b4-441c-9925-88ee582abb26

## Autores ✒️

* **Paula Natalia Paez Vega* **Manuel Felipe Barrera Barrera - *Initial work* - [Paulinguis993](https://github.com/Paulinguis993)

## Licencia 📄

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
