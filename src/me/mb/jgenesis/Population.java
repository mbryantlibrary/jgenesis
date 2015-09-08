package me.mb.jgenesis;

public class Population extends AbstractPopulation {
	public Population ( int nPopulation, int demeSize, float mutSD, float pCross,
			Phenotype phenotype ) {
		super( nPopulation, demeSize, mutSD, pCross, phenotype );
	}
	
	
	@Override
	public void doGeneration() {
		for ( int i = 0; i < individuals.size(); i++ ) {
			int index1 = selectIndividual(), index2 = selectSecondIndividual(
																				index1, demeSize );
			
			float fitness1 = calculateFitnessAt( index1 ), fitness2 = calculateFitnessAt( index2 );
			
			doReproductionEvent( index1, index2, fitness1, fitness2 );
			
		}
		stats.update( individuals );
	}
}
