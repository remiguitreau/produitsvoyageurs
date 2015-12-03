package fr.dwarfconan.produitsvoyageurs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class Traveller {

    private String id;

    private String name;

    private String firstName;

    private String email;

    private String phone;

    private Date deliveryDate;

    @Setter
    private List<String> deliveryLocations = new ArrayList<>();

    @Setter
    private List<Article> articlesAvailable = new ArrayList<>();

    @Setter
    private List<MultipleContract> contracts = new ArrayList<>();

    public Traveller(final String id) {
        this.id = id;
    }

    public void addArticle(final Article article) {
        articlesAvailable.add(article);
    }

    public void removeArticle(final Article article) {
        articlesAvailable.remove(article);
    }

    public void addContract(final MultipleContract contract) {
        contracts.add(contract);
    }

    public void removeContract(final Contract contract) {
        contracts.remove(contract);
    }

    @Override
    public String toString() {
        return firstName + " " + getName();
    }
}
