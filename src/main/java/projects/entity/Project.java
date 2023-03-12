package projects.entity;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;


public class Project {
	private int project_id;
	private String project_name;
	private BigDecimal estimated_hours;
	private BigDecimal actual_hours;
	private int difficulty;
	private String notes;
	private List<material> material = new LinkedList<material>(); 
	
	


	private List<step> step = new LinkedList<step>(); 
	private List<category> category = new LinkedList<category>(); 
	
	@Override
	public String toString() {
		String sProject ="\n project_id=" + project_id + "\n project_name=" + project_name + "\n estimated_hours="
				+ estimated_hours + "\n actual_hours =" +actual_hours +"\n difficulty=" + difficulty + "\n notes=" + notes;
		
		sProject+="\n Materials:";
		for(material m:material)
		{
			sProject+="\n";
			sProject+=m;
		}
		
		sProject+="\n Steps:";
		for(step s:step)
		{
			sProject+="\n";
			sProject+=s;
		}
		
		sProject+="\n Categories:";
		for(category c:category)
		{
			sProject+="\n";
			sProject+=c;
		}
		
		return sProject;
	}
	
	public List<step> getStep() {
		return step;
	}


	public List<category> getCategory() {
		return category;
	}


	public List<material> getMaterial() {
		return material;
	}
	
	public int getProject_id() {
		return project_id;
	}
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public BigDecimal getEstimated_hours() {
		return estimated_hours;
	}
	public void setEstimated_hours(BigDecimal estimated_hours) {
		this.estimated_hours = estimated_hours;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}


	public BigDecimal getActual_hours() {
		return actual_hours;
	}


	public void setActual_hours(BigDecimal actual_hours) {
		this.actual_hours = actual_hours;
	}

}
