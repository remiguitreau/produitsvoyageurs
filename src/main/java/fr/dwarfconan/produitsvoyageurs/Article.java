/**
 *
 */
package fr.dwarfconan.produitsvoyageurs;

import lombok.Data;

@Data
public class Article {

    private String name;

    private float unitPrice = -1;

    @Override
    public String toString() {
        return name + (unitPrice >= 0 ? " (" + String.valueOf(unitPrice) + " €)" : "(Pas de prix)");
    }
}
