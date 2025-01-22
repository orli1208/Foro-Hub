package foro.hub.api.domain.topico.dto;

import java.time.LocalDateTime;

public record ConsultaTopicoDTO(Long id,
                                String titulo,
                                String mensaje,
                                LocalDateTime fechaCreacion,
                                boolean status,
                                String autor,
                                String curso) {
}
