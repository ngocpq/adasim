# How to use the random generator #

The random generator creates almost-random simulation configurations based on parameters passed to it.
Currently it supports the following parameters:

|**Option**|Description|
|:---------|:----------|
|-N | --num-nodes 

&lt;n&gt;

 | number of nodes to generate (integer)|
|-C | --num-cars 

&lt;c&gt;

| number of cars to generate (integer)|
|-D | --node-degree 

&lt;d&gt;

| avg. out degree of nodes (actual values well be chosen from a uniform distribution, but will be centered around this mean (integer)|
|-o | --output-file 

&lt;file&gt;

| name of the file to write the config to (string)|
|-d | --node-delay 

&lt;min:max&gt;

| range of node delays from which to draw uniformly (integer:integer)|
|-S | --car-strategies 

&lt;list&gt;

| a comma-separated list of fully qualified class names for route-finding strategies. A strategy will be chosen randomly for each car. [List of strategies](Strategies.md) distributed with Adasim. |
| --one-way-prob 

&lt;d&gt;

| Probability for a street to be one way. **Optional**, defaults to 0.01 |
| --node-capacity 

&lt;n&gt;

| The default node capacity to be used for all nodes. **Optional**, defaults to 0 |
| --graph 

&lt;file&gt;

 | Specifies an XML file that contains only the `graph` portion of the configuration. No new graph will be generated when this argument is passed. **Optional**, overrides all other arguments that configure graph generation. |


To run the simulator, call:
```
java adasim.generator.Generator <options>
```