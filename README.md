# EntidadesApp - Gestión de Autores y Libros 📚

**EntidadesApp** es una aplicación de ejemplo para Android desarrollada con un enfoque académico y
 profesional. La aplicación permite gestionar una relación de uno a muchos entre **Autores** y sus **Libros**, implementando las mejores prácticas de desarrollo moderno en Android.

## 🚀 Arquitectura y Patrones
El proyecto sigue la arquitectura recomendada por Google para aplicaciones robustas y escalables:

*   
**MVVM (Model-View-ViewModel):** Separación clara entre la lógica de interfaz de usuario, la lógica de negocio y el acceso a datos.
*   **Repository Pattern:** (Simplificado mediante DAOs) para la gestión centralizada de los datos.
*   **State Management:** Uso de `StateFlow
` para manejar el estado de la UI de forma reactiva y segura frente a ciclos de vida.

## 🛠️ Tecnologías Utilizadas
*   **Jetpack Compose:** Interfaz de usuario declarativa y moderna.
*   **Room Database:** Persistencia de datos local sobre SQLite con soporte para cor
rutinas y Flow.
*   **Kotlin Coroutines & Flow:** Manejo de operaciones asíncronas y flujos de datos en tiempo real.
*   **Navigation Compose:** Navegación fluida entre pantallas con paso de argumentos (ID del autor).
*   **Material 3:** Implement
ación de la última guía de diseño de Google con un tema personalizado "Noble y Académico".

## 📋 Características Principales
*   **Gestión de Autores:**
    *   Listado de autores registrados en tarjetas (Cards).
    *   Creación de nuevos autores mediante diálogos.
    *   
Edición de nombres de autores existentes.
    *   Eliminación con diálogo de confirmación de seguridad.
*   **Gestión de Libros:**
    *   Relación vinculada a un autor específico (Foreign Key).
    *   CRUD completo (Crear, Leer, Actualizar, Borrar
) para libros.
    *   Eliminación en cascada (si se borra un autor, se borran sus libros).
*   **Diseño Visual:**
    *   Paleta de colores azul profundo y gris claro.
    *   Interfaz limpia, sobria y profesional.

## 
🗄️ Modelo de Datos
La base de datos Room (`AppDatabase`) consta de dos entidades principales:
1.  **AuthorEntity:** `id` (PK), `name`.
2.  **BookEntity:** `id` (PK), `title`, `authorId` (FK refer
enciando a AuthorEntity).

## ⚙️ Requisitos de Instalación
1.  Clonar el repositorio.
2.  Abrir con **Android Studio Ladybug** (o superior).
3.  SDK Mínimo: **API 26 (Android 8.0)**.

4.  Target SDK: **API 35**.

---

**Desarrollado como proyecto de aprendizaje en arquitectura MVVM y persistencia de datos.**