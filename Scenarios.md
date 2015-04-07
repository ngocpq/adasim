# Scenarios #

This page contains a list of scenarios we expect our simulator to handle.  There will help us identify the relevant objects and uncertainties to consider.

### Scenario 1: ###

  * 30 cars
  * 55 intersections, laid out in a triangle similar to a tree, with the top row having one intersection and the last row having 10. Each intersection is connected to all surrounding intersections, up to 6
  * 10 randomly distributed accidents, 5 on roads, 5 at intersections
  * Posted speed limit of 60 mph on all roads, real speed limit decreases by 5 mph for every additional car on the edge
  * Single lane roads, no passing

### Scenario 2: ###

  * 1000 cars
  * 500 intersections, laid in a 20 x 25 grid
  * Multiple lanes on each edge so passing is allowed
  * Whenever more than 5 cars are on any particular edge, an accident occurs
  * Speed limit is 50mph, however there is a minimum speed of 40mph

### Scenario 3: ###

  * 500 cars
  * 400 intersections, 20 x 20 grid
  * All streets except for the outer edge streets are one way
  * One way streets are one lane so no passing, outer edges have multiple lanes so passing is allowed
  * Accidents can occur at any intersection with 3 or more cars with 1/500 probability
  * Speed limit on one way streets is 40mph, with the real limit decreasing by some amount for additional cars
  * Outer edges have a limit of 70mph, there are enough lanes to handle all traffic

### Scenario 4: ###

  * 500-1000 cars
  * a big grid of roads (say 25x25 or even larger) and an overlay of express routes with a few turnoffs
  * normal roads have low speed limits (say 30), express routes have high speed limits and higher traffic capacity
  * no passing on normal roads
  * high traffic penalty on normal roads is greater than on express routes

  * add in a bunch of accidents with a probability depending on traffic density)

### Scenario 5: ###

  * 2 cars
  * 16 intersections, 4x4 grid
  * Both start in the upper left corner, have a destination of the bottom right corner
  * All roads have posted speed limit of 40 mph, but 2 cars on an edge lowers speed limit to 30 mph
  * All roads are 2 way, but no passing
  * Car 2 starts moving right after Car 1
  * No accidents

### Scenario 6: ###

  * 10 cars
  * 100 intersections, 10x10 grid
  * Each car starts at a different spot on the top row, and their destination is the mirror location on the bottom row
  * Passing allowed on horizontal streets, not on vertical streets
  * Any intersection with 2 or more cars has a 1/500 chance of an accident occurring
  * Constant speed limit of 50 mph on horizontal streets, starts at 50 mph and goes down by 5 mph with each additional car on vertical streets

### Scenario 7: ###

  * 50 cars, half are trucks that are double the length of normal cars
  * 100 intersections, 10x10 grid
  * Random start and end points for each car
  * All roads have passing lanes, but trucks cannot be passed or pass other cars
  * Speed limit of 60mph, decreases by some function with additional load, trucks decrease speed faster than cars
  * No accidents