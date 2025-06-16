package com.example.catalogdocgenerator.model

import groovy.transform.ToString
import groovy.transform.builder.Builder
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@ToString(includeNames=true, ignoreNulls=true)
@Builder
@JsonIgnoreProperties(ignoreUnknown = true) // To ignore fields like @type, @schemaLocation from API response
class ProductOffering {
    String id
    String href
    String name
    String description
    String version
    String lastUpdate // Assuming this will be a String, might need Date parsing later
    Boolean isBundle
    Boolean isSellable
    String lifecycleStatus
    TimePeriod validFor
    ProductSpecificationRef productSpecification
    List<CategoryRef> category
    List<ProductOfferingPriceRef> productOfferingPrice
    // Add other fields as needed, e.g., prodSpecCharValueUse, bundledProductOffering, etc.
    // For simplicity, starting with the main ones.
}
