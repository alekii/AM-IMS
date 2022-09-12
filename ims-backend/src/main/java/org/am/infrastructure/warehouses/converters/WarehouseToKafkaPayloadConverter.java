package org.am.infrastructure.warehouses.converters;

import org.am.commons.utils.Converter;
import org.am.infrastructure.warehouses.projections.WarehouseProjection;
import org.am.library.events.resources.WarehouseKafkaPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WarehouseToKafkaPayloadConverter extends Converter<WarehouseProjection, WarehouseKafkaPayload> {

    @Override
    @Mapping(target = "warehouseSid", source = "source.sid")
    @Mapping(target = "warehouseName", source = "source.name")
    @Mapping(target = "phoneNumber", source = "source.phoneNumber")
    @Mapping(target = "contactName", source = "source.contactName")
    @Mapping(target = "createdAt", source = "source.createdAt")
    @Mapping(target = "trackingNumberCount", source = "source.trackingNumberCount")
    @Mapping(target = "addressPayload.streetName", source = "source.addressStreet")
    @Mapping(target = "addressPayload.mapUrl", source = "source.addressMapUrl")
    @Mapping(target = "addressPayload.longitude", source = "source.addressLongitude")
    @Mapping(target = "addressPayload.latitude", source = "source.addressLatitude")
    @Mapping(target = "addressPayload.townPayload.townSid", source = "source.townSid")
    @Mapping(target = "addressPayload.townPayload.name", source = "source.townName")
    @Mapping(target = "addressPayload.countyPayload.countySid", source = "source.countySid")
    @Mapping(target = "addressPayload.countyPayload.name", source = "source.countyName")
    WarehouseKafkaPayload convert(WarehouseProjection source);
}
