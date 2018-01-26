package de.demo.image.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Kunde.
 */
@Entity
@Table(name = "kunde")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Kunde implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stammnummer")
    private String stammnummer;

    @Column(name = "s_datum")
    private LocalDate sDatum;

    @Column(name = "e_datum")
    private LocalDate eDatum;

    @Column(name = "steuernummer")
    private String steuernummer;

    @ManyToOne
    private Mandant kunde;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStammnummer() {
        return stammnummer;
    }

    public Kunde stammnummer(String stammnummer) {
        this.stammnummer = stammnummer;
        return this;
    }

    public void setStammnummer(String stammnummer) {
        this.stammnummer = stammnummer;
    }

    public LocalDate getsDatum() {
        return sDatum;
    }

    public Kunde sDatum(LocalDate sDatum) {
        this.sDatum = sDatum;
        return this;
    }

    public void setsDatum(LocalDate sDatum) {
        this.sDatum = sDatum;
    }

    public LocalDate geteDatum() {
        return eDatum;
    }

    public Kunde eDatum(LocalDate eDatum) {
        this.eDatum = eDatum;
        return this;
    }

    public void seteDatum(LocalDate eDatum) {
        this.eDatum = eDatum;
    }

    public String getSteuernummer() {
        return steuernummer;
    }

    public Kunde steuernummer(String steuernummer) {
        this.steuernummer = steuernummer;
        return this;
    }

    public void setSteuernummer(String steuernummer) {
        this.steuernummer = steuernummer;
    }

    public Mandant getKunde() {
        return kunde;
    }

    public Kunde kunde(Mandant mandant) {
        this.kunde = mandant;
        return this;
    }

    public void setKunde(Mandant mandant) {
        this.kunde = mandant;
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
        Kunde kunde = (Kunde) o;
        if (kunde.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), kunde.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Kunde{" +
            "id=" + getId() +
            ", stammnummer='" + getStammnummer() + "'" +
            ", sDatum='" + getsDatum() + "'" +
            ", eDatum='" + geteDatum() + "'" +
            ", steuernummer='" + getSteuernummer() + "'" +
            "}";
    }
}
