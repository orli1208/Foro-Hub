package foro.hub.api.domain.topico.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ActualizarTopicoDTO(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        Boolean estado,
        String autor,
        String curso ){
}
