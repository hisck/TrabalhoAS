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
 * A Salada.
 */
@Entity
@Table(name = "salada")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Salada implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome_da_salada", nullable = false)
    private String nomeDaSalada;

    @ManyToMany(mappedBy = "saladas")
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

    public String getNomeDaSalada() {
        return nomeDaSalada;
    }

    public Salada nomeDaSalada(String nomeDaSalada) {
        this.nomeDaSalada = nomeDaSalada;
        return this;
    }

    public void setNomeDaSalada(String nomeDaSalada) {
        this.nomeDaSalada = nomeDaSalada;
    }

    public Set<Cardapio> getCardapios() {
        return cardapios;
    }

    public Salada cardapios(Set<Cardapio> cardapios) {
        this.cardapios = cardapios;
        return this;
    }

    public Salada addCardapio(Cardapio cardapio) {
        this.cardapios.add(cardapio);
        cardapio.getSaladas().add(this);
        return this;
    }

    public Salada removeCardapio(Cardapio cardapio) {
        this.cardapios.remove(cardapio);
        cardapio.getSaladas().remove(this);
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
        Salada salada = (Salada) o;
        if (salada.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), salada.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Salada{" +
            "id=" + getId() +
            ", nomeDaSalada='" + getNomeDaSalada() + "'" +
            "}";
    }
}
