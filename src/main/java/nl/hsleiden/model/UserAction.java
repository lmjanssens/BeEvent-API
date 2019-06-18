package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import nl.hsleiden.View;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_action")
public class UserAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userActionID", columnDefinition = "serial")
    @JsonView(View.Public.class)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    @JsonBackReference("actionUserRef")
    private User user;

    @NotNull
    @Column(name = "action")
    @JsonView(View.Public.class)
    private String action;

    @NotNull
    @Column(name = "date")
    @JsonView(View.Public.class)
    private String timestamp;
}
