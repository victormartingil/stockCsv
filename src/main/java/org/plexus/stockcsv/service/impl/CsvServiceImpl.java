package org.plexus.stockcsv.service.impl;

import org.plexus.stockcsv.models.Article;
import org.plexus.stockcsv.service.CsvService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class CsvServiceImpl implements CsvService {

    // File
    String rootPath = "./csvFiles/";
    String fileName;
    String filePath;
    String divider = ",";

    @Override
    public List<Article> findStock() {
        List<Article> articlesFromSizeCsv = readSizeCsv();
        List<Article> articlesFromStockCsv = readStockCsv();
        List<Article> articlesFromProductCsv = readProductCsv();

        List<Article> mergedStockArticles = mergeArticles(articlesFromProductCsv, articlesFromSizeCsv, articlesFromStockCsv);
        List<Article> specialArticles = specialArticles(mergedStockArticles);
        List<Article> printableArticles = printableArticles(specialArticles);
        List<Article> sortedArticles = sortArticles(printableArticles);

        printArticles(sortedArticles);

        return sortedArticles;

    }

    /**
     * @return a list with the articles read from size.csv file
     */
    @Override
    public List<Article> readSizeCsv() {
        fileName = "size.csv";
        filePath = rootPath + fileName;

        List<Article> articlesFromSizeCsv = new ArrayList<>();

        try {
            Stream<String> streamFile = Files.lines(Paths.get(filePath));
            articlesFromSizeCsv = streamFile.map(line -> line.split(divider))
                    .map(array -> {
                        return new Article(Integer.parseInt(array[1]), -1, Integer.parseInt(array[0]), Boolean.parseBoolean(array[2]), Boolean.parseBoolean(array[3]), -1);
                    })
                    .toList();

            streamFile.close();

            // System.out.println("\nSizeCsv...");
            // articlesFromSizeCsv.forEach(System.out::println);

            return articlesFromSizeCsv;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return a list with the articles read from stock.csv
     */
    @Override
    public List<Article> readStockCsv() {
        fileName = "stock.csv";
        filePath = rootPath + fileName;

        List<Article> articlesFromStockCsv = new ArrayList<>();

        try {
            Stream<String> streamFile = Files.lines(Paths.get(filePath));
            articlesFromStockCsv = streamFile.map(line -> line.split(divider))
                    .map(array -> {
                        return new Article(-1, -1, Integer.parseInt(array[0]), false, false, Integer.parseInt(array[1]));
                    })
                    .toList();

            streamFile.close();

            // System.out.println("\nStockCsv...");
            // articlesFromStockCsv.forEach(System.out::println);

            return articlesFromStockCsv;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return a list with the articles read from product.csv file
     */
    @Override
    public List<Article> readProductCsv() {
        fileName = "product.csv";
        filePath = rootPath + fileName;

        List<Article> articlesFromProductCsv;

        try {
            Stream<String> streamFile = Files.lines(Paths.get(filePath));
            articlesFromProductCsv = streamFile.map(line -> line.split(divider))
                    .map(array -> {
                        return new Article(Integer.parseInt(array[0]), Integer.parseInt(array[1]), -1, false, false, -1);
                    })
                    .toList();

            streamFile.close();

            // System.out.println("\nProductCsv...");
            // articlesFromProductCsv.forEach(System.out::println);

            return articlesFromProductCsv;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @param articlesFromProductCsv list of articles read from product.csv file
     * @param articlesFromSizeCsv list of articles read from size.csv file
     * @param articlesFromStockCsv list of articles read from stock.csv file
     * @return a list of articles merging the content of the above params
     */
    @Override
    public List<Article> mergeArticles(List<Article> articlesFromProductCsv, List<Article> articlesFromSizeCsv, List<Article> articlesFromStockCsv) {
        List<Article> mergedArticles = new ArrayList<>();

        mergedArticles.addAll(articlesFromSizeCsv);

        articlesFromStockCsv.forEach(stockArticle -> {
            mergedArticles.forEach(mergedArticle -> {
                if (stockArticle.getSizeId() == mergedArticle.getSizeId()) {
                    mergedArticle.setQuantity(stockArticle.getQuantity());
                }
            });
        });

        mergedArticles.forEach(mergedArticle -> {
            articlesFromProductCsv.forEach(sequenceArticle -> {
                if (mergedArticle.getId() == sequenceArticle.getId()) {
                    mergedArticle.setSequence(sequenceArticle.getSequence());
                }
            });
        });

        return mergedArticles;
    }

    /**
     * In this method we remove the article if there is a "special" article, but there is not the same "non-special" article (with same productId(id) and sizeId)
     * @param mergedStockArticles list of articles merging the content of all csv files
     * @return a list of articles filtering "special" feature
     */
    @Override
    public List<Article> specialArticles(List<Article> mergedStockArticles) {
        List<Article> allSpecialArticles = new ArrayList<>();

        for (int i = 0; i < mergedStockArticles.size(); i++) {
            if (mergedStockArticles.get(i).isSpecial()) {
                allSpecialArticles.add(mergedStockArticles.remove(i));
            }
        }

        mergedStockArticles.forEach(mergedArticle -> {
            allSpecialArticles.forEach(specialArticle -> {
                if (mergedArticle.getId() == specialArticle.getId() && mergedArticle.getSizeId() == specialArticle.getSizeId() && !mergedArticle.isSpecial()) {
                    mergedStockArticles.add(specialArticle);
                }
            });
        });

        return mergedStockArticles;

    }

    /**
     * @param specialArticles list of articles filtered by "special" feature
     * @return a list of articles when there is stock (quantity > 0) or when backSoon is true
     */
    @Override
    public List<Article> printableArticles(List<Article> specialArticles) {
        List<Article> printableArticles = new ArrayList<>();

        specialArticles.forEach(article -> {
            if (article.isBackSoon() || article.getQuantity() > 0) {
                printableArticles.add(article);
            }
        });

        return printableArticles;

    }

    /**
     * @param printableArticles list of articles when there is stock (quantity > 0) or when backSoon is true
     * @return a list of articles sorted by sequence property
     */
    @Override
    public List<Article> sortArticles(List<Article> printableArticles) {
        return printableArticles.stream()
                .sorted((a1, a2) -> { //Sort by sequence
                    Integer seq1 = a1.getSequence();
                    Integer seq2 = a2.getSequence();
                    return seq1.compareTo(seq2);
                })
                .toList();
    }

    /**
     * This method prints the articles that we pass in the param
     * @param sortedArticles list of articles sorted by sequence property
     */
    @Override
    public void printArticles(List<Article> sortedArticles) {
        // I don't understand which are the criteria for display the output available articles,
        // so I'm showing all attributes of all articles that have stock, are backSoon, and agree with "special" criteria

        System.out.println("\nOutput Available Articles (Full Desctiption)...");
        sortedArticles.forEach(System.out::println);

        System.out.println("\nOutput CSV style...");
        sortedArticles.forEach(a -> {
            System.out.println(a.getId() + "," + a.getSizeId() + "," + a.getQuantity());
        });


    }
}
