package me.mb.jgenesis;

import static org.junit.Assert.*;

import java.util.Random;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;

import org.junit.Test;

public class PopulationTest {

	Phenotype pheno = new Phenotype() {
		@Override
		public float calculateFitness(float[] genes) {
			return 0;
		}

		@Override
		public int getGenotypeLength() {
			return 10;
		}
	};

	@Mocked Random rand;
	
	@Test
	public void populationCreatesPopulationOfIndividualsOnInit() throws Exception {
		AbstractPopulation pop = new Population(10,3,0.1f,0.05f, pheno);
		assertEquals(10,pop.getSize());
	}
	
	@Test
	public void selectsIndividualsWithinDeme() throws Exception {
		AbstractPopulation pop = new Population(10,3,0.1f,0.05f,  pheno);
		new Expectations() {{
			rand.nextInt(3); result = 2;
		}};
		
		int expected = 1;
		int result = pop.selectSecondIndividual(9, 3);
		
		assertEquals(expected,result);		
	}
	
	@Test
	public void doesntSelectSameIndividualTwice() throws Exception {
		AbstractPopulation pop = new Population(10,3,0.1f,0.05f,  pheno);
		new Expectations() {{
			rand.nextInt(3); result = 0;result=1;
		}};

		int notExpected = 9,
				expected = 0;
		int result = pop.selectSecondIndividual(9, 3);
		
		assertNotEquals(notExpected,result);
		assertEquals(expected,result);
		
	}

}
