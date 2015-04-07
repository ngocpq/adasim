# Upcoming Design Decisions #

  * The core simulator should only know about agents, independent of what they are.
    * How does the simulator know when it is done simulating? See [issue 56](https://code.google.com/p/adasim/issues/detail?id=56)
    * At the moment, GraphNodes are implicitly agents, but they are handled separately by the simulator. **This is now fixed. The simulator now only knows about agents.**
  * Who tracks/sets vehicle locations? At the moment, the only active agents are GraphNodes. They decide when vehicles get a chance to do something, and they move vehicles around and control vehicle speed. This is not realistic and we might need some other entity to track vehicle locations.
    * Do we want the simulator to prevent vehicles from "teleporting"? **Yes, how is this possible now?**
    * Do we want the simulator generally to enforce that vehicles don't do impossible things (like drive onto a blocked road)? **What is a blocked road?  Yes**
    * How do we manage traffic volume, speed limits, vehicles of different speeds, etc.? Right now, each GraphNode takes care of that. The current implementation enforces speed limits and all vehicles always move with the maximum speed. If we want to model things like cars passing other cars or crashing into them because they went to fast, this is not possible.