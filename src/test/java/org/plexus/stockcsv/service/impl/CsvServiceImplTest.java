package org.plexus.stockcsv.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.plexus.stockcsv.models.Article;
import org.plexus.stockcsv.service.CsvService;

import java.util.List;

class CsvServiceImplTest {

    CsvService csvService = new CsvServiceImpl();

    @Test
    void testFindStock() {
        List<Article> outputArticle = csvService.findStock();
        Assertions.assertNotNull(outputArticle);
    }

    @Test
    void testReadProductCsv() {
        List<Article> expectedProductCsvArticles = csvService.readProductCsv();
        Assertions.assertNotNull(expectedProductCsvArticles);
    }

    @Test
    void testReadSizeCsv() {
        List<Article> expectedSizeCsvArticles = csvService.readSizeCsv();
        Assertions.assertNotNull(expectedSizeCsvArticles);
    }

    @Test
    void testReadStockCsv() {
        List<Article> expectedStockCsvArticles = csvService.readStockCsv();
        Assertions.assertNotNull(expectedStockCsvArticles);
    }

    @Test
    void testMergeArticles() {
        List<Article> articlesFromProductCsv = csvService.readProductCsv();
        List<Article> articlesFromSizeCsv = csvService.readSizeCsv();
        List<Article> articlesFromStockCsv = csvService.readStockCsv();

        List<Article> mergedArticles = csvService.mergeArticles(articlesFromProductCsv, articlesFromSizeCsv, articlesFromStockCsv);

        Assertions.assertNotNull(mergedArticles);
    }

    @Test
    void testSpecialArticles() {
        List<Article> articlesFromProductCsv = csvService.readProductCsv();
        List<Article> articlesFromSizeCsv = csvService.readSizeCsv();
        List<Article> articlesFromStockCsv = csvService.readStockCsv();

        List<Article> mergedArticles = csvService.mergeArticles(articlesFromProductCsv, articlesFromSizeCsv, articlesFromStockCsv);
        List<Article> specialArticles = csvService.specialArticles(mergedArticles);

        Assertions.assertNotNull(specialArticles);

    }

    @Test
    void testPrintableArticles() {
        List<Article> articlesFromProductCsv = csvService.readProductCsv();
        List<Article> articlesFromSizeCsv = csvService.readSizeCsv();
        List<Article> articlesFromStockCsv = csvService.readStockCsv();

        List<Article> mergedArticles = csvService.mergeArticles(articlesFromProductCsv, articlesFromSizeCsv, articlesFromStockCsv);
        List<Article> specialArticles = csvService.specialArticles(mergedArticles);

        List<Article> printableArticles = csvService.printableArticles(specialArticles);

        Assertions.assertNotNull(printableArticles);

    }

    @Test
    void testSortArticles() {
        List<Article> articlesFromProductCsv = csvService.readProductCsv();
        List<Article> articlesFromSizeCsv = csvService.readSizeCsv();
        List<Article> articlesFromStockCsv = csvService.readStockCsv();

        List<Article> mergedArticles = csvService.mergeArticles(articlesFromProductCsv, articlesFromSizeCsv, articlesFromStockCsv);
        List<Article> specialArticles = csvService.specialArticles(mergedArticles);
        List<Article> printableArticles = csvService.printableArticles(specialArticles);

        List<Article> sameIdArticles = csvService.sortArticles(printableArticles);

        Assertions.assertNotNull(sameIdArticles);
        if (sameIdArticles.size() > 1) {
            Assertions.assertTrue(printableArticles.get(0).getSequence() > printableArticles.get(printableArticles.size() - 1).getSequence());
        }

    }

}