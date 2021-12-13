/*
 * EmployeeUpdateFormController.java
 * 사용자 정의 컨트롤러
 * 직원 데이터 수정 폼 요청에 대한 액션 처리
 * DAO 객체에 대한 의존성 주입(DI)을 위한 준비 필요
 * 인터페이스 형태의 자료형을 속성으로 구성, setter 메소드 구성
 */

package com.test.mvc;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class EmployeeUpdateFormController implements Controller
{
	// 인터페이스 (각각의 인터페이스를 따로 운용하는 방법으로 처리함)
	private IEmployeeDAO employeeDAO;
	private IRegionDAO regionDAO;
	private IDepartmentDAO departmentDAO;
	private IPositionDAO positionDAO;
	
	// setter 구성
	public void setEmployeeDAO(IEmployeeDAO employeeDAO)
	{
		this.employeeDAO = employeeDAO;
	}

	public void setRegionDAO(IRegionDAO regionDAO)
	{
		this.regionDAO = regionDAO;
	}

	public void setDepartmentDAO(IDepartmentDAO departmentDAO)
	{
		this.departmentDAO = departmentDAO;
	}

	public void setPositionDAO(IPositionDAO positionDAO)
	{
		this.positionDAO = positionDAO;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		
		ArrayList<Region> regionList = new ArrayList<Region>();
		ArrayList<Department> departmentList = new ArrayList<Department>();
		ArrayList<Position> positionList = new ArrayList<Position>();
		
		try
		{
			// EmployeeList.jsp로부터 employeeId 수신
			String employeeId = request.getParameter("employeeId");
			
			Employee employee = new Employee();
			employee = employeeDAO.searchId(employeeId);
			
			regionList = regionDAO.list();
			departmentList = departmentDAO.list();
			positionList = positionDAO.list();
			
			mav.addObject("employee", employee);
			mav.addObject("regionList", regionList);
			mav.addObject("departmentList", departmentList);
			mav.addObject("positionList", positionList);
			
			/*
			mav.addObject("employeeId", employee.getEmployeeId());
			mav.addObject("name", employee.getName());
			mav.addObject("ssn1", employee.getSsn1());
			mav.addObject("birthday", employee.getBirthday());
			mav.addObject("lunar", employee.getLunar());
			mav.addObject("telephone", employee.getTelephone());
			mav.addObject("departmentId", employee.getDepartmentId());
			mav.addObject("positionId", employee.getPositionId());
			mav.addObject("regionId", employee.getRegionId());
			mav.addObject("basicPay", employee.getBasicPay());
			mav.addObject("extraPay", employee.getExtraPay());
			*/
			
			mav.setViewName("WEB-INF/view/EmployeeUpdateForm.jsp");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
	}

}
