package com.bash.Unitrack.Utilities;


public class Haversine {

    private static final double R = 6371.0; // Earth's radius in kilometers

    public static double calculateDistance(RequestClass request) {
        double lat1 = request.getOrigin().getLocation().getLatLng().getLatitude();
        double lon1 = request.getOrigin().getLocation().getLatLng().getLongitude();
        double lat2 = request.getDestination().getLocation().getLatLng().getLatitude();
        double lon2 = request.getDestination().getLocation().getLatLng().getLongitude();

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double radLat1 = Math.toRadians(lat1);
        double radLat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // distance in kilometers
    }
}

