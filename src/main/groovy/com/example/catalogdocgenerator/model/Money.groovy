package com.example.catalogdocgenerator.model

import groovy.transform.ToString
import groovy.transform.builder.Builder

@ToString(includeNames=true)
@Builder
class Money {
    BigDecimal value // Using BigDecimal for currency is a good practice
    String unit
}
