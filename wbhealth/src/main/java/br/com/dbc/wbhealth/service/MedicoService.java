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

        PessoaEntity pessoaEntity = new PessoaEntity(
                medicoInputDTO.getNome(),
                medicoInputDTO.getCep(),
                medicoInputDTO.getDataNascimento(),
                medicoInputDTO.getCpf(),
                medicoInputDTO.getSalarioMensal(),
                medicoInputDTO.getEmail()
        );

        PessoaEntity pessoaSave = pessoaRepository.save(pessoaEntity);

        MedicoEntity medico = new MedicoEntity();
        medico.setPessoa(pessoaSave);
        medico.setCrm(medicoInputDTO.getCrm());
        medico.setIdHospital(medicoInputDTO.getIdHospital());

        MedicoEntity medicoAtualizado = medicoRepository.save(medico);

        MedicoOutputDTO medicoOutputDTO = converterMedicoOutput(medicoAtualizado);

        return medicoOutputDTO;
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
        MedicoEntity medicoEntity = objectMapper.convertValue(medicoInput, MedicoEntity.class);

        return medicoEntity;
    }

    public MedicoOutputDTO converterMedicoOutput(MedicoEntity medico) {
        MedicoOutputDTO medicoOutput = objectMapper.convertValue(medico, MedicoOutputDTO.class);

        medicoOutput.setNome(medico.getPessoa().getNome());
        medicoOutput.setCep(medico.getPessoa().getCep());
        medicoOutput.setDataNascimento(medico.getPessoa().getDataNascimento());
        medicoOutput.setCpf(medico.getPessoa().getCpf());
        medicoOutput.setSalarioMensal(medico.getPessoa().getSalarioMensal());
        medicoOutput.setEmail(medico.getPessoa().getEmail());
        medicoOutput.setIdPessoa(medico.getPessoa().getIdPessoa());

        return medicoOutput;
    }

}
