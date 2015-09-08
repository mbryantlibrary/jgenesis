package me.mb.jgenesis;

import static org.junit.Assert.*;
import mockit.Expectations;
import mockit.Mocked;

import org.junit.Test;

public class ConcurrentPopulationTest {
	
	Phenotype phenotype;
	
	@Test
	public void testCreatesNewThread() {
		phenotype = new Phenotype() {
			
			@Override
			public int getGenotypeLength() {
				return 6;
			}
			
			@Override
			public float calculateFitness( float[] genes ) {
				float sum = 0;
				for(float gene: genes)
					sum += gene;
				return sum/getGenotypeLength();
			}
		};
		
		ConcurrentPopulation pop = new ConcurrentPopulation( 6, 2, 0.1f, 0.05f, phenotype );
		
		
		pop.doGeneration();
		
	}
	
}
