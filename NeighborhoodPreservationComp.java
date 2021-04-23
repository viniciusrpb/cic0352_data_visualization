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
import java.util.logging.Level;
import java.util.logging.Logger;
import labeledprojection.model.LabeledProjectionModel;
import matrix.AbstractMatrix;
import matrix.MatrixFactory;
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
 * @author Jose Gustavo
 */
@VisComponent(hierarchy = "Data Analysis",
name = "Neighborhood Preservation",
description = "calculate the neighboorhod preservation measure.")
public class NeighborhoodPreservationComp implements AbstractComponent {

    @Override
    public void execute() throws IOException {
        ArrayList<Serie> series = new ArrayList<Serie>();
        AbstractDissimilarity diss = null;
        if (this.disstype != null)
            diss = DissimilarityFactory.getInstance(this.disstype);

        if (this.dataFileName.trim().length() > 0) {
            if (this.dataFileType == 0) {
                try {
                    AbstractMatrix matrix = MatrixFactory.getInstance(this.dataFileName);
                    //AbstractDissimilarity diss = DissimilarityFactory.getInstance(this.disstype);
                    dmat = new DistanceMatrix(matrix, diss);
                } catch (IOException ex) {
                    Logger.getLogger(NeighborhoodPreservationComp.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (this.dataFileType == 1) {
                try {
                    dmat = new DistanceMatrix(this.dataFileName);
                } catch (IOException ex) {
                    Logger.getLogger(NeighborhoodPreservationComp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("Data filename not informed.");
        }

        if (dmat == null) {
            System.out.println("Error in DistanceMatrix.");
            return;
        }

        if ((models == null || models.isEmpty()) && (matrices == null || matrices.isEmpty()) && (dmats == null || dmats.isEmpty())) {
            System.out.println("No input informed.");
            return;
        }

        if (models != null && !models.isEmpty()) {
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

        resultValues = NeighborhoodPreservation.getInstance(null).display(this.dmat,series,this.dmats,this.nrNeighbors,this.useVisEuclideanDistance,this.useWeight,this.useEuclideanAsWeights,this.distanceCalcType);
    }

    public void attach(@Param(name = "simple tree model") SimpleTreeModel model) {
        if (models == null)
            models = new ArrayList<ProjectionModel>();
        if (model != null)
            models.add((ProjectionModel) model);
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

    public void attach(@Param(name = "projection model") LabeledProjectionModel model) {
        if (models == null)
            models = new ArrayList<ProjectionModel>();
        if (model != null)
            models.add(model);
    }

    public void attach(@Param(name = "topic projection model") TopicProjectionModel model) {
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
            paramview = new NeighborhoodPreservationParamView(this);
        }
        return paramview;
    }

    @Override
    public void reset() {
        models = null;
        matrices = null;
        dmats = null;
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

    public DissimilarityType getDissimilarityType() {
        return disstype;
    }

    public void setDissimilarityType(DissimilarityType diss) {
        this.disstype = diss;
    }

    public boolean isDistanceMatrixProvided() {
        return (getDmat() != null);
    }
    
    public DistanceMatrix getDmat() {
        return dmat;
    }

    public void setDmat(DistanceMatrix dmat) {
        this.dmat = dmat;
    }

    public String getDataFileName() {
        return dataFileName;
    }

    public void setDataFileName(String dataFileName) {
        this.dataFileName = dataFileName;
    }

    public int getDataFileType() {
        return dataFileType;
    }

    public void setDataFileType(int dataFileType) {
        this.dataFileType = dataFileType;
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

    public static final long serialVersionUID = 1L;
    private transient ArrayList<ProjectionModel> models;
    private transient ArrayList<AbstractMatrix> matrices;
    private transient ArrayList<DistanceMatrix> dmats;

    private boolean useWeight = false;
    private int nrNeighbors = 30;
    private transient NeighborhoodPreservationParamView paramview;
    private DissimilarityType disstype = DissimilarityType.EUCLIDEAN;
    private transient DistanceMatrix dmat;
    private String dataFileName = "";
    private int dataFileType = 0;
    private boolean useVisEuclideanDistance = false;
    private boolean useEuclideanAsWeights = false;
    private int distanceCalcType = 1;
    TreeMap<String,double[]> resultValues;

    public int getDistanceCalcType() {
        return distanceCalcType;
    }

    public void setDistanceCalcType(int selectedIndex) {
        distanceCalcType = selectedIndex;
    }

    public TreeMap<String,double[]> getResultValues() {
        return resultValues;
    }

    public void setResultValues(TreeMap<String,double[]> resultValues) {
        this.resultValues = resultValues;
    }
    
}
