package com.feriaApp.Services;

import com.feriaApp.repository.AsistenciaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeriaService {

    @Autowired
    private AsistenciaRepositorio asistenciaRepository;

    // Aquí puedes poner clienteId si tienes el contexto del cliente actual para validar asistencia
    public boolean estaAsistenciaConfirmada(String feriaId, String clienteId) {
        return asistenciaRepository.findByFeriaIdAndClienteIdAndConfirmada(feriaId, clienteId, true).isPresent();
    }
}
