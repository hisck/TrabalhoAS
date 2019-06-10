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
 * A Sobremesa.
 */
@Entity
@Table(name = "sobremesa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sobremesa implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome_da_sobremesa", nullable = false)
    private String nomeDaSobremesa;

    @ManyToMany(mappedBy = "sobremesas")
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

    public String getNomeDaSobremesa() {
        return nomeDaSobremesa;
    }

    public Sobremesa nomeDaSobremesa(String nomeDaSobremesa) {
        this.nomeDaSobremesa = nomeDaSobremesa;
        return this;
    }

    public void setNomeDaSobremesa(String nomeDaSobremesa) {
        this.nomeDaSobremesa = nomeDaSobremesa;
    }

    public Set<Cardapio> getCardapios() {
        return cardapios;
    }

    public Sobremesa cardapios(Set<Cardapio> cardapios) {
        this.cardapios = cardapios;
        return this;
    }

    public Sobremesa addCardapio(Cardapio cardapio) {
        this.cardapios.add(cardapio);
        cardapio.getSobremesas().add(this);
        return this;
    }

    public Sobremesa removeCardapio(Cardapio cardapio) {
        this.cardapios.remove(cardapio);
        cardapio.getSobremesas().remove(this);
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
        Sobremesa sobremesa = (Sobremesa) o;
        if (sobremesa.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sobremesa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sobremesa{" +
            "id=" + getId() +
            ", nomeDaSobremesa='" + getNomeDaSobremesa() + "'" +
            "}";
    }
}
