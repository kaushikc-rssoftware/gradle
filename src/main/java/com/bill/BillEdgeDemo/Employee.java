package com.bill.BillEdgeDemo;
import java.util.List;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/employee")
public class Employee {
    static List<EmployeeInfo> employeeInfoList=new  ArrayList<>();

    private EmployeeInfo getSearchedEmployee(String empId)
    {
        EmployeeInfo foundEmp=null;
        for (EmployeeInfo emp : employeeInfoList) {

            if (emp.getId().equals(empId)) {
                foundEmp = emp;
                break;
            }
        }
        return foundEmp;
    }
    @GetMapping("/{EmpId}")
    public ResponseEntity<EmployeeInfo> GetEmployee(@PathVariable(required = false)String EmpId)
    {
        EmployeeInfo foundEmp=getSearchedEmployee(EmpId);

        if(foundEmp!=null)
            return new ResponseEntity<>(foundEmp, HttpStatus.FOUND);
       return new ResponseEntity<>(new EmployeeInfo(), HttpStatus.NOT_FOUND);

    }
    @GetMapping("/")
    public ResponseEntity<List<EmployeeInfo>> GetEmployee()
    {
        return new ResponseEntity<>(employeeInfoList, HttpStatus.OK);
    }
    @PostMapping("/")
    public ResponseEntity<EmployeeInfo> AddEmployee(@RequestBody EmployeeInfo emp){
        EmployeeInfo foundEmp=getSearchedEmployee(emp.getId());
        if(foundEmp==null) {
            employeeInfoList.add(emp);
            return new ResponseEntity<>(emp, HttpStatus.CREATED);
        }
        else
        {
            return new ResponseEntity<>(emp, HttpStatus.CONFLICT);
        }
    }
    @PutMapping("/")
    public ResponseEntity<EmployeeInfo> UpdateEmployee(@RequestBody EmployeeInfo newEmp){

        EmployeeInfo foundEmp=getSearchedEmployee(newEmp.getId());
        int index=0;

        if(foundEmp==null)
        return new ResponseEntity<>(new EmployeeInfo(), HttpStatus.NOT_FOUND);
        else {
            index=employeeInfoList.indexOf(foundEmp);
            employeeInfoList.remove(foundEmp);
            employeeInfoList.add(index, newEmp);
        }
        return new ResponseEntity<>(newEmp, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{EmpId}")
    public ResponseEntity<EmployeeInfo> DeleteEmployee(@PathVariable(required = false)String EmpId)
    {
        EmployeeInfo foundEmp=getSearchedEmployee(EmpId);

        if(foundEmp!=null)
        {
            employeeInfoList.remove(foundEmp);
            return new ResponseEntity<>(foundEmp, HttpStatus.FOUND);
        }

        return new ResponseEntity<>(new EmployeeInfo(), HttpStatus.NOT_FOUND);
    }
}
