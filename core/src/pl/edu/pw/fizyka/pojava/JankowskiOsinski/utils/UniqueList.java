package pl.edu.pw.fizyka.pojava.JankowskiOsinski.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

/**
 * 
 * @author robjan
 * @category Not duplicated list
 * @param <T>
 */
public class UniqueList<T> {

	private HashSet<T> masterSet = new HashSet<>();
	private ArrayList<T> innerList;

	public UniqueList() {
		this.innerList = new ArrayList<>();
	}

	public UniqueList(int size) {
		this.innerList = new ArrayList<>(size);
	}

	public boolean add(T thing) {
		if (!masterSet.contains(thing)) {
			this.masterSet.add(thing);
			this.innerList.add(thing);
			return true;
		}
		return false;
	}

	public List<T> getList() {
		return innerList;
	}

	public T get(int index) {
		if (index >= 0 && index <= innerList.size()) {
			return innerList.get(index);
		}
		return null;
	}

	public int size() {
		return masterSet.size();
	}

	public boolean remove(int index) {
		if (index < innerList.size() && index >= 0) {
			T tmp = innerList.get(index);
			this.innerList.remove(index);
			this.masterSet.remove(tmp);
			return true;
		}
		return false;
	}

	public Stream<T> stream() {
		return innerList.stream();
	}

	public void show() {
		innerList.forEach(System.out::println);
	}
}
