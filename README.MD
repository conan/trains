Trains
======

Notes
-----

There are a couple of things worth mentioning:

* A method that calculates distances but produces Strings is annoying; better to return nulls or throw exceptions so it can be used as a component.  Java, Ruby and C# all have ways of doing this better.  I've made a wrapper method to meet the spec, and a helper method that returns nulls to indicate invalid routes for this reason, it would be easy to throw an exception instead.

* It's a bit weird that "The length of the shortest route (in terms of distance to travel)" from a town to itself isn't 0 - nobody would get a train to the same station they're at.  It also makes the algorithm more complicated, so this would be a good requirement to question (I assume I'm not allowed to do that here!).

Graph Representation
--------------------

I've used an adjacency matrix to represent the graphs, but Dijkstra's algorithm and the Depth-First Search algorithm would be (slightly) cleaner with an adjacency list.  I went with the matrix because I thought it made it easier to express the test data clearly, and it doesn't make much difference.  There's a memory overhead to it, but we're talking about train stations, and there are tens or maybe hundreds of thousands of those in the world, no more.

Algorithms
----------

Dijkstra's algorithm is a nice choice for single-source shortest-path calculations, and it can produce much more valuable output if needed; the implementation here is short-circuited to return when it's found the one route it's asked for, but it could easily store the predecessor tree which would give you all the shortest routes from the source; in a real application this could be used alongside a caching strategy.

I read that Java's built-in PriorityQueue could be replaced with a Fibonnaci heap for a speedup because it's more efficient at fiddling the keys in the priority queue, but for a rail network it's always going to be a pretty sparse graph for financial reasons, so I didn't consider it worth implementing one.

Breadth-First Search would seem like a more obvious choice for the bounded path counts because it naturally bounds its own path lengths as it progresses, but it's pretty similar to Dijkstra in the way it queues stuff up and what can I say, I love a recursion - it gives a really neat expression.  There's essentially no performance difference, especially with the fairly low upper bounds on how many train stations people in the world actually need.

Architecture
------------

A bunch of static methods in a single class isn't ideal.  It's reasonable to think that there might be a need to change algorithms at some point, and a more modular approach would make sense.  The three different algorithms could be split out into separate classes or something, but given that this doesn't seem to be a component yet I didn't want to make assumptions about architecture, and it's nice and easy to read this way.