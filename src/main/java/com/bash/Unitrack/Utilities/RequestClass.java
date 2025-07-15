package com.bash.Unitrack.Utilities;

import lombok.Data;
import lombok.NoArgsConstructor;

public class RequestClass {

    private LocationWrapper origin;
    private LocationWrapper destination;
    private String travelMode;
    public RequestClass(
            double originLatitude,
            double originLongitude,
            double destinationLatitude,
            double destinationLongitude){
        this.origin = new LocationWrapper(new Location(new LatLng( originLatitude, originLongitude)));
        this.destination = new LocationWrapper(new Location(new LatLng(destinationLatitude, destinationLongitude)));

    }

    public static class LocationWrapper {
        private Location location;

        public LocationWrapper(Location location) {
            this.location = location;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }
    }
    @Data
    @NoArgsConstructor
    public static class Location {
        private LatLng latLng;

        public LatLng getLatLng() {
            return latLng;
        }

        public void setLatLng(LatLng latLng) {
            this.latLng = latLng;
        }

        public Location(LatLng latLng) {
            this.latLng = latLng;
        }
    }

    @Data
    @NoArgsConstructor
    public static class LatLng{
        private double latitude;
        private double longitude;
        public LatLng(double latitude, double longitude){
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }


    public LocationWrapper getOrigin() {
        return origin;
    }

    public void setOrigin(LocationWrapper origin) {
        this.origin = origin;
    }

    public LocationWrapper getDestination() {
        return destination;
    }

    public void setDestination(LocationWrapper destination) {
        this.destination = destination;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }
}
