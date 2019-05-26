package nl.hsleiden.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_action")
public class UserAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userActionID", columnDefinition = "serial")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private User user;

    @NotNull
    @Column(name = "action")
    private String action;

    @NotNull
    @Column(name = "date")
    private String timestamp;
}
