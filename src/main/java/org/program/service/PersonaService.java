package org.program.service;

import org.program.dao.PersonaDAO;
import org.program.model.Domicilio;
import org.program.model.Persona;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class PersonaService implements GenericService<Persona> {

    private final PersonaDAO personaDAO = new PersonaDAO();
    private final DomicilioService domicilioService = new DomicilioService(); // reutilizamos validación

    @Override
    public void guardar(Persona persona) throws SQLException {
        validar(persona);
        personaDAO.guardar(persona);
    }

    @Override
    public void actualizar(Persona persona) throws SQLException {
        validar(persona);
        personaDAO.actualizar(persona);
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        personaDAO.eliminar(id);
    }

    @Override
    public Optional<Persona> buscarPorId(Long id) throws SQLException {
        return personaDAO.buscarPorId(id);
    }

    @Override
    public List<Persona> listarTodos() throws SQLException {
        return personaDAO.listarTodos();
    }

    private void validar(Persona p) {
        if (p == null) throw new IllegalArgumentException("La persona no puede ser null.");
        // if (p.getId() == null) throw new IllegalArgumentException("La persona debe tener un ID.");
        if (p.getNombre() == null || p.getNombre().isBlank()) throw new IllegalArgumentException("El nombre no puede estar vacío.");
        if (p.getApellido() == null || p.getApellido().isBlank()) throw new IllegalArgumentException("El apellido no puede estar vacío.");
        domicilioService.validar(p.getDomicilio());
    }
}