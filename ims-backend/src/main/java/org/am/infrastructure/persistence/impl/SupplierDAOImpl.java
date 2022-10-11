package org.am.infrastructure.persistence.impl;

import lombok.RequiredArgsConstructor;
import org.am.domain.catalog.Supplier;
import org.am.domain.catalog.exceptions.NotFound.SupplierNotFoundException;
import org.am.infrastructure.persistence.api.SupplierDAO;
import org.am.infrastructure.persistence.converters.SupplierConverter;
import org.am.infrastructure.persistence.converters.SupplierToSupplierEntityConverter;
import org.am.infrastructure.suppliers.SuppplierRepository;
import org.am.library.entities.SupplierEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SupplierDAOImpl implements SupplierDAO {

    private final SuppplierRepository supplierRepository;

    private final SupplierConverter supplierConverter;

    private final SupplierToSupplierEntityConverter supplierToSupplierEntityConverter;

    @Override
    public Supplier create(Supplier supplier) {

        final SupplierEntity savedEntity = supplierRepository.save(supplierToSupplierEntityConverter.convert(supplier));
        return supplierConverter.convert(savedEntity);
    }

    @Override
    public List<Supplier> findAll() {

        return supplierRepository.findAll().stream()
                .map(supplierConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Supplier> findBySid(UUID supplierSid) {

        Optional<SupplierEntity> supplier = supplierRepository.findBySid(supplierSid);

        if (supplier.isEmpty()) {
            throw SupplierNotFoundException.forSid(supplierSid);
        }

        return supplier.map(supplierConverter::convert);
    }

    @Override
    public Optional<Supplier> updateSupplier(Supplier supplier) {

        return supplierRepository.findBySid(supplier.getSid())
                .map(supplierEntity -> {
                    SupplierEntity toPersist = supplierToSupplierEntityConverter.convert(supplier);
                    toPersist.setId(supplierEntity.getId());
                    return supplierRepository.save(toPersist);
                })
                .map(supplierConverter::convert);
    }
}
