package com.mycompany.myapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PratoPrincipal.
 */
@Entity
@Table(name = "prato_principal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PratoPrincipal implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome_do_prato", nullable = false)
    private String nomeDoPrato;

    @ManyToMany(mappedBy = "principals")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Cardapio> cardapios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeDoPrato() {
        return nomeDoPrato;
    }

    public PratoPrincipal nomeDoPrato(String nomeDoPrato) {
        this.nomeDoPrato = nomeDoPrato;
        return this;
    }

    public void setNomeDoPrato(String nomeDoPrato) {
        this.nomeDoPrato = nomeDoPrato;
    }

    public Set<Cardapio> getCardapios() {
        return cardapios;
    }

    public PratoPrincipal cardapios(Set<Cardapio> cardapios) {
        this.cardapios = cardapios;
        return this;
    }

    public PratoPrincipal addCardapio(Cardapio cardapio) {
        this.cardapios.add(cardapio);
        cardapio.getPrincipals().add(this);
        return this;
    }

    public PratoPrincipal removeCardapio(Cardapio cardapio) {
        this.cardapios.remove(cardapio);
        cardapio.getPrincipals().remove(this);
        return this;
    }

    public void setCardapios(Set<Cardapio> cardapios) {
        this.cardapios = cardapios;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PratoPrincipal pratoPrincipal = (PratoPrincipal) o;
        if (pratoPrincipal.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pratoPrincipal.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PratoPrincipal{" +
            "id=" + getId() +
            ", nomeDoPrato='" + getNomeDoPrato() + "'" +
            "}";
    }
}
