package com.example.catalogdocgenerator.service

import com.example.catalogdocgenerator.model.ProductCategory
import com.example.catalogdocgenerator.model.ProductOffering
import com.example.catalogdocgenerator.model.ProductSpecification
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFParagraph
import org.apache.poi.xwpf.usermodel.XWPFRun
import org.apache.poi.xwpf.usermodel.ParagraphAlignment
import org.apache.poi.xwpf.usermodel.BreakType
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation
import org.springframework.stereotype.Service

import java.io.ByteArrayOutputStream

@Service
class WordDocumentGeneratorService {

    ByteArrayOutputStream generateWordDocument(
            List<ProductOffering> offerings,
            List<ProductCategory> categories,
            List<ProductSpecification> specifications // May not use all initially, but good to have
    ) {
        XWPFDocument document = new XWPFDocument()
        ByteArrayOutputStream out = new ByteArrayOutputStream()

        // Set page orientation to landscape for more space if needed (optional)
        // document.getDocument().getBody().addNewSectPr().addNewPgSz().setOrient(STPageOrientation.LANDSCAPE);


        // Title
        XWPFParagraph title = document.createParagraph()
        title.setAlignment(ParagraphAlignment.CENTER)
        XWPFRun titleRun = title.createRun()
        titleRun.setText("Product Catalog Document")
        titleRun.setBold(true)
        titleRun.setFontSize(20)
        titleRun.addBreak()

        // Product Offerings Section
        if (offerings) {
            addSectionTitle(document, "Product Offerings")
            offerings.eachWithIndex { offering, index ->
                addHeading1(document, offering.name ?: "Unnamed Offering")
                addParagraph(document, "ID: ${offering.id ?: 'N/A'}")
                addParagraph(document, "Description: ${offering.description ?: 'N/A'}")
                addParagraph(document, "Lifecycle Status: ${offering.lifecycleStatus ?: 'N/A'}")
                addParagraph(document, "Version: ${offering.version ?: 'N/A'}")
                addParagraph(document, "Sellable: ${offering.isSellable}")

                if (offering.validFor) {
                    addParagraph(document, "Valid From: ${offering.validFor.startDateTime ?: 'N/A'} To: ${offering.validFor.endDateTime ?: 'N/A'}")
                }

                if (offering.productOfferingPrice) {
                    addHeading2(document, "Prices:")
                    offering.productOfferingPrice.each { priceRef ->
                        // Here we'd ideally have the full ProductOfferingPrice object
                        // For now, just displaying what's in the ref
                        addParagraph(document, "- Name: ${priceRef.name ?: 'N/A'} (ID: ${priceRef.id ?: 'N/A'})")
                        // To get full price details, another API call might be needed based on priceRef.href or id
                    }
                }

                if (offering.productSpecification) {
                     addHeading2(document, "Product Specification Reference:")
                     addParagraph(document, "  Name: ${offering.productSpecification.name ?: 'N/A'} (ID: ${offering.productSpecification.id ?: 'N/A'})")
                     // If we have fetched the full specification object and passed it, we could print its details here
                }

                if (index < offerings.size() - 1) {
                    titleRun.addBreak(BreakType.PAGE) // Or just a simple addBreak() for a line break
                }
            }
        }

        // Placeholder for Categories Section (similar structure)
        if (categories) {
            addSectionTitle(document, "Product Categories")
            categories.eachWithIndex { category, index ->
                addHeading1(document, category.name ?: "Unnamed Category")
                addParagraph(document, "ID: ${category.id ?: 'N/A'}")
                addParagraph(document, "Description: ${category.description ?: 'N/A'}")
                addParagraph(document, "Lifecycle Status: ${category.lifecycleStatus ?: 'N/A'}")
                 if (category.productOffering) {
                    addHeading2(document, "Associated Offerings:")
                    category.productOffering.each { poRef ->
                        addParagraph(document, "- Name: ${poRef.name ?: 'N/A'} (ID: ${poRef.id ?: 'N/A'})")
                    }
                }
                if (index < categories.size() - 1) {
                    titleRun.addBreak(BreakType.PAGE)
                }
            }
        }

        // Placeholder for Specifications Section (similar structure)
        // This section would be more detailed if we pass full ProductSpecification objects
        if (specifications) {
            addSectionTitle(document, "Product Specifications")
            specifications.eachWithIndex { spec, index ->
                addHeading1(document, spec.name ?: "Unnamed Specification")
                addParagraph(document, "ID: ${spec.id ?: 'N/A'}")
                addParagraph(document, "Description: ${spec.description ?: 'N/A'}")
                addParagraph(document, "Lifecycle Status: ${spec.lifecycleStatus ?: 'N/A'}")
                addParagraph(document, "Brand: ${spec.brand ?: 'N/A'}")
                if (index < specifications.size() - 1) {
                    titleRun.addBreak(BreakType.PAGE)
                }
            }
        }

        document.write(out)
        return out
    }

    private void addSectionTitle(XWPFDocument document, String text) {
        XWPFParagraph paragraph = document.createParagraph()
        paragraph.setAlignment(ParagraphAlignment.CENTER)
        XWPFRun run = paragraph.createRun()
        run.setText(text)
        run.setBold(true)
        run.setFontSize(16)
        run.addBreak()
    }

    private void addHeading1(XWPFDocument document, String text) {
        XWPFParagraph paragraph = document.createParagraph()
        XWPFRun run = paragraph.createRun()
        run.setText(text)
        run.setBold(true)
        run.setFontSize(14)
        // run.addBreak() // Optional break after heading
    }

    private void addHeading2(XWPFDocument document, String text) {
        XWPFParagraph paragraph = document.createParagraph()
        XWPFRun run = paragraph.createRun()
        run.setText(text)
        run.setBold(true)
        run.setFontSize(12)
        // run.addBreak() // Optional break after heading
    }

    private void addParagraph(XWPFDocument document, String text) {
        XWPFParagraph paragraph = document.createParagraph()
        XWPFRun run = paragraph.createRun()
        run.setText(text)
    }
}
