import dao.ActorDAO;
import dao.PeliculaDao;
import modelos.Actor;
import modelos.Pelicula;

public class Main {
    public static void main(String[] args) {
        PeliculaDao peliDao = new PeliculaDao();
        ActorDAO actorDao = new ActorDAO();

        Pelicula nuevaPelicula = new Pelicula("Todo sobre mi madre", "Drama", 101, 4500000);
        Actor actor = new Actor("Antonia San Juan", "España", 64);

        //peliDao.insertarPelicula(nuevaPelicula);
        //System.out.println("Película añadida");

        //nuevaPelicula.setPresupuesto(4550000);
        //peliDao.actualizarPelicula(nuevaPelicula, 11);
        //System.out.println("Datos actualizados");

        //peliDao.eliminarPelicula(11);
        //System.out.println("Película eliminada");

        //actorDao.insertarActor(actor);
        //System.out.println("Actor insertado");

        //actor.setEdad(65);
        //actorDao.actualizarActor(actor, 11);
        //System.out.println("Actor actualizado");

        //actorDao.eliminarActor(11);
        //System.out.println("Actor borrado");

        //actorDao.asignarActorAPelicula(1, 2, "Personaje de prueba");
        //System.out.println("Actor id=1 asignado a película id=2.");
        //actorDao.eliminarActorDePelicula(1, 2);
        //System.out.println("Actor id=1 eliminado de película id=2.");

        System.out.println("Todas las películas con total de actores:");
        System.out.println(peliDao.obtenerPeliculasYTotalActores());
        System.out.println("₍^. .^₎⟆");

        System.out.println("Actores de la película con id=1:");
        System.out.println(peliDao.obtenerActoresPorPelicula(1));
        System.out.println("₍^. .^₎⟆");

        System.out.println("Películas con más de 3 actores:");
        System.out.println(peliDao.obtenerPeliculasConMasDe3Actores());
        System.out.println("₍^. .^₎⟆");

        System.out.println("Top 3 películas por presupuesto:");
        peliDao.obtenerTop3PorPresupuesto().forEach(System.out::println);
        System.out.println("₍^. .^₎⟆");

        System.out.println("Película más larga del género 'Acción':");
        System.out.println(peliDao.obtenerMasLargaPorGenero("Acción"));
        System.out.println("₍^. .^₎⟆");

        System.out.println("Actores por nacionalidad:");
        System.out.println(actorDao.obtenerActoresPorNacionalidad());
        System.out.println("₍^. .^₎⟆");

        System.out.println("Edad media de los actores:");
        System.out.printf("%.2f años%n", actorDao.obtenerEdadMedia());
        System.out.println("₍^. .^₎⟆");

        System.out.println("Actores sin película:");
        System.out.println(actorDao.obtenerActoresSinPelicula());
        System.out.println("₍^. .^₎⟆");
    }
}