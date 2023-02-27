package com.idevhub.tapas.domain;

import com.idevhub.tapas.domain.enumeration.PrivilegeType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "_privilege_category")
@EqualsAndHashCode(of = "code")
@ToString(of = "code")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Accessors(chain = true)
public class PrivilegeCategory {

    @Id
    private String code;

    private String fullNameUkr;

    private String fullNameEng;

    private String customsHouse;

    private boolean isGlobal = true;

    @Enumerated(EnumType.STRING)
    private PrivilegeType privilegeCategoryType;

    @OneToMany(mappedBy = "privilegeCategory", fetch = FetchType.EAGER)
    private Set<Privilege> privileges;

    public PrivilegeCategory(String code) {
        this.code = code;
    }

    public PrivilegeCategory() {
    }
}
