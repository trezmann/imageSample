package de.demo.image.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Mandant.
 */
@Entity
@Table(name = "mandant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mandant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kurzbez")
    private String kurzbez;

    @Column(name = "bezeichnung")
    private String bezeichnung;

    @Column(name = "aktiv")
    private Boolean aktiv;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKurzbez() {
        return kurzbez;
    }

    public Mandant kurzbez(String kurzbez) {
        this.kurzbez = kurzbez;
        return this;
    }

    public void setKurzbez(String kurzbez) {
        this.kurzbez = kurzbez;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public Mandant bezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
        return this;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public Boolean isAktiv() {
        return aktiv;
    }

    public Mandant aktiv(Boolean aktiv) {
        this.aktiv = aktiv;
        return this;
    }

    public void setAktiv(Boolean aktiv) {
        this.aktiv = aktiv;
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
        Mandant mandant = (Mandant) o;
        if (mandant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mandant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Mandant{" +
            "id=" + getId() +
            ", kurzbez='" + getKurzbez() + "'" +
            ", bezeichnung='" + getBezeichnung() + "'" +
            ", aktiv='" + isAktiv() + "'" +
            "}";
    }
}
