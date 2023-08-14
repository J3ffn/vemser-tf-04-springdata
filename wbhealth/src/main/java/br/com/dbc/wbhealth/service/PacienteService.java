package br.com.dbc.wbhealth.service;

import br.com.dbc.wbhealth.exceptions.EntityNotFound;
import br.com.dbc.wbhealth.model.dto.paciente.PacienteInputDTO;
import br.com.dbc.wbhealth.model.dto.paciente.PacienteOutputDTO;
import br.com.dbc.wbhealth.model.entity.PacienteEntity;
import br.com.dbc.wbhealth.model.entity.PessoaEntity;
import br.com.dbc.wbhealth.repository.PacienteRepository;
import br.com.dbc.wbhealth.repository.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PacienteService {
    private final PacienteRepository pacienteRepository;
    private final PessoaRepository pessoaRepository;
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
        pessoaRepository.save(paciente.getPessoa());
        PacienteEntity pacienteCriado = pacienteRepository.save(paciente);
        return convertPacienteToOutput(pacienteCriado);
    }

    public PacienteOutputDTO update(Integer idPaciente, PacienteInputDTO pacienteInput) throws EntityNotFound {
        PacienteEntity paciente = getPacienteById(idPaciente);
        PacienteEntity pacienteModificado = convertInputToPaciente(pacienteInput);

        paciente.getPessoa().setNome(pacienteModificado.getPessoa().getNome());
        paciente.getPessoa().setCep(pacienteModificado.getPessoa().getCep());
        paciente.getPessoa().setDataNascimento(pacienteModificado.getPessoa().getDataNascimento());
        paciente.getPessoa().setCpf(pacienteModificado.getPessoa().getCpf());
        paciente.getPessoa().setSalarioMensal(pacienteModificado.getPessoa().getSalarioMensal());
        paciente.getPessoa().setEmail(pacienteModificado.getPessoa().getEmail());

        pessoaRepository.save(paciente.getPessoa());
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
        PacienteEntity paciente = objectMapper.convertValue(pacienteInput, PacienteEntity.class);

        PessoaEntity pessoa = new PessoaEntity(
                pacienteInput.getNome(),
                pacienteInput.getCep(),
                pacienteInput.getDataNascimento(),
                pacienteInput.getCpf(),
                pacienteInput.getSalarioMensal(),
                pacienteInput.getEmail()
        );

        paciente.setPessoa(pessoa);
        return paciente;
    }

    private PacienteOutputDTO convertPacienteToOutput(PacienteEntity paciente){
        PacienteOutputDTO pacienteOutput = objectMapper.convertValue(paciente, PacienteOutputDTO.class);

        PessoaEntity pessoa = paciente.getPessoa();
        pacienteOutput.setIdPessoa(pessoa.getIdPessoa());
        pacienteOutput.setNome(pessoa.getNome());
        pacienteOutput.setCep(pessoa.getCep());
        pacienteOutput.setDataNascimento(pessoa.getDataNascimento());
        pacienteOutput.setCpf(pessoa.getCpf());
        pacienteOutput.setSalarioMensal(pessoa.getSalarioMensal());
        pacienteOutput.setEmail(pessoa.getEmail());

        return pacienteOutput;
    }
}
