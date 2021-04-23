/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dataanalysis;

import distance.DetailedDistanceMatrix;
import distance.DistanceMatrix;
import distance.dissimilarity.AbstractDissimilarity;
import distance.dissimilarity.DissimilarityFactory;
import distance.dissimilarity.DissimilarityFactory.DissimilarityType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import labeledprojection.model.LabeledProjectionModel;
import matrix.AbstractMatrix;
import projection.model.ProjectionModel;
import simpletree.model.SimpleTreeModel;
import topics.model.TopicProjectionModel;
import topics.model.TopicTreeModel;
import tree.model.TreeModel;
import vispipelinebasics.annotations.Param;
import vispipelinebasics.annotations.VisComponent;
import vispipelinebasics.interfaces.AbstractComponent;
import vispipelinebasics.interfaces.AbstractParametersView;

/**
 *
 * @author Fernando V. Paulovic
 */
@VisComponent(hierarchy = "Data Analysis",
name = "Neighborhood Hit",
description = "calculate the neighboorhod hit measure.")
public class NeighborhoodHitComp implements AbstractComponent {

    @Override
    public void execute() throws IOException {
        //NeighborhoodHitView.getInstance(null).display();
        ArrayList<Serie> series = new ArrayList<Serie>();
        AbstractDissimilarity diss = null;
        if (this.disstype != null)
            diss = DissimilarityFactory.getInstance(this.disstype);

        if ((models == null || models.isEmpty()) && (matrices == null || matrices.isEmpty()) && (dmats == null || dmats.isEmpty())) {
            System.out.println("No input informed.");
            return;
        }

        if (models != null && !models.isEmpty()) {
            for (int i=0;i<models.size();i++) {
                if (models.get(i) instanceof SimpleTreeModel) {
                    if (originalPoints == null) {
                        JOptionPane.showMessageDialog(paramview,
                                                      "The original points matrix should be informed!",
                                                      "Error",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }
            for (int i=0;i<models.size();i++) {
                Serie serie = new Serie(models.get(i));
                series.add(serie);
            }
        }

        //Creating dmats with the matrix informed...
        if (matrices != null && !matrices.isEmpty()) {
            for (int i=0;i<matrices.size();i++) {
                DistanceMatrix dm = new DetailedDistanceMatrix(matrices.get(i),diss);
                if (dm != null) {
                    if (dmats == null)
                        dmats = new ArrayList<DistanceMatrix>();
                    dmats.add(dm);
                }
            }
        }
        
        //NeighborhoodHit.Serie serie = new NeighborhoodHit.Serie(model);
        //series.add(serie);


//        for (int i = 0; i < this.projTable.getRowCount(); i++) {
//            String filename = (String) this.projTable.getValueAt(i, 1);
//
//            if (filename.trim().length() > 0) {
//                String description = (String) this.projTable.getValueAt(i, 0);
//
//                NeighborhoodHit.Serie serie = new NeighborhoodHit.Serie(description, filename);
//                try {
//                    serie.readModel();
//                    series.add(serie);
//                } catch (IOException ex) {
//                    Logger.getLogger(NeighborhoodHitView.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//            }
//        }

        resultValues = NeighborhoodHit.getInstance(null).display(series,this.dmats,this.nrNeighbors,this.useVisEuclideanDistance,this.useWeight,this.useEuclideanAsWeights,this.originalPoints);
    }

    public void attach(@Param(name = "simple tree model") SimpleTreeModel model) {
        if (models == null)
            models = new ArrayList<ProjectionModel>();
        if (model != null)
            models.add((ProjectionModel) model);
    }
    
    public void input(@Param(name = "original points matrix") AbstractMatrix matrix) {
        originalPoints = matrix;
    }
    
    public void attach(@Param(name = "topic projection model") TopicProjectionModel model) {
        if (models == null)
            models = new ArrayList<ProjectionModel>();
        if (model != null)
            models.add(model);
    }

     public void attach(@Param(name = "projection model") LabeledProjectionModel model) {
        if (models == null)
            models = new ArrayList<ProjectionModel>();
        if (model != null)
            models.add(model);
    }

     public void attach(@Param(name = "tree model") TreeModel model) {
        if (models == null)
            models = new ArrayList<ProjectionModel>();
        if (model != null)
            models.add((ProjectionModel) model);
    }

    public void attach(@Param(name = "topic tree model") TopicTreeModel model) {
        if (models == null)
            models = new ArrayList<ProjectionModel>();
        if (model != null)
            models.add(model);
    }

    public void attach(@Param(name = "points matrix") AbstractMatrix matrix) {
        if (matrices == null)
            matrices = new ArrayList<AbstractMatrix>();
        if (matrices != null)
            matrices.add(matrix);
    }

    public void attach(@Param(name = "distance matrix") DistanceMatrix dmat) {
        if (dmats == null)
            dmats = new ArrayList<DistanceMatrix>();
        if (dmats != null)
            dmats.add(dmat);
    }

//    public AbstractMatrix output() {
//        return input;
//    }

    @Override
    public AbstractParametersView getParametersEditor() {
        if (paramview == null) {
            paramview = new NeighborhoodHitParamView(this);
        }
        return paramview;
    }

    @Override
    public void reset() {
        models = null;
        matrices = null;
        dmats = null;
        originalPoints = null;
    }

    public boolean isUseWeight() {
        return useWeight;
    }

    public void setUseWeight(boolean useWeight) {
        this.useWeight = useWeight;
    }

    public int getNrNeighbors() {
        return nrNeighbors;
    }

    public void setNrNeighbors(int nrNeighbors) {
        this.nrNeighbors = nrNeighbors;
    }

    public boolean getUseVisEuclidianDistance() {
        return useVisEuclideanDistance;
    }

    public void setUseVisEuclidianDistance(boolean selected) {
        this.useVisEuclideanDistance = selected;
    }

    public boolean getUseEuclideanAsWeights() {
        return useEuclideanAsWeights;
    }

    public void setUseEuclideanAsWeights(boolean selected) {
        this.useEuclideanAsWeights = selected;
    }

    public DissimilarityType getDissimilarityType() {
        return disstype;
    }

    public void setDissimilarityType(DissimilarityType diss) {
        this.disstype = diss;
    }

    public static final long serialVersionUID = 1L;
    private transient ArrayList<ProjectionModel> models;
    private transient ArrayList<AbstractMatrix> matrices;
    private transient ArrayList<DistanceMatrix> dmats;
    private DissimilarityType disstype = DissimilarityType.EUCLIDEAN;
    
    private transient AbstractMatrix originalPoints;

    private boolean useWeight = false;
    private int nrNeighbors = 30;
    private transient NeighborhoodHitParamView paramview;
    private boolean useVisEuclideanDistance = false;
    private boolean useEuclideanAsWeights = false;
    
    TreeMap<String,double[]> resultValues;
    
    public TreeMap<String,double[]> getResultValues() {
        return resultValues;
    }

    public void setResultValues(TreeMap<String,double[]> resultValues) {
        this.resultValues = resultValues;
    }
    
}
