

> # Adasim user manual #

This document describes how to download and use Adasim, how to implement your own solutions to the automated traffic routing problem, where to find detailed API documentation, and links to examples.

## Using Adasim ##

### Prerequisites ###
Adasim expects to run on a JRE that has a Schema capable XML parser configured for its JAXP interface. Sun/Oracle JDKs from JDK 6 onwards come with Apache Xerces and thus fulfill this requirement.

The release JARs package all other required libraries, so no CLASSPATH setup is needed.

You also need a configuration file describing the simulation you want to run. Either create this file manually (the format is defined here), or use the random configuration Generator.

### Installation ###
To "install", simply download the latest release from the [download section](http://code.google.com/p/adasim/downloads/list).

### Running Adasim ###
To run Adasim on the commandline type

```
java -jar adasim-nnn.jar -I config.xml
```

Replace `nnn` with the release number you downloaded and `config.xml` with the name of the configuration file you want to process.

### Running the configuration generator ###

To run the configuration generator, call

```
java -cp adasim-xxx.jar adasim.generator.Generator <options>
```

See the [full command line options for the generator](Generator.md) documentation.

### Running multiple random simulations at once ###

To run multiple randomly generated simulations at once, you can use the scripts provided in the directory resources/scripts in the source download.

See the [scripts usage documentation](Scripts.md).

## Extending Adasim ##

### API documentation ###

The end-user API of Adasim is documented in the source. To obtain compiled JavaDoc, run `ant javadoc` on the source.

See the [configuration file format documentation](XML_config.md).

### How to implement your own routing algorithm ###

Routing algorithms must implement the interface `adasim.algorithm.routing.RoutingAlgorithm` (in older versions of Adasim, this interface was called `traffic.strategy.VehicleStrategy`. For convenience, we provide an adapter class `adasim.algorithm.routing.AbstractRoutingAlgorithm` that implements most trivial methods and has state that is most likely useful to all implementers of a routing algorithm.

The protocol of routing algorithms requires that `getPath()` returns a path between the two argument nodes, if such a path exists. But more importantly, it is required that `getNextNode()` will always return the next node on the path. That means that the routing algorithm has to track progress!!!

For example,

```
List<RoadSegment> path = algo.getPath(start, finish);
//path has node IDS [3,4,5]
int i = algo.getNextNode(); //i == 3
i = algo.getNextNode(); //i==4
i = algo.getNextNode(); //i==5
```

Loading routing algorithms via configuration files will assure that all the state setting methods will be called correctly, so before the algorithm is called for the first time, the state will be correct (assuming that getters/setters are implmented correctly).

### How to implement your own uncertainty filters ###

Uncertainty filters are easy to implement. They must extend the `adasim.filter.AdasimFilter` interface. As a convenience, we provide the `adasim.filter.IdentityFilter`, that implements identity functions for all filter methods, so users only have to override what they need.

The call protocol is even simpler. Vehicles will call the configured uncertainty filter every time a client accesses one of their uncertain properties. If the filter is configured through the XML configuration file, developers only have to ensure that a default constructor is available.

We do encourage implementing the singleton pattern with a `getInstance()` method for stateless filters to save memory.

### How to implement your own agent ###

Agents are active elements in the simulation that can perform actions in every simulation cycle. For example, the randomized generation of road closing events such as traffic accidents is implemented as an agent that can be added to simulations where such behavior is desired.

To implement your own agent, implement the interface `adasim.agent.AdasimAgent` or extend `adasim.agent.AbstractAdasimAgent`. The abstract agent implementation has fields for the required state and implements getters/setters for this state. It also implements a default `isFinished()` method that always returns `true`.

All the behavior your agent should have must be in the method `takeSimulationStep()`. This method will be called once in each simulation cycle.

The protocol for agents requires that the method `isFinished()` eventually returns `true`, but it does not have to do so immediately.
For example, vehicles implement a `isFinished()` method that returns `true` only when the vehicle can no longer move. On the other hand, the RoadClosureAgent has no restriction on when it will finish, and thus always returns `true`.

### How to implement your own traffic delay function ###

Traffic delay functions must implement the interface `adasim.algorithm.delay.TrafficDelayFunction`. The purpose of this function is to modify the standard delay of a road segment based on current traffic volume. The package `adasim.algorithm.delay` contains several example implementations.

## Running Adasim on your own solutions ##

To use your own algorithms and functions in a simulation, create a configuration file uses your implementations. How to configure the different parts of a simulation with custom algorithms is described in the [XML configuration documentation](XML_config.md).