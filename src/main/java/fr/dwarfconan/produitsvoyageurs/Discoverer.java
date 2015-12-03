/**
 *
 */
package fr.dwarfconan.produitsvoyageurs;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Un consom'acteur
 * @author Rémi "DwarfConan" Guitreau
 * @since 18 avr. 08 : Création
 */
@Data
@NoArgsConstructor
public class Discoverer {

    private String id;

    private String name;

    private String firstName;

    private String address;

    private String postCode;

    private String town;

    private String telephone;

    private String email;

    public Discoverer(final String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name + " " + firstName;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Discoverer && id.equals(((Discoverer) obj).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
