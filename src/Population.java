import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by CptAmerica on 3/24/17.
 */

enum Selection { SURVIVAL, PARENT}

public class Population {
    DistMatrix dMatrix;
    ArrayList<Individual> pop;
    Random rand;

    Population(City[] cities, int popSize){
        this.rand = new Random();

        //O(n^2): Build the distance matrix for fitness calculation
        this.dMatrix = new DistMatrix(cities);

        //O(nm): Randomly generate population. ( Worse case O(n^2) )
        this.pop = new ArrayList<>();
        for(int i=0; i < popSize; i++) {

            //Create and encode chromosome:
            int[] chromosome = new int[cities.length];
            for(int j=0; j < cities.length; j++) {
                chromosome[j] = cities[j].name;
            }
            //Create randomize Chromosome Encoding:
            shuffle(chromosome);
            this.pop.add(new Individual(chromosome, 0, 0));
        }

    }


    private void shuffle(int[] chromosome){
        ArrayList<Integer> shuf = new ArrayList<>();
        for (int i = 0; i < chromosome.length; i++) {
            shuf.add(chromosome[i]);
        }
        Collections.shuffle(shuf);
        for (int i = 0; i < chromosome.length; i++) {
            chromosome[i] = shuf.get(i);
        }
    }

    //Via Tournament Selection O(n^(3/2))
    public Individual individualSelection(Selection selectType) {
        //Setup O(1):
        int tourneySize = (int)Math.floor(Math.sqrt(this.pop.size()));
        Individual mostFit = null;

        //Pick a random gene point and calculate it's fitness O(n):
        int genePoint = this.rand.nextInt(tourneySize);
        mostFit = this.pop.get(genePoint);
        fitnessOf(mostFit);

        for(int i=0; i < tourneySize; i++){
            if(selectType == Selection.PARENT){
                if(fitnessOf(this.pop.get(i)) < mostFit.fitness) {
                    mostFit = this.pop.get(i);
                }
            }else{
                if(fitnessOf(this.pop.get(i)) > mostFit.fitness) {
                    mostFit = this.pop.get(i);
                }
            }
        }
        return mostFit;
    }


    public void survivorSelectionAB() {
        //O(n)
        for(int i=0; i < this.pop.size(); i++){
            this.pop.get(i).age++;
        }

        //O(1)
        Individual[] oldest = {this.pop.get(0), this.pop.get(1)};
        int[] remove = {0, 1};

        //O(n)
        for(int i=0; i < this.pop.size(); i++){
            int curLifeSpan = this.pop.get(i).age;
            if((curLifeSpan > oldest[0].age) && (curLifeSpan > oldest[1].age)){
                if(oldest[0].age > oldest[1].age){
                    oldest[0] = this.pop.get(i);
                    remove[0] = i;
                }else{
                    oldest[1] = this.pop.get(i);
                    remove[1] = i;
                }
            }
            if((curLifeSpan > oldest[0].age) && (curLifeSpan < oldest[1].age)){
                oldest[0] = this.pop.get(i);
                remove[0] = i;
            }
            if((curLifeSpan < oldest[0].age) && (curLifeSpan > oldest[1].age)){
                oldest[1] = this.pop.get(i);
                remove[1] = i;
            }
            this.pop.remove(oldest[0]);
            this.pop.remove(oldest[1]);
        }
    }

    public void survivorSelectionFB(){
        Individual remove1 = individualSelection(Selection.SURVIVAL);
        Individual remove2 = individualSelection(Selection.SURVIVAL);
        this.pop.remove(remove1);
        this.pop.remove(remove2);

    }

    public double fitnessOf(Individual individual) {
        double dist = 0.0;
        for(int i=0; i < (individual.chromosome.length-1); i++) {
            dist += dMatrix.matrix[individual.chromosome[i]][individual.chromosome[i+1]];
        }
        individual.fitness = dist;
        return individual.fitness;
    }

    Individual getFittest(){
        Individual fittest = this.pop.get(0);
        fitnessOf(fittest);
        for(int i=0; i < this.pop.size(); i++){
            fittest = (fitnessOf(this.pop.get(i)) < fittest.fitness) ? this.pop.get(i) : fittest;
        }
        return fittest;
    }


}
