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
        PessoaEntity pessoa = convertInputToPessoa(pacienteInput);
        PessoaEntity pessoaCriada = pessoaRepository.save(pessoa);

        PacienteEntity paciente = convertInputToPaciente(pessoaCriada, pacienteInput);
        PacienteEntity pacienteCriado = pacienteRepository.save(paciente);

        return convertPacienteToOutput(pacienteCriado);
    }

    public PacienteOutputDTO update(Integer idPaciente, PacienteInputDTO pacienteInput) throws EntityNotFound {
        PessoaEntity pessoaModificada = convertInputToPessoa(pacienteInput);
        PacienteEntity pacienteModificado = convertInputToPaciente(pessoaModificada, pacienteInput);

        PacienteEntity paciente = getPacienteById(idPaciente);
        PessoaEntity pessoa = paciente.getPessoa();

        pessoa.setNome(pacienteModificado.getPessoa().getNome());
        pessoa.setCep(pacienteModificado.getPessoa().getCep());
        pessoa.setDataNascimento(pacienteModificado.getPessoa().getDataNascimento());
        pessoa.setCpf(pacienteModificado.getPessoa().getCpf());
        pessoa.setSalarioMensal(pacienteModificado.getPessoa().getSalarioMensal());
        pessoa.setEmail(pacienteModificado.getPessoa().getEmail());

        pessoaRepository.save(paciente.getPessoa());
        PacienteEntity pacienteAtualizado = pacienteRepository.save(paciente);
        return convertPacienteToOutput(pacienteAtualizado);
    }

    public void delete(Integer idPaciente) throws EntityNotFound {
        PacienteEntity paciente = getPacienteById(idPaciente);
        pacienteRepository.delete(paciente);
    }

    protected PacienteEntity getPacienteById(Integer idPaciente) throws EntityNotFound {
        return pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new EntityNotFound("Paciente não encontrado"));
    }

    private PessoaEntity convertInputToPessoa(PacienteInputDTO pacienteInput){
        return new PessoaEntity(
                pacienteInput.getNome(),
                pacienteInput.getCep(),
                pacienteInput.getDataNascimento(),
                pacienteInput.getCpf(),
                pacienteInput.getSalarioMensal(),
                pacienteInput.getEmail()
        );
    }

    private PacienteEntity convertInputToPaciente(PessoaEntity pessoa, PacienteInputDTO pacienteInput){
        PacienteEntity paciente = new PacienteEntity();
        paciente.setPessoa(pessoa);
        paciente.setIdHospital(pacienteInput.getIdHospital());
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
