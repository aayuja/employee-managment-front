package com.demo.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.demo.spring.entity.Employee;
import com.demo.spring.entity.Leave;
import com.demo.spring.entity.Payslip;
import com.demo.spring.entity.Salary;
import com.demo.spring.entity.Timesheet;

@Controller
@RequestMapping("/emp")
public class ViewController {
	@Autowired
	private RestTemplate rt;
	
	long a=1;
	
	@GetMapping(path = "/home")
	public String getHomePage() {
		return "home";
	}
	
	@GetMapping(path = "/homeEmp")
	public String getEmpHomePage() {
		return "empHome";
	}
	
	@GetMapping(path = "/homeAdmin")
	public String getAdminHomePage() {
		return "adminHome";
	}


//----------------------------Admin view emp list-------------------------------	
	@GetMapping("/viewEmp")
	public ModelAndView viewEmp(ModelMap map) {
		ResponseEntity<List<Employee>> empData = rt.exchange("http://employee-microservice/empl/emp", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Employee>>() {
				});;
				ModelAndView mv= new ModelAndView();
				mv.addObject("empData", empData.getBody());
				mv.setViewName("viewEmp");
				return mv;
	}
	//-----------------------Admin view leave list--------------------------
	@GetMapping("/viewLeave")
	public ModelAndView viewLeave(ModelMap map) {
		ResponseEntity<List<Leave>> empData = rt.exchange("http://employee-microservice/leav/emp/leave/list", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Leave>>() {
				});;
				ModelAndView mv= new ModelAndView();
				mv.addObject("leave", empData.getBody());
				mv.setViewName("viewLeave");
				return mv;
	}
	
	
	
  //------------------------Employee view leaves -----------------------
	@GetMapping("/findLeave/{id}")
	public ModelAndView findLeave(ModelMap map,@PathVariable long id) {
		ResponseEntity<List<Leave>> leaveData = rt.exchange("http://employee-microservice/leav/emp/" + id + "/leave", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Leave>>() {
				});;
				ModelAndView mv= new ModelAndView();
				mv.addObject("leaveData", leaveData.getBody());
				mv.setViewName("find-leave");
				return mv;
	}
	//------------------------Employee timesheet leaves -----------------------
		@GetMapping("/findTimesheet/{id}")
		public ModelAndView findTimesheet(ModelMap map,@PathVariable long id) {
			ResponseEntity<List<Timesheet>> timesheetData = rt.exchange("http://employee-microservice/timeshe/emp/" + id + "/timesheet", HttpMethod.GET, null,
					new ParameterizedTypeReference<List<Timesheet>>() {
					});;
					ModelAndView mv= new ModelAndView();
					mv.addObject("timesheetData", timesheetData.getBody());
					mv.setViewName("find-timesheet");
					return mv;
		}
	
	@GetMapping("/viewEmpAction")
	public ModelAndView viewEmpAction(ModelMap map) {
		ResponseEntity<List<Employee>> empDataAction = rt.exchange("http://employee-microservice/empl/emp", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Employee>>() {
				});;
				System.out.println("Employee received");
				ModelAndView mv= new ModelAndView();
				mv.addObject("empDataAction", empDataAction.getBody());
				mv.setViewName("viewEmpAction");
				return mv;
	} 

//----------------------------Emp view profile-----------------------------------------
	@GetMapping(path = "find")
	public String getPage() {
		return "findEmp";
	}
	
	
	@PostMapping("/find")
	public  ModelAndView findEmp(@RequestParam("empId") int id) {
		System.out.println(id);
		Employee e= rt.getForObject("http://employee-microservice/empl/emp/" + id, Employee.class);
		System.out.println(e.getName());		
		ModelAndView mv = new ModelAndView();
		mv.addObject("employeeData", e);
		System.out.println(e.getName());
		mv.setViewName("findEmp");
		return mv;
	}
	
	
	//-----------------------admin add payslips--------------------------
	
	@GetMapping(path = "addPayslip/{id}")
	public String getPayslip(@PathVariable long id,ModelMap map) {
		a=id;
		Payslip p=new Payslip();
		map.addAttribute("payslip", p);
		return "add-payslip";	}
	
	@PostMapping(path = "/addingPayslip")
	public ModelAndView addingPayslip(@ModelAttribute("payslip") Payslip p) {
		ModelAndView mv = new ModelAndView();
		HttpEntity req = new HttpEntity(p);
		ResponseEntity<String> resp = rt.exchange("http://employee-microservice/empl/emp/" + a+ "/addPayslip", HttpMethod.PUT, req, String.class);
		mv.setViewName("saved-emp");
		mv.addObject("resp", resp.getBody());
		return mv;
	}
	//-------------------------admin add timesheet--------------------  
	@GetMapping(path = "addTimesheet/{id}")
	public String getTimesheet(@PathVariable long id,ModelMap map) {
		a=id;
		Timesheet t=new Timesheet();
		map.addAttribute("timesheet", t);
		return "add-timesheet";	}
	
	@PostMapping(path = "/addingTimesheet")
	public ModelAndView addingTimesheet(@ModelAttribute("timesheet") Timesheet t) {
		
		ModelAndView mv = new ModelAndView();
		HttpEntity req = new HttpEntity(t);
		ResponseEntity<String> resp = rt.exchange("http://employee-microservice/empl/emp/" + a+ "/addTimesheet", HttpMethod.PUT, req, String.class);
		mv.setViewName("saved-emp");
		mv.addObject("resp", resp.getBody());
		return mv;
	}
//---------------------------admin add leaves-----------------------
	@GetMapping(path = "addLeaves/{id}")
	public String getLeaves(@PathVariable long id,ModelMap map) {
		a=id;
		Leave l=new Leave();
		map.addAttribute("leave", l);
		return "add-leaves";	}
	
	@PostMapping(path = "/addingLeaves")
	public ModelAndView addingLeaves(@ModelAttribute("leaves") Leave l) {
		
		ModelAndView mv = new ModelAndView();
		HttpEntity req = new HttpEntity(l);
		ResponseEntity<String> resp = rt.exchange("http://employee-microservice/empl/emp/" + a+ "/addLeave", HttpMethod.PUT, req, String.class);
		mv.setViewName("saved-emp");
		mv.addObject("resp", resp.getBody());
		return mv;
	}
//-------------------admin add salary---------------------------

	@GetMapping(path = "addSalary/{id}")
	public String getSalary(@PathVariable long id,ModelMap map) {
		a=id;
		Salary s=new Salary();
		map.addAttribute("salary", s);
		return "add-salary";	}


	@PostMapping(path = "/addingSalary")
	public ModelAndView addingSalary(@ModelAttribute("salary") Salary s) {
		
		ModelAndView mv = new ModelAndView();
		HttpEntity req = new HttpEntity(s);
		ResponseEntity<String> resp = rt.exchange("http://employee-microservice/empl/emp/" + a+ "/addSalary", HttpMethod.PUT, req, String.class);
		mv.setViewName("saved-emp");
		mv.addObject("resp", resp.getBody());
		return mv;
	}
	
	
	//------------------Admin add employee----------------------------
	
	@GetMapping(path = "addingEmp")
	public String empAddPage(ModelMap map) {
		Employee employee = new Employee();
		map.addAttribute("employee", employee);
		return "add-emp";
	}
	
	@PostMapping(path = "/savingEmp")
	public ModelAndView empSavePage(@ModelAttribute("employee") Employee e) {
		ModelAndView mv = new ModelAndView();
		HttpEntity req = new HttpEntity(e);
		ResponseEntity<String> resp = rt.exchange("http://employee-microservice/empl/emp", HttpMethod.POST, req,
				String.class);
		mv.setViewName("saved-emp");
		mv.addObject("resp", resp.getBody());
		return mv;
	}
	
	
	//here
	
//--------------------------------------Admin Approve leave ---------------
	
	@GetMapping(path = "approveLeaves/{id}")
	public String approveLeaves(@PathVariable long id,ModelMap map) {
		a=id;
		Leave l=new Leave();
		map.addAttribute("leave", l);
		return "approve-leaves";	}
	
	@PostMapping(path = "/approvingLeaves")
	public ModelAndView approvingLeaves(@ModelAttribute("leaves") Leave l) {
		
		ModelAndView mv = new ModelAndView();
		HttpEntity req = new HttpEntity(l);
		ResponseEntity<String> resp = rt.exchange("http://employee-microservice/leav/emp/" + a+ "/updateLeave/approve", HttpMethod.PUT, req, String.class);
		mv.setViewName("saved-emp");
		mv.addObject("resp", resp.getBody());
		return mv;
	}

}
