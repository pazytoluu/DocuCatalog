package com.example.catalogdocgenerator.model

import groovy.transform.ToString
import groovy.transform.builder.Builder
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@ToString(includeNames=true, ignoreNulls=true)
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
class ProductSpecification {
    String id
    String href
    String name
    String description
    String version
    String lifecycleStatus
    TimePeriod validFor
    String brand
    Boolean isBundle
    String productNumber
    // List<Attachment> attachment // Define Attachment class if needed
    // List<ProductSpecificationCharacteristic> productSpecCharacteristic // Define ProductSpecificationCharacteristic if needed
    // List<ResourceSpecificationRef> resourceSpecification // Define ResourceSpecificationRef if needed
    // List<ServiceSpecificationRef> serviceSpecification // Define ServiceSpecificationRef if needed
}
