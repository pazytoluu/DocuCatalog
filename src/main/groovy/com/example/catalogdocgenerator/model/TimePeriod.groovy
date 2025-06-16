package com.example.catalogdocgenerator.model

import groovy.transform.ToString
import groovy.transform.builder.Builder

@ToString(includeNames=true)
@Builder
class TimePeriod {
    String startDateTime
    String endDateTime
}
