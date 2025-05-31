package org.program.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Persona extends Base {
    private String nombre;
    private String apellido;
    private Domicilio domicilio;
}