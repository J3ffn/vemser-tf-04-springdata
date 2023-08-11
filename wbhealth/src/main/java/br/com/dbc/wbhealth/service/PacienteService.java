package br.com.dbc.wbhealth.service;

import br.com.dbc.wbhealth.exceptions.BancoDeDadosException;
import br.com.dbc.wbhealth.exceptions.EntityNotFound;
import br.com.dbc.wbhealth.model.dto.paciente.PacienteInputDTO;
import br.com.dbc.wbhealth.model.dto.paciente.PacienteOutputDTO;
import br.com.dbc.wbhealth.model.entity.Paciente;
import br.com.dbc.wbhealth.repository.PacienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PacienteService {
    private final PacienteRepository pacienteRepository;
    private final ObjectMapper objectMapper;

    public List<PacienteOutputDTO> findAll() throws BancoDeDadosException {
        return pacienteRepository.findAll()
                .stream().map(paciente -> objectMapper.convertValue(paciente, PacienteOutputDTO.class))
                .toList();
    }

    public PacienteOutputDTO findById(Integer idPaciente) throws BancoDeDadosException, EntityNotFound {
        Paciente pacienteEncontrado = pacienteRepository.findById(idPaciente);
        return objectMapper.convertValue(pacienteEncontrado, PacienteOutputDTO.class);
    }

    public PacienteOutputDTO save(PacienteInputDTO pacienteInput) throws BancoDeDadosException {
        Paciente paciente = objectMapper.convertValue(pacienteInput, Paciente.class);
        Paciente novoPaciente = pacienteRepository.save(paciente);
        return objectMapper.convertValue(novoPaciente, PacienteOutputDTO.class);
    }

    public PacienteOutputDTO update(Integer idPaciente, PacienteInputDTO pacienteInput)
            throws BancoDeDadosException, EntityNotFound {
        Paciente pacienteModificado = objectMapper.convertValue(pacienteInput, Paciente.class);
        Paciente pacienteAtualizado = pacienteRepository.update(idPaciente, pacienteModificado);
        return objectMapper.convertValue(pacienteAtualizado, PacienteOutputDTO.class);
    }

    public void deleteById(Integer idPaciente) throws BancoDeDadosException, EntityNotFound {
        pacienteRepository.deleteById(idPaciente);
    }

    /*public boolean buscarCpf(Paciente paciente) throws BancoDeDadosException {
        return pacienteRepository.buscarCpf(paciente);
    }*/
}
