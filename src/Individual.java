import java.util.UUID;

/**
 * Created by CptAmerica on 3/24/17.
 */
public class Individual {
    public int[] chromosome;
    public int age;
    public double fitness;
    public String name;

    Individual(int[] chromosome, int age, int fitness){
        this.name = UUID.randomUUID().toString();
        this.age = age;
        this.fitness = fitness;
        this.chromosome = chromosome;
    }

    @Override
    public String toString() {
        String chromo = "[";
        for(int i=0; i < this.chromosome.length; i++) {
            if( (i+1) < this.chromosome.length) {
                chromo += this.chromosome[i]+",";
            }else{
                chromo += this.chromosome[i];
            }
        }
        chromo += "]";
        return chromo;
    }
}
