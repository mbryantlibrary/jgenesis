package me.mb.jgenesis;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.MockUp;
import mockit.Mocked;

import org.junit.Test;

public class GATest {

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
	@Mocked AbstractPopulation pop;
	

}
