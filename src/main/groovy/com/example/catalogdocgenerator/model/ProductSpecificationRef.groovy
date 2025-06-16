package com.example.catalogdocgenerator.model

import groovy.transform.ToString
import groovy.transform.builder.Builder

@ToString(includeNames=true)
@Builder
class ProductSpecificationRef {
    String id
    String href
    String name
    String version
    // '@referredType' // Swagger uses @, which is not directly usable in Groovy var names without quotes. Handle during deserialization if needed.
}
