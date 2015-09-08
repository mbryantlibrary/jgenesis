package me.mb.jgenesis;

public class SchwefelsDemo {

	public static void main(String[] args) {
		AbstractPopulation pop = new Population(500, 20,0.1f,0.05f,  new SchwefelPhenotype(30));
		System.out.println(pop.getStats().getConsoleHeaders());

		for (int i = 0; i < 500; i++) {
			pop.doGeneration();
			System.out.println(pop.getStats().getConsoleOutputLine());
		}
		
		System.out.println("Best Individual:");
		
		System.out.println(pop.getGenotypeAt(pop.getStats().getIndexMax()));
		
	}

	public static float schwefels(float[] vector) {
		// from
		// http://www.cs.cmu.edu/afs/cs/project/jair/pub/volume24/ortizboyer05a-html/node6.html

		float outerSum = 0;
		for (int i = 0; i < vector.length; i++) {
			float val = vector[i]*500;
			outerSum += val*(float)Math.sin(Math.sqrt(Math.abs(val)));
		}
		return 418.9829f*vector.length - outerSum;
	}

	public static class SchwefelPhenotype implements Phenotype {

		private final int dimensions;

		public SchwefelPhenotype(int dimensions) {
			this.dimensions = dimensions;
		}

		@Override
		public float calculateFitness(float[] genes) {
			// TODO Auto-generated method stub
			return (12000f-schwefels(genes))/12000f;
		}

		@Override
		public int getGenotypeLength() {
			// TODO Auto-generated method stub
			return dimensions;
		}

	}

}
