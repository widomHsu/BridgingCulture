package edu.monash.bridgingculture.service.entity.quiz;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Data
@ToString
public class TripAdvisor {

    @SerializedName("data")
    private List<DataDTO> data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @SerializedName("location_id")
        private String locationId;
        @SerializedName("name")
        private String name;
        @SerializedName("distance")
        private String distance;
        @SerializedName("bearing")
        private String bearing;
        @SerializedName("address_obj")
        private AddressObjDTO addressObj;

        @NoArgsConstructor
        @Data
        public static class AddressObjDTO {
            @SerializedName("street1")
            private String street1;
            @SerializedName("city")
            private String city;
            @SerializedName("state")
            private String state;
            @SerializedName("country")
            private String country;
            @SerializedName("postalcode")
            private String postalcode;
            @SerializedName("address_string")
            private String addressString;
        }
    }
}
