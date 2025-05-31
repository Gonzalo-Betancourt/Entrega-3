package org.program.dao;

import org.program.config.DatabaseConnection;
import org.program.model.Persona;
import org.program.model.Domicilio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonaDAO implements GenericDAO<Persona> {

    @Override
    public void guardar(Persona persona) throws SQLException {
        String sql = "INSERT INTO persona (nombre, apellido, domicilio_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getApellido());
            ps.setLong(3, persona.getDomicilio().getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Fallo al insertar persona, no se afectaron filas.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    persona.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("No se pudo obtener el ID generado para persona.");
                }
            }
        }
    }

    @Override
    public void actualizar(Persona persona) throws SQLException {
        String sql = "UPDATE persona SET nombre = ?, apellido = ?, domicilio_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, persona.getNombre());
            ps.setString(2, persona.getApellido());
            ps.setLong(3, persona.getDomicilio().getId());
            ps.setLong(4, persona.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "DELETE FROM persona WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Optional<Persona> buscarPorId(Long id) throws SQLException {
        String sql = "SELECT p.id, p.nombre, p.apellido, d.id as d_id, d.calle, d.numero " +
                "FROM persona p LEFT JOIN domicilio d ON p.domicilio_id = d.id WHERE p.id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Domicilio domicilio = Domicilio.builder()
                            .id(rs.getLong("d_id"))
                            .calle(rs.getString("calle"))
                            .numero(rs.getInt("numero"))
                            .build();

                    Persona persona = Persona.builder()
                            .id(rs.getLong("id"))
                            .nombre(rs.getString("nombre"))
                            .apellido(rs.getString("apellido"))
                            .domicilio(domicilio)
                            .build();

                    return Optional.of(persona);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Persona> listarTodos() throws SQLException {
        String sql = "SELECT p.id, p.nombre, p.apellido, d.id as d_id, d.calle, d.numero " +
                "FROM persona p LEFT JOIN domicilio d ON p.domicilio_id = d.id";

        List<Persona> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Domicilio domicilio = Domicilio.builder()
                        .id(rs.getLong("d_id"))
                        .calle(rs.getString("calle"))
                        .numero(rs.getInt("numero"))
                        .build();

                Persona persona = Persona.builder()
                        .id(rs.getLong("id"))
                        .nombre(rs.getString("nombre"))
                        .apellido(rs.getString("apellido"))
                        .domicilio(domicilio)
                        .build();

                lista.add(persona);
            }
        }
        return lista;
    }
}