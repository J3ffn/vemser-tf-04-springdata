package br.com.dbc.wbhealth.service;

import br.com.dbc.wbhealth.exceptions.BancoDeDadosException;
import br.com.dbc.wbhealth.exceptions.EntityNotFound;
import br.com.dbc.wbhealth.model.dto.atendimento.AtendimentoInputDTO;
import br.com.dbc.wbhealth.model.dto.atendimento.AtendimentoOutputDTO;
import br.com.dbc.wbhealth.model.dto.hospital.HospitalOutputDTO;
import br.com.dbc.wbhealth.model.entity.Atendimento;
import br.com.dbc.wbhealth.model.entity.HospitalEntity;
import br.com.dbc.wbhealth.model.entity.Paciente;
import br.com.dbc.wbhealth.model.enumarator.TipoEmail;
import br.com.dbc.wbhealth.repository.AtendimentoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor

@Service
public class AtendimentoService {

    private final AtendimentoRepository atendimentoRepository;

    private final ObjectMapper objectMapper;

    private final EmailService emailService;

    private final PacienteService pacienteService;

    private final MedicoService medicoService;

    private final HospitalService hospitalService;

    private void enviarEmails(AtendimentoInputDTO atendimento, TipoEmail tipo) throws MessagingException, BancoDeDadosException, EntityNotFound {
//        Paciente paciente = objectMapper.convertValue(pacienteService.findById(atendimento.getIdPaciente()), Paciente.class);
//        Medico medico = objectMapper.convertValue(medicoService.findById(atendimento.getIdMedico()), Medico.class);
//
//        emailService.sendEmailAtendimento(paciente, tipo);
//        emailService.sendEmailAtendimento(medico, tipo);

//        Paciente paciente = new Paciente();
//        paciente.setEmail("sacwbhealth@gmail.com");
//        paciente.setIdPaciente(1);
//        paciente.setCpf("12345678901");
//        paciente.setCep("58970000");
//        paciente.setNome("Jeff");
//        paciente.setDataNascimento(LocalDate.now());
//        paciente.setIdPessoa(1);
//        paciente.setSalarioMensal(800.0);
//        emailService.sendEmailAtendimento(paciente, tipo);
    }

    private void verificarIdentificadores(AtendimentoInputDTO atendimentoDeEntrada) throws BancoDeDadosException, EntityNotFound {
//        Integer idPaciente = atendimentoDeEntrada.getIdPaciente();
//        Integer idHospital = atendimentoDeEntrada.getIdHospital();
//        Integer idMedico = atendimentoDeEntrada.getIdMedico();
//
//        hospitalService.findById(idHospital);
//        medicoService.findById(idMedico);
//        pacienteService.findById(idPaciente);
    }

    public AtendimentoOutputDTO save(AtendimentoInputDTO atendimentoNovo) throws BancoDeDadosException, EntityNotFound, MessagingException {
        verificarIdentificadores(atendimentoNovo);

        HospitalEntity hospital = objectMapper.convertValue(hospitalService.findById(atendimentoNovo.getIdHospital()), HospitalEntity.class);

        Atendimento atendimento = objectMapper.convertValue(atendimentoNovo, Atendimento.class);

        atendimento.setHospitalEntity(hospital);

        atendimento = atendimentoRepository.save(atendimento);

        enviarEmails(atendimentoNovo, TipoEmail.CONFIRMACAO);

        AtendimentoOutputDTO atendimentoOutputDTO = objectMapper.convertValue(atendimento, AtendimentoOutputDTO.class);
        atendimentoOutputDTO.setIdHospital(hospital.getIdHospital());

        return atendimentoOutputDTO;
    }

    public List<AtendimentoOutputDTO> findAll() throws BancoDeDadosException {
        return atendimentoRepository.findAll()
                .stream()
                .map(atendimento -> objectMapper.convertValue(atendimento, AtendimentoOutputDTO.class))
                .toList();
    }

    public AtendimentoOutputDTO findById(Integer id) throws BancoDeDadosException, EntityNotFound {
        return objectMapper.convertValue(atendimentoRepository.findById(id.longValue()), AtendimentoOutputDTO.class);
    }

    public List<AtendimentoOutputDTO> bucarAtendimentoPeloIdUsuario(Integer idPaciente) throws BancoDeDadosException {
        return findAll()
                .stream()
                .filter(atendimento -> atendimento.getIdPaciente().equals(idPaciente))
                .toList();
    }

    public AtendimentoOutputDTO update(Integer idAtendimento, AtendimentoInputDTO atendimentoAtualizado) throws BancoDeDadosException, EntityNotFound, MessagingException {
        verificarIdentificadores(atendimentoAtualizado);

        Atendimento atendimentoConvertido = objectMapper.convertValue(atendimentoAtualizado, Atendimento.class);
        Atendimento atendimentoModificado = atendimentoRepository.save(atendimentoConvertido);
        atendimentoModificado.setIdAtendimento(idAtendimento);

        enviarEmails(atendimentoAtualizado, TipoEmail.ATUALIZACAO);
        return objectMapper.convertValue(atendimentoModificado, AtendimentoOutputDTO.class);
    }

    public void deletarPeloId(Integer id) throws EntityNotFound {
        try {
            AtendimentoInputDTO atendimento = objectMapper.convertValue(findById(id), AtendimentoInputDTO.class);

            atendimentoRepository.deleteById(id.longValue());

            enviarEmails(atendimento, TipoEmail.CANCELAMENTO);

        } catch (BancoDeDadosException | MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
