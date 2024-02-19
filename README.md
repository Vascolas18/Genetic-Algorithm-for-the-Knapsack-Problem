# Genetic-Algorithm-for-the-Knapsack-Problem
This is an assignment that I had to solve in Java in my master's course where it focuses on knowledge of Parallel and Concurrent Programming

Evaluated Skills
This assignment is designed to evaluate your understanding of the threading model, the thread lifecycle and the synchronization primitives available in Java, as well as your programming skills to apply those concepts in a small-scale project. Additionally, you communication skill is also evaluated in the report, together with your data analysis ability.

Objectives
The Knasack problem is a common problem in computer science and, although it is usually presented as filling a backpack with the most valuable items within a given weight allowance, it occurs frequently in the real world, from manufacturing to financial investiments.

However, due to the high computational cost of computing all possible combinations of items (from an available pool), sometimes we resort to using heuristic methods. In this assignment, you will be using a Genetic Algorithm, where we start with a population of individuals (each individual is represented by a binary array, where each position indicates whether the corresponding item from the pool is included in that solution or not.

We start with a population of random solutions (i.e., combinations of items), we evaluate how good the solution is (its fitness) and we start the reproduction step.

For each position in the new population, we select two parent solutions: we randomly select K individuals from the old population, and from those K, we keep the best. This process is repeated for each parent. Then we perform a crossover: we define a random midpoint in the array, and we keep the first half from parent1 and the second half from parent 2. As such, each new individual will be a mix of the two parents.

Finally, with a given probability, we change a random position of the individual to create novelty in the combinations.

We repeat this process throughout a given number of generations, hoping the final result is better.

Task
Your task is to implement different alternatives to parallelize this small program (see the code attached). The goal is to improve performance, while retaining correctness. You should explore the alternatives mentioned in class.

Note that there is a single print per generation that should be kept in the parallel version.
