/**
 *
 */
package fr.dwarfconan.produitsvoyageurs.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.dwarfconan.produitsvoyageurs.Discoverer;

public class DiscovererPanel extends JPanel {

    private final static Dimension COMMON_SIZE = new Dimension(150, 20);

    private final static Dimension ADDR_SIZE = new Dimension(300, 20);

    private final static Dimension POST_CODE_SIZE = new Dimension(80, 20);

    private JTextField tfName;

    private JTextField tfFirstName;

    private JTextField tfAddress;

    private JTextField tfPostCode;

    private JTextField tfTown;

    private JTextField tfTelephone;

    private JTextField tfEmail;

    private boolean editable;

    private Discoverer discoverer;

    private final FocusListener focusListener;

    public DiscovererPanel() {
        this(false);
    }

    public DiscovererPanel(final boolean editable) {
        this.editable = editable;
        this.focusListener = new FocusAdapter() {
            @Override
            public void focusLost(final FocusEvent event) {
                focusLostOnField(event);
            }
        };

        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());

        int y = 0;

        // Nom
        final JLabel nameLabel = new JLabel("Nom :");
        add(nameLabel, new GridBagConstraints(0, y, 1, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

        tfName = new JTextField();
        tfName.setPreferredSize(COMMON_SIZE);
        add(tfName, new GridBagConstraints(1, y++, 1, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        tfName.addFocusListener(focusListener);

        // Prénom
        final JLabel firstNameLabel = new JLabel("Prénom :");
        add(firstNameLabel, new GridBagConstraints(0, y, 1, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

        tfFirstName = new JTextField();
        tfFirstName.setPreferredSize(COMMON_SIZE);
        add(tfFirstName, new GridBagConstraints(1, y++, 1, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        tfFirstName.addFocusListener(focusListener);

        // Adresse
        final JLabel addressLabel = new JLabel("Adresse :");
        add(addressLabel, new GridBagConstraints(0, y, 1, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

        tfAddress = new JTextField();
        tfAddress.setPreferredSize(ADDR_SIZE);
        add(tfAddress, new GridBagConstraints(1, y++, 1, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        tfAddress.addFocusListener(focusListener);

        // Code postal
        final JLabel postCodeLabel = new JLabel("Code Postal :");
        add(postCodeLabel, new GridBagConstraints(0, y, 1, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

        tfPostCode = new JTextField();
        tfPostCode.setPreferredSize(COMMON_SIZE);
        add(tfPostCode, new GridBagConstraints(1, y++, 1, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        tfPostCode.addFocusListener(focusListener);

        // Ville
        final JLabel townLabel = new JLabel("Ville :");
        add(townLabel, new GridBagConstraints(0, y, 1, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

        tfTown = new JTextField();
        tfTown.setPreferredSize(COMMON_SIZE);
        add(tfTown, new GridBagConstraints(1, y++, 1, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        tfTown.addFocusListener(focusListener);

        // Téléphone
        final JLabel phoneLabel = new JLabel("Téléphone :");
        add(phoneLabel, new GridBagConstraints(0, y, 1, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

        tfTelephone = new JTextField();
        tfTelephone.setPreferredSize(COMMON_SIZE);
        add(tfTelephone, new GridBagConstraints(1, y++, 1, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        tfTelephone.addFocusListener(focusListener);

        // E mail
        final JLabel mailLabel = new JLabel("Email :");
        add(mailLabel, new GridBagConstraints(0, y, 1, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

        tfEmail = new JTextField();
        tfEmail.setPreferredSize(ADDR_SIZE);
        add(tfEmail, new GridBagConstraints(1, y++, 1, 1, 0.5, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        tfEmail.addFocusListener(focusListener);

        updateEditableFields();
    }

    private void updateEditableFields() {
        tfName.setEditable(editable);
        tfName.setFocusable(editable);
        tfFirstName.setEditable(editable);
        tfFirstName.setFocusable(editable);
        tfAddress.setEditable(editable);
        tfAddress.setFocusable(editable);
        tfPostCode.setEditable(editable);
        tfPostCode.setFocusable(editable);
        tfTown.setEditable(editable);
        tfTown.setFocusable(editable);
        tfTelephone.setEditable(editable);
        tfTelephone.setFocusable(editable);
        tfEmail.setEditable(editable);
        tfEmail.setFocusable(editable);
    }

    private void displayDiscoverer() {
        if (discoverer == null) {
            tfName.setText("");
            tfFirstName.setText("");
            tfAddress.setText("");
            tfPostCode.setText("");
            tfTown.setText("");
            tfTelephone.setText("");
            tfEmail.setText("");
        } else {
            tfName.setText(discoverer.getName());
            tfFirstName.setText(discoverer.getFirstName());
            tfAddress.setText(discoverer.getAddress());
            tfPostCode.setText(discoverer.getPostCode());
            tfTown.setText(discoverer.getTown());
            tfTelephone.setText(discoverer.getTelephone());
            tfEmail.setText(discoverer.getEmail());
        }
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(final boolean editable) {
        this.editable = editable;
        updateEditableFields();
    }

    public Discoverer getDiscoverer() {
        return discoverer;
    }

    public void setDiscoverer(final Discoverer discoverer) {
        this.discoverer = discoverer;
        displayDiscoverer();
    }

    private void focusLostOnField(final FocusEvent event) {
        if (event.getSource() == tfName) {
            discoverer.setName(tfName.getText());
        } else if (event.getSource() == tfFirstName) {
            discoverer.setFirstName(tfFirstName.getText());
        } else if (event.getSource() == tfAddress) {
            discoverer.setAddress(tfAddress.getText());
        } else if (event.getSource() == tfPostCode) {
            discoverer.setPostCode(tfPostCode.getText());
        } else if (event.getSource() == tfTown) {
            discoverer.setTown(tfTown.getText());
        } else if (event.getSource() == tfTelephone) {
            discoverer.setTelephone(tfTelephone.getText());
        } else if (event.getSource() == tfEmail) {
            discoverer.setEmail(tfEmail.getText());
        }
    }
}