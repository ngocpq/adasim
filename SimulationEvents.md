**NOTE:** This documentation refers to the latest development version. Not all events may be available in released binaries.

Events appear in the log output of Adasim as st words in all capitals. For example:

```
106 [main] INFO traffic.graph.GraphNode  - ENTER: Vehicle: 0 At: 0
```

logs the events that vehicle 0 enters node 0.

# Events #

| **Event** | **Source** | **Description** |
|:----------|:-----------|:----------------|
| SIMULATION | TrafficSimulator | Logs a new simulation cycle. The suffix contains the number of the cycle. |
| ENTER | GraphNode | Event when a vehicle enters a node. Vehicle ID and Node ID follow. |
| MOVE | Vehicle | Event when a car _attempts_ to move to new node. This can fail, in which case the car will stop immediately afterwards. |
| INVALID | GraphNode | Event when an invalid move attempt is detected. |
| STOP | GraphNode | Event when a vehicle stops. This happens either when the vehicle reaches its target, or when it attempts an illegal move. |
| OPEN | RoadClosureAgent | Event when the agent opens a road to traffic. |
| CLOSE | RoadClosureAgent | Event when the agent closes a road to traffic. |