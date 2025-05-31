package org.program.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GenericService<T> {
    void guardar(T entidad) throws SQLException;
    void actualizar(T entidad) throws SQLException;
    void eliminar(Long id) throws SQLException;
    Optional<T> buscarPorId(Long id) throws SQLException;
    List<T> listarTodos() throws SQLException;
}