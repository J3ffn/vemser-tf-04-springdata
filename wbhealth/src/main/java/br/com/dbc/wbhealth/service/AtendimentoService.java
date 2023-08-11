package br.com.dbc.wbhealth.service;

import br.com.dbc.wbhealth.exceptions.BancoDeDadosException;
import br.com.dbc.wbhealth.exceptions.EntityNotFound;
import br.com.dbc.wbhealth.model.dto.atendimento.AtendimentoInputDTO;
import br.com.dbc.wbhealth.model.dto.atendimento.AtendimentoOutputDTO;
import br.com.dbc.wbhealth.model.entity.Atendimento;
import br.com.dbc.wbhealth.model.entity.Medico;
import br.com.dbc.wbhealth.model.entity.Paciente;
import br.com.dbc.wbhealth.model.enumarator.TipoEmail;
import br.com.dbc.wbhealth.repository.AtendimentoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

@Service
public class AtendimentoService {
    private final AtendimentoRepository atendimentoRepository;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    private final PacienteService pacienteService;
    private final MedicoService medicoService;
    private final HospitalService hospitalService;

    @Autowired
    public AtendimentoService(AtendimentoRepository atendimentoRepository, ObjectMapper objectMapper,
                              EmailService emailService, PacienteService pacienteService,
                              MedicoService medicoService, HospitalService hospitalService) {
        this.atendimentoRepository = atendimentoRepository;
        this.objectMapper = objectMapper;
        this.emailService = emailService;
        this.pacienteService = pacienteService;
        this.medicoService = medicoService;
        this.hospitalService = hospitalService;
    }

    private void enviarEmails(AtendimentoInputDTO atendimento, TipoEmail tipo) throws MessagingException, BancoDeDadosException, EntityNotFound {
        Paciente paciente = objectMapper.convertValue(pacienteService.findById(atendimento.getIdPaciente()), Paciente.class);
        Medico medico = objectMapper.convertValue(medicoService.findById(atendimento.getIdMedico()), Medico.class);

        emailService.sendEmailAtendimento(paciente, tipo);
        emailService.sendEmailAtendimento(medico, tipo);
    }

    private void verificarIdentificadores(AtendimentoInputDTO atendimentoDeEntrada) throws BancoDeDadosException, EntityNotFound {
        Integer idPaciente = atendimentoDeEntrada.getIdPaciente();
        Integer idHospital = atendimentoDeEntrada.getIdHospital();
        Integer idMedico = atendimentoDeEntrada.getIdMedico();

        hospitalService.findById(idHospital);
        medicoService.findById(idMedico);
        pacienteService.findById(idPaciente);
    }

    public AtendimentoOutputDTO save(AtendimentoInputDTO atendimentoNovo) throws BancoDeDadosException, EntityNotFound, MessagingException {
        verificarIdentificadores(atendimentoNovo);

        Atendimento atendimento = objectMapper.convertValue(atendimentoNovo, Atendimento.class);
        atendimento = atendimentoRepository.save(atendimento);

        enviarEmails(atendimentoNovo, TipoEmail.CONFIRMACAO);
        return objectMapper.convertValue(atendimento, AtendimentoOutputDTO.class);
    }

    public List<AtendimentoOutputDTO> findAll() throws BancoDeDadosException {
        return atendimentoRepository.findAll()
                .stream()
                .map(atendimento -> objectMapper.convertValue(atendimento, AtendimentoOutputDTO.class))
                .toList();
    }

    public AtendimentoOutputDTO findById(Integer id) throws BancoDeDadosException, EntityNotFound {
        return objectMapper.convertValue(atendimentoRepository.findById(id), AtendimentoOutputDTO.class);
    }

    public List<AtendimentoOutputDTO> bucarAtendimentoPeloIdUsuario(Integer idPaciente) throws BancoDeDadosException {
        return findAll()
                .stream()
                .filter(atendimento -> atendimento.getIdPaciente().equals(idPaciente))
                .toList();
    }

    public AtendimentoOutputDTO update(Integer id, AtendimentoInputDTO atendimentoAtualizado) throws BancoDeDadosException, EntityNotFound, MessagingException {
        verificarIdentificadores(atendimentoAtualizado);

        Atendimento atendimentoConvertido = objectMapper.convertValue(atendimentoAtualizado, Atendimento.class);
        Atendimento atendimentoModificado = atendimentoRepository.update(id, atendimentoConvertido);
        atendimentoModificado.setIdAtendimento(id);

        enviarEmails(atendimentoAtualizado, TipoEmail.ATUALIZACAO);
        return objectMapper.convertValue(atendimentoModificado, AtendimentoOutputDTO.class);
    }

    public void deletarPeloId(Integer id) throws EntityNotFound {
        try {
            AtendimentoInputDTO atendimento = objectMapper.convertValue(findById(id), AtendimentoInputDTO.class);

            atendimentoRepository.deleteById(id);

            enviarEmails(atendimento, TipoEmail.CANCELAMENTO);

        } catch (BancoDeDadosException | MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
