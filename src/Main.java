import dao.ActorDAO;
import dao.PeliculaDao;
import modelos.Pelicula;

public class Main {
    public static void main(String[] args) {
        PeliculaDao pelidao = new PeliculaDao();
        ActorDAO actordao = new ActorDAO();

        Pelicula nuevaPelicula = new Pelicula( "Todo sobre mi madre", "Drama", 101, 4500000);
    }
}