package br.com.dbc.wbhealth.service;

import br.com.dbc.wbhealth.model.entity.AtendimentoEntity;
import br.com.dbc.wbhealth.model.entity.PacienteEntity;
import br.com.dbc.wbhealth.model.entity.PessoaEntity;
import br.com.dbc.wbhealth.model.enumarator.TipoEmail;
import br.com.dbc.wbhealth.utils.ImageConverter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import javassist.ClassPath;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    private final Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${spring.mail.username}")
    private String emailSuporte;

    public void sendEmailAtendimento(AtendimentoEntity atendimento, TipoEmail tipoEmail) throws MessagingException {
        sendTemplateEmail(atendimento, tipoEmail);
    }

    public void sendTemplateEmail(AtendimentoEntity atendimento, TipoEmail tipoEmail) throws MessagingException {
        MimeMessage emailTemplate = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(emailTemplate, true);
        PessoaEntity paciente = atendimento.getPacienteEntity().getPessoa();

        this.enviatParaPaciente(helper, paciente, tipoEmail, atendimento);
    }

    private void enviatParaPaciente(MimeMessageHelper helper, PessoaEntity pessoa, TipoEmail tipoEmail, AtendimentoEntity atendimento) {
        try {
            helper.setFrom(from);
            helper.setTo(pessoa.getEmail());
            helper.setSubject(tipoEmail.getTitulo());
            helper.setText(getContentFromTemplate(pessoa, tipoEmail, atendimento), true);

            mailSender.send(helper.getMimeMessage());

        } catch (IOException | TemplateException | MessagingException e) {
            e.printStackTrace();
        }
    }

    private String getContentFromTemplate(PessoaEntity paciente, TipoEmail tipoEmail, AtendimentoEntity atendimento) throws IOException, TemplateException {
        String estruturaTemplate = null;
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", paciente.getNome());
        dados.put("id", paciente.getIdPessoa().toString());
        dados.put("emailSuporte", emailSuporte);

        dados.put("tipo", atendimento.getTipoDeAtendimento());
        dados.put("data", atendimento.getDataAtendimento());
        dados.put("laudo", atendimento.getLaudo());
        dados.put("medico", paciente.getNome());
        dados.put("valor", atendimento.getValorDoAtendimento());

        StringBuilder estruturaDaMensagem = new StringBuilder();

        switch (tipoEmail) {
            case CONFIRMACAO -> {
                estruturaTemplate = "email-templateHtmlConfirmacao.ftl";
                estruturaDaMensagem.append("Seu atendimento foi confirmado!");
            }
            case ATUALIZACAO -> {
                estruturaTemplate = "email-templateHtmlAtualizacao.ftl";
                estruturaDaMensagem.append("Seu atendimento foi atulizado!");
            }
            case CANCELAMENTO -> {
                estruturaTemplate = "email-templateHtmlCancelamento.ftl";
                estruturaDaMensagem.append("Seu atendimento foi desmarcado!");
            }
        }

        dados.put("mensagem", estruturaDaMensagem.toString());

        Template template = fmConfiguration.getTemplate(estruturaTemplate);

        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);

        return html;
    }

}
