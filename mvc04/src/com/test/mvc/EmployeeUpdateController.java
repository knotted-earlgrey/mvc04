/*
 * EmployeeUpdateController.java
 * 사용자 정의 컨트롤러
 * 직원 데이터 수정 액션 수행 → employeelist.action 다시 요청하도록 안내
 * DAO 객체에 대한 의존성 주입(DI)을 위한 준비(인터페이스 속성 구성) → setter 메소드 준비
 */

package com.test.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class EmployeeUpdateController implements Controller
{
	private IEmployeeDAO employeeDAO;
	
	// setter 구성
	public void setEmployeeDAO(IEmployeeDAO employeeDAO)
	{
		this.employeeDAO = employeeDAO;
	}


	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		
		// String sql="UPDATE EMPLOYEE SET NAME=?, BIRTHDAY=TO_DATE(?, 'YYYY-MM-DD'), LUNAR=?, TELEPHONE=?"
		// + ", DEPARTMENTID=?, POSITIONID=?, REGIONID=?, BASICPAY=?, EXTRAPAY=?"
		// + ", SSN1=?, SSN2=CRYPTPACK.ENCRYPT(?, ?) WHERE EMPLOYEEID=?";
		
		int result=0;
		
		try
		{
			// 데이터 수신 → EmployeeUpdateForm.jsp(수정폼)으로부터 넘겨받은 데이터
			Employee employee = new Employee();
			
			// String sql="UPDATE EMPLOYEE SET NAME=?, BIRTHDAY=TO_DATE(?, 'YYYY-MM-DD'), LUNAR=?, TELEPHONE=?"
			//+ ", DEPARTMENTID=?, POSITIONID=?, REGIONID=?, BASICPAY=?, EXTRAPAY=?"
			//+ ", SSN1=?, SSN2=CRYPTPACK.ENCRYPT(?, ?) WHERE EMPLOYEEID=?";
			
			// EmployeeUpdateForm.jsp의 name("employeeId", "name", "ssn1", ...)을 통해 데이터 받아옴
			employee.setEmployeeId(request.getParameter("employeeId"));
			employee.setName(request.getParameter("name"));
			employee.setSsn1(request.getParameter("ssn1"));
			employee.setSsn2(request.getParameter("ssn2"));
			employee.setBirthday(request.getParameter("birthday"));
			employee.setLunar(Integer.parseInt(request.getParameter("lunar")));
			employee.setTelephone(request.getParameter("telephone"));
			employee.setRegionId(request.getParameter("regionId"));
			employee.setDepartmentId(request.getParameter("departmentId"));
			employee.setPositionId(request.getParameter("positionId"));
			employee.setBasicPay(Integer.parseInt(request.getParameter("basicPay")));
			employee.setExtraPay(Integer.parseInt(request.getParameter("extraPay")));;
			
			result = employeeDAO.modify(employee);
			
			mav.setViewName("redirect:employeelist.action");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
	}

}
