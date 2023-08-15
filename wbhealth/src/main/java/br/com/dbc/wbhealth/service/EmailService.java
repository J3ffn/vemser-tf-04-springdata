package br.com.dbc.wbhealth.service;

import br.com.dbc.wbhealth.model.entity.Pessoa;
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
//            helper.setText(getContentFromTemplate(paciente, "email-teste.ftl", tipoEmail), true);
            helper.setText(testeFreemarker(), true);

            mailSender.send(helper.getMimeMessage());

        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private String testeFreemarker() throws IOException, TemplateException {

        Map<String, Object> dados = new HashMap<>();

        String imagemLogo = "";
        String facebookIcon = "";
        String instagramIcon = "";
        String iconeBase = "https://uploaddeimagens.com.br/images/004/574/927/full/Icone_WB.png?1691946339";
        String iconeTipo = "";
        String iconeData = "";
        String iconeLaudo = "";
        String iconeMedico = "";
        String iconeValor = "";


        dados.put("nome", "Jeff");
        dados.put("titulo", imagemLogo);

        dados.put("tipoIcon", iconeTipo);
        dados.put("tipo", "CONSULTA");
        dados.put("dataIcon", iconeData);
        dados.put("data", LocalDate.now().toString());
        dados.put("laudoIcon", iconeLaudo);
        dados.put("laudo", "Dor de cabeça");
        dados.put("medicoIcon", iconeMedico);
        dados.put("medico", "Girisval");
        dados.put("valorIcon", iconeValor);
        dados.put("valor", 255);

        dados.put("facebook", facebookIcon);
        dados.put("instagram", instagramIcon);
        dados.put("icone", iconeBase);
        dados.put("emailSuporte", "jeff@teste.com.br");

        Template template = fmConfiguration.getTemplate("email-templateHtml.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);

        System.out.println(html);

        return html;
    }

    private String getContentFromTemplate(Pessoa paciente, String estruturaTemplate, TipoEmail tipoEmail) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", paciente.getNome());
        dados.put("id", paciente.getIdPessoa().toString());
        dados.put("emailSuporte", emailSuporte);
        File imagem = new ClassPathResource("templates/imagens/Logo_WBHEALTH.png").getFile();
//        DataSource fds = new FileDataSource(JavaMailSender.class.getClassLoader().getResource("images/image1.jpg").getFile());

        dados.put("titulo", imagem);
        dados.put("facebook", "../resources/templates/imagens/Facebook.png");
        dados.put("instagram", "../resources/templates/imagens/Instagram.png");
        dados.put("icone", "../resources/templates/imagens/Icone_WB.png");

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
