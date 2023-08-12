package br.com.dbc.wbhealth.service;

import br.com.dbc.wbhealth.exceptions.EntityNotFound;
import br.com.dbc.wbhealth.model.dto.paciente.PacienteInputDTO;
import br.com.dbc.wbhealth.model.dto.paciente.PacienteOutputDTO;
import br.com.dbc.wbhealth.model.entity.PacienteEntity;
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

    public List<PacienteOutputDTO> findAll(){
        return pacienteRepository.findAll().stream().map(this::convertPacienteToOutput).toList();
    }

    public PacienteOutputDTO findById(Integer idPaciente) throws EntityNotFound {
        PacienteEntity pacienteEncontrado = getPacienteById(idPaciente);
        return convertPacienteToOutput(pacienteEncontrado);
    }

    public PacienteOutputDTO save(PacienteInputDTO pacienteInput){
        PacienteEntity paciente = convertInputToPaciente(pacienteInput);
        PacienteEntity pacienteCriado = pacienteRepository.save(paciente);
        return convertPacienteToOutput(pacienteCriado);
    }

    public PacienteOutputDTO update(Integer idPaciente, PacienteInputDTO pacienteInput) throws EntityNotFound {
        PacienteEntity paciente = getPacienteById(idPaciente);
        PacienteEntity pacienteModificado = convertInputToPaciente(pacienteInput);

        paciente.setNome(pacienteModificado.getNome());
        paciente.setCep(pacienteModificado.getCep());
        paciente.setDataNascimento(pacienteModificado.getDataNascimento());
        paciente.setCpf(pacienteModificado.getCpf());
        paciente.setSalarioMensal(pacienteModificado.getSalarioMensal());
        paciente.setEmail(pacienteModificado.getEmail());

        PacienteEntity pacienteAtualizado = pacienteRepository.save(paciente);
        return convertPacienteToOutput(pacienteAtualizado);
    }

    public void delete(Integer idPaciente) throws EntityNotFound {
        PacienteEntity paciente = getPacienteById(idPaciente);
        pacienteRepository.delete(paciente);
    }

    private PacienteEntity getPacienteById(Integer idPaciente) throws EntityNotFound {
        return pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new EntityNotFound("Paciente não encontrado"));
    }

    private PacienteEntity convertInputToPaciente(PacienteInputDTO pacienteInput){
        return objectMapper.convertValue(pacienteInput, PacienteEntity.class);
    }

    private PacienteOutputDTO convertPacienteToOutput(PacienteEntity paciente){
        return objectMapper.convertValue(paciente, PacienteOutputDTO.class);
    }
}
