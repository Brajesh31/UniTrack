package com.bash.Unitrack.Data.DTO;

public class ResponseDTO {

    private DistanceDuration [] routes;

    public DistanceDuration[] getRoutes() {
        return routes;
    }

    public void setRoutes(DistanceDuration[] routes) {
        this.routes = routes;
    }

    // Distance Duration class
    public static class DistanceDuration{
        private Long distanceMeters;
        private String duration;

        public Long getDistanceMeters() {
            return distanceMeters;
        }

        public void setDistanceMeters(Long distanceMeters) {
            this.distanceMeters = distanceMeters;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }
    }
}
