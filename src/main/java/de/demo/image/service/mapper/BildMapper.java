package de.demo.image.service.mapper;

import de.demo.image.domain.*;
import de.demo.image.service.dto.BildDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Bild and its DTO BildDTO.
 */
@Mapper(componentModel = "spring", uses = {KundeMapper.class, MandantMapper.class})
public interface BildMapper extends EntityMapper<BildDTO, Bild> {

    @Mapping(source = "bild.id", target = "bildId")
    @Mapping(source = "bild.id", target = "bildId")
    BildDTO toDto(Bild bild);

    @Mapping(source = "bildId", target = "bild")
    @Mapping(source = "bildId", target = "bild")
    Bild toEntity(BildDTO bildDTO);

    default Bild fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bild bild = new Bild();
        bild.setId(id);
        return bild;
    }
}
