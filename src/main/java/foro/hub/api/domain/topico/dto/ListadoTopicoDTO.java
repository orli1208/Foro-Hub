package foro.hub.api.domain.topico.dto;

import foro.hub.api.domain.topico.Topico;

import java.time.LocalDateTime;

public record ListadoTopicoDTO(Long id,
                               String titulo,
                               String mensaje,
                               LocalDateTime fechaCreacion,
                               Boolean estado,
                               String autor,
                               String curso) {

    public ListadoTopicoDTO(Topico topico){
        this(topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getEstado(),
                topico.getAutor(),
                topico.getCurso());
    }
}
