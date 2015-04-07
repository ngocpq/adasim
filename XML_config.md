# Introduction #

**Note:** This documentation refers to the latest release version, which may not be compatible with the latest head version in the repository.

Adasim uses XML configuration files to specify simulations. The full definition of the XML file is contained in the XML schema `adasim.xsd`. This file can be found in `resource/xml/` in the source of Adasim.

This document describes and documents the configuration file structure.

An example XML configuration file:
```
<simulation>
	<defaults>
		<filters>
		...
		</filters>
	</defaults>
	<cars default_strategy="traffic.strategy.LookaheadDijkstraCarStrategy">
		<car id="1" start="00" end="02"  />
                ...
	</cars>

	<graph default_strategy="traffic.strategy.LinearSpeedStrategy" default_capacity="1">
		<node id="0" neighbors="6" />
		<node id="1" neighbors="2 3 6" delay="3" />
                ...
	</graph>
        <agents>
                <agent id="42" class="traffic.agents.DemoAgent"/>
        </agents>
</simulation>
```

XML elements and their attributes:

|**Element**|**Attribute**|**Description**|
|:----------|:------------|:--------------|
|`<cars>`|  | The parent node containing all definitions of vehicles participating in the simulation.  **Required**|
|  | default\_strategy | The default route-finding strategy to be used by cars that have no strategy specified or that have a strategy specified that cannot be loaded. Must be the fully qualified class name of a class implementing `adasim.algorithm.routing.RoutingAlgorithm`. **Optional**, defaults to `adasim.algorithm.routing.ShortestPathRoutingAlgorithm`|
|`<car>`|  |Defines one vehicle. Has only leaf attributes, and no non-leaf children. At least on car is **required** for a simulation. |
|  |id| Integer ID of the vehicle. **Required** |
|  |start, end| Integer ID of the vehicle's starting and end nodes. **Required**|
|  | strategy | The route-finding strategy for the vehicle. Must be the fully qualified class name of a class implementing `traffic.strategy.CarStrategy`. **Optional**|
|  | `start_time` | The starting time (cycle) of the car. Must be an integer greater than 0. **Optional**, defaults to 1.|
|`<graph>`|  | Defines the simulation map. **Required**|
|  | default\_strategy | The default capacity function. Must be the fully qualified class name of a class implementing `traffic.strategy.SpeedStrategy`. **Required** |
|  | default\_capacity | The default capacity of the node. Capacity functions are permitted to ignore traffic up to this limit. Must be an integer >=0. **Optional**, defaults to 1. |
|  | uncertainty\_filter | The default uncertainty filter to be assigned to nodes that have no explicit declaration. **Optional** Defaults to `traffic.filter.IdentityFilter`|
|  | privacy\_filter | The default privacy filter to be assigned to nodes that have no explicit declaration. **Optional** Defaults to `traffic.filter.IdentityFilter`|
| `<node>` |  | A node, representing a street segment between two intersections. At least one is **required** for a simulation. |
|  | id | Integer ID of the node. **Required** |
|  | neighbors | A space-separated list of node IDs for nodes that can be reached from this node. (These edges are directed.) **Required**  |
|  | strategy | The capacity function. Must be the fully qualified class name of a class implementing `traffic.strategy.SpeedStrategy`. **Optional** |
|  | delay | The node's weight: the number of simulation cycles required to traverse this node when only a single vehicle is present. **Optional**, if omitted, the simulation uses the default value of 1. |
|  | capacity | The traffic capacity of the node. **Optional**. Defaults to `default_capacity` of the graph.|
|  | uncertainty\_filter | The uncertainty filter to be applied to all properties of nodes. **Optional** Defaults to the uncertainty\_filter of the `graph`|
|  | privacy\_filter | The privacy filter to be applied to all properties of nodes. **Optional** Defaults to the privacy\_filter of the `graph`|
| `<agents>` |  | Parent node containing all agent definitions. **Optional** |
| `<agent>`|  | An agent declaration. **Required** if `<agents>` are present. |
|  | id | An integer value identifying the agent. **Required** |
|  | class | The fully qualified classname of the class implementing `traffic.model.AdasimAgent`. **Required** |
|  | parameters | A string containing parameters that will be passed to the agent. **Optional**. |
| `<defaults>` |  | Global default settings. **Optional**. |
| `<filters>` |  | Container for uncertainty and privacy filter declarations. Filter configuration is explained below. **Optional**|
| `<filter>` |  | A filter declaration. **Optional**, at least one must be present if `<filters>` is used. |
|  | `type` | The type of filter. Must be one of "uncertainty" or "privacy". **Required**|
|  | `filter` | Fully qualified classname of the filter. The filter must implement `adasim.filter.AdasimFilter`. **Required** |
|  | `agent` | The fully qualified classname of the agents that this declaration applies to. **Optional**. Defaults to `java.lang.Object` in the `<defaults>` section, `adasim.model.RoadSegment` in the `<graph>` section, and to `adasim.model.Vehicle` in the `<cars>` section. |
|  | `criterion` | The fully qualified classname of the caller type that a privacy filter applies to. **Required for privacy filters**. |

# Configuration Correctness Checks #

The configuration parser checks the following correctness conditions when loading a configuration file. If correctness conditions are violated, the offending attribute might be ignored or parsing might halt, preventing the execution of the a simulation, depending on the severity of the violation.
|**Condition**|**Description**|**Consequence**|
|:------------|:--------------|:--------------|
| `<cars>` element must exist. | If there are no vehicles in the simulation, the simulation will not run. | No simulation execution. |
| At least one `<car>` element must exist. | If there are no vehicles in the simulation, the simulation will not run. | No simulation execution. |
| `<graph>` element must exist. | If there is no graph, the simulation will not run. | No simulation execution. |
| At least one `<node>` elements must exist. | If there is no graph, the simulation will not run. | No simulation execution. |
| For every `<car>` element, the 'start' and 'end' attributes must be valid node IDs. | If the vehicle does not define valid starting and ending points, that vehicle is ignored. | The simulation runs without this vehicle. |
| Every `<node>` element must have at least one valid neighbor. | The simulation does not allow, and ignores, single, detached street segments.   | The simulation runs without this node. |

# Filter Configuration #

Configuring uncertainty and privacy filters can be rather complex and tedious. We provide a number of ways to define defaults, and individual filters.

## Adasim Default Filters ##
If you do not declare any filters anywhere in your configuration, all filters will automatically configured to be `adasim.filter.IdentityFilter`s, i.e. no filtering happens.

The default privacy filter criterion is `java.lang.Object`. While you can remove configured filters programmatically, removing the default filter mapped there **will break the simulation**. Updating this default to a valid filter is fine and encouraged!

Any further declaration you make overrides some of these defaults.

## Explicit Filter Declarations ##

You can declare filters at different levels of the simulation, and the effect of these declarations varies. You can declare filters
  1. Globally, inside the `<defaults>` tag. These filter declarations apply to all agents in the entire simulation.
  1. As a child declaration of `<graph>` or `<agents>`. These declarations apply to all nodes and agents respectively.
  1. As a child of individual nodes or agents. These declarations apply to that node/agent only.

In general, the most fine-grainded declaration will always override the higher level defaults.

### Basic Example ###

A simple filter declaration for an agent looks like this.

```
<agent id="47" class="some.Agent">
   <filters>
      <filter type="uncertainty" filter="my.uncertainty.Filter"/>
      <filter type="privacy" filter="my.privacy.Filter" criterion="calling.agents.Class"/>
      <filter type="privacy" filter="my.default.Filter" criterion="java.lang.Object"/>
   </filters>
</agent>
```

This filter declaration applies only to the Agent 47. It sets an uncertainty filter, and specifies two privacy filters. One applies to caller of type `calling.agents.Class`, and the other filter, assigned with the criterion `java.lang.Object` applies to all other caller types.

While it is legal to use the `agent` attribute here, it will always be ignored.

### Example with Hierarchy ###

```
	<agents>
		<filters>
			<filter type="uncertainty" agent="java.lang.Object" filter="adasim.model.internal.FakeFilter4"/>
			<filter type="privacy" agent="adasim.model.RoadSegment" criterion="adasim.model.internal.FakeAgent" filter="adasim.model.internal.FakeFilter3"/>
			<filter type="privacy" agent="java.lang.Object" criterion="java.lang.Object" filter="adasim.filter.FakeFilter"/>
		</filters>

		<agent id="101" class="adasim.model.internal.FakeAgent"/>
		<agent id="102" class="adasim.model.internal.FakeAgent">
			<filters>
				<filter type="uncertainty" agent="java.lang.Object" filter="adasim.model.internal.FakeFilter3"/>
				<filter type="privacy" agent="adasim.model.RoadSegment" criterion="adasim.model.internal.FakeAgent" filter="adasim.model.internal.FakeFilter4"/>
				<filter type="privacy" agent="java.lang.Object" criterion="java.lang.Object" filter="adasim.filter.IdentityFilter"/>
			</filters>
		</agent>	
	</agents>
```

This example uses two `<filters>` elements. One to declare default filters for all agents, and one specific to a single agent. here, Agent 101 has no explicit declaration, and thus will be configured with the defaults for all agents, which Agent 102 will be configured with only the specific filters (in this example, the detailed declaration overrides all defaults).