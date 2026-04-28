package dao;

import modelos.Actor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActorDAO {
    private String url = "jdbc:mysql://127.0.0.1:3306/productora";
    private String user = "root";
    private String password = "1234";

    public int insertarActor(Actor actor) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO actores (nombre, nacionalidad, edad) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, actor.getNombre());
            pstmt.setString(2, actor.getNacionalidad());
            pstmt.setInt(3, actor.getEdad());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return -1;
    }

    public void actualizarActor(Actor actor, int id) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "UPDATE actores SET nombre = ?, nacionalidad = ?, edad = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, actor.getNombre());
            pstmt.setString(2, actor.getNacionalidad());
            pstmt.setInt(3, actor.getEdad());
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void eliminarActor(int id) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "DELETE FROM actores WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void asignarActorAPelicula(int actorId, int peliculaId, String personaje) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO reparto (actor_id, pelicula_id, personaje) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, actorId);
            pstmt.setInt(2, peliculaId);
            pstmt.setString(3, personaje);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void eliminarActorDePelicula(int actorId, int peliculaId) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "DELETE FROM reparto WHERE actor_id = ? AND pelicula_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, actorId);
            pstmt.setInt(2, peliculaId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public List<String> obtenerActoresPorNacionalidad() {
        List<String> resultado = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT nacionalidad, COUNT(*) AS total " +
                    "FROM actores GROUP BY nacionalidad ORDER BY total DESC";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                resultado.add("Nacionalidad: " + rs.getString("nacionalidad") + " | Total: " + rs.getInt("total"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return resultado;
    }

    public double obtenerEdadMedia() {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT AVG(edad) AS edad_media FROM actores";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("edad_media");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return 0;
    }

    public List<Actor> obtenerActoresSinPelicula() {
        List<Actor> actores = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT a.id, a.nombre, a.nacionalidad, a.edad " +
                    "FROM actores a LEFT JOIN reparto r ON a.id = r.actor_id " +
                    "WHERE r.actor_id IS NULL";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                actores.add(new Actor(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("nacionalidad"),
                        rs.getInt("edad")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return actores;
    }
}