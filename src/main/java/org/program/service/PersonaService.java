package org.program.service;

import org.program.dao.PersonaDAO;
import org.program.model.Persona;

import java.util.List;
import java.util.Optional;


public class PersonaService implements GenericService<Persona> {

    private final PersonaDAO personaDAO = new PersonaDAO();
    private final DomicilioService domicilioService = new DomicilioService();

    @Override
    public void guardar(Persona persona) throws Exception {
        validar(persona);
        if (persona.getDomicilio().getId() == null) domicilioService.guardar(persona.getDomicilio());
        personaDAO.guardar(persona);
    }

    @Override
    public void actualizar(Persona persona) throws Exception {
        validar(persona);

        if (persona.getDomicilio() != null) {
            if (persona.getDomicilio().getId() == null) {
                domicilioService.guardar(persona.getDomicilio());
            } else {
                domicilioService.actualizar(persona.getDomicilio());
            }
        }

        personaDAO.actualizar(persona);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        personaDAO.eliminar(id);
    }

    @Override
    public Optional<Persona> buscarPorId(Long id) throws Exception {
        return personaDAO.buscarPorId(id);
    }

    @Override
    public List<Persona> listarTodos() throws Exception {
        return personaDAO.listarTodos();
    }

    private void validar(Persona p) {
        if (p == null) throw new IllegalArgumentException("La persona no puede ser null.");
        if (p.getNombre() == null || p.getNombre().isBlank()) throw new IllegalArgumentException("El nombre no puede estar vacío.");
        if (p.getApellido() == null || p.getApellido().isBlank()) throw new IllegalArgumentException("El apellido no puede estar vacío.");
    }
}