package com.example.catalogdocgenerator.model

import groovy.transform.ToString
import groovy.transform.builder.Builder

@ToString(includeNames=true)
@Builder
class CategoryRef {
    String id
    String href
    String name
    String version
}
