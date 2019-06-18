package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "email_text")
public class EmailText {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emailtextid", columnDefinition = "serial")
    @JsonView(View.Public.class)
    private Long id;

    @NotNull
    @Length(max = 255)
    @Column(name = "email_type")
    @JsonView(View.Public.class)
    private String emailType;

    @NotNull
    @Length(max = 255)
    @Column(name = "email_text")
    @JsonView(View.Public.class)
    private String emailText;

    public EmailText(@NotNull @Length(max = 255) String emailType, @NotNull @Length(max = 255) String emailText) {
        this.emailType = emailType;
        this.emailText = emailText;
    }

    public EmailText () {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public String getEmailText() {
        return emailText;
    }

    public void setEmailText(String emailText) {
        this.emailText = emailText;
    }
}
