package dao;
import modelos.Actor;
import modelos.Pelicula;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PeliculaDao {
    private String url = "jdbc:mysql://127.0.0.1:3306/productora";
    private String user = "root";
    private String password = "1234";

    public void insertarPelicula(Pelicula peli) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO peliculas (titulo, genero, duracion, presupuesto) VALUES " +
                    "(?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, peli.getTitulo());
            ps.setString(2, peli.getGenero());
            ps.setInt(3, peli.getDuracion());
            ps.setDouble(4, peli.getPresupuesto());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void actualizarPelicula(Pelicula peli) {
        String sql = "UPDATE peliculas SET titulo = ?, genero = ?, duracion = ?, presupuesto = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, peli.getTitulo());
            ps.setString(2, peli.getGenero());
            ps.setInt(3, peli.getDuracion());
            ps.setDouble(4, peli.getPresupuesto());
            ps.setInt(5, peli.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void eliminarPelicula(int id) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "DELETE FROM peliculas WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public List<String> obtenerPeliculasYTotalActores() {
        List<String> resultado = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT p.titulo, COUNT(r.actor_id) AS total_actores " +
                    "FROM peliculas p LEFT JOIN reparto r " +
                    "ON p.id = r.pelicula_id GROUP BY p.id";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                String registro = "Película: " + rs.getString("titulo") + " | Actores: " + rs.getInt("total_actores");
                resultado.add(registro);
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return resultado;
    }

    public List<Actor> obtenerActoresPorPelicula(int peliculaId) {
        String sql = "SELECT a.id, a.nombre, a.nacionalidad, a.edad " +
                "FROM actores a JOIN reparto r ON a.id = r.actor_id " +
                "WHERE r.pelicula_id = ?";
        List<Actor> actores = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, peliculaId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                actores.add(new Actor(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("nacionalidad"),
                        rs.getInt("edad")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return actores;
    }

    public List<Pelicula> obtenerPeliculasConMasDe3Actores() {
        String sql = "SELECT p.id, p.titulo, p.genero, p.duracion, p.presupuesto " +
                "FROM peliculas p JOIN reparto r ON p.id = r.pelicula_id " +
                "GROUP BY p.id " +
                "HAVING COUNT(r.actor_id) > 3";
        List<Pelicula> peliculas = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                peliculas.add(new Pelicula(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("genero"),
                        rs.getInt("duracion"),
                        rs.getDouble("presupuesto")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return peliculas;
    }

    public List<Pelicula> obtenerTop3PorPresupuesto() {
        String sql = "SELECT id, titulo, genero, duracion, presupuesto " +
                "FROM peliculas ORDER BY presupuesto DESC LIMIT 3";
        List<Pelicula> peliculas = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                peliculas.add(new Pelicula(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("genero"),
                        rs.getInt("duracion"),
                        rs.getDouble("presupuesto")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return peliculas;
    }

    public Pelicula obtenerMasLargaPorGenero(String genero) {
        String sql = "SELECT id, titulo, genero, duracion, presupuesto " +
                "FROM peliculas WHERE genero = ? ORDER BY duracion DESC LIMIT 1";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, genero);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Pelicula(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("genero"),
                        rs.getInt("duracion"),
                        rs.getDouble("presupuesto")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }
}


