package me.mb.jgenesis;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GA {

	private final AbstractPopulation population;
	private final int demeSize;

	public GA(AbstractPopulation population,int demeSize) {
		this.population = population;
		this.demeSize = demeSize;
	}

	public void run(int nGenerations) {
		for (int i = 0; i < nGenerations; i++) {
			population.doGeneration();
		}
	}

	public int getPopulationSize() {
		return population.getSize();
	}
	
	public AbstractPopulation getPopulation() {
		return population;
	}
	

}
