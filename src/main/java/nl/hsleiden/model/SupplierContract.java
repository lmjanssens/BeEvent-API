package nl.hsleiden.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "supplier_contract")
public class SupplierContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contractID", columnDefinition = "serial")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplierID", nullable = false)
    private Supplier supplier;

    @NotNull
    @Length(max = 100)
    @Column(name = "type_contract")
    private boolean typeContract;

    @Length(max = 20)
    @Column(name = "contract_title")
    private String title;

    @NotNull
    @Length(max = 100)
    @Column(name = "contract_description")
    private String description;

    @NotNull
    @Column(name = "contract_incl_btw")
    private String incluBtw;

    @NotNull
    @Column(name = "contract_excl_btw")
    private String supervisor;

    @Length(max = 150)
    @Column(name = "contract_btw_percentage")
    private int percentage;

    @Column(name = "contract_preconditions")
    private String preconditions;

    @Column(name = "contract_insurance")
    private String insurance;

    @Column(name = "contract_responsibility")
    private String responsibility;

    @Column(name = "contract_extras")
    private String extras;
}
