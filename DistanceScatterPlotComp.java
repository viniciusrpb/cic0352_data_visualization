/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dataanalysis;

import distance.DistanceMatrix;
import distance.dissimilarity.DissimilarityFactory.DissimilarityType;
import java.io.IOException;
import java.util.ArrayList;
import projection.model.ProjectionModel;
import tree.model.TreeModel;
import vispipelinebasics.annotations.VisComponent;
import vispipelinebasics.annotations.Param;
import vispipelinebasics.interfaces.AbstractParametersView;
import vispipelinebasics.interfaces.AbstractComponent;

/**
 *
 * @author Jose Gustavo
 */
@VisComponent(hierarchy = "Data Analysis",
name = "Distance Scatterplot",
description = "calculate the the distance scatterplot of a layout.")
public class DistanceScatterPlotComp implements AbstractComponent {

    @Override
    public void execute() throws IOException {
        if (dmat == null) return;
        for (int i=0;i<models.size();i++) {
            Serie serie = new Serie(models.get(i));
            DistanceScatterPlot.getInstance(null).display(this.dmat,serie,this.useWeight);
        }
    }

    public void attach(@Param(name = "tree model") TreeModel model) {
        if (models == null)
            models = new ArrayList<ProjectionModel>();
        if (model != null)
            models.add(model);
    }

    public void attach(@Param(name = "projection model") ProjectionModel model) {
        if (models == null)
            models = new ArrayList<ProjectionModel>();
        if (model != null)
            models.add(model);
    }

//    public AbstractMatrix output() {
//        return input;
//    }

    @Override
    public AbstractParametersView getParametersEditor() {
        if (paramview == null) {
            paramview = new DistanceScatterPlotParamView(this);
        }
        return paramview;
    }

    @Override
    public void reset() {
        models = null;
    }

    public boolean isUseWeight() {
        return useWeight;
    }

    public void setUseWeight(boolean useWeight) {
        this.useWeight = useWeight;
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

    public static final long serialVersionUID = 1L;
    private ArrayList<ProjectionModel> models;
    private transient boolean useWeight;
    private transient DistanceScatterPlotParamView paramview;
    private DissimilarityType disstype = DissimilarityType.EUCLIDEAN;
    private transient DistanceMatrix dmat;
    
}
