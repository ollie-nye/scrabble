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
	
	private HashMap<String, Vertex> children;
	
	private String content;
	
	private boolean isWord;
	
	public Vertex(String content, boolean isWord) {
		this.children = new HashMap<>();
		this.content = content;
		this.isWord = isWord;
	}
	
	public void addChild(Vertex vertex) {
		children.put(vertex.getContent(), vertex);
	}
	
	public HashMap<String, Vertex> getChildren() {
		return this.children;
	}
	
	public Vertex getChild(String key) {
		return this.children.get(key);
	}
	
	public String getContent() {
		return this.content;
	}
	
	public boolean isWord() {
		return this.isWord;
	}
}
