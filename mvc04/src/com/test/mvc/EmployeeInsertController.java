/*
 * EmployeeInsertController.java
 * 사용자 정의 컨트롤러
 * 직원 데이터 입력 액션 수행 (DAO 필요)
 * 이후 employeelist.action을 다시 요청할 수 있도록 안내
 * DAO 객체에 대한 의존성 주입(DI)을 위한 준비 (→ 인터페이스 형태의 자료형을 속성으로 구성, setter 메소드 구성)
 */

package com.test.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class EmployeeInsertController implements Controller
{
	// 인터페이스 구성
	private IEmployeeDAO dao;
	
	// setter 구성
	public void setDao(IEmployeeDAO dao)
	{
		this.dao = dao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mav = new ModelAndView();
		
		int result = 0;
		
		try
		{
			Employee employee = new Employee();
			
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
			
			result = dao.employeeAdd(employee);
			
			//mav.addObject("result", result);
			mav.setViewName("redirect:employeelist.action");		// ★리다이렉트!!! 해야지 새로고침한다고 데이터 중복입력 안됨!!!!
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
	}

}
