/*
 * EmployeeDeleteController.java
 * 사용자 정의 컨트롤러
 * 직원 데이터 삭제 액션 처리 → 처리 후 employeelist.action을 다시 요청할 수 있도록 안내
 * DAO 객체에 대한 의존성 주입(DI)을 위한 준비
 * → 인터페이스 형태의 자료형을 속성으로 구성
 * → setter 메소드 구성
 */

package com.test.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class EmployeeDeleteController implements Controller
{
	// 인터페이스 속성 구성
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
		
		int result = 0;
		String employeeId = request.getParameter("employeeId");
		
		try
		{
			result = employeeDAO.remove(employeeId);
			
			
			mav.setViewName("redirect:employeelist.action");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
	}

}
