package me.mb.jgenesis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import me.mb.dlwp.CSVWriter;
import me.mb.dlwp.CSVWriter.CSVParamBuilder;

public abstract class AbstractPopulation {
	
	Logger								LOG		= Logger.getLogger( AbstractPopulation.class.getName() );
	
	protected final List<Individual>	individuals;
	protected final Phenotype			phenotype;
	protected final int					demeSize;
	public static final Random			rand	= new Random();
	protected final PopulationStats		stats;
	protected final float				mutSD;
	protected final float				pCross;
	
	public AbstractPopulation ( int nPopulation, int demeSize, float mutSD, float pCross,
			Phenotype phenotype ) {
		LOG.info( String
				.format(
							"Initialising population using %s phenotype with %d individuals, deme size %d, mutation SD of %f and crossover probability %f\n",
							phenotype.getClass().getName(), nPopulation, demeSize, mutSD, pCross ) );
		individuals = new ArrayList<>( nPopulation );
		stats = new PopulationStats();
		this.phenotype = phenotype;
		this.demeSize = demeSize;
		this.mutSD = mutSD;
		this.pCross = pCross;
		LOG.info( "Creating genotypes and calculating fitnesses..." );
		for ( int i = 0; i < nPopulation; i++ ) {
			Individual ind = Individual.withRandomGenome( phenotype.getGenotypeLength() );
			ind.setFitness( phenotype.calculateFitness( ind.getGenotype().getGenes() ) );
			individuals.add( ind );
		}
		LOG.info( "Finished calculating fitnesses." );
	}
	
	public Genotype getGenotypeAt( int index ) {
		return individuals.get( index ).getGenotype();
	}
	
	public void setGenotypeAt( int index, Genotype genotype ) {
		individuals.get( index ).setGenotype( genotype );
	}
	
	public int selectIndividual() {
		return rand.nextInt( getSize() );
	}
	
	public int selectSecondIndividual( int firstIndex, int demeSize ) {
		int newIndex = firstIndex;
		while ( newIndex == firstIndex ) {
			newIndex = firstIndex + rand.nextInt( demeSize );
		}
		while ( getSize() - 1 < newIndex ) {
			newIndex -= getSize();
		}
		return newIndex;
	}
	
	public void setFitnessAt( int index, float fitness ) {
		individuals.get( index ).setFitness( fitness );
	}
	
	public int getSize() {
		return individuals.size();
	}
	
	public float calculateFitnessAt( int index ) {
		float fitness = phenotype.calculateFitness( getGenotypeAt( index )
				.getGenes() );
		setFitnessAt( index, fitness );
		return fitness;
		
	}
	
	public float getFitnessAt( int index ) {
		return individuals.get( index ).getFitness();
	}
	
	public PopulationStats getStats() {
		return stats;
	}
	
	public void saveGenotypesToCSV( File file ) throws IOException {
		LOG.info( "Saving population genotypes to file '" + file.getAbsolutePath() + "'" );
		CSVParamBuilder cpb = new CSVParamBuilder();
		cpb.addHeader( "fitness" );
		
		for ( int i = 0; i < phenotype.getGenotypeLength(); i++ ) {
			cpb.addHeader( "g" + i );
		}
		
		for ( Individual ind : individuals ) {
			List<Object> row = new ArrayList<>();
			
			row.add( ind.getFitness() );
			for ( Float gene : ind.getGenotype().getGenes() ) {
				row.add( gene );
			}
			cpb.addDataRow( row );
		}
		
		CSVWriter.writeToCSVFile( file, cpb );
		
	}
	
	public void reproduce( int losingIndex, int winningIndex ) {
		Genotype newGeno = getGenotypeAt( losingIndex ).crossover(
																	getGenotypeAt( winningIndex ), pCross ).mutate( mutSD );
		
		for ( float gene : newGeno.getGenes() ) {
			if ( gene < -1 || gene > 1 ) {
				LOG.warning( "Gene value is out of range [-1,1]: " + gene );
			}
		}
		setGenotypeAt( losingIndex, newGeno );
		calculateFitnessAt( losingIndex );
	}
	
	public abstract void doGeneration();
	
	protected void doReproductionEvent( int index1, int index2, float fitness1, float fitness2 ) {
		
		int winningIndex = 0, losingIndex = 0;
		if ( fitness1 > fitness2 ) {
			winningIndex = index1;
			losingIndex = index2;
		} else if ( fitness1 > fitness2 ){
			winningIndex = index2;
			losingIndex = index1;
		} else {
			return;
		}
		reproduce( losingIndex, winningIndex );
	}
	
	public List<Individual> getIndividuals() {
		return individuals;
	}
	
}