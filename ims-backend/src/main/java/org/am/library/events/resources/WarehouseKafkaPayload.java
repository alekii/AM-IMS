package org.am.library.events.resources;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class WarehouseKafkaPayload {

    private UUID warehouseSid;

    private String warehouseName;

    private String phoneNumber;

    private String contactName;

    private Instant createdAt;

    private Integer trackingNumberCount;

    private AddressPayload addressPayload;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class AddressPayload {

        private String streetName;

        private String mapUrl;

        private Double longitude;

        private Double latitude;

        private TownPayload townPayload;

        private CountyPayload countyPayload;

        @Getter
        @Setter
        @AllArgsConstructor
        public static class TownPayload {

            private UUID townSid;

            private String name;
        }

        @Getter
        @Setter
        @AllArgsConstructor
        public static class CountyPayload {

            private UUID countySid;

            private String name;
        }
    }
}
