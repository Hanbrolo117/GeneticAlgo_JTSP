import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by CptAmerica on 3/24/17.
 */

enum Survival {AGE, FITNESS}

public class GA {
    Population population_pool;
    Random rand;
    double mutation;
    int generations;
    Survival survivalType;


    public GA(City[] map, int popSize, int generations, double mutation, Survival survivalType) {
        this.mutation = mutation;
        this.rand = new Random();
        this.population_pool = new Population(map, popSize);
        this.generations = generations;
        this.survivalType = survivalType;
    }


    //O(n)
    public Individual ox1(Individual parent1, Individual parent2) {
        //Generate new Offspring
        return new Individual(generateChild(parent1, parent2), 0, 0);
    }

    public int[] generateChild(Individual parent1, Individual parent2) {
        int startGene = this.rand.nextInt(parent1.chromosome.length);
        int endGene = (this.rand.nextInt( ((parent1.chromosome.length - startGene)) ) + startGene);
        int[] offspring = new int[parent1.chromosome.length];

        //TODO: Break Down to smaller functions:
        //copy subsequence to new child O(n):
        // Build Gene SubSequence with a map:
        HashMap<Integer,Integer> geneSubSeq = new HashMap<>();

        // Will Iterate less than n times: O(n)
        for(int i = startGene; i <= endGene; i++){
            geneSubSeq.put(parent1.chromosome[i], i);
            offspring[i] = parent1.chromosome[i];
        }

        //Iterate ((n - endGene) + startGene) times:
        int j = 0;
        int i = 0;
        while((j < startGene) && (i < parent2.chromosome.length)){
            if(!geneSubSeq.containsKey(parent2.chromosome[i])){
                offspring[j] = parent2.chromosome[i];
                j++;
            }
            i++;
        }

        //Iterate (n - startGene) times:
        j = endGene+1;
        while((j < parent2.chromosome.length ) && (i < parent2.chromosome.length)){
            if(!geneSubSeq.containsKey(parent2.chromosome[i])){
                offspring[j] = parent2.chromosome[i];
                j++;
            }
            i++;
        }
        return offspring;
    }// END of genChild function


    public void toCtring(int[] chromosome) {
        String chromo = "[";
        for(int i=0; i < chromosome.length; i++) {
            if( (i+1) < chromosome.length) {
                chromo += chromosome[i]+",";
            }else{
                chromo += chromosome[i];
            }
        }
        chromo += "]";
        System.out.println(chromo);
    }

    public void mutateChromosome(Individual mutatee) {
        if(this.rand.nextDouble() <= this.mutation){
            int randPos1 = this.rand.nextInt(mutatee.chromosome.length);
            int randPos2 = this.rand.nextInt(mutatee.chromosome.length);
            int temp = mutatee.chromosome[randPos1];
            mutatee.chromosome[randPos1] = mutatee.chromosome[randPos2];
            mutatee.chromosome[randPos2] = temp;
        }
    }

    public void ga_tsp() {
        Individual originalFittest = this.population_pool.getFittest();
        for(int cur_gen=0; cur_gen < this.generations; cur_gen++){
            System.out.println("Simulating Gen "+cur_gen+"...");
            // Parent Selection
            Individual parent1 = this.population_pool.individualSelection(Selection.PARENT);// O(n^(3/2))
            Individual parent2 = this.population_pool.individualSelection(Selection.PARENT);// O(n^(3/2))

            // Apply Crossover operator to selected parents to generate new
            // individuals
            Individual offspring1 = this.ox1(parent1, parent2);
            // Possibly Mutate new offspring (based on probability)
            this.mutateChromosome(offspring1);
            //Introduce new offspring to the population:
            this.population_pool.pop.add(offspring1);
            // Offspring2:
            Individual offspring2 = this.ox1(parent2, parent1);
            // Possibly Mutate new offspring (based on probability)
            this.mutateChromosome(offspring2);
            //Introduce new offspring to the population:
            this.population_pool.pop.add(offspring2);

            // Survivor Selection (Age based)
            if(this.survivalType == Survival.AGE){
                this.population_pool.survivorSelectionAB();
            }else{
                this.population_pool.survivorSelectionFB();
            }
        }
        Individual newFittest = this.population_pool.getFittest();
//         System.out.println('Original Most Fit Individual level: ${originalFittest.fitness}');
//         System.out.println('Original Most Fit Individual age: ${originalFittest.age}');
//         System.out.println('Original Most Fit Individual age: ${originalFittest.chromosome.toString()}');
//         System.out.println('\n');
//         System.out.println('New Most Fit Individual level: ${newFittest.fitness}');
//         System.out.println('New Most Fit Individual age: ${newFittest.age}');
//         System.out.println('New Most Fit Individual age: ${newFittest.chromosome.toString()}');
    }

    public static City[] genMap(){
        City[] map = new City[50];
        map[0] = (new City( 0, 450, 6));
        map[1] = (new City( 1, 38, 71));
        map[2] = (new City( 2, 485, 291));
        map[3] = (new City( 3, 81, 443));
        map[4] = (new City( 4, 274, 136));
        map[5] = (new City( 5, 194, 290));
        map[6] = (new City( 6, 103, 273));
        map[7] = (new City( 7, 26, 186));
        map[8] = (new City( 8, 178, 438));
        map[9] = (new City( 9, 260, 181));
        map[10] = (new City( 10, 273, 34));
        map[11] = (new City( 11, 116, 492));
        map[12] = (new City( 12, 82, 49));
        map[13] = (new City( 13, 464, 371));
        map[14] = (new City( 14, 109, 34));
        map[15] = (new City( 15, 152, 295));
        map[16] = (new City( 16, 230, 391));
        map[17] = (new City( 17, 33, 374));
        map[18] = (new City( 18, 297, 433));
        map[19] = (new City( 19, 446, 373));
        map[20] = (new City( 20, 496, 426));
        map[21] = (new City( 21, 165, 392));
        map[22] = (new City( 22, 164, 328));
        map[23] = (new City( 23, 60, 434));
        map[24] = (new City( 24, 134, 77));
        map[25] = (new City( 25, 361, 315));
        map[26] = (new City( 26, 93, 267));
        map[27] = (new City( 27, 291, 401));
        map[28] = (new City( 28, 432, 442));
        map[29] = (new City( 29, 497, 436));
        map[30] = (new City( 30, 104, 102));
        map[31] = (new City( 31, 53, 495));
        map[32] = (new City( 32, 149, 462));
        map[33] = (new City( 33, 292, 245));
        map[34] = (new City( 34, 425, 395));
        map[35] = (new City( 35, 428, 105));
        map[36] = (new City( 36, 435, 331));
        map[37] = (new City( 37, 365, 217));
        map[38] = (new City( 38, 126, 94));
        map[39] = (new City( 39, 495, 64));
        map[40] = (new City(40, 377, 36));
        map[41] = (new City(41, 138, 26));
        map[42] = (new City(42, 34, 431));
        map[43] = (new City(43, 53, 194));
        map[44] = (new City(44, 442, 95));
        map[45] = (new City(45, 162, 18));
        map[46] = (new City(46, 49, 109));
        map[47] = (new City(47, 229, 23));
        map[48] = (new City(48, 192, 19));
        map[49] = (new City( 49, 435, 122));
        return map;
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter 'y' to enter a filename to build a city from, enter 'n' if you would like the default map hard-coded in ga.java to be used:");
        System.out.print("> ");
        String cmd = sc.nextLine();
        System.out.println();

        City[] map = null;
        if(cmd.equalsIgnoreCase("y")){
            System.out.println("Enter the name of file (no extension, not expecting .txt) :");
            System.out.print("> ");
            String filename = sc.nextLine();
            map = GA.buildMap(filename);
            System.out.println();

        }else{
            map = GA.genMap();
        }

        double mutation = 0.05;
        int generations = 100000;
        int popSize = 100;
        int deathType = 0;
        Survival survival = Survival.AGE;
        System.out.println("Enter 'y' if you would like to customize the settings of the GA or 'n' if you want the default settings:");
        System.out.println("Default Settings:\n\tMutation: 0.05\n\tGenerations: 100,000\n\tPopulation-Size: 100\n\t Death: By Age\n\n");
        System.out.print("> ");
        cmd = sc.nextLine();
        if(cmd.equalsIgnoreCase("y")){
            System.out.println("Enter the mutation rate (0.0 - 1.0): ");
            System.out.println("Tip: The lower the better, however, having 0 doesn't really help contribute to adding diversity to the population.");
            System.out.print("> ");
            mutation = sc.nextDouble();
            System.out.println();

            System.out.println("Enter the number of generations to run (the terminating condition): ");
            System.out.println("Tip: The more generations simulated the better the evolution.");
            System.out.print("> ");
            generations = sc.nextInt();
            System.out.println();

            System.out.println("Enter the number of individuals to be in the population for each generation: ");
            System.out.println("Tip: large sizes means tournament selection will not represent population deversity as well.\n" +
                    "Too small of a population on the other-hand slows down evolution strive for a goldy-locks value, somewhere around\n" +
                    "the hundreds.");
            System.out.print("> ");
            popSize = sc.nextInt();
            System.out.println();

            System.out.println("How would you like individuals to die? Based on age (enter 0)? Or based on fitness (enter 1)?");
            System.out.println("Age based is more Objective thus better maintaining diversity, fitness based is a bit more subjective but can still\n" +
                    "be quite affective.");
            System.out.print("> ");
            deathType = sc.nextInt();
            if(deathType == 1){
                survival = Survival.FITNESS;
            }
            System.out.println();
        }

        GA ga = new GA(map, popSize, generations, mutation, survival);
        Individual og = ga.population_pool.getFittest();
        //Grabing Stats:
        ga.ga_tsp();
        Individual ng = ga.population_pool.getFittest();

        String ogCorrupt = ga.isCorruptChromosome(og.chromosome) ? "CORRUPT CHROMOSOME!" : "";
        String ngCorrupt = ga.isCorruptChromosome(ng.chromosome) ? "CORRUPT CHROMOSOME!" : "";
        System.out.println("Generation-1 Solution "+ogCorrupt+":");
        System.out.println("\tName: "+og.name+":");
        System.out.println("\tAge: "+og.age);
        System.out.println("\tFitness: "+og.fitness);
        System.out.println("\tChromosome: "+og.toString());
        System.out.println();
        System.out.println("Generation-"+generations+" Solution "+ngCorrupt+":");
        System.out.println("\tName: "+ng.name+":");
        System.out.println("\tAge: "+ng.age);
        System.out.println("\tFitness: "+ng.fitness);
        System.out.println("\tChromosome: "+ng.toString());

    }

    public static City[] buildMap(String filename) throws IOException {
        ArrayList<City> map = new ArrayList<>();
        int cityCount = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(filename+".txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                String[] coordinates = line.split(",");
                map.add(new City(cityCount,Integer.parseInt(coordinates[0]),Integer.parseInt(coordinates[1])));
                cityCount++;
                line = br.readLine();
            }
        }
        City[] ArMap = new City[map.size()];
        for(int i=0; i < ArMap.length; i++){
            ArMap[i] = map.get(i);
        }
        return ArMap;
    }

    public boolean isCorruptChromosome(int[] chromosome) {
        int i=0, j=0;
        boolean isCorrupt = false;
        while( (i < chromosome.length) && !isCorrupt) {
            while( (j < chromosome.length) && !isCorrupt) {
                if(i != j) {
                    if (chromosome[i] == chromosome[j]) {
                        isCorrupt = true;
                    }
                }
                j++;
            }
            i++;
        }
        return isCorrupt;
    }

}
