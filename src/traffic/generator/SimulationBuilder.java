/*******************************************************************************
 * Copyright (c) 2011 - Jochen Wuttke.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jochen Wuttke (wuttkej@gmail.com) - initial API and implementation
 ********************************************************************************
 *
 * Created: Dec 12, 2011
 */

package traffic.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import traffic.agent.AdasimAgent;
import traffic.model.AdasimMap;
import traffic.model.GraphNode;
import traffic.model.Vehicle;
import traffic.model.ConfigurationException;
import traffic.model.TrafficSimulator;
import traffic.model.internal.SimulationXMLBuilder;
import traffic.model.internal.VehicleManager;
import traffic.strategy.VehicleStrategy;
import traffic.strategy.LinearSpeedStrategy;
import traffic.strategy.SpeedStrategy;

/**
 * This class constructs (randomizes) a TrafficSimulation based
 * on the values passed as arguments through a set of 
 * {@link ConfigurationOptions}
 * 
 * @author Jochen Wuttke - wuttkej@gmail.com
 *
 */
public class SimulationBuilder {

	private Random random;

	/**
	 * Sets up the {@link SimulationBuilder}
	 */
	SimulationBuilder() {
		random = new Random();
	}

	/**
	 * Constructs a {@link SimulationBuilder} with the given random seed.
	 * This is intended for reproducing the exact same results despite
	 * randomization. Either for repeating experiments or testing.
	 * @param seed
	 */
	SimulationBuilder( long seed ) {
		random = new Random( seed );
	}

	
	/**
	 * Build an complete {@link TrafficSimulator}
	 * @param opts the options parsed from the commandline arguments
	 * @return a complete {@link TrafficSimulator}
	 * @throws ConfigurationException
	 * @throws JDOMException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	TrafficSimulator build( ConfigurationOptions opts ) throws ConfigurationException, IOException, JDOMException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		AdasimMap g = buildGraph(opts);
		VehicleManager m = new VehicleManager();
		return new TrafficSimulator( buildGraph(opts), m, buildVehicles(opts, g) );
	}

	/**
	 * @param opts
	 * @return a list of vehicles of the size spec'd on the commandline
	 * @throws ConfigurationException 
	 */
	private List<AdasimAgent> buildVehicles(ConfigurationOptions opts, AdasimMap g) throws ConfigurationException {
		List<AdasimAgent> vehicles = new ArrayList<AdasimAgent>();
		for ( int i = 0; i < opts.getNumVehicles(); i++ ) {
			vehicles.add( buildVehicle( i, opts, g ) );
		}
		return vehicles;
	}

	/**
	 * @param i
	 * @param opts
	 * @return a fully configured vehicle
	 * @throws ConfigurationException 
	 */
	private Vehicle buildVehicle(int i, ConfigurationOptions opts, AdasimMap g) throws ConfigurationException {
		VehicleStrategy cs = randomVehicleStrategy( opts.getStrategies() );
		cs.setGraph(g);
		List<GraphNode> nodes = g.getNodes();
		GraphNode start = randomNode( nodes );
		GraphNode end;
		do {
			end = randomNode(nodes);
		} while ( start.equals(end) );

		return new Vehicle( start, end, cs, i);
	}

	/**
	 * @param nodes
	 * @return the ID of the randomly chosen node
	 */
	private GraphNode randomNode(List<GraphNode> nodes) {
		return nodes.get( random.nextInt( nodes.size() ) );
	}

	/**
	 * @param strategies
	 * @return a random strategy picked uniformly from the list of allowed strategies
	 * @throws ConfigurationException 
	 */
	private VehicleStrategy randomVehicleStrategy(List<String> strategies) throws ConfigurationException {
		String s = strategies.get( random.nextInt( strategies.size() ) );
		try {
			@SuppressWarnings("rawtypes")
			Class c = Class.forName( s );
			return (VehicleStrategy) c.newInstance();
		} catch (Exception e) {
			throw new ConfigurationException(e);
		} 	
	}

	/**
	 * @param opts
	 * @return a fully configured graph
	 * @throws ConfigurationException 
	 * @throws IOException 
	 * @throws JDOMException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	private AdasimMap buildGraph(ConfigurationOptions opts) throws ConfigurationException, JDOMException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		AdasimMap g = new AdasimMap( new HashSet<GraphNode>() );
		if ( opts.getGraphFile() == null ) {
			//we have to generate a graph
			for ( int i = 0; i < opts.getNumNodes(); i++ ) {
				g.addNode( buildNode( opts, i ) );
			}
			for ( GraphNode node : g.getNodes() ) {
				randomizeNeighbors(node, g.getNodes(), opts.getDegreeProb(), opts.getOneWayProbability() );
			}
		} else {
			//we have to read the graph from a file
			g = readGraphFromFile( opts.getGraphFile() );
		}
		return g;
	}

	/**
	 * TODO: Move this into a more generic XML reader
	 * @param graphFile
	 * @throws IOException 
	 * @throws JDOMException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ConfigurationException 
	 */
	private AdasimMap readGraphFromFile(File graphFile) throws JDOMException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, ConfigurationException {
		SAXBuilder sbuilder = new SAXBuilder(false);
		Document doc = sbuilder.build(graphFile);
		return new SimulationXMLBuilder().buildGraph( doc.getRootElement() );
	}

	/**
	 * @param opts
	 * @return a fully configured node
	 * @throws ConfigurationException 
	 */
	private GraphNode buildNode(ConfigurationOptions opts, int id ) throws ConfigurationException {
		SpeedStrategy ss = randomSpeedStrategy( opts );
		int delay = randomDelay( opts );
		GraphNode node = new GraphNode(id, ss, delay, opts.getCapacity() );
		return node;
	}

	/**
	 * @param opts
	 * @return a random delay picked uniformly from the allowed range
	 */
	private int randomDelay(ConfigurationOptions opts) {
		int[] nodeDelay = opts.getNodeDelay();
		return random.nextInt( nodeDelay[1] - nodeDelay[0] + 1 ) + nodeDelay[0];
	}

	private static SpeedStrategy ss = new LinearSpeedStrategy();
	/**
	 * @param opts
	 * @return a randomized speed strategy (this is constant right now)
	 * @throws ConfigurationException 
	 */
	private SpeedStrategy randomSpeedStrategy(ConfigurationOptions opts) throws ConfigurationException {
		return ss;
	}

	private void randomizeNeighbors(GraphNode node, List<GraphNode> nodes, double degreeProb, double oneWayProb ) {
		for ( int i = 0; i < nodes.size(); i++) {
			if ( random.nextDouble() < degreeProb/2/(1+oneWayProb) && !nodes.get(i).equals(node)) {
				GraphNode target = nodes.get(i);
				node.addEdge(target);
				if ( random.nextDouble() > oneWayProb ) {
					target.addEdge( node );
				}
			}
		}
	}
}
