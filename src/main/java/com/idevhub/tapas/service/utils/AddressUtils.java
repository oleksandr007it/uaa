package com.idevhub.tapas.service.utils;

import com.idevhub.tapas.domain.address.Address;

public class AddressUtils {

    private AddressUtils() {

    }

    public static String getKoatuu(Address address) {
        /*NaisAtsObject adminObj = address.getAdminTerritorialObject();

        while (true) {
            if (adminObj.getKoatuu().isEmpty()) {
                if (adminObj.getParent() == null) return "";

                adminObj = adminObj.getParent();
            } else {
                return adminObj.getKoatuu();
            }
        }*/
        return "";   //TODO refactor
    }

    // TODO refactor
    public static String genAddress(Address address) {
        String delimeter = ", ";
        String absObjectAddress = "";
        /*NaisAtsObject adminObj = address.getAdminTerritorialObject();

        while (true) {
            absObjectAddress = adminObj.getType().getShortName() +
                " " +
                adminObj.getNaisAtsObjectName().getName() +
                absObjectAddress;

            if (adminObj.getParent() == null) {
                break;
            }

            absObjectAddress = ", " + absObjectAddress;
            adminObj = adminObj.getParent();
        }*/

        String targetAddress = absObjectAddress;
//            + (address.getHouseNumber().isEmpty() ? "" : delimeter + address.getHouseNumber())
//            + (address.getBlock().isEmpty() ? "" : delimeter + address.getBlock())
//            + (address.getAppartmentNumber() == null ? "" : delimeter + address.getAppartmentNumber());

        return targetAddress;
    }
}
