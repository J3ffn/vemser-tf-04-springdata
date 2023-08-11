package br.com.dbc.wbhealth.service;

import br.com.dbc.wbhealth.exceptions.BancoDeDadosException;
import br.com.dbc.wbhealth.exceptions.EntityNotFound;
import br.com.dbc.wbhealth.model.dto.medico.MedicoInputDTO;
import br.com.dbc.wbhealth.model.dto.medico.MedicoOutputDTO;
import br.com.dbc.wbhealth.model.entity.Medico;
import br.com.dbc.wbhealth.repository.MedicoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;
    @Autowired
    private ObjectMapper objectMapper;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

//    public boolean buscarCpf(Medico medico) {
//        return medicoRepository.buscarCpf(medico);
//    }


//    objectMapper.registerModule(new JavaTimeModule());


//    public Medico buscarId(Integer id) throws BancoDeDadosException {
//        return medicoRepository.findById(id);

    public MedicoOutputDTO save(MedicoInputDTO medicoInputDTO) {
        Medico medico = new Medico();
        medico = objectMapper.convertValue(medicoInputDTO, Medico.class);
        MedicoOutputDTO medicoOutputDTO = new MedicoOutputDTO();
        try {
            Medico medicoAtualizado = medicoRepository.save(medico);
            medicoOutputDTO = objectMapper.convertValue(medicoAtualizado, MedicoOutputDTO.class);
//            String cpf = medicoInputDTO.getCpf().replaceAll("[^0-9]", "");
//            if (cpf.length() != 11) {
//                throw new Exception("CPF Invalido!");
//            }
//            medico.setCpf(cpf);

//            String cep = medico.getCep().replaceAll("[^0-9]", "");
//            if (cep.length() != 8) {
//                throw new Exception("CEP inválido! Deve conter exatamente 8 dígitos numéricos.");
//            }

//            String crm = medicoInputDTO.getCrm().replaceAll("^[A-Z]{2}-\\\\d{7}/\\\\d{2}$", "");
//            if(crm.length() != 13){
//                throw new Exception("CRM invalido. Deve ser digitado no formato: UF-1234567/89.");
//            }
//            medico.setCep(cep);
//            medicoRepository.save(medico);
//            novoMedico= medico;
//            System.out.println(CoresMenu.VERDE_BOLD + "\nOperação realizada com sucesso!" + CoresMenu.RESET);

        } catch (BancoDeDadosException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unnexpected error: " + e.getMessage());
        }
        return medicoOutputDTO;
    }

    public List<MedicoOutputDTO> findAll() throws BancoDeDadosException {
        return medicoRepository.findAll()
                .stream()
                .map(medico -> objectMapper.convertValue(medico, MedicoOutputDTO.class))
                .toList();
    }

    public MedicoOutputDTO findById(Integer id) throws BancoDeDadosException, EntityNotFound {
        Medico medico = medicoRepository.findById(id);
        return objectMapper.convertValue(medico, MedicoOutputDTO.class);
    }

    public MedicoOutputDTO update(Integer idMedico, MedicoInputDTO medicoInputDTO) throws BancoDeDadosException, EntityNotFound {
        Medico medico = new Medico();
        try {
            Medico medicoAux = medicoRepository.findAll().stream()
                    .filter(x -> x.getIdMedico() == idMedico)
                    .findFirst().orElseThrow(() -> new EntityNotFound("Id não encontrado"));
            medicoAux.setCpf(medicoInputDTO.getCpf());
            medicoAux.setCrm(medicoInputDTO.getCrm());
            medicoAux.setCep(medicoInputDTO.getCep());
            medicoAux.setNome(medicoInputDTO.getNome());
            medicoAux.setDataNascimento(medicoInputDTO.getDataNascimento());
            medicoAux.setSalarioMensal(medicoInputDTO.getSalarioMensal());
            medicoAux.setEmail(medicoInputDTO.getEmail());
            medico = medicoRepository.findById(idMedico);

        } catch (BancoDeDadosException e) {
            e.printStackTrace();

        } catch (EntityNotFound e) {
            throw new EntityNotFound("Medico não encontrado!");
        }
        return objectMapper.convertValue(medico, MedicoOutputDTO.class);
    }

    public String deletarPeloId(Integer id) throws EntityNotFound {
        String retorno = new String();
        try {
            boolean removeu = medicoRepository.deleteById(id);
            if (removeu) {
                retorno = "Medico deletado com sucesso.";
            }

        } catch (BancoDeDadosException | EntityNotFound e) {
            e.printStackTrace();
        }
        return retorno;

    }

}
