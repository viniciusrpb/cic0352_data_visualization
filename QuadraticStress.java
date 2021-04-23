/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package projection.stress;

import distance.DistanceMatrix;
import distance.LightWeightDistanceMatrix;
import distance.dissimilarity.AbstractDissimilarity;
import distance.dissimilarity.Euclidean;
import java.io.IOException;
import matrix.AbstractMatrix;

/**
 *
 * @author Fernando
 */
public class QuadraticStress extends Stress {

    @Override
    public float calculate(AbstractMatrix projection, AbstractMatrix matrix,
            AbstractDissimilarity diss) throws IOException {
        LightWeightDistanceMatrix dmat = new LightWeightDistanceMatrix(matrix, diss);
        return calculate(projection, dmat);
    }

    @Override
    public float calculate(AbstractMatrix projection, DistanceMatrix dmat) throws IOException {
        LightWeightDistanceMatrix dmatprj = new LightWeightDistanceMatrix(projection, new Euclidean());

        float num = 0.0f;
        int count = 0;
        for (int i = 0; i < dmat.getElementCount(); i++) {
            for (int j = i; j < dmat.getElementCount(); j++) {
                num += (dmat.getDistance(i, j) - dmatprj.getDistance(i, j)) *
                        (dmat.getDistance(i, j) - dmatprj.getDistance(i, j));
                count++;
            }
        }

        return num / count;
    }

}
