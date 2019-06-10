package com.mycompany.myapp.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Cardapio.
 */
@Entity
@Table(name = "cardapio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cardapio implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "dia", nullable = false)
    private String dia;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "cardapio_principal",
               joinColumns = @JoinColumn(name = "cardapio_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "principal_id", referencedColumnName = "id"))
    private Set<PratoPrincipal> principals = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "cardapio_acompanhamento",
               joinColumns = @JoinColumn(name = "cardapio_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "acompanhamento_id", referencedColumnName = "id"))
    private Set<Acompanhamento> acompanhamentos = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "cardapio_salada",
               joinColumns = @JoinColumn(name = "cardapio_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "salada_id", referencedColumnName = "id"))
    private Set<Salada> saladas = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "cardapio_vegetariano",
               joinColumns = @JoinColumn(name = "cardapio_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "vegetariano_id", referencedColumnName = "id"))
    private Set<Vegetariano> vegetarianos = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "cardapio_sobremesa",
               joinColumns = @JoinColumn(name = "cardapio_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "sobremesa_id", referencedColumnName = "id"))
    private Set<Sobremesa> sobremesas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDia() {
        return dia;
    }

    public Cardapio dia(String dia) {
        this.dia = dia;
        return this;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Set<PratoPrincipal> getPrincipals() {
        return principals;
    }

    public Cardapio principals(Set<PratoPrincipal> pratoPrincipals) {
        this.principals = pratoPrincipals;
        return this;
    }

    public Cardapio addPrincipal(PratoPrincipal pratoPrincipal) {
        this.principals.add(pratoPrincipal);
        pratoPrincipal.getCardapios().add(this);
        return this;
    }

    public Cardapio removePrincipal(PratoPrincipal pratoPrincipal) {
        this.principals.remove(pratoPrincipal);
        pratoPrincipal.getCardapios().remove(this);
        return this;
    }

    public void setPrincipals(Set<PratoPrincipal> pratoPrincipals) {
        this.principals = pratoPrincipals;
    }

    public Set<Acompanhamento> getAcompanhamentos() {
        return acompanhamentos;
    }

    public Cardapio acompanhamentos(Set<Acompanhamento> acompanhamentos) {
        this.acompanhamentos = acompanhamentos;
        return this;
    }

    public Cardapio addAcompanhamento(Acompanhamento acompanhamento) {
        this.acompanhamentos.add(acompanhamento);
        acompanhamento.getCardapios().add(this);
        return this;
    }

    public Cardapio removeAcompanhamento(Acompanhamento acompanhamento) {
        this.acompanhamentos.remove(acompanhamento);
        acompanhamento.getCardapios().remove(this);
        return this;
    }

    public void setAcompanhamentos(Set<Acompanhamento> acompanhamentos) {
        this.acompanhamentos = acompanhamentos;
    }

    public Set<Salada> getSaladas() {
        return saladas;
    }

    public Cardapio saladas(Set<Salada> saladas) {
        this.saladas = saladas;
        return this;
    }

    public Cardapio addSalada(Salada salada) {
        this.saladas.add(salada);
        salada.getCardapios().add(this);
        return this;
    }

    public Cardapio removeSalada(Salada salada) {
        this.saladas.remove(salada);
        salada.getCardapios().remove(this);
        return this;
    }

    public void setSaladas(Set<Salada> saladas) {
        this.saladas = saladas;
    }

    public Set<Vegetariano> getVegetarianos() {
        return vegetarianos;
    }

    public Cardapio vegetarianos(Set<Vegetariano> vegetarianos) {
        this.vegetarianos = vegetarianos;
        return this;
    }

    public Cardapio addVegetariano(Vegetariano vegetariano) {
        this.vegetarianos.add(vegetariano);
        vegetariano.getCardapios().add(this);
        return this;
    }

    public Cardapio removeVegetariano(Vegetariano vegetariano) {
        this.vegetarianos.remove(vegetariano);
        vegetariano.getCardapios().remove(this);
        return this;
    }

    public void setVegetarianos(Set<Vegetariano> vegetarianos) {
        this.vegetarianos = vegetarianos;
    }

    public Set<Sobremesa> getSobremesas() {
        return sobremesas;
    }

    public Cardapio sobremesas(Set<Sobremesa> sobremesas) {
        this.sobremesas = sobremesas;
        return this;
    }

    public Cardapio addSobremesa(Sobremesa sobremesa) {
        this.sobremesas.add(sobremesa);
        sobremesa.getCardapios().add(this);
        return this;
    }

    public Cardapio removeSobremesa(Sobremesa sobremesa) {
        this.sobremesas.remove(sobremesa);
        sobremesa.getCardapios().remove(this);
        return this;
    }

    public void setSobremesas(Set<Sobremesa> sobremesas) {
        this.sobremesas = sobremesas;
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
        Cardapio cardapio = (Cardapio) o;
        if (cardapio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cardapio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cardapio{" +
            "id=" + getId() +
            ", dia='" + getDia() + "'" +
            "}";
    }
}
