package modelos;

public class Pelicula {
    private int id;
    private String titulo;
    private String genero;
    private int duracion;
    private double presupuesto;

    public Pelicula(int id, String titulo, String genero, int duracion, double presupuesto) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.duracion = duracion;
        this.presupuesto = presupuesto;
    }

    public Pelicula(String titulo, String genero, int duracion, double presupuesto) {
        this.titulo = titulo;
        this.genero = genero;
        this.duracion = duracion;
        this.presupuesto = presupuesto;
    }

    public Pelicula() {
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", genero='" + genero + '\'' +
                ", duracion=" + duracion +
                ", presupuesto=" + presupuesto +
                '}';
    }
}
