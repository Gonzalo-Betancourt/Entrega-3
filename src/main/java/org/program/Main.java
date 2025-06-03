package org.program;

import org.program.model.Domicilio;
import org.program.model.Persona;
import org.program.service.DomicilioService;
import org.program.service.PersonaService;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DomicilioService domicilioService = new DomicilioService();
        PersonaService personaService = new PersonaService();

        try {
            // Crear domicilio sin ID
            Domicilio domicilio = Domicilio.builder()
                    .calle("Av. Siempre Viva")
                    .numero(742)
                    .build();

            domicilioService.guardar(domicilio);
            System.out.println("Domicilio guardado con ID: " + domicilio.getId());

            // Crear persona sin ID y con domicilio asociado
            Persona persona = Persona.builder()
                    .nombre("Homero")
                    .apellido("Simpson")
                    .domicilio(domicilio)
                    .build();

            personaService.guardar(persona);
            System.out.println("Persona guardada con ID: " + persona.getId());

            // Buscar persona por ID
            System.out.println("Persona con ID 1: " + personaService.buscarPorId(1L));

            // Listar todas las personas
            List<Persona> personas = personaService.listarTodos();
            System.out.println("Listado de personas:");
            for (Persona p : personas) {
                System.out.println(p);
            }

        } catch (SQLException e) {
            System.err.println("Error de base de datos: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
        }
    }
}
