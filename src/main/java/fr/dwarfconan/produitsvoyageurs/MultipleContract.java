/**
 *
 */
package fr.dwarfconan.produitsvoyageurs;

import java.util.List;

import lombok.Getter;

public class MultipleContract implements Contract {

    @Getter
    private Discoverer discoverer;

    @Getter
    private List<SimpleContract> contracts;

    @Override
    public void setDiscoverer(final Discoverer discoverer) {
        this.discoverer = discoverer;
        updateDiscovererForSubContracts();
    }

    public void setContracts(final List<SimpleContract> contracts) {
        this.contracts = contracts;
        updateDiscovererForSubContracts();
    }

    private void updateDiscovererForSubContracts() {
        if (contracts != null) {
            for (final SimpleContract contract : contracts) {
                contract.setDiscoverer(discoverer);
            }
        }
    }
}
