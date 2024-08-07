/* ***** BEGIN LICENSE BLOCK *****
 *
 * Copyright (c) 2005-2007 Universidade de Sao Paulo, Sao Carlos/SP, Brazil.
 * All Rights Reserved.
 *
 * This file is part of Projection Explorer (PEx).
 *
 * How to cite this work:
 *  
@inproceedings{paulovich2007pex,
author = {Fernando V. Paulovich and Maria Cristina F. Oliveira and Rosane 
Minghim},
title = {The Projection Explorer: A Flexible Tool for Projection-based 
Multidimensional Visualization},
booktitle = {SIBGRAPI '07: Proceedings of the XX Brazilian Symposium on 
Computer Graphics and Image Processing (SIBGRAPI 2007)},
year = {2007},
isbn = {0-7695-2996-8},
pages = {27--34},
doi = {http://dx.doi.org/10.1109/SIBGRAPI.2007.39},
publisher = {IEEE Computer Society},
address = {Washington, DC, USA},
}
 *  
 * PEx is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * PEx is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details.
 *
 * This code was developed by members of Computer Graphics and Image
 * Processing Group (http://www.lcad.icmc.usp.br) at Instituto de Ciencias
 * Matematicas e de Computacao - ICMC - (http://www.icmc.usp.br) of 
 * Universidade de Sao Paulo, Sao Carlos/SP, Brazil. The initial developer 
 * of the original code is Fernando Vieira Paulovich <fpaulovich@gmail.com>.
 *
 * Contributor(s): Rosane Minghim <rminghim@icmc.usp.br>
 *
 * You should have received a copy of the GNU General Public License along 
 * with PEx. If not, see <http://www.gnu.org/licenses/>.
 *
 * ***** END LICENSE BLOCK ***** */

package dataanalysis;

import distance.DistanceMatrix;
import distance.dissimilarity.Euclidean;
import graph.model.Edge;
import graph.model.GraphModel;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import labeledgraph.model.LabeledGraphModel;
import matrix.AbstractMatrix;
import matrix.dense.DenseMatrix;
import matrix.dense.DenseVector;
import net.sf.epsgraphics.ColorMode;
import net.sf.epsgraphics.EpsGraphics;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import projection.model.ProjectionInstance;
import projection.model.ProjectionModel;
import projection.model.Scalar;
import util.filter.EPSFilter;
import visualizationbasics.util.SaveDialog;

/**
 *
 * @author  Jose Gustavo de Souza Paiva
 */
public class DistanceScatterPlot extends javax.swing.JDialog {

    /** Creates new form DistanceScatterPlot */
    private DistanceScatterPlot(javax.swing.JDialog parent) {
        super(parent);
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonPanel = new javax.swing.JPanel();
        saveImageButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Neighborhood Preservation");
        setModalityType(java.awt.Dialog.ModalityType.MODELESS);

        saveImageButton.setText("Save Image");
        saveImageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveImageButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(saveImageButton);

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(closeButton);

        getContentPane().add(buttonPanel, java.awt.BorderLayout.PAGE_END);

        getAccessibleContext().setAccessibleName("Distance ScatterPlot");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void saveImageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveImageButtonActionPerformed
        int result = SaveDialog.showSaveDialog(new EPSFilter(), this, "", "image.eps");

        if (result == JFileChooser.APPROVE_OPTION) {
            String filename = SaveDialog.getFilename();

            FileOutputStream out = null;

            try {
                // Save this document to example.eps
                out = new FileOutputStream(filename);

                // Create a new document with bounding box 0 <= x <= 100 and 0 <= y <= 100.
                EpsGraphics g = new EpsGraphics(filename, out, 0, 0,
                        panel.getWidth() + 1, panel.getHeight() + 1, ColorMode.COLOR_RGB);

                freechart.draw(g, new Rectangle2D.Double(0, 0, panel.getWidth() + 1,
                        panel.getHeight() + 1));

                // Flush and close the document (don't forget to do this!)
                g.flush();
                g.close();

            } catch (IOException ex) {
                Logger.getLogger(DistanceScatterPlot.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Problems saving the file", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (out != null) {
                    try {
                        out.flush();
                        out.close();
                    } catch (IOException ex) {
                        Logger.getLogger(DistanceScatterPlot.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_saveImageButtonActionPerformed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_closeButtonActionPerformed

    public static DistanceScatterPlot getInstance(javax.swing.JDialog parent) {
        return new DistanceScatterPlot(parent);
    }

//    public void display(final DistanceMatrix dmatdata, final ArrayList<Serie> series, final boolean useWeight) {
//        final MessageDialog md = MessageDialog.show(this, "Calculating Distance ScatterPlot...");
//
//        Thread t = new Thread() {
//
//            @Override
//            public void run() {
//                try {
//                    DistanceScatterPlot.this.freechart = createScatter(createAllSeries(dmatdata,series,useWeight));
//                    DistanceScatterPlot.this.panel = new ChartPanel(freechart);
//                    DistanceScatterPlot.this.getContentPane().add(panel, BorderLayout.CENTER);
//                    DistanceScatterPlot.this.getContentPane().add(panel, BorderLayout.CENTER);
//
//                    DistanceScatterPlot.this.setPreferredSize(new Dimension(650, 400));
//                    DistanceScatterPlot.this.setSize(new Dimension(650, 400));
//
//                    DistanceScatterPlot.this.setLocationRelativeTo(DistanceScatterPlot.this.getParent());
//
//                    md.close();
//
//                    DistanceScatterPlot.this.setVisible(true);
//                } catch (IOException ex) {
//                    Logger.getLogger(DistanceScatterPlot.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//
//        };
//
//        t.start();
//    }

    public void display(final DistanceMatrix dmatdata, final Serie serie, final boolean useWeight) {
        final MessageDialog md = MessageDialog.show(this, "Calculating Distance ScatterPlot...");

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    DistanceScatterPlot.this.freechart = createScatter(createAllSeries(dmatdata,serie));
                    if (DistanceScatterPlot.this.freechart != null) {
                        DistanceScatterPlot.this.panel = new ChartPanel(freechart);
                        DistanceScatterPlot.this.getContentPane().add(panel, BorderLayout.CENTER);
                        DistanceScatterPlot.this.getContentPane().add(panel, BorderLayout.CENTER);

                        DistanceScatterPlot.this.setPreferredSize(new Dimension(650, 400));
                        DistanceScatterPlot.this.setSize(new Dimension(650, 400));

                        DistanceScatterPlot.this.setLocationRelativeTo(DistanceScatterPlot.this.getParent());

                        md.close();

                        DistanceScatterPlot.this.setVisible(true);
                    }
                    else md.close();
                } catch (IOException ex) {
                    Logger.getLogger(DistanceScatterPlot.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        };

        t.start();
    }

    private JFreeChart createScatter(XYDataset xydataset) {
        if (xydataset == null) return null;
        JFreeChart chart = ChartFactory.createScatterPlot("Distances","Original Space","Projected Space",xydataset,PlotOrientation.VERTICAL,true,true,false);

        chart.setBackgroundPaint(Color.WHITE);

        XYPlot xyplot = (XYPlot) chart.getPlot();
        NumberAxis numberaxis = (NumberAxis) xyplot.getRangeAxis();
        numberaxis.setAutoRangeIncludesZero(false);

        xyplot.setDomainGridlinePaint(Color.BLACK);
        xyplot.setRangeGridlinePaint(Color.BLACK);

        xyplot.setOutlinePaint(Color.BLACK);
        xyplot.setOutlineStroke(new BasicStroke(1.0f));
        xyplot.setBackgroundPaint(Color.white);
        xyplot.setDomainCrosshairVisible(true);
        xyplot.setRangeCrosshairVisible(true);

        xyplot.setDrawingSupplier(new DefaultDrawingSupplier(
                new Paint[]{Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA,
                    Color.CYAN, Color.ORANGE, Color.BLACK, Color.DARK_GRAY, Color.GRAY,
                    Color.LIGHT_GRAY, Color.YELLOW
                }, DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE,
                DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE));

        XYItemRenderer xyrenderer = xyplot.getRendererForDataset(xydataset);

        return chart;
    }

//    private XYDataset createAllSeries(DistanceMatrix dmatdata, ArrayList<Serie> series) throws IOException {
//        XYSeriesCollection xyseriescollection = new XYSeriesCollection();
//        double[] oValues = new double[dmatdata.getElementCount()];
//        //Getting original distances...
//        for (int i=0;i<dmatdata.getElementCount();i++)
//            for (int j=(i+1);j<dmatdata.getElementCount();j++)
//                oValues[i] = dmatdata.getDistance(i,j);
//
//        for (int i=0;i<series.size();i++) {
//            double[] pValues = this.getDistances(series.get(i).model,dmatdata);
//            XYSeries xyseries = this.createSerie(series.get(i).name,oValues,pValues);
//            xyseriescollection.addSeries(xyseries);
//        }
//        return xyseriescollection;
//    }

    private XYDataset createAllSeries(DistanceMatrix dmatdata, Serie serie) throws IOException {
        XYSeriesCollection xyseriescollection = new XYSeriesCollection();
        double[] oValues = new double[dmatdata.getElementCount()];
        //Getting original distances...
        for (int i=0;i<dmatdata.getElementCount();i++)
            for (int j=(i+1);j<dmatdata.getElementCount();j++)
                oValues[i] = dmatdata.getDistance(i,j);

        double[] pValues = this.getDistances(serie.model,dmatdata);

        if ((pValues == null)||(oValues.length != pValues.length)) {
            return null;
        }
        
        XYSeries xyseries = this.createSerie(serie.name,oValues,pValues);

        xyseriescollection.addSeries(xyseries);

        return xyseriescollection;
    }

    private XYSeries createSerie(String name, double[] oValues, double[] pValues) {
        XYSeries xyseries = new XYSeries(name);
        for (int i=0;i<oValues.length;i++)
            xyseries.add(oValues[i],pValues[i]);
        return xyseries;
    }

    private void normalize(DistanceMatrix dmat, double[] values) {
        double min = dmat.getMinDistance();
        double max = dmat.getMaxDistance();
        for (int i=0;i<values.length;i++)
            values[i] = (values[i]-min)/(max-min);
    }

    public static AbstractMatrix exportPoints(ProjectionModel model, Scalar scalar) {

        AbstractMatrix matrix = new DenseMatrix();

        if (scalar == null) {
            ArrayList<Scalar> scalars = model.getScalars();
            if ((scalars != null)&&(!scalars.isEmpty())) scalar = scalars.get(0);
        }

        ArrayList<String> labels = new ArrayList<String>();

        for (int i = 0; i < model.getInstances().size(); i++) {
            float[] point = new float[2];
            point[0] = ((ProjectionInstance)model.getInstances().get(i)).getX();
            point[1] = ((ProjectionInstance)model.getInstances().get(i)).getY();

            float cdata = ((ProjectionInstance)model.getInstances().get(i)).getScalarValue(scalar);
            Integer id = ((ProjectionInstance)model.getInstances().get(i)).getId();
            labels.add(((ProjectionInstance)model.getInstances().get(i)).toString());

            matrix.addRow(new DenseVector(point, id, cdata));
        }

        matrix.setLabels(labels);

        ArrayList<String> attributes = new ArrayList<String>();
        attributes.add("x");
        attributes.add("y");

        matrix.setAttributes(attributes);

        return matrix;
    }

    public DistanceMatrix createDistanceMatrix(LabeledGraphModel model, boolean useWeights) {

        DistanceMatrix dm = new DistanceMatrix();

        float[][] dmat = new float[model.getInstances().size()][model.getInstances().size()];
        for (int i=0;i<dmat.length;i++)
            for (int j=0;j<dmat[i].length;j++)
                if (i == j) dmat[i][j] = 0;
                else dmat[i][j] = Float.MAX_VALUE;

        //ArrayList<Edge> edges = model.getConnectivityByName("Neighbor-Joinning").getEdges();
        ArrayList<Edge> edges = null;
        if (model.getSelectedConnectivity() != null)
            edges = model.getSelectedConnectivity().getEdges();
        else if ((model.getConnectivities() != null)&&(model.getConnectivities().size() > 1))
                edges = model.getConnectivities().get(1).getEdges();

        if (edges == null) return null;

        for (int k=0;k<edges.size();k++) {
            Edge ed = edges.get(k);
            int x = model.getInstances().indexOf(model.getInstanceById(ed.getSource()));
            int y = model.getInstances().indexOf(model.getInstanceById(ed.getTarget()));
            if (useWeights) dmat[x][y] = dmat[y][x] = ed.getWeight();
            else dmat[x][y] = dmat[y][x] = 1.0f;
        }

        //Calculating the shortest path, in the tree, among all nodes (including virtual nodes)
        //Floyd Warshall algorithm.
        int n = dmat.length;
        for (int k=0; k<n; k++)
            for (int i=0; i<n; i++)
                for (int j=0; j<n; j++) {
                    float dd = dmat[i][k] + dmat[k][j];
                    if (dmat[i][j] > dd) dmat[i][j] = dd;
                }

        int k = -1;
        ArrayList<ArrayList<Float>> ndmat = new ArrayList<ArrayList<Float>>();
        for (int i=0;i<dmat.length;i++) {
            if (!model.getInstances().get(i).toString().isEmpty()) {
                k++;
                ndmat.add(new ArrayList<Float>());
                for (int j=0;j<dmat[i].length;j++) {
                    if (!model.getInstances().get(j).toString().isEmpty()) {
                        ndmat.get(k).add(dmat[i][j]);
                    }
                }
            }
        }

        //Create and fill the distance distmatrix
        dm.setElementCount(ndmat.size());

        float maxDistance = Float.NEGATIVE_INFINITY;
        float minDistance = Float.POSITIVE_INFINITY;

        float[][] distmat = new float[ndmat.size() - 1][];
        for (int i=0; i<ndmat.size()-1; i++) {
            distmat[i] = new float[i + 1];
            for (int j=0;j<distmat[i].length; j++) {
                float distance = ndmat.get(i+1).get(j);
                if (distance < minDistance) minDistance = distance;
                if (distance > maxDistance) maxDistance = distance;
                if ((i+1)!=j) {
                    if ((i+1) < j) distmat[j - 1][(i+1)] = distance;
                    else distmat[(i+1) - 1][j] = distance;
                }
            }
        }
        dm.setMinDistance(minDistance);
        dm.setMaxDistance(maxDistance);
        dm.setDistmatrix(distmat);

        return dm;

    }

    private double[] getDistances(ProjectionModel model, DistanceMatrix dmatdata) throws IOException {

        double[] values = new double[dmatdata.getElementCount()];
        if (model == null) return values;

        Scalar scdata = model.addScalar("cdata");
        AbstractMatrix points = exportPoints(model,scdata);
        
        //Getting projected distances...
        DistanceMatrix dmatproj = null;
        if (model instanceof GraphModel) {//Hierarquical Projection, uses paths to determine distances...
            dmatproj = createDistanceMatrix((LabeledGraphModel)model,false);
            ArrayList<String> labels = points.getLabels();
            ArrayList<Integer> ids = points.getIds();
            float[] c = points.getClassData();
            ArrayList<Float> cdatas = new ArrayList<Float>();
            int k=0;
            //excluding virtual nodes...
            for (int i=0;i<labels.size();i++) {
                if (labels.get(i).isEmpty()) {
                    labels.remove(i);
                    ids.remove(i);
                    i--;
                }else cdatas.add(c[k]);
                k++;
            }
            float[] c2 = new float[cdatas.size()];
            for (int i=0;i<cdatas.size();i++) c2[i] = cdatas.get(i);
            dmatproj.setLabels(labels);
            dmatproj.setIds(ids);
            dmatproj.setClassData(c2);
        }else dmatproj = new DistanceMatrix(points, new Euclidean());

        if (dmatdata.getElementCount() != dmatproj.getElementCount()) {
            System.out.println("Data set different from projection.");
            return null;
        }
        for (int i=0;i<dmatproj.getElementCount();i++)
            for (int j=(i+1);j<dmatproj.getElementCount();j++)
                values[i] = dmatproj.getDistance(i,j);
        normalize(dmatproj,values);
        return values;
    }
        
    private JFreeChart freechart;
    private JPanel panel;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton saveImageButton;
    // End of variables declaration//GEN-END:variables
}
