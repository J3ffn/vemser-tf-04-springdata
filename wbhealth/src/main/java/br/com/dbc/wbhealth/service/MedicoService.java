package br.com.dbc.wbhealth.service;

import br.com.dbc.wbhealth.exceptions.BancoDeDadosException;
import br.com.dbc.wbhealth.exceptions.EntityNotFound;
import br.com.dbc.wbhealth.model.dto.medico.MedicoInputDTO;
import br.com.dbc.wbhealth.model.dto.medico.MedicoOutputDTO;
import br.com.dbc.wbhealth.model.entity.MedicoEntity;
import br.com.dbc.wbhealth.model.entity.PacienteEntity;
import br.com.dbc.wbhealth.model.entity.PessoaEntity;
import br.com.dbc.wbhealth.repository.MedicoRepository;
import br.com.dbc.wbhealth.repository.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@RequiredArgsConstructor
@Service
@RequiredArgsConstructor
public class MedicoService {

    //    private final PessoaRepository pessoaRepository;
    private final MedicoRepository medicoRepository;
    private final PessoaRepository pessoaRepository;
    private final ObjectMapper objectMapper;

    public List<MedicoOutputDTO> findAll() throws BancoDeDadosException {
        return medicoRepository.findAll().stream().map(this::converterMedicoOutput).toList();
    }

    public MedicoOutputDTO findById(Integer idMedico) throws BancoDeDadosException, EntityNotFound {
        MedicoEntity medicoEncontrado = medicoRepository.findById(idMedico).get();
        return converterMedicoOutput(medicoEncontrado);
    }

    public MedicoOutputDTO save(MedicoInputDTO medicoInputDTO){

        // Crie a pessoaEntity a partir dos dados do DTO
        PessoaEntity pessoaEntity = new PessoaEntity(
                medicoInputDTO.getNome(),
                medicoInputDTO.getCep(),
                medicoInputDTO.getDataNascimento(),
                medicoInputDTO.getCpf(),
                medicoInputDTO.getSalarioMensal(),
                medicoInputDTO.getEmail()
        );

        // Salva a pessoaEntity no banco
        PessoaEntity pessoaSave = pessoaRepository.save(pessoaEntity);

        // Crie o médico associado à pessoa criada
        MedicoEntity medico = new MedicoEntity();
        medico.setPessoa(pessoaSave);
        medico.setCrm(medicoInputDTO.getCrm());
        medico.setIdHospital(medicoInputDTO.getIdHospital());

        // Salva a pessoaEntity no banco
        MedicoEntity medicoAtualizado = medicoRepository.save(medico);

        // Converte médico para MedicoOutputDTO
        MedicoOutputDTO medicoOutputDTO = objectMapper.convertValue(medicoAtualizado, MedicoOutputDTO.class);

        return medicoOutputDTO;
    }


    public MedicoOutputDTO update(Integer idMedico, MedicoInputDTO medicoInputDTO) throws BancoDeDadosException, EntityNotFound {
        MedicoEntity medico = objectMapper.convertValue(medicoInputDTO, MedicoEntity.class);

        MedicoEntity medicoUpdated = medicoRepository.findById(idMedico).get();
        /*medicoUpdated.setNome(medico.getNome());
        medicoUpdated.setCep(medico.getCep());
        medicoUpdated.setDataNascimento(medico.getDataNascimento());
        medicoUpdated.setCpf(medico.getCpf());
        medicoUpdated.setSalarioMensal(medico.getSalarioMensal());
        medicoUpdated.setEmail(medico.getEmail());*/
        medicoUpdated.setIdHospital(medico.getIdHospital());
        medicoUpdated.setCrm(medico.getCrm());

        return objectMapper.convertValue(
                medicoRepository.save(medicoUpdated), MedicoOutputDTO.class
        );
    }

    public String deletarPeloId(Integer id) throws EntityNotFound {
        String retorno = new String();

        return retorno;

    }

    public MedicoEntity convertInputToMedico(MedicoInputDTO medicoInput) {
        MedicoEntity medicoEntity = objectMapper.convertValue(medicoInput, MedicoEntity.class);

        return medicoEntity;
    }

    public MedicoOutputDTO converterMedicoOutput(MedicoEntity medico) {
        MedicoOutputDTO medicoOutput = objectMapper.convertValue(medico, MedicoOutputDTO.class);

        return medicoOutput;
    }

}
