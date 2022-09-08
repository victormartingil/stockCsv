package org.plexus.stockcsv;

import org.plexus.stockcsv.service.CsvService;
import org.plexus.stockcsv.service.impl.CsvServiceImpl;

public class StockCsvApplication {

    public static void main(String[] args) {
        CsvService csvService = new CsvServiceImpl();
        csvService.findStock();
    }

}
