package br.com.dbc.wbhealth.service;

import br.com.dbc.wbhealth.model.entity.Pessoa;
import br.com.dbc.wbhealth.model.enumarator.TipoEmail;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    private final Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${spring.mail.username}")
    private String emailSuporte;

    public void sendEmailAtendimento(Pessoa pessoa, TipoEmail tipoEmail) throws MessagingException {
        sendTemplateEmail(pessoa, "email-atendimento.ftl", tipoEmail);
    }

    public void sendTemplateEmail(Pessoa paciente, String estrutura, TipoEmail tipoEmail) throws MessagingException {
        MimeMessage emailTemplate = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(emailTemplate, true);

        try {
            helper.setFrom(from);
            helper.setTo(paciente.getEmail());
            helper.setSubject(tipoEmail.getTitulo());
            helper.setText(getContentFromTemplate(paciente, estrutura, tipoEmail), true);

            mailSender.send(helper.getMimeMessage());

        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private String getContentFromTemplate(Pessoa paciente, String estruturaTemplate, TipoEmail tipoEmail) throws IOException, TemplateException {
        Map<String, String> dados = new HashMap<>();
        dados.put("nome", paciente.getNome());
        dados.put("id", paciente.getIdPessoa().toString());
        dados.put("emailSuporte", emailSuporte);

        StringBuilder estruturaDaMensagem = new StringBuilder();

        switch (tipoEmail) {
            case CONFIRMACAO -> estruturaDaMensagem.append("Seu atendimento foi confirmado!");
            case ATUALIZACAO -> estruturaDaMensagem.append("Seu atendimento foi atulizado!");
            case CANCELAMENTO -> estruturaDaMensagem.append("Seu atendimento foi desmarcado!");
        }

        dados.put("mensagem", estruturaDaMensagem.toString());

        Template template = fmConfiguration.getTemplate(estruturaTemplate);
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);

        return html;
    }

}
