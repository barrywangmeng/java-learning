package cn.barrywangmeng.cache.guava;

import com.google.common.base.MoreObjects;

/**
 * @Description:
 * @Author: wangmeng
 * @Date: 2018/12/9-16:55
 */
public class Employee {
    private String name;
    private String dept;
    private String empId;

    public Employee(String name, String dept, String empId) {
        this.name = name;
        this.dept = dept;
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("Name", this.getName()).add("Department", this.getDept()).add("EmployeeId", this.getEmpId()).toString();
    }
}
