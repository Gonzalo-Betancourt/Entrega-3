package org.program.dao;

import org.program.config.DatabaseConnection;
import org.program.model.Domicilio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DomicilioDAO implements GenericDAO<Domicilio> {

    @Override
    public void guardar(Domicilio domicilio) throws SQLException {
        String sql = "INSERT INTO domicilio (calle, numero) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, domicilio.getCalle());
            ps.setInt(2, domicilio.getNumero());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Fallo al insertar domicilio, no se afectaron filas.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    domicilio.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("No se pudo obtener el ID generado para domicilio.");
                }
            }
        }
    }

    @Override
    public void actualizar(Domicilio domicilio) throws SQLException {
        String sql = "UPDATE domicilio SET calle = ?, numero = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, domicilio.getCalle());
            ps.setInt(2, domicilio.getNumero());
            ps.setLong(3, domicilio.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM domicilio WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Optional<Domicilio> buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM domicilio WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Domicilio domicilio = Domicilio.builder()
                            .id(rs.getLong("id"))
                            .calle(rs.getString("calle"))
                            .numero(rs.getInt("numero"))
                            .build();
                    return Optional.of(domicilio);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Domicilio> listarTodos() throws SQLException {
        String sql = "SELECT * FROM domicilio";
        List<Domicilio> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Domicilio domicilio = Domicilio.builder()
                        .id(rs.getLong("id"))
                        .calle(rs.getString("calle"))
                        .numero(rs.getInt("numero"))
                        .build();
                lista.add(domicilio);
            }
        }
        return lista;
    }
}