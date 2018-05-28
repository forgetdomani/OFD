package nvision.ofd.testData;

public class Department implements Comparable<Department> {

	private String departmentCode;
	private String departmentName;
	private Department parentDept;

	public Department() {
	}

	public Department(String departmentCode, String departmentName) {
		this.departmentCode = departmentCode;
		this.departmentName = departmentName;
	}

	public Department(String departmentCode, String departmentName, Department parentDeptName) {
		this.departmentCode = departmentCode;
		this.departmentName = departmentName;
		this.parentDept = parentDeptName;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Department getParentDept() {
		return parentDept;
	}

	public void setParentDept(Department parentDeptName) {
		this.parentDept = parentDeptName;
	}

	public int hashCode() {
		return (departmentName + departmentCode).hashCode();
	}

	public boolean equals(Department d) {
		if (this.departmentName.equals(d.getDepartmentName())) {
			if (this.departmentCode.equals(d.getDepartmentCode())) {
				if (this.parentDept == null && d.getParentDept() == null) {
					return true;
				}
				if (this.parentDept.equals(d.getParentDept()))
					return true;
			}
		}
		return false;
	}

	public int compareTo(Department d) {
		return departmentName.compareTo(d.getDepartmentName());
	}
}
