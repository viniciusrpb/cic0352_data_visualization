/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projection.stress;

import distance.LightWeightDistanceMatrix;
import distance.dissimilarity.AbstractDissimilarity;
import distance.dissimilarity.Euclidean;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import matrix.AbstractMatrix;
import matrix.MatrixFactory;
import matrix.dense.DenseMatrix;

/**
 *
 * @author paulovich
 */
public class StressCurve {

    public BufferedImage generate(Dimension size, float alpha, AbstractMatrix projection,
            AbstractMatrix matrix, AbstractDissimilarity diss) throws IOException {
        BufferedImage image = new BufferedImage(size.width, size.height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();

        //filling the background
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, size.width, size.height);       

        //finding the max and min distances
        LightWeightDistanceMatrix dmat = new LightWeightDistanceMatrix(matrix, diss);
        LightWeightDistanceMatrix dmatprj = new LightWeightDistanceMatrix(projection, new Euclidean());

        double maxrn = Double.NEGATIVE_INFINITY;
        double maxr2 = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < dmat.getElementCount(); i++) {
            for (int j = i + 1; j < dmat.getElementCount(); j++) {
                double valuern = dmat.getDistance(i, j);
                double valuer2 = dmatprj.getDistance(i, j);

                if (valuern > maxrn) {
                    maxrn = valuern;
                }

                if (valuer2 > maxr2) {
                    maxr2 = valuer2;
                }
            }
        }

        //drawing the dots
        int rgbcolor = Color.GRAY.getRGB();//(new Color(110,130,105)).getRGB(); //Color.BLUE.getRGB();
        for (int i = 0; i < dmat.getElementCount(); i++) {
            for (int j = i + 1; j < dmat.getElementCount(); j++) {
                double distrn = dmat.getDistance(i, j) / maxrn;
                double distr2 = dmatprj.getDistance(i, j) / maxr2;

                int x = 2 * SPACE + (int) ((size.width - 3 * SPACE) * distrn);
                int y = 2 * SPACE + (int) ((size.height - 3 * SPACE) * distr2);

                simulateAlpha(image, alpha, x, y, rgbcolor);
            }
        }

        //drawing the axis
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(4));
        g2.drawLine(SPACE / 2, SPACE, size.width - SPACE, SPACE);
        g2.drawLine(SPACE, SPACE / 2, SPACE, size.height - SPACE);
        
        g2.setStroke(new BasicStroke(3f));
        g2.setColor(Color.RED);
        g2.drawLine(SPACE/2, SPACE / 2, size.width - SPACE, size.height - SPACE);

        g2.setStroke(new BasicStroke(1));

        //reflecting the image around x
        BufferedImage refimage = new BufferedImage(size.height, size.width,
                BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < size.height; i++) {
            for (int j = 0; j < size.width; j++) {
                refimage.setRGB(j, i, image.getRGB(j, size.height - i - 1));
            }
        }

        return refimage;
    }

    private void simulateAlpha(BufferedImage image, float alpha, int x, int y, int rgb) {
        try {
            //C = (alpha * (A-B)) + B
            int oldrgb = image.getRGB(x, y);
            int oldr = (oldrgb >> 16) & 0xFF;
            int oldg = (oldrgb >> 8) & 0xFF;
            int oldb = oldrgb & 0xFF;

            int newr = (int) ((alpha * (((rgb >> 16) & 0xFF) - oldr)) + oldr);
            int newg = (int) ((alpha * (((rgb >> 8) & 0xFF) - oldg)) + oldg);
            int newb = (int) ((alpha * ((rgb & 0xFF) - oldb)) + oldb);

            int newrgb = newb | (newg << 8) | (newr << 16);
            image.setRGB(x, y, newrgb);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println(x + "," + y);
        }
    }

    public static void main(String[] args) throws IOException {
        String dir = "/home/paulovich/Dropbox/dados/";
        AbstractMatrix data = MatrixFactory.getInstance(dir + "segmentation-normcols.data-notnull.data");
        AbstractMatrix proj = new DenseMatrix();
        proj.load(dir + "segmentation-normcols.data-notnull.data-lamp.prj");

        StressCurve sc = new StressCurve();
        BufferedImage image = sc.generate(new Dimension(800, 800), 0.1f, proj, data, new Euclidean());
        ImageIO.write(image, "png", new File("/home/paulovich/stress_pastel.png"));
    }

    private static final int SPACE = 20;
}
