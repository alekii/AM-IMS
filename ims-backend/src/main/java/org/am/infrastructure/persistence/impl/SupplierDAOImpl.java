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
    public Supplier findBySid(final UUID supplierSid) {

        SupplierEntity supplier = supplierRepository
                .findBySid(supplierSid)
                .orElseThrow(() -> SupplierNotFoundException.forSid(supplierSid));

        return supplierConverter.convert(supplier);
    }

    public SupplierEntity checkExistense(final UUID supplierSid) {

        return supplierRepository
                .findBySid(supplierSid)
                .orElseThrow(() -> SupplierNotFoundException.forSid(supplierSid));
    }

    @Override
    public Supplier updateSupplier(Supplier supplier) {

        final SupplierEntity persistedSupplier = checkExistense(supplier.getSid());

        SupplierEntity toPersist = supplierToSupplierEntityConverter.convert(supplier);
        toPersist.setId(persistedSupplier.getId());
        SupplierEntity updatedSupplierEntity = supplierRepository.save(toPersist);
        return supplierConverter.convert(updatedSupplierEntity);
    }
}
