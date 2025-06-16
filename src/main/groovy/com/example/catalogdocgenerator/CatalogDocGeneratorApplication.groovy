package com.example.catalogdocgenerator

import com.example.catalogdocgenerator.model.ProductCategory
import com.example.catalogdocgenerator.model.ProductOffering
import com.example.catalogdocgenerator.model.ProductSpecification
import com.example.catalogdocgenerator.service.ProductCatalogApiService
import com.example.catalogdocgenerator.service.WordDocumentGeneratorService
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

@SpringBootApplication
class CatalogDocGeneratorApplication implements CommandLineRunner, ApplicationContextAware {

    private final ProductCatalogApiService apiService
    private final WordDocumentGeneratorService wordService

    @Value('${output.file.path}')
    String outputFilePath

    @Value('${output.file.name}')
    String outputFileName

    // Constructor injection is preferred
    CatalogDocGeneratorApplication(ProductCatalogApiService apiService, WordDocumentGeneratorService wordService) {
        this.apiService = apiService
        this.wordService = wordService
    }

    static void main(String[] args) {
        SpringApplication.run(CatalogDocGeneratorApplication.class, args)
    }

    @Override
    void run(String... args) throws Exception {
        System.out.println("Fetching product catalog data...")

        // Fetch main entities
        List<ProductOffering> offerings = apiService.getProductOfferings()
        List<ProductCategory> categories = apiService.getProductCategories() // Fetching, but might not use all details in Word yet
        // Fetch all specifications separately - could be large.
        // For now, we will try to get specific specs for each offering later.
        List<ProductSpecification> allSpecifications = apiService.getProductSpecifications()


        // Enhance offerings with full specifications if needed (example)
        // This part can be refined based on how much detail is needed.
        // For now, we'll pass the separately fetched 'allSpecifications'
        // and the Word generator can try to find matches or we can process here.
        // A more robust approach would be to fetch specific spec for each offering if not embedded.

        System.out.println("Generating Word document...")
        ByteArrayOutputStream baos = wordService.generateWordDocument(offerings, categories, allSpecifications)

        Path path = Paths.get(outputFilePath, outputFileName)
        try {
            Files.createDirectories(path.getParent()) // Ensure directory exists
            Files.write(path, baos.toByteArray(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
            System.out.println("Word document generated successfully at: " + path.toAbsolutePath())
        } catch (IOException e) {
            System.err.println("Error writing Word document: " + e.getMessage())
            e.printStackTrace()
        }
    }

    // Implementing ApplicationContextAware just to demonstrate how to manually get beans if needed,
    // though constructor injection is used here and is better.
    private ApplicationContext applicationContext

    @Override
    void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext
    }
}
