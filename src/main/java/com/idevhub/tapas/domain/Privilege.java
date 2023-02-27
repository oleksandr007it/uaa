package com.idevhub.tapas.domain;

import com.idevhub.tapas.domain.enumeration.PrivilegeType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Data
@Table(name = "_privilege")
@EqualsAndHashCode(of = "code")
@ToString(of="code")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Accessors(chain = true)
public class Privilege {

    @Id
    private String code;

    private String fullNameUkr;

    private String fullNameEng;

    @Enumerated(EnumType.STRING)
    private PrivilegeType privilegeType;

    @ManyToOne
    @JoinColumn(name = "category_code")
    private PrivilegeCategory privilegeCategory;

    public Privilege() {
    }

    public Privilege(String code) {
        this.code = code;
    }
}
