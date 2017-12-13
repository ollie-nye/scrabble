package validation;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author Ollie Nye
 * @version 1.1
 */
/* 
 * REVISIONS
 * 1.0 - Create class and constructor
 * 1.1 - 
 */
public class Vertex {
	
	private HashMap<String, Vertex> nodes;
	
	private String content;
	
	private boolean isWord;
	
	public Vertex(String content, boolean isWord) {
		this.nodes = new HashMap<>();
		this.content = content;
		this.isWord = isWord;
	}
	
	public void addNode(Vertex vertex) {
		nodes.put(vertex.getContent(), vertex);
	}
	
	public HashMap<String, Vertex> getNodes() {
		return this.nodes;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public boolean isWord() {
		return this.isWord;
	}
}
