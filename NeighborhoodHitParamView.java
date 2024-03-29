package dataanalysis;

import distance.dissimilarity.DissimilarityFactory.DissimilarityType;
import java.io.IOException;
import vispipelinebasics.interfaces.AbstractParametersView;

/**
 *
 * @author Jose Gustavo de Souza Paiva
 */
public class NeighborhoodHitParamView extends AbstractParametersView {

    /** Creates new form NJConnectionParamView */
    public NeighborhoodHitParamView(NeighborhoodHitComp comp) {
        initComponents();
        for (DissimilarityType disstype : DissimilarityType.values()) {
            this.distanceComboBox.addItem(disstype);
        }
        this.comp = comp;
        reset();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        nrNeighborsPanel = new javax.swing.JPanel();
        nrNeighborsLabel = new javax.swing.JLabel();
        nrNeighborsTextField = new javax.swing.JTextField();
        treePanel = new javax.swing.JPanel();
        useWeightCheckBox = new javax.swing.JCheckBox();
        useVisEuclideanDistanceCheckBox = new javax.swing.JCheckBox();
        useEuclideanAsWeightsCheckBox = new javax.swing.JCheckBox();
        chooseDistanceTypePanel2 = new javax.swing.JPanel();
        distanceComboBox = new javax.swing.JComboBox();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Neighborhood Hit Parameters"));
        setLayout(new java.awt.GridBagLayout());

        nrNeighborsPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        nrNeighborsLabel.setText("Number of neighbors");
        nrNeighborsPanel.add(nrNeighborsLabel);

        nrNeighborsTextField.setColumns(10);
        nrNeighborsTextField.setText("30");
        nrNeighborsPanel.add(nrNeighborsTextField);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(nrNeighborsPanel, gridBagConstraints);

        treePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Trees"));
        treePanel.setLayout(new java.awt.GridBagLayout());

        useWeightCheckBox.setText("Use Edges Weights");
        useWeightCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        useWeightCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                useWeightCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        treePanel.add(useWeightCheckBox, gridBagConstraints);

        useVisEuclideanDistanceCheckBox.setText("Use Euclidean Distance (Visualization Plane)");
        useVisEuclideanDistanceCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                useVisEuclideanDistanceCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        treePanel.add(useVisEuclideanDistanceCheckBox, gridBagConstraints);

        useEuclideanAsWeightsCheckBox.setText("Use Plane Euclidean Distances as Weights");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        treePanel.add(useEuclideanAsWeightsCheckBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(treePanel, gridBagConstraints);

        chooseDistanceTypePanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Choose the Distance Type"));
        chooseDistanceTypePanel2.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        chooseDistanceTypePanel2.add(distanceComboBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(chooseDistanceTypePanel2, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void useWeightCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_useWeightCheckBoxActionPerformed
        if (useWeightCheckBox.isSelected()) {
            useEuclideanAsWeightsCheckBox.setEnabled(true);
        }else {
            useEuclideanAsWeightsCheckBox.setSelected(false);
            useEuclideanAsWeightsCheckBox.setEnabled(false);
        }
}//GEN-LAST:event_useWeightCheckBoxActionPerformed

    private void useVisEuclideanDistanceCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_useVisEuclideanDistanceCheckBoxActionPerformed
        if (useVisEuclideanDistanceCheckBox.isSelected()) {
            useWeightCheckBox.setSelected(false);
            useWeightCheckBox.setEnabled(false);
            useEuclideanAsWeightsCheckBox.setSelected(false);
            useEuclideanAsWeightsCheckBox.setEnabled(false);
        }else {
            useWeightCheckBox.setEnabled(true);
        }
}//GEN-LAST:event_useVisEuclideanDistanceCheckBoxActionPerformed

    @Override
    public void reset() {
        if (comp.getNrNeighbors() == 0)
            nrNeighborsTextField.setText("30");
        else
            nrNeighborsTextField.setText(Integer.toString(comp.getNrNeighbors()));
        distanceComboBox.setSelectedItem(comp.getDissimilarityType());
        useVisEuclideanDistanceCheckBox.setSelected(comp.getUseVisEuclidianDistance());
        useWeightCheckBox.setSelected(comp.isUseWeight());
        useEuclideanAsWeightsCheckBox.setSelected(comp.getUseEuclideanAsWeights());
        useWeightCheckBoxActionPerformed(null);
        useVisEuclideanDistanceCheckBoxActionPerformed(null);
    }

    @Override
    public void finished() throws IOException {
        comp.setNrNeighbors(Integer.parseInt(nrNeighborsTextField.getText()));
        comp.setDissimilarityType((DissimilarityType) distanceComboBox.getSelectedItem());
        comp.setUseWeight(useWeightCheckBox.isSelected());
        comp.setUseVisEuclidianDistance(this.useVisEuclideanDistanceCheckBox.isSelected());
        comp.setUseEuclideanAsWeights(this.useEuclideanAsWeightsCheckBox.isSelected());
    }

    private NeighborhoodHitComp comp;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chooseDistanceTypePanel2;
    private javax.swing.JComboBox distanceComboBox;
    private javax.swing.JLabel nrNeighborsLabel;
    private javax.swing.JPanel nrNeighborsPanel;
    private javax.swing.JTextField nrNeighborsTextField;
    private javax.swing.JPanel treePanel;
    private javax.swing.JCheckBox useEuclideanAsWeightsCheckBox;
    private javax.swing.JCheckBox useVisEuclideanDistanceCheckBox;
    private javax.swing.JCheckBox useWeightCheckBox;
    // End of variables declaration//GEN-END:variables
}
