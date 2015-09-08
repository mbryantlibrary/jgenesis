# jgenesis

JGenesis is an evolutionary algorithm library implementing a Microbial GA [1] for optimisation. The problem is formulated in a custom Phenotype object which translates a set of 'genes' (numerical parameters) which specify a solution to the problem. The Phenotype then calculates the performance of the solution, or the **fitness**. The GA then optimises the genes based on their relative performance.

Similarly to [jctrnn](https://github.com/mbryantlibrary/jctrnn/), this library was originally part of my [Artificial Life project](https://github.com/mbryantlibrary/PreMCA). There are some major differences here; firstly I developed a concurrent version which could do calculations in parallel. This was cool to do, and was a nice introduction to the Java concurrency API, but was ultimately pointless as I ended up running several experiments in parallel anyway. 

Secondly, for my [MSc thesis](https://github.com/mbryantlibrary/ImplicitForwardModels) I needed to do [coevolution](http://wiki.ece.cmu.edu/ddl/index.php/Coevolutionary_algorithms). Admittedly I was under pressure and foolishly didn't write tests, but luckily the functionality hadn't changed too much and it seemed to work well enough. 


[1] http://users.sussex.ac.uk/~inmanh/Microbial.pdf
