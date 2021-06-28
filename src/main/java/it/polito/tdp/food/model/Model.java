package it.polito.tdp.food.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	Graph <Food, DefaultWeightedEdge> grafo;
	Map<Integer, Food> allFood;
	Map<Integer, Food> idMap;
	FoodDao dao;
	
	public String creaGrafo(int porzioni) {
		dao = new FoodDao();
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		allFood = new HashMap<>();
		idMap = new HashMap<>();
		
		for(Food f: dao.listAllFoods())
			allFood.put(f.getFood_code(), f);
		
		//aggiungo i vertici
		List<Food> vertici = dao.getVertici(porzioni, allFood);
		for(Food f: vertici)
			idMap.put(f.getFood_code(), f);
		Graphs.addAllVertices(grafo, vertici);
		
		//aggiungo gli archi
		for(Adiacenza a : dao.getAdiacenze(idMap)) {
			if(!grafo.containsEdge(a.getF1(), a.getF2())) {
				//aggiungo arco nuovo
				double peso = a.getSomma() / a.getConta();
				Graphs.addEdge(grafo, a.getF1(), a.getF2(), peso);
			}else {
				DefaultWeightedEdge e = grafo.getEdge(a.getF1(), a.getF2());
				double peso  = (a.getSomma() / a.getConta()) + grafo.getEdgeWeight(e);
				peso = peso/2;
				grafo.setEdgeWeight(e, peso);
			}
		}
		
		return String.format("Grafo creato con %d vertici e %d archi", grafo.vertexSet().size(), grafo.edgeSet().size());
	}
	
	

}
