package com.ecommerce.order.util;

import java.math.BigDecimal;

public class ConversionUtil {
    public static double toDouble(BigDecimal bigDecimal) {
        return bigDecimal != null ? bigDecimal.doubleValue() : 0.0;
    }
}
