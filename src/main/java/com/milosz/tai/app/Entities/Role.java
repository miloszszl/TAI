package com.milosz.tai.app.Entities;

import com.milosz.tai.app.Entities.Parameters.RoleName;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

import static com.milosz.tai.app.Entities.Parameters.ConstraintValues.USER_ROLE_NAME_LENGTH;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, length = USER_ROLE_NAME_LENGTH)
    @NaturalId
    @Enumerated(EnumType.STRING)
    private RoleName name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }
}
