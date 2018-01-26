package de.demo.image.service.mapper;

import de.demo.image.domain.*;
import de.demo.image.service.dto.MandantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Mandant and its DTO MandantDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MandantMapper extends EntityMapper<MandantDTO, Mandant> {



    default Mandant fromId(Long id) {
        if (id == null) {
            return null;
        }
        Mandant mandant = new Mandant();
        mandant.setId(id);
        return mandant;
    }
}
