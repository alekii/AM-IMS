package org.am.library.events.resources;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "Builder")
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
    @NoArgsConstructor
    @lombok.Builder(builderClassName = "Builder")
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
        @NoArgsConstructor
        @lombok.Builder(builderClassName = "Builder")
        public static class TownPayload {

            private UUID townSid;

            private String name;
        }

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @lombok.Builder(builderClassName = "Builder")
        public static class CountyPayload {

            private UUID countySid;

            private String name;
        }
    }
}
