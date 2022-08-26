package org.am.infrastructure.persistence.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.catalog.Warehouse;
import org.am.infrastructure.persistence.api.WarehouseDAO;
import org.am.infrastructure.persistence.converters.WarehouseConverter;
import org.am.infrastructure.warehouses.WarehouseRepository;
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

    private final WarehouseConverter warehouseConverter;

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
