package de.demo.image.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Kunde entity.
 */
public class KundeDTO implements Serializable {

    private Long id;

    private String stammnummer;

    private LocalDate sDatum;

    private LocalDate eDatum;

    private String steuernummer;

    private Long kundeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStammnummer() {
        return stammnummer;
    }

    public void setStammnummer(String stammnummer) {
        this.stammnummer = stammnummer;
    }

    public LocalDate getsDatum() {
        return sDatum;
    }

    public void setsDatum(LocalDate sDatum) {
        this.sDatum = sDatum;
    }

    public LocalDate geteDatum() {
        return eDatum;
    }

    public void seteDatum(LocalDate eDatum) {
        this.eDatum = eDatum;
    }

    public String getSteuernummer() {
        return steuernummer;
    }

    public void setSteuernummer(String steuernummer) {
        this.steuernummer = steuernummer;
    }

    public Long getKundeId() {
        return kundeId;
    }

    public void setKundeId(Long mandantId) {
        this.kundeId = mandantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        KundeDTO kundeDTO = (KundeDTO) o;
        if(kundeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), kundeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KundeDTO{" +
            "id=" + getId() +
            ", stammnummer='" + getStammnummer() + "'" +
            ", sDatum='" + getsDatum() + "'" +
            ", eDatum='" + geteDatum() + "'" +
            ", steuernummer='" + getSteuernummer() + "'" +
            "}";
    }
}
