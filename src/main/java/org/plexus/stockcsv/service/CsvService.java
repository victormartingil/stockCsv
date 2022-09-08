package org.plexus.stockcsv.service;

import org.plexus.stockcsv.models.Article;

import java.util.List;

public interface CsvService {

    public List<Article> findStock();

    public List<Article> readProductCsv();

    public List<Article> readSizeCsv();

    public List<Article> readStockCsv();

    public List<Article> mergeArticles(List<Article> articlesFromProductCsv, List<Article> articlesFromSizeCsv, List<Article> articlesFromStockCsv);

    public List<Article> specialArticles(List<Article> mergedStockArticles);

    public List<Article> sortArticles(List<Article> sameIdArticles);

    public List<Article> printableArticles(List<Article> sameIdArticles);

    public void printArticles(List<Article> sortedArticles);

}
