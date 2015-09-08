package me.mb.jgenesis;

public interface Phenotype {
	public float calculateFitness(float[] genes);
	
	public int getGenotypeLength();
}
