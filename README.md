# jgenesis

JGenesis is an evolutionary algorithm library implementing a Microbial GA [1] for optimisation. The problem is formulated in a custom Phenotype object which translates a set of 'genes' (numerical parameters) which specify a solution to the problem. The Phenotype then calculates the performance of the solution, or the **fitness**. The GA then optimises the genes based on their relative performance.

[1] http://users.sussex.ac.uk/~inmanh/Microbial.pdf
