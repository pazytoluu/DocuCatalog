package com.example.catalogdocgenerator.model

import groovy.transform.ToString
import groovy.transform.builder.Builder
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@ToString(includeNames=true, ignoreNulls=true)
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
class ProductOfferingPrice {
    String id
    String href
    String name
    String description
    String version
    String lifecycleStatus
    TimePeriod validFor
    Boolean isBundle
    String priceType // e.g., Charge, Discount
    String recurringChargePeriodType // e.g., monthly
    Integer recurringChargePeriodLength
    Money price
    // List<TaxItem> tax // Define TaxItem if needed
    // List<ProductSpecificationCharacteristicValueUse> prodSpecCharValueUse // Define if needed
}
