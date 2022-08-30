package org.am.infrastructure.persistence.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.catalog.Address;
import org.am.domain.catalog.Warehouse;
import org.am.domain.catalog.WarehouseCreation;
import org.am.domain.catalog.exceptions.validations.TownNotExistException;
import org.am.infrastructure.Address.AddressRepository;
import org.am.infrastructure.Towns.TownRepository;
import org.am.infrastructure.persistence.api.WarehouseDAO;
import org.am.infrastructure.persistence.converters.AddressConverter;
import org.am.infrastructure.persistence.converters.WarehouseConverter;
import org.am.infrastructure.persistence.converters.WarehouseCreationToWarehouseEntityConverter;
import org.am.infrastructure.warehouses.WarehouseRepository;
import org.am.library.entities.AddressEntity;
import org.am.library.entities.TownEntity;
import org.am.library.entities.WarehouseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Component
@RequiredArgsConstructor
public class WarehouseDAOImpl implements WarehouseDAO {

    private final WarehouseRepository warehouseRepository;

    private final AddressRepository addressRepository;

    private final TownRepository townRepository;

    private final WarehouseConverter warehouseConverter;

    private final WarehouseCreationToWarehouseEntityConverter warehouseCreationToWarehouseEntityConverter;

    private final AddressConverter addressConverter;

    @Override
    @Transactional
    public Warehouse create(WarehouseCreation warehouseCreation) {

        final AddressEntity address = buildAndCreateAddress(warehouseCreation.getAddress());

        final UUID warehouseSid = UUID.randomUUID();

        warehouseCreation.setSid(warehouseSid);

        final WarehouseEntity warehouseEntity = warehouseCreationToWarehouseEntityConverter.convert(warehouseCreation, address);

        return warehouseConverter.convert(warehouseRepository.save(warehouseEntity));
    }

    private AddressEntity buildAndCreateAddress(final Address address) {

        TownEntity town = getTownBySid(address);

        final AddressEntity addressEntity = addressConverter.convert(address, town);

        return addressRepository.save(addressEntity);
    }

    private TownEntity getTownBySid(Address address) {

        final TownEntity town = findTownBySid(address.getTown().getSid());

        if (!town.getCounty().getSid().equals(address.getTown().getCounty().getSid())) {
            throw TownNotExistException.forSid(address.getTown().getSid());
        }
        return town;
    }

    private TownEntity findTownBySid(UUID townSid) {

        return Optional.ofNullable(townRepository.findBySID(townSid))
                .orElseThrow(() -> TownNotExistException.forSid(townSid));
    }

    @Override
    public List<Warehouse> findAll() {

        return warehouseRepository.findAll().stream()
                .map(warehouseEntity -> warehouseConverter.convert(warehouseEntity))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Warehouse> findBySid(UUID warehouseSid) {

        return warehouseRepository.findBySid(warehouseSid)
                .map(warehouseConverter::convert);
    }
}
