package dao;

import modelos.Actor;
import modelos.Pelicula;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDao {
    private String url = "jdbc:mysql://127.0.0.1:3306/productora";
    private String user = "root";
    private String password = "1234";

    public int insertarPelicula(Pelicula peli) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO peliculas (titulo, genero, duracion, presupuesto) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, peli.getTitulo());
            pstmt.setString(2, peli.getGenero());
            pstmt.setInt(3, peli.getDuracion());
            pstmt.setDouble(4, peli.getPresupuesto());
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

    public void actualizarPelicula(Pelicula peli, int id) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "UPDATE peliculas SET titulo = ?, genero = ?, duracion = ?, presupuesto = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, peli.getTitulo());
            pstmt.setString(2, peli.getGenero());
            pstmt.setInt(3, peli.getDuracion());
            pstmt.setDouble(4, peli.getPresupuesto());
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void eliminarPelicula(int id) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "DELETE FROM peliculas WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
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
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // AQUÍ: He dejado el add a la lista para que el Main lo pueda imprimir
                resultado.add("Película: " + rs.getString("titulo") + " | Actores: " + rs.getInt("total_actores"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return resultado;
    }

    public List<Actor> obtenerActoresPorPelicula(int peliculaId) {
        List<Actor> actores = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT a.id, a.nombre, a.nacionalidad, a.edad " +
                    "FROM actores a JOIN reparto r ON a.id = r.actor_id " +
                    "WHERE r.pelicula_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, peliculaId);
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

    public List<Pelicula> obtenerPeliculasConMasDe3Actores() {
        List<Pelicula> peliculas = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT p.id, p.titulo, p.genero, p.duracion, p.presupuesto " +
                    "FROM peliculas p JOIN reparto r ON p.id = r.pelicula_id " +
                    "GROUP BY p.id HAVING COUNT(r.actor_id) > 3";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
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
            System.out.println("Error: " + e.getMessage());
        }
        return peliculas;
    }

    public List<Pelicula> obtenerTop3PorPresupuesto() {
        List<Pelicula> peliculas = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT id, titulo, genero, duracion, presupuesto " +
                    "FROM peliculas ORDER BY presupuesto DESC LIMIT 3";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
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
            System.out.println("Error: " + e.getMessage());
        }
        return peliculas;
    }

    public Pelicula obtenerMasLargaPorGenero(String genero) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT id, titulo, genero, duracion, presupuesto " +
                    "FROM peliculas WHERE genero = ? ORDER BY duracion DESC LIMIT 1";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, genero);
            ResultSet rs = pstmt.executeQuery();
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
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}