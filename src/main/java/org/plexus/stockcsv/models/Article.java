package org.plexus.stockcsv.models;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Article represents each product read from the CSV file
 *
 * @author victor.martingil
 */

@Data
@NoArgsConstructor
public class Article {

    private int id;
    private int sequence;
    private int sizeId;
    private boolean backSoon;
    private boolean special;
    private int quantity;

    /**
     * @param id
     * @param sequence -> order to show in the output
     * @param sizeId -> id for the size
     * @param backSoon -> if the product will be replaced soon
     * @param special -> if has special condition
     * @param quantity -> number of items (stock)
     */

    public Article(int id, int sequence, int sizeId, boolean backSoon, boolean special, int quantity) {
        this.id = id;
        this.sequence = sequence;
        this.sizeId = sizeId;
        this.backSoon = backSoon;
        this.special = special;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public boolean isBackSoon() {
        return backSoon;
    }

    public void setBackSoon(boolean backSoon) {
        this.backSoon = backSoon;
    }

    public boolean isSpecial() {
        return special;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
