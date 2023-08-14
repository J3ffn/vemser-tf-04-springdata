package br.com.dbc.wbhealth.service;

import br.com.dbc.wbhealth.exceptions.BancoDeDadosException;
import br.com.dbc.wbhealth.exceptions.EntityNotFound;
import br.com.dbc.wbhealth.model.dto.medico.MedicoInputDTO;
import br.com.dbc.wbhealth.model.dto.medico.MedicoOutputDTO;
import br.com.dbc.wbhealth.model.entity.MedicoEntity;
import br.com.dbc.wbhealth.repository.MedicoRepository;
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
    private final ObjectMapper objectMapper;

    public List<MedicoOutputDTO> findAll() throws BancoDeDadosException {
        List<MedicoEntity> medicos = medicoRepository.findAll();
        List<MedicoOutputDTO> medicosOutput = new ArrayList<>();

        for(MedicoEntity medico : medicos) {
            medicosOutput.add(objectMapper.convertValue(medico, MedicoOutputDTO.class));
        }
        return medicosOutput;
    }

    public MedicoOutputDTO findById(Integer idMedico) throws BancoDeDadosException, EntityNotFound {
        MedicoEntity medico = medicoRepository.findById(idMedico).get();
        return objectMapper.convertValue(medico, MedicoOutputDTO.class);
    }

    public MedicoOutputDTO save(MedicoInputDTO medicoInputDTO) {
        MedicoEntity medico = objectMapper.convertValue(medicoInputDTO, MedicoEntity.class);
        MedicoEntity medicoAtualizado = medicoRepository.save(medico);


//        pessoaRepository.save(medico);

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

}
