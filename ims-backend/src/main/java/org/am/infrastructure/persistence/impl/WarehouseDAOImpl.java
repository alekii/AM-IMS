package org.am.infrastructure.persistence.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.catalog.Address;
import org.am.domain.catalog.Warehouse;
import org.am.domain.catalog.exceptions.conflicts.TownCountyMismatch;
import org.am.domain.catalog.exceptions.conflicts.TownNotExistException;
import org.am.domain.catalog.exceptions.conflicts.WarehouseAlreadyExistsException;
import org.am.infrastructure.Address.AddressRepository;
import org.am.infrastructure.Towns.TownRepository;
import org.am.infrastructure.persistence.api.WarehouseDAO;
import org.am.infrastructure.persistence.converters.AddressToAddressEntityConverter;
import org.am.infrastructure.persistence.converters.WarehouseConverter;
import org.am.infrastructure.persistence.converters.WarehouseToWarehouseEntityConverter;
import org.am.infrastructure.warehouses.WarehouseRepository;
import org.am.library.entities.AddressEntity;
import org.am.library.entities.TownEntity;
import org.am.library.entities.WarehouseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
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

    private final WarehouseToWarehouseEntityConverter warehouseToWarehouseEntityConverter;

    private final AddressToAddressEntityConverter addressToAddressEntityConverter;

    @Override
    @Transactional
    public Warehouse create(Warehouse warehouse) {

        findWarehouseByName(warehouse.getName());

        final AddressEntity address = buildAndCreateAddress(warehouse.getAddress());

        final WarehouseEntity warehouseEntity = warehouseToWarehouseEntityConverter.convert(warehouse, address);
        warehouseEntity.setCreatedAt(Instant.now());
        WarehouseEntity toConvert = warehouseRepository.save(warehouseEntity);
        return warehouseConverter.convert(toConvert);
    }

    private void findWarehouseByName(String warehouseName) {

        warehouseRepository.findSidByName(warehouseName)
                .ifPresent(sid -> {
                    throw WarehouseAlreadyExistsException.forName(warehouseName);
                });
    }

    private AddressEntity buildAndCreateAddress(final Address address) {

        TownEntity town = getTownBySid(address);

        final AddressEntity addressEntity = addressToAddressEntityConverter.convert(address, town);

        return addressRepository.save(addressEntity);
    }

    private TownEntity getTownBySid(Address address) {

        final TownEntity town = findTownBySid(address.getTown().getSid());

        if (!town.getCounty().getSid().equals(address.getCounty().getSid())) {
            throw TownCountyMismatch.forSid(address.getTown().getSid());
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
