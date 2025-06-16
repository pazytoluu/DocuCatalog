package com.example.catalogdocgenerator.service

import com.example.catalogdocgenerator.model.ProductCategory
import com.example.catalogdocgenerator.model.ProductOffering
import com.example.catalogdocgenerator.model.ProductSpecification
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.http.ResponseEntity
import org.springframework.core.ParameterizedTypeReference

@Service
class ProductCatalogApiService {

    private final RestTemplate restTemplate
    private final String baseUrl

    ProductCatalogApiService(RestTemplate restTemplate, @Value('${api.base.url}') String baseUrl) {
        this.restTemplate = restTemplate
        this.baseUrl = baseUrl
    }

    List<ProductOffering> getProductOfferings() {
        // The actual endpoint might have pagination, filters, etc.
        // For now, assuming a simple GET that returns a list.
        // Example: GET http://localhost:8085/pcm/productCatalogManagement/v4/productOffering
        String url = baseUrl + "/productOffering"
        try {
            ResponseEntity<List<ProductOffering>> response = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductOffering>>() {}
            )
            return response.body
        } catch (Exception e) {
            // Basic error logging, more robust handling can be added
            System.err.println("Error fetching product offerings: " + e.getMessage())
            return [] // Return empty list on error
        }
    }

    List<ProductCategory> getProductCategories() {
        String url = baseUrl + "/category"
        try {
            ResponseEntity<List<ProductCategory>> response = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductCategory>>() {}
            )
            return response.body
        } catch (Exception e) {
            System.err.println("Error fetching product categories: " + e.getMessage())
            return []
        }
    }

    List<ProductSpecification> getProductSpecifications() {
        String url = baseUrl + "/productSpecification"
        try {
            ResponseEntity<List<ProductSpecification>> response = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductSpecification>>() {}
            )
            return response.body
        } catch (Exception e) {
            System.err.println("Error fetching product specifications: " + e.getMessage())
            return []
        }
    }

    // We might need methods to fetch individual items by ID if the Word doc needs more detail
    // e.g., getProductOfferingById(String id), getProductSpecificationById(String id)

    // A method to fetch a single ProductSpecification for a ProductOffering
    // This is a common need if ProductOffering only contains a ProductSpecificationRef
    ProductSpecification getProductSpecificationForOffering(ProductOffering offering) {
        if (offering.productSpecification?.href) {
            // Prefer href if available as it's the direct link
            String url = offering.productSpecification.href
            // Ensure href is a full URL, otherwise prepend baseUrl
            if (!url.startsWith("http")) {
                 url = baseUrl + (url.startsWith("/") ? "" : "/") + url
            }
            try {
                return restTemplate.getForObject(url, ProductSpecification.class)
            } catch (Exception e) {
                System.err.println("Error fetching product specification from href " + url + ": " + e.getMessage())
                return null
            }
        } else if (offering.productSpecification?.id) {
            String url = baseUrl + "/productSpecification/" + offering.productSpecification.id
            try {
                return restTemplate.getForObject(url, ProductSpecification.class)
            } catch (Exception e) {
                System.err.println("Error fetching product specification by id " + offering.productSpecification.id + ": " + e.getMessage())
                return null
            }
        }
        return null
    }
}
