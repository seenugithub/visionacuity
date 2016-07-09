package com.marvel.visionacuity;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class LRUCache<K, V> extends LinkedHashMap<K, V>{
	private static final long serialVersionUID = 7890350519907243838L;
	static int cacheSize = 10;
	
	public LRUCache(){
		super(cacheSize, 0.5f, true);
	}
	public LRUCache(int capacity){
		super(capacity, 0.5f, true);
		cacheSize = capacity;
	}
	
	@Override
	protected boolean removeEldestEntry(Entry<K, V> eldest) {
		return (size()>cacheSize);
	}
}
