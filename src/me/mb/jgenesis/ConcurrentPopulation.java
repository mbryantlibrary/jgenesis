package me.mb.jgenesis;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConcurrentPopulation extends AbstractPopulation {
	
	private final ExecutorService	threadPool;
	
	/**
	 * @param nPopulation
	 * @param demeSize
	 * @param mutSD
	 * @param pCross
	 * @param phenotype
	 */
	public ConcurrentPopulation ( int nPopulation, int demeSize, float mutSD, float pCross, Phenotype phenotype ) {
		super( nPopulation, demeSize, mutSD, pCross, phenotype );
		threadPool = Executors.newFixedThreadPool( 2 );
	}
	
	@Override
	public void doGeneration() {
		for ( int i = 0; i < individuals.size(); i++ ) {
			final int index1 = selectIndividual();
			final int index2 = selectSecondIndividual( index1, demeSize );
			
			Future<Float> result1 = threadPool.submit(new FitnessCalculation( phenotype, getGenotypeAt( index1 ).getGenes() ));
			Future<Float> result2 = threadPool.submit(new FitnessCalculation( phenotype, getGenotypeAt( index2 ).getGenes() ));
			
			while(!result1.isDone() && !result2.isDone()) {
				Thread.yield();
			}			
			try {
				doReproductionEvent( index1, index2, result1.get(), result2.get() );
			} catch ( InterruptedException | ExecutionException e ) {
				throw new RuntimeException( e );
			}
		}
		stats.update( individuals );
	}
	
	private static class FitnessCalculation implements Callable<Float> {

		private final float[] genes;
		private final Phenotype phenotype; 
		
		public FitnessCalculation (Phenotype phenotype, float[] genes) {
			this.genes = genes;
			this.phenotype = phenotype;
		}
		
		@Override
		public Float call() throws Exception {
			return phenotype.calculateFitness( genes );
		}	
		
	}
	
}
