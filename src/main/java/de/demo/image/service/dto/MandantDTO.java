package de.demo.image.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Mandant entity.
 */
public class MandantDTO implements Serializable {

    private Long id;

    private String kurzbez;

    private String bezeichnung;

    private Boolean aktiv;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKurzbez() {
        return kurzbez;
    }

    public void setKurzbez(String kurzbez) {
        this.kurzbez = kurzbez;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public Boolean isAktiv() {
        return aktiv;
    }

    public void setAktiv(Boolean aktiv) {
        this.aktiv = aktiv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MandantDTO mandantDTO = (MandantDTO) o;
        if(mandantDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mandantDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MandantDTO{" +
            "id=" + getId() +
            ", kurzbez='" + getKurzbez() + "'" +
            ", bezeichnung='" + getBezeichnung() + "'" +
            ", aktiv='" + isAktiv() + "'" +
            "}";
    }
}
