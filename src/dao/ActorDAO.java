package dao;

import modelos.Actor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActorDAO {
    private String url = "jdbc:mysql://127.0.0.1:3306/productora";
    private String user = "root";
    private String password = "1234";

    public void insertarActor(Actor actor) {
        String sql = "INSERT INTO actores (nombre, nacionalidad, edad) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, actor.getNombre());
            ps.setString(2, actor.getNacionalidad());
            ps.setInt(3, actor.getEdad());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                actor.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void actualizarActor(Actor actor) {
        String sql = "UPDATE actores SET nombre = ?, nacionalidad = ?, edad = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, actor.getNombre());
            ps.setString(2, actor.getNacionalidad());
            ps.setInt(3, actor.getEdad());
            ps.setInt(4, actor.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void eliminarActor(int id) {
        String sql = "DELETE FROM actores WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void asignarActorAPelicula(int actorId, int peliculaId, String personaje) {
        String sql = "INSERT INTO reparto (actor_id, pelicula_id, personaje) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, actorId);
            ps.setInt(2, peliculaId);
            ps.setString(3, personaje);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void eliminarActorDePelicula(int actorId, int peliculaId) {
        String sql = "DELETE FROM reparto WHERE actor_id = ? AND pelicula_id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, actorId);
            ps.setInt(2, peliculaId);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public List<String> obtenerActoresPorNacionalidad() {
        String sql = "SELECT nacionalidad, COUNT(*) AS total " +
                "FROM actores GROUP BY nacionalidad ORDER BY total DESC";
        List<String> resultado = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                resultado.add("Nacionalidad: " + rs.getString("nacionalidad") +
                        " | Total: " + rs.getInt("total"));
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return resultado;
    }

    public double obtenerEdadMedia() {
        String sql = "SELECT AVG(edad) AS edad_media FROM actores";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("edad_media");
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return 0;
    }

    public List<Actor> obtenerActoresSinPelicula() {
        String sql = "SELECT a.id, a.nombre, a.nacionalidad, a.edad " +
                "FROM actores a LEFT JOIN reparto r ON a.id = r.actor_id " +
                "WHERE r.actor_id IS NULL";
        List<Actor> actores = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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
}