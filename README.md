# Documentación del Proyecto de Automatización de APIs (Swagger PetStore)

Este proyecto contiene la estructura completa de pruebas automatizadas para la API de **Swagger PetStore (Mascotas)**. Está implementado bajo el patrón de diseño **Screenplay** en Serenity BDD con Cucumber y Java 17.

---

## 1. Estructura General del Código

El proyecto sigue una estructura desacoplada y orientada a la mantenibilidad:

```text
CapacitacionApiAuto/
│
├── src/
│   ├── main/java/org/example/
│   │   ├── Questions/              # Aserciones y validaciones de respuesta
│   │   │   └── ResponseCode.java   # Pregunta para capturar el código HTTP retornado
│   │   │
│   │   └── Tasks/                  # Acciones técnicas del CRUD
│   │       ├── PostPet.java        # Crear mascota (POST /pet)
│   │       ├── GetPet.java         # Consultar mascota por ID (GET /pet/{id})
│   │       ├── PutPet.java         # Actualizar mascota (PUT /pet)
│   │       └── DeletePet.java      # Eliminar mascota (DELETE /pet/{id})
│   │
│   └── src/test/
│       ├── java/org/example/
│       │   ├── StepDefinitions/    # Conexión entre lenguaje Gherkin y código Java
│       │   │   └── PetStepDefinitions.java  # Pegamento de los pasos del flujo PetStore
│       │   │
│       │   └── RunnerTest.java     # Clase Runner principal de JUnit
│       │
│       └── resources/features/
│           └── pet.feature         # Escenarios de comportamiento redactados en Gherkin
```

---

## 2. Flujo de Negocio y Lógica del CRUD

El flujo de pruebas sigue el estándar CRUD de forma autónoma:

1.  **Create (POST `/pet`):** Registra una mascota inyectando de forma dinámica un **ID único** generado al vuelo (para evitar solapamiento de datos). Se configuran por defecto en la categoría `"perritos"` y se mapea su **raza** en la lista de `tags`.
2.  **Read (GET `/pet/{id}`):** Valida que el perrito registrado exista en la tienda consultándolo por su ID.
3.  **Update (PUT `/pet`):** Actualiza el estado del perrito a `"adoptado"` (o el estado final asignado en el ejemplo).
4.  **Delete (DELETE `/pet/{id}`) & Error (GET `404`):** Borra el perrito de la tienda y, acto seguido, intenta consultarlo de nuevo esperando un error **`404 Not Found`** para asegurar que la limpieza se completó exitosamente.

---

## 3. Parámetros de Prueba (Luna, Max y Lhyo)

Se ejecutan múltiples combinaciones a través de un `Scenario Outline` con los siguientes perritos:
*   **luna** | Raza: *labrador* | Estado inicial: *disponible* | Estado final: *adoptado*
*   **max** | Raza: *poodle* | Estado inicial: *pendiente* | Estado final: *disponible*
*   **lhyo** | Raza: *beagle* | Estado inicial: *adoptado* | Estado final: *disponible*

---

```

### Reporte Serenity
Tras la ejecución, abre el archivo indexador interactivo en tu navegador:
*   **Ruta local del reporte:** `target/site/serenity/index.html`
