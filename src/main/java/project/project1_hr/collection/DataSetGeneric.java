package project.project1_hr.collection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DataSetGeneric<E extends Measurable> extends ArrayList<E> {
	
	private static final long serialVersionUID = 1L;
	// this class should not have an instance variable.  Since it extends ArrayList all the 
	// data can be held in the superclass
	public DataSetGeneric() {}


	@Override
	public boolean add(E objToAdd) {
		return super.add(objToAdd);
	}


	public int size() {
		return super.size();
	}


	public E getMin() {
		if (super.isEmpty()) {
			return null;
		}
		E obj = super.get(0);
		for (int i = 1; i < super.size(); i++) {
			if (obj.getMeasure() > super.get(i).getMeasure()) {
				obj = super.get(i);
			}
		}
		return obj;
	}


	public E getMax() {
		if (super.isEmpty()){
			return null;
		}
		E obj = super.get(0);
		for (int i = 1; i < super.size(); i++) {
			if (obj.getMeasure() < super.get(i).getMeasure()) {
				obj = super.get(i);
			}
		}
		return obj;
	}

	public List<E> sortBy(Comparator<? super E> comparator) {
		this.sort(comparator);
		return this;
	}

	public double getObjMeasure() {
		double total = 0;
		for (int i = 1; i < super.size(); i++) {
			total += super.get(i).getMeasure();
		}
		return total;
	}


	@Override
	public String toString() {
		return super.toString();
	}



}
