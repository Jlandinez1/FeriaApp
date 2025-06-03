package com.feriaApp.Services;

import com.feriaApp.models.FeriaConAsistentesDTO;
import com.feriaApp.repository.AsistenciaRepositorio;
import com.feriaApp.repository.FeriaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    @Autowired
    private FeriaRepositorio feriaRepositorio;

    @Autowired
    private AsistenciaRepositorio asistenciaRepositorio;

    // Obtener listado de ferias con cantidad de asistentes confirmados
    public List<FeriaConAsistentesDTO> getReporteAsistencias() {
        return feriaRepositorio.findAll().stream()
                .map(feria -> {
                    long totalAsistentes = asistenciaRepositorio.findByFeriaIdAndConfirmada(feria.getId(), true).size();
                    return new FeriaConAsistentesDTO(
                            feria.getId(),
                            feria.getNombre(),
                            feria.getFechaInicio(),
                            feria.getFechaFin(),
                            feria.getUbicacion(),
                            totalAsistentes
                    );
                })
                .collect(Collectors.toList());
    }
}