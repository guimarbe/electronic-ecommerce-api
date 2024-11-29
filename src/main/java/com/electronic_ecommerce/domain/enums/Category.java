package com.electronic_ecommerce.domain.enums;

import lombok.Getter;

@Getter
public enum Category {
    ACCESSORIES(1, "Accessories"),
    CLOTHING(2, "Clothing"),
    ELECTRONICS(3, "Electronics"),
    FOOTWEAR(4, "Footwear"),
    HOME_APPLIANCE(5, "Home Appliances"),
    HOME_KITCHEN(6, "Home & Kitchen"),
    SPORTS(7, "Sports"),
    STATIONERY(8, "Stationery"),
    TOYS_GAMES(9, "Toys & Games");

    private final Integer code;
    private final String description;

    Category(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

}
