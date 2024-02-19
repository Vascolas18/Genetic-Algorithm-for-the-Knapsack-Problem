package knapsack;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;

/*
 * CLASSE SEM A TECNICA PHASER
 * fc56374 - Vasco Maria
 */
public class KnapsackGANoPhaser {
	private static final int N_GENERATIONS = 500;
	private static final int POP_SIZE = 100000;
	private static final double PROB_MUTATION = 0.5;
	private static final int TOURNAMENT_SIZE = 3;
	public static final int nCORES = 12; //FUI ALTERANDO 4/8/12

	ThreadLocalRandom r = ThreadLocalRandom.current();
	Thread[] threads = new Thread[nCORES];
	int div = POP_SIZE / nCORES;
	Phaser phaser = new Phaser(nCORES);

	private Individual[] population = new Individual[POP_SIZE];

	public KnapsackGANoPhaser() {
		populateInitialPopulationRandomly();
	}

	private void populateInitialPopulationRandomly() {
		/* Creates a new population, made of random individuals */
		for (int i = 0; i < POP_SIZE; i++) {
			population[i] = Individual.createRandom(r);
		}
	}

	public void run() {
		for (int generation = 0; generation < N_GENERATIONS; generation++) {

			// Step1 - Calculate Fitness
			calculateFitnessParallelize();

			// Step2 - Print the best individual so far.
			Individual best = bestOfPopulation();
			System.out.println("Best at generation " + generation + " is " + best + " with " + best.fitness);

			// Step3 - Find parents to mate (cross-over)
			Individual[] newPopulation = new Individual[POP_SIZE];
			newPopulation[0] = best; 

			crossOverParallelize(newPopulation);

			// Step4 - Mutate

			mutatePopParallelize(newPopulation);

			population = newPopulation;
		}
	}

	private void crossOverParallelize(Individual[] newPopulation) {
		for (int t = 0; t < nCORES; t++) {
			final int threadId = t;
			threads[t] = new Thread(() -> {
				int end;
				if (threadId == nCORES - 1) {
					end = POP_SIZE;
				} else {
					end = (threadId + 1) * div;
				}
				for (int i = threadId * div; i < end; i++) {
					if (i != 0) {
						Individual parent1 = tournament(TOURNAMENT_SIZE, r);
						Individual parent2 = tournament(TOURNAMENT_SIZE, r);
						newPopulation[i] = parent1.crossoverWith(parent2, r);
					}

				}
			});
			threads[t].start();
		}
		joinThreads();
	}

	private void mutatePopParallelize(Individual[] newPopulation) {
		for (int t = 0; t < nCORES; t++) {
			final int threadId = t;
			threads[t] = new Thread(() -> {
				int end;
				if (threadId == nCORES - 1) {
					end = POP_SIZE;
				} else {
					end = (threadId + 1) * div;
				}
				for (int i = threadId * div; i < end; i++) {
					if (r.nextDouble() < PROB_MUTATION) {
						newPopulation[i].mutate(r);
					}
				}
			});
			threads[t].start();
		}
		joinThreads();
	}

	private void calculateFitnessParallelize() {
		for (int t = 0; t < threads.length; t++) {
			final int threadId = t;
			threads[t] = new Thread(() -> {
				int end;
				if (threadId == nCORES - 1) {
					end = POP_SIZE;
				} else {
					end = (threadId + 1) * div;
				}
				for (int i = threadId * div; i < end; i++) {
					population[i].measureFitness();
				}
			});
			threads[t].start();
		}

		joinThreads();
	}

	private Individual tournament(int tournamentSize, Random r) {
		/*
		 * In each tournament, we select tournamentSize individuals at random, and we
		 * keep the best of those.
		 */
		Individual best = population[r.nextInt(POP_SIZE)];
		for (int i = 0; i < tournamentSize; i++) {
			Individual other = population[r.nextInt(POP_SIZE)];
			if (other.fitness > best.fitness) {
				best = other;
			}
		}
		return best;
	}

	private Individual bestOfPopulation() {
		/*
		 * Returns the best individual of the population.
		 */
		final Individual[] best = new Individual[1];
		// usei um objeto array para usar no synchronized

		best[0] = population[0];

		for (int t = 0; t < nCORES; t++) {
			final int threadId = t;
			threads[t] = new Thread(() -> {
				Individual localBest = population[threadId];
				for (int j = 0; j < population.length; j += nCORES) {
					Individual individual = population[j];
					if (individual.fitness > localBest.fitness) {
						localBest = individual;
					}
				}

				synchronized (best) {
					if (localBest.fitness > best[0].fitness) {
						best[0] = localBest;
					}
				}
			});
			threads[t].start();
		}
		joinThreads();

		return best[0];
	}

	private void joinThreads() {

		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
