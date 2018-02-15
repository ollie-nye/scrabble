package validation;

import java.util.HashMap;

/**
 * 
 * @author Ollie Nye
 * @version 1.4
 */
/* 
 * REVISIONS
 * 1.0 - Create class and constructor
 * 1.1 - Create addChild, getChildren, addContent, and isWord methods
 * 1.2 - Change from ArrayList to HashMap to speed up operation
 * 1.3 - Incorporate isWord into the constructor
 * 1.4 - Add getChild method to speed up operation and increase abstraction 
 */
public class Vertex {
	
	/**
	 * HashMap containing every vertex that this node is a parent of, indexed by the content of the child
	 */
	private HashMap<String, Vertex> children;
	
	/**
	 * String to store inside this node
	 */
	private String content;
	
	/**
	 * If this node is the end of a particular word, this is true
	 */
	private boolean isWord;
	
	/**
	 * Constructor taking the content and boolean true if this node is the end of a word
	 * @param content			Content to contain in this node
	 * @param isWord			True if this is the end of a word
	 */
	public Vertex(String content, boolean isWord) {
		this.children = new HashMap<>();
		this.content = content;
		this.isWord = isWord;
	}
	
	/**
	 * Add a child to this node's children collection, setting the key to be the prospective child's content
	 * @param vertex			Node to add
	 */
	public void addChild(Vertex vertex) {
		children.put(vertex.getContent(), vertex);
	}
	
	/**
	 * Returns the collection of children of this node
	 * @return					Collection of all children of this node				
	 */
	public HashMap<String, Vertex> getChildren() {
		return this.children;
	}
	
	/**
	 * Gets a particular child in O(1) time
	 * @param key				Content of child to get
	 * @return					Child, null if not exists
	 */
	public Vertex getChild(String key) {
		return this.children.get(key);
	}
	
	/**
	 * Gets the content of this node
	 * @return					String containing content of this node
	 */
	public String getContent() {
		return this.content;
	}
	
	/**
	 * Getter for isWord field
	 * @return					Boolean true if this node is the end of a word
	 */
	public boolean isWord() {
		return this.isWord;
	}
}
