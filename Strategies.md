#Strategies distributed with Adasim.

# Car Route Finding Strategies #

All car route finding strategies must implement the interface `traffic.strategy.CarStrategy`. As a convenience, there is also the abstract class `traffic.strategy.AbstractCarStrategy` that encapsulates some standard information we expect every strategy to need. All strategies distributed with Adasim inherit from this abstract class.

|**Strategy**|**Description**|
|:-----------|:--------------|
| LookaheadShortestPathCarStrategy | The base class implementing a modified version of Dijkstras algorithm. This class comes with two constructors. The zero argument constructor (used when this class is instantiated from the a configuration file) creates an instance of the strategy that will compute shortest paths based on node weights alone and will never recompute a path. The second constructor takes two arguments: _lookahead_ and _recompute_. These two parameters determine how far away from its current node a car can perceive traffic, and how often to recompute the path, respectively.|
| AlwaysRecomputeCarStrategy | Instantiates LookaheadShortestPathCarStrategy with _lookahead=5_ and _recompute=1_, leading the strategy to recompute its path at every step. |
| AdaptiveCarStrategy| Instantiates LookaheadShortestPathCarStrategy with _lookahead=5_ and _recompute=5_. |

# Traffic Delay Functions #

Traffic Delay Functions modify how nodes handle traffic.

(**Note**: Currently, the generator does not allow the configuration of traffic delay functions. It will always use LinearSpeedStrategy.)
(**Note: This interface is likely to change in the future!)**

|**Function**|**Description**|
|:-----------|:--------------|
|LinearSpeedStrategy| Every car on a node above the _capacity_ of the node will increase the delay by 1. |