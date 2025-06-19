package org.example.server.email;


import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class EmailTemplate {

    private final TemplateEngine templateEngine;

    public EmailTemplate(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String buildEmail(String name, String link) {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("link", link);
        return templateEngine.process("email-verification-template", context);
    }

}
