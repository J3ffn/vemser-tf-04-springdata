package br.com.dbc.wbhealth.service;

import br.com.dbc.wbhealth.exceptions.BancoDeDadosException;
import br.com.dbc.wbhealth.exceptions.EntityNotFound;
import br.com.dbc.wbhealth.model.dto.medico.MedicoInputDTO;
import br.com.dbc.wbhealth.model.dto.medico.MedicoOutputDTO;
import br.com.dbc.wbhealth.model.entity.MedicoEntity;
import br.com.dbc.wbhealth.model.entity.PessoaEntity;
import br.com.dbc.wbhealth.repository.MedicoRepository;
import br.com.dbc.wbhealth.repository.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicoService {
    private final MedicoRepository medicoRepository;
    private final PessoaRepository pessoaRepository;
    private final ObjectMapper objectMapper;

    public List<MedicoOutputDTO> findAll(){
        return medicoRepository.findAll().stream().map(this::converterMedicoOutput).toList();
    }

    public MedicoOutputDTO findById(Integer idMedico) throws EntityNotFound {
        MedicoEntity medicoEncontrado = medicoRepository.findById(idMedico).get();
        return converterMedicoOutput(medicoEncontrado);
    }

    public MedicoOutputDTO save(MedicoInputDTO medicoInputDTO){

        PessoaEntity pessoaEntity = convertInputToPessoa(medicoInputDTO);
        PessoaEntity pessoaSave = pessoaRepository.save(pessoaEntity);

        MedicoEntity medico = convertInputToMedico(pessoaSave, medicoInputDTO);
        MedicoEntity medicoAtualizado = medicoRepository.save(medico);

        return converterMedicoOutput(medicoAtualizado);
    }

    public MedicoOutputDTO update(Integer idMedico, MedicoInputDTO medicoInput) throws EntityNotFound {
        PessoaEntity pessoaModificada = convertInputToPessoa(medicoInput);
        MedicoEntity medicoModificado = convertInputToMedico(pessoaModificada, medicoInput);

        MedicoEntity medico = getMédicoById(idMedico);
        PessoaEntity pessoa = medico.getPessoa();

        pessoa.setNome(medicoModificado.getPessoa().getNome());
        pessoa.setCep(medicoModificado.getPessoa().getCep());
        pessoa.setDataNascimento(medicoModificado.getPessoa().getDataNascimento());
        pessoa.setCpf(medicoModificado.getPessoa().getCpf());
        pessoa.setSalarioMensal(medicoModificado.getPessoa().getSalarioMensal());
        pessoa.setEmail(medicoModificado.getPessoa().getEmail());

        pessoaRepository.save(medico.getPessoa());
        MedicoEntity medicoAtualizado = medicoRepository.save(medico);

        return converterMedicoOutput(medicoAtualizado);
    }

    public void delete(Integer idMedico) throws EntityNotFound {
        MedicoEntity medico = getMédicoById(idMedico);
        medicoRepository.delete(medico);
    }

    private MedicoEntity getMédicoById(Integer idMedico) throws EntityNotFound {
        return medicoRepository.findById(idMedico)
                .orElseThrow(() -> new EntityNotFound("Médico não encontrado"));
    }

    private PessoaEntity convertInputToPessoa(MedicoInputDTO medicoInput){
        return new PessoaEntity(
                medicoInput.getNome(),
                medicoInput.getCep(),
                medicoInput.getDataNascimento(),
                medicoInput.getCpf(),
                medicoInput.getSalarioMensal(),
                medicoInput.getEmail()
        );
    }
    public MedicoEntity convertInputToMedico(PessoaEntity pessoa, MedicoInputDTO medicoInput) {
        MedicoEntity medico = new MedicoEntity();
        medico.setPessoa(pessoa);
        medico.setCrm(medicoInput.getCrm());
        medico.setIdHospital(medicoInput.getIdHospital());
        return medico;
    }

    public MedicoOutputDTO converterMedicoOutput(MedicoEntity medico) {
        MedicoOutputDTO medicoOutput = objectMapper.convertValue(medico, MedicoOutputDTO.class);

        PessoaEntity pessoa = medico.getPessoa();
        medicoOutput.setNome(pessoa.getNome());
        medicoOutput.setCep(pessoa.getCep());
        medicoOutput.setDataNascimento(pessoa.getDataNascimento());
        medicoOutput.setCpf(pessoa.getCpf());
        medicoOutput.setSalarioMensal(pessoa.getSalarioMensal());
        medicoOutput.setEmail(pessoa.getEmail());
        medicoOutput.setIdPessoa(pessoa.getIdPessoa());

        return medicoOutput;
    }

}
