package br.com.dbc.wbhealth.service;

import br.com.dbc.wbhealth.model.entity.Paciente;
import br.com.dbc.wbhealth.model.enumarator.TipoEmail;
import freemarker.template.Configuration;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;
import java.time.LocalDate;

class EmailServiceTest {

    private EmailService emailService;

    @Test
    void sendEmailAtendimento() throws MessagingException {
        emailService = new EmailService(new JavaMailSenderImpl(), new Configuration());
        Paciente paciente = new Paciente();
        paciente.setEmail("sacwbhealth@gmail.com");
        paciente.setIdPaciente(1);
        paciente.setCpf("12345678901");
        paciente.setCep("58970000");
        paciente.setNome("Jeff");
        paciente.setDataNascimento(LocalDate.now());
        paciente.setIdPessoa(1);
        paciente.setSalarioMensal(800.0);
        emailService.sendEmailAtendimento(paciente, TipoEmail.CONFIRMACAO);
    }
}