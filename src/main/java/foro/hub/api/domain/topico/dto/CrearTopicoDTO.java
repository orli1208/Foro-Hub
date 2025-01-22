package foro.hub.api.domain.topico.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CrearTopicoDTO(@NotBlank
                             String titulo,
                             @NotBlank
                             String mensaje,
                             @NotBlank
                             String autor,
                             @NotBlank
                             String curso
                             ) {
}
