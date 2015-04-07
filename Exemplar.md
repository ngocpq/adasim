# THIS PAGE IS DEPRECATED!  View and edit only http://seams.self-adapt.org/wiki/ModelProblem:ATRP #



# The automated traffic routing problem #

Careful comparison and evaluation of techniques requires common problems, benchmarks, and exemplars.  The automated traffic routing problem is one such exemplar, targeted specifically at self-adaptive techniques.

[Adasim](http://adasim.googlecode.com) -- an open-source simulator for the automated traffic routing problem -- allows for fast development of solutions to the problem.  This, in turn, facilitates the comparison and evaluation of self-adaptation techniques.

The automated traffic routing problem was first discussed, and formally defined, in `[3]`: "[Trafﬁc Routing for Evaluating Self-Adaptation](http://www.cs.washington.edu/homes/brun/pubs/pubs/Wuttke12seams.pdf)," at the 7th International Symposium on Software Engineering
for Adaptive and Self-Managing Systems in Zürich, Switzerland.

## Description ##

The automated traffic routing problem involves vehicles, traveling on a map.
Each vehicle has a starting and a target point and a starting time.
The goal of each vehicle is to traverse the map, along its streets, from the starting to the target point.
The vehicles compete for resources.
The streets impose speed limits, and as the number of vehicles and congestion on a given street increases, the maximum allowed speed decreases.
Some events, such as traffic accidents or road closures, can cause further slowdowns.

While intuitively, the automated traffic routing problem describes cars, it is relevant to a much richer space of real-world scenarios, including network packet routing, air traffic control, and constraint satisfaction.

Decision making is central to solving the automated traffic routing problem.
The vehicles themselves are a natural set of decision-making agents, but it is also possible for the streets, intersections, and other virtual agents to make decisions.
It is possible for the agents to have a complete and accurate view of the world, though it is more realistic for each agent to only be able to observe a small, local part of the world.
Further, such observations are likely to be noisy and create uncertainty.
These properties and large instance sizes make this problem ideal to be solved with self-adaptation.

Systems that solve the automated traffic routing problem will differ in two important ways:
the answers to problem instances they produce, and the properties of the process they follow to find those answers.  Both differences should be considered in technique comparison.

For formal definitions of the automated traffic routing problem and solution evaluation dimensions, see [[3](http://www.cs.washington.edu/homes/brun/pubs/pubs/Wuttke12seams.pdf)].

## Relevance ##

The automated traffic routing problem is well suited for some self-adaptation techniques for three reasons:
(1) it involves a large number of agents,
(2) each agent has access only to partial information, and
(3) each agent's view may involve significant uncertainty.

The automated traffic routing problem can involve numerous vehicles, streets, and intersections, each with their own goals.
These goals, as well as the sets of vehicles, and even streets and intersections, can change dynamically during runtime.
In the real world, each agent is likely to have only a partial view of the environment, e.g., only local traffic information.
Finally, sensing that environment is likely to be inexact, with real-world noise and delays.

Sophisticated solutions to the automated traffic routing problem should, therefore, implement smart
adaptation strategies to scale well and to deal with partial and noise views of the world.
Versions of the automated traffic routing problem have previously been used to demonstrate self-adaptation `[1,2]`.

Together with the automated traffic routing problem's relevance to self-adaptation, its formal definition `[3]` and open-source simulator [Adasim](http://adasim.googlecode.com) make it ideal for evaluating and comparing self-adaptive techniques.

## Engineering and adaptation challenges ##

The automated traffic routing problem poses both engineering and adaptation challenges.

  * _Scalability_: Solutions should scale in two dimensions: number of vehicles and size of the map.  For instance, centralized solutions, with a single agent collecting all the data and making all the decisions, may work well for small maps, but may fail on large maps with many vehicles.

  * _Robustness_: Solutions should be robust to noise.  Sensors are noisy and vehicles and other agents may fail suddenly and stop reporting data.

  * _Monitoring_: Monitoring the environment is essential to self-adaptation.  Developing solutions involves decisions on how often, and what to monitor.  Finding the right balance between precision and performance can be challenging.

  * _React_: Reacting to environmental changes (e.g., street closure and traffic) is critical for adaptation.  Ignoring such changes likely results in solutions that perform poorly in highly-dynamic environments.

  * _Avoid side effects_: Some self-adapting decisions may have side-effects on other vehicles and on future decisions.  For example, all vehicles on a congested street moving to an adjacent street will not avoid traffic and will result in a poor solution.  Avoiding or planning for such side-effects, and leveraging cooperation, is an important challenge in building a self-adaptive solution.

## Developing and deploying solutions ##

We have ade available an open-source simulator for the automated traffic routing problem, called [Adasim](http://adasim.googlecode.com).  Adasim allows for fast development and deployment of solutions to the automated traffic routing problem.

Adasim has six inputs:
  1. map
  1. vehicle set
  1. measurement uncertainty filters
  1. data privacy policies
  1. agents
  1. sensors

1-4 define an instance of the automated traffic routing problem.
5-6 define the solution.
Each agent controls a subset of the vehicle set and includes the decision-making algorithms.

For more information on each of these inputs, and on how to execute Adasim simulations, see the [Adasim user manual](http://code.google.com/p/adasim/wiki/UserDocumentation) and `[3]`.
The Adasim distribution includes scripts to automate map and simulation generation and result visualization.

## References ##

  1. H. Seebach et al., “Designing self-healing in automotive sys- tems,” in Autonomic and Trusted Computing, 2010, vol. 6407, pp. 47–61.
  1. D. Weyns et al., “The MACODO middleware for context- driven dynamic agent organizations,” TAAS, vol. 5, no. 1, 2010.
  1. J. Wuttke, Y. Brun, A. Gorla, and J. Ramaswamy. "[Trafﬁc Routing for Evaluating Self-Adaptation](http://www.cs.washington.edu/homes/brun/pubs/pubs/Wuttke12seams.pdf)," SEAMS 2012, Zürich, Switzerland.