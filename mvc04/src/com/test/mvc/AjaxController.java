/*
 * AjaxController.java
 * 사용자 정의 컨트롤러
 */

package com.test.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class AjaxController implements Controller
{
	// 인터페이스 형태의 속성 구성
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
		
		// 데이터 수신 (→ EmployeeInsertForm.jsp로부터 positionId 수신)
		String positionId = request.getParameter("positionId");
  		
		int result = 0;
		
		try
		{
			result = dao.getMinBasicPay(positionId);
			
			mav.addObject("result", result);
			mav.setViewName("/WEB-INF/view/Ajax.jsp");
			
		} catch (Exception e)
		{
			System.out.println(e.toString());
		}
		
		return mav;
	}

}
