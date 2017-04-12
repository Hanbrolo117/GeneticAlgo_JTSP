/**
 * Created by CptAmerica on 3/24/17.
 */
public class DistMatrix {
    double[][] matrix;

    DistMatrix(City[] cities){
        matrix  = new double[cities.length][cities.length];
        for(int i=0; i < cities.length; i++) {
            for(int j=0; j < cities.length; j++) {
                double xDist = Math.abs(cities[j].x - cities[i].x);
                double yDist = Math.abs(cities[j].y - cities[i].y);
                double ij_dist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
                matrix[i][j] = ij_dist;
            }
        }
    }

}
