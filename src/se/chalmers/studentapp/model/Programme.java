package se.chalmers.studentapp.model;

import java.util.ArrayList;
import java.util.List;

public class Programme {

	private String name;
	private List<Branch> branches;
	
	protected Programme(String name){
		this.name = name;
		branches = new ArrayList<Branch>();
	}
	
	protected void addBranch(Branch branch){
		if(branch != null){
			branches.add(branch);
		}
	}
	
	public String getName(){
		return name;
	}
	
	public List<Branch> getBranches(){
		ArrayList<Branch> branches = new ArrayList<Branch>(this.branches.size());
		branches.addAll(this.branches);
		return branches;
	}
	
	@Override
	public String toString(){
		return getName();
	}
	
}
