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
 * A Acompanhamento.
 */
@Entity
@Table(name = "acompanhamento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Acompanhamento implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome_do_acompanhamento", nullable = false)
    private String nomeDoAcompanhamento;

    @ManyToMany(mappedBy = "acompanhamentos")
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

    public String getNomeDoAcompanhamento() {
        return nomeDoAcompanhamento;
    }

    public Acompanhamento nomeDoAcompanhamento(String nomeDoAcompanhamento) {
        this.nomeDoAcompanhamento = nomeDoAcompanhamento;
        return this;
    }

    public void setNomeDoAcompanhamento(String nomeDoAcompanhamento) {
        this.nomeDoAcompanhamento = nomeDoAcompanhamento;
    }

    public Set<Cardapio> getCardapios() {
        return cardapios;
    }

    public Acompanhamento cardapios(Set<Cardapio> cardapios) {
        this.cardapios = cardapios;
        return this;
    }

    public Acompanhamento addCardapio(Cardapio cardapio) {
        this.cardapios.add(cardapio);
        cardapio.getAcompanhamentos().add(this);
        return this;
    }

    public Acompanhamento removeCardapio(Cardapio cardapio) {
        this.cardapios.remove(cardapio);
        cardapio.getAcompanhamentos().remove(this);
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
        Acompanhamento acompanhamento = (Acompanhamento) o;
        if (acompanhamento.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), acompanhamento.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Acompanhamento{" +
            "id=" + getId() +
            ", nomeDoAcompanhamento='" + getNomeDoAcompanhamento() + "'" +
            "}";
    }
}
