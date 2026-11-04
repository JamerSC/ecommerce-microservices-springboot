package com.ecommerce.product.util;

import java.math.BigDecimal;

public class Conversion {
    public static double toDouble(BigDecimal bigDecimal) {
        return bigDecimal != null ? bigDecimal.doubleValue() : 0.0;
    }
}
