package com.example.catalogdocgenerator.model

import groovy.transform.ToString
import groovy.transform.builder.Builder
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@ToString(includeNames=true, ignoreNulls=true)
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
class ProductCategory {
    String id
    String href
    String name
    String description
    String version
    String lifecycleStatus
    TimePeriod validFor
    String parentId
    Boolean isRoot
    List<ProductOfferingRef> productOffering // This should likely be ProductOffering, not ProductOfferingRef, if it's meant to hold full offering details. Or it's a simplified reference.
    List<ProductCategory> subCategory // If categories can be nested
    // '@type', '@schemaLocation' - ignored by JsonIgnoreProperties
}
