package de.demo.image.service.mapper;

import de.demo.image.domain.*;
import de.demo.image.service.dto.KundeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Kunde and its DTO KundeDTO.
 */
@Mapper(componentModel = "spring", uses = {MandantMapper.class})
public interface KundeMapper extends EntityMapper<KundeDTO, Kunde> {

    @Mapping(source = "kunde.id", target = "kundeId")
    KundeDTO toDto(Kunde kunde);

    @Mapping(source = "kundeId", target = "kunde")
    Kunde toEntity(KundeDTO kundeDTO);

    default Kunde fromId(Long id) {
        if (id == null) {
            return null;
        }
        Kunde kunde = new Kunde();
        kunde.setId(id);
        return kunde;
    }
}
