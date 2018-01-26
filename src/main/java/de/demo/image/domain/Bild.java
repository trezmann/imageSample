package de.demo.image.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Bild.
 */
@Entity
@Table(name = "bild")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bild implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "bild_datei")
    private byte[] bildDatei;

    @Column(name = "bild_datei_content_type")
    private String bildDateiContentType;

    @ManyToOne
    private Kunde bild;

    @ManyToOne
    private Mandant bild;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getBildDatei() {
        return bildDatei;
    }

    public Bild bildDatei(byte[] bildDatei) {
        this.bildDatei = bildDatei;
        return this;
    }

    public void setBildDatei(byte[] bildDatei) {
        this.bildDatei = bildDatei;
    }

    public String getBildDateiContentType() {
        return bildDateiContentType;
    }

    public Bild bildDateiContentType(String bildDateiContentType) {
        this.bildDateiContentType = bildDateiContentType;
        return this;
    }

    public void setBildDateiContentType(String bildDateiContentType) {
        this.bildDateiContentType = bildDateiContentType;
    }

    public Kunde getBild() {
        return bild;
    }

    public Bild bild(Kunde kunde) {
        this.bild = kunde;
        return this;
    }

    public void setBild(Kunde kunde) {
        this.bild = kunde;
    }

    public Mandant getBild() {
        return bild;
    }

    public Bild bild(Mandant mandant) {
        this.bild = mandant;
        return this;
    }

    public void setBild(Mandant mandant) {
        this.bild = mandant;
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
        Bild bild = (Bild) o;
        if (bild.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bild.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Bild{" +
            "id=" + getId() +
            ", bildDatei='" + getBildDatei() + "'" +
            ", bildDateiContentType='" + getBildDateiContentType() + "'" +
            "}";
    }
}
