package org.am.infrastructure.persistence.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.catalog.Address;
import org.am.domain.catalog.Warehouse;
import org.am.domain.catalog.exceptions.NotFound.WarehouseNotFoundException;
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
import org.am.library.events.EventName;
import org.am.library.events.EventPublisher;
import org.am.library.events.ImsEventFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Component
@RequiredArgsConstructor
public class WarehouseDAOImpl implements WarehouseDAO {

    private final WarehouseRepository warehouseRepository;

    private final EventPublisher eventPublisher;

    private final AddressRepository addressRepository;

    private final TownRepository townRepository;

    private final WarehouseConverter warehouseConverter;

    private final WarehouseToWarehouseEntityConverter warehouseToWarehouseEntityConverter;

    private final AddressToAddressEntityConverter addressToAddressEntityConverter;

    @Override
    @Transactional
    public Warehouse create(final Warehouse warehouse) {

        findWarehouseByName(warehouse.getName());

        final AddressEntity address = buildAndCreateAddress(warehouse.getAddress());

        final WarehouseEntity warehouseEntity = warehouseToWarehouseEntityConverter.convert(warehouse, address);
        warehouseEntity.setCreatedAt(Instant.now());
        WarehouseEntity toConvert = warehouseRepository.save(warehouseEntity);
        publishWarehouseEvent(toConvert.getId());
        return warehouseConverter.convert(toConvert);
    }

    @Override
    @Transactional
    public Warehouse update(final Warehouse warehouse) {

        final WarehouseEntity warehousePersisted = checkExistense(warehouse.getSid());

        final WarehouseEntity warehouseToUpdate = prepareWarehouseToUpdate(warehouse, warehousePersisted);

        return warehouseConverter.convert(warehouseRepository.save(warehouseToUpdate));
    }

    private void findWarehouseByName(final String warehouseName) {

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

    private TownEntity getTownBySid(final Address address) {

        final TownEntity town = findTownBySid(address.getTown().getSid());

        if (!town.getCounty().getSid().equals(address.getCounty().getSid())) {
            throw TownCountyMismatch.forSid(address.getTown().getSid());
        }
        return town;
    }

    private TownEntity findTownBySid(final UUID townSid) {

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
    public Optional<Warehouse> findBySid(final UUID warehouseSid) {

        return Optional.of(warehouseRepository.findBySid(warehouseSid)
                                   .map(warehouseConverter::convert))
                .orElseThrow(() -> WarehouseNotFoundException.forSid(warehouseSid));
    }

    public WarehouseEntity checkExistense(final UUID warehouseSid) {

        return warehouseRepository
                .findBySid(warehouseSid)
                .orElseThrow(() -> WarehouseNotFoundException.forSid(warehouseSid));
    }

    public WarehouseEntity prepareWarehouseToUpdate(final Warehouse warehouse,
                                                    final WarehouseEntity warehousePersisted) {

        final TownEntity townEntity = handleTownChange(warehouse.getAddress(), warehousePersisted.getAddress());

        AddressEntity addressEntity = fetchWarehouseAddress(warehousePersisted.getAddress().getId(),
                                                            townEntity,
                                                            warehouse.getAddress());

        return warehouseToWarehouseEntityConverter.convert(warehouse, addressEntity);
    }

    private AddressEntity fetchWarehouseAddress(final int addressId,
                                                final TownEntity townEntity,
                                                final Address address) {

        AddressEntity convertedAddressEntity = addressToAddressEntityConverter.convert(address, townEntity);
        convertedAddressEntity.setId(addressId);
        return convertedAddressEntity;
    }

    private TownEntity handleTownChange(final Address address, final AddressEntity persistedAddress) {

        if (isPersistedTownNull(persistedAddress) || !isTownUpdated(address, persistedAddress)) {
            return findTownBySid(address.getTown().getSid());
        } else {
            return persistedAddress.getTown();
        }
    }

    private boolean isTownUpdated(final Address address, final AddressEntity persistedAddress) {

        return address.getTown().getSid().equals(persistedAddress.getTown().getSid())
                || address.getCounty().getSid().equals(persistedAddress.getTown().getCounty().getSid());
    }

    private boolean isPersistedTownNull(AddressEntity persistedAddress) {

        return Objects.isNull(persistedAddress)
                || Optional.ofNullable(persistedAddress.getTown())
                .map(TownEntity::getSid)
                .isEmpty();
    }

    private void publishWarehouseEvent(Object payload) {

        eventPublisher.publishEvent(ImsEventFactory.buildWarehouseCreatedEvent(
                this,
                EventName.WAREHOUSE_CREATED,
                payload));
    }
}
