/**
 *
 */
package fr.dwarfconan.produitsvoyageurs;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimpleContract implements Contract {

    private Discoverer discoverer;

    private int nbArticles;

    private Article article;

    private Date beginDate;

    private Date endDate;

    private int paiement;
}
