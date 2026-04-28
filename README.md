# 🎬 Gestión de Productora de Cine

Aplicación Java para gestionar películas y actores de una productora de cine, con conexión a base de datos MySQL mediante JDBC.

---

## 📁 Estructura del proyecto

```
src/
├── Main.java
├── modelos/
│   ├── Pelicula.java
│   └── Actor.java
└── dao/
    ├── PeliculaDao.java
    └── ActorDao.java
```

---

## 🗄️ Base de datos

La base de datos se llama `productora` y contiene tres tablas:

- **peliculas** — almacena las películas con su título, género, duración y presupuesto.
- **actores** — almacena los actores con su nombre, nacionalidad y edad.
- **reparto** — tabla intermedia que relaciona actores y películas, con el nombre del personaje.

### Configuración de la conexión

```
URL:      jdbc:mysql://127.0.0.1:3306/productora
Usuario:  root
Password: 1234
```

---

## 📦 Modelos

### Pelicula

| Campo | Tipo |
|-------|------|
| id | int |
| titulo | String |
| genero | String |
| duracion | int |
| presupuesto | double |

### Actor

| Campo | Tipo |
|-------|------|
| id | int |
| nombre | String |
| nacionalidad | String |
| edad | int |

---

## 🎥 PeliculaDao

| Método | Descripción |
|--------|-------------|
| `insertarPelicula(Pelicula)` | Inserta una nueva película |
| `actualizarPelicula(Pelicula)` | Actualiza todos los campos según el ID |
| `eliminarPelicula(int id)` | Elimina una película por su ID |
| `obtenerPeliculasYTotalActores()` | Devuelve todas las películas con su número de actores |
| `obtenerActoresPorPelicula(int id)` | Devuelve los actores de una película dado su ID |
| `obtenerPeliculasConMasDe3Actores()` | Devuelve las películas con más de 3 actores |
| `obtenerTop3PorPresupuesto()` | Devuelve las 3 películas con mayor presupuesto |
| `obtenerMasLargaPorGenero(String genero)` | Devuelve la película más larga de un género |

---

## 🎭 ActorDao

| Método | Descripción |
|--------|-------------|
| `insertarActor(Actor)` | Inserta un nuevo actor |
| `actualizarActor(Actor)` | Actualiza todos los campos según el ID |
| `eliminarActor(int id)` | Elimina un actor por su ID |
| `asignarActorAPelicula(int actorId, int peliculaId, String personaje)` | Asigna un actor a una película con su personaje |
| `eliminarActorDePelicula(int actorId, int peliculaId)` | Elimina un actor de una película |
| `obtenerActoresPorNacionalidad()` | Devuelve el número de actores por nacionalidad |
| `obtenerEdadMedia()` | Devuelve la edad media de todos los actores |
| `obtenerActoresSinPelicula()` | Devuelve los actores que no participan en ninguna película |

---

## ▶️ Ejecución

En el `Main.java` las operaciones de inserción, actualización y borrado están comentadas para poder ejecutar el programa varias veces sin alterar los datos. Para probarlas, descomenta el bloque correspondiente.

```java
//peliculaDao.insertarPelicula(peli);
//System.out.println("Película insertada: " + peli);
//peli.setTitulo("Todo sobre mi madre (Almodóvar)");
//peliculaDao.actualizarPelicula(peli);
//System.out.println("Película actualizada: " + peli);
//peliculaDao.eliminarPelicula(11);
//System.out.println("Película borrada");
```

---

## 🛠️ Tecnologías

- Java
- MySQL
- JDBC
