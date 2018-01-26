package de.demo.image.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Bild entity.
 */
public class BildDTO implements Serializable {

    private Long id;

    @Lob
    private byte[] bildDatei;
    private String bildDateiContentType;

    private Long bildId;

    private Long bildId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getBildDatei() {
        return bildDatei;
    }

    public void setBildDatei(byte[] bildDatei) {
        this.bildDatei = bildDatei;
    }

    public String getBildDateiContentType() {
        return bildDateiContentType;
    }

    public void setBildDateiContentType(String bildDateiContentType) {
        this.bildDateiContentType = bildDateiContentType;
    }

    public Long getBildId() {
        return bildId;
    }

    public void setBildId(Long kundeId) {
        this.bildId = kundeId;
    }

    public Long getBildId() {
        return bildId;
    }

    public void setBildId(Long mandantId) {
        this.bildId = mandantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BildDTO bildDTO = (BildDTO) o;
        if(bildDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bildDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BildDTO{" +
            "id=" + getId() +
            ", bildDatei='" + getBildDatei() + "'" +
            "}";
    }
}
