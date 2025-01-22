package foro.hub.api.controller;

import foro.hub.api.domain.topico.Topico;
import foro.hub.api.domain.topico.TopicoRepository;
import foro.hub.api.domain.topico.dto.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    public ResponseEntity<ConsultaTopicoDTO> registrarTopicos(@RequestBody @Valid CrearTopicoDTO datosRegistroTopicos,
                                                              UriComponentsBuilder uriComponentsBuilder) {
        Topico topico = topicoRepository.save(new Topico(datosRegistroTopicos));
        ConsultaTopicoDTO datosConsultaTopico = new ConsultaTopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getEstado(),
                topico.getAutor(),
                topico.getCurso().toString());
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosConsultaTopico);
    }

    @GetMapping
    public ResponseEntity<Page<ListadoTopicoDTO>> ListadoTopicos(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findAll(paginacion).map(ListadoTopicoDTO::new));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> actualizarTopico(@PathVariable Long id,
                                              @RequestBody @Valid ActualizarTopicoDTO datosActualizarTopico) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);

        if (optionalTopico.isPresent()) {
            Topico topico = optionalTopico.get();
            topico.actualizarDatos(datosActualizarTopico);
            topicoRepository.save(topico);

            var datosTopico = new RespuestaTopicoDTO(
                    topico.getTitulo(),
                    topico.getMensaje(),
                    topico.getAutor(),
                    topico.getCurso().toString()
            );
            return ResponseEntity.ok(datosTopico);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El tópico con ID " + id + " no fue encontrado.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerTopicoPorId(@PathVariable Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);

        if (optionalTopico.isPresent()) {
            Topico topico = optionalTopico.get();
            var datosTopico = new ConsultaTopicoDTO(
                    topico.getId(),
                    topico.getTitulo(),
                    topico.getMensaje(),
                    topico.getFechaCreacion(),
                    topico.getEstado(),
                    topico.getAutor(),
                    topico.getCurso().toString()
            );
            return ResponseEntity.ok(datosTopico);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El tópico con ID " + id + " no fue encontrado.");
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);

        if (optionalTopico.isPresent()) {
            Topico topico = optionalTopico.get();
            topico.desactivarTopico();
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El tópico con ID " + id + " no fue encontrado.");
        }
    }
}
