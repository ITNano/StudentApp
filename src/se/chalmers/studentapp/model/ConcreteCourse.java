package se.chalmers.studentapp.model;

public class ConcreteCourse implements Comparable<ConcreteCourse>{

	private Course course;
	private State state;
	
	protected ConcreteCourse(Course course, State state){
		this.course = course;
		this.state = state;
	}
	
	public Course getCourse(){
		return course;
	}
	
	public State getState(){
		return state;
	}
	
	public void setState(State state){
		//FIXME how to solve?
		this.state = state;
	}

	@Override
	public int compareTo(ConcreteCourse course) {
		if(course == null){
			return 1;
		}else{
			return course.getState().getNum()-state.getNum();
		}
	}

	
	public enum State{
		COMPLETED(0),
		REGISTERED(1),
		QUEUED(2),
		MANDATORY(3),
		NONE(4);
		
		private int num;
		private State(int num){
			this.num = num;
		}
		public int getNum(){
			return num;
		}
	}	
}
