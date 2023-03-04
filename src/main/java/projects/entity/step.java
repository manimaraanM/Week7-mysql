package projects.entity;

public class step {
	private int step_id;
	private int project_id;
	private String step_text;
	private int step_order;
	
	
	@Override
	public String toString() {
		return "step [step_id=" + step_id + ", project_id=" + project_id + ", step_text=" + step_text + ", step_order="
				+ step_order + "]";
	}
	
	
	public int getStep_id() {
		return step_id;
	}
	public void setStep_id(int step_id) {
		this.step_id = step_id;
	}
	public int getProject_id() {
		return project_id;
	}
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	public String getStep_text() {
		return step_text;
	}
	public void setStep_text(String step_text) {
		this.step_text = step_text;
	}
	public int getStep_order() {
		return step_order;
	}
	public void setStep_order(int step_order) {
		this.step_order = step_order;
	}

}

