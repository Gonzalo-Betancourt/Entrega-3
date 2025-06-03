package org.program.service;

import org.program.dao.DomicilioDAO;
import org.program.model.Domicilio;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DomicilioService implements GenericService<Domicilio> {

    private final DomicilioDAO domicilioDAO = new DomicilioDAO();

    @Override
    public void guardar(Domicilio d) throws SQLException {
        validar(d);
        domicilioDAO.guardar(d);
    }

    @Override
    public void actualizar(Domicilio d) throws SQLException {
        validar(d);
        domicilioDAO.actualizar(d);
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        domicilioDAO.eliminar(id);
    }

    @Override
    public Optional<Domicilio> buscarPorId(Long id) throws SQLException {
        return domicilioDAO.buscarPorId(id);
    }

    @Override
    public List<Domicilio> listarTodos() throws SQLException {
        return domicilioDAO.listarTodos();
    }

    public void validar(Domicilio d) {
        if (d == null) throw new IllegalArgumentException("El domicilio no puede ser null.");
        if (d.getCalle() == null || d.getCalle().isBlank()) throw new IllegalArgumentException("La calle no puede estar vacía.");
        if (d.getNumero() <= 0) throw new IllegalArgumentException("El número debe ser mayor a 0.");
    }
}