package org.program.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GenericService<T> {
    void guardar(T entidad) throws Exception;
    void actualizar(T entidad) throws Exception;
    void eliminar(Long id) throws Exception;
    Optional<T> buscarPorId(Long id) throws Exception;
    List<T> listarTodos() throws Exception;
}