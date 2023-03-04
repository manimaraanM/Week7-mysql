package projects.entity;

import java.math.BigDecimal;

public class material {
	private int material_id;
	private int project_id;
	private String material_name;
	private int num_required;
	private BigDecimal cost;
	
	
	@Override
	public String toString() {
		return "material [material_id=" + material_id + ", project_id=" + project_id + ", material_name="
				+ material_name + ", num_required=" + num_required + ", cost=" + cost + "]";
	}
	
	public int getMaterial_id() {
		return material_id;
	}
	public void setMaterial_id(int material_id) {
		this.material_id = material_id;
	}
	public int getProject_id() {
		return project_id;
	}
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	public String getMaterial_name() {
		return material_name;
	}
	public void setMaterial_name(String material_name) {
		this.material_name = material_name;
	}
	public int getNum_required() {
		return num_required;
	}
	public void setNum_required(int num_required) {
		this.num_required = num_required;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

}


