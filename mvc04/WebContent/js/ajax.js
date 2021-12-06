/**
 * ajax.js
 */

var ajax = null;

// AJAX 객체(XMLHttpRequest 객체) 생성
function createAjax()
{
	/*
	// ActiveXObject를 지원하는 브라우저라면
	if (window.ActiveXObject)	// 보통 IE 7 이전 버전
	{
		return new ActiveXObject("Msxml2.XMLHTTP");
		// 이와 같은 방식으로 XMLHttpRequest 객체를 생성해서 반환하라.
		// (ActiveXObject 객체 → XMLHttpRequest 객체의 대리 객체)
	}
	else	// 그 외 브라우저
	{
		if (window.XMLHttpRequest)	// XMLHttpRequest 지원 브라우저
		{
			return new XMLHttpRequest();
			// 이와 같은 방식으로 XMLHttpRequest 객체를 생성해서 반환하라.
		}
		else // AJAX를 지원하지 않는 브라우저
		{
			return null;
		}
	}
	*/
	
	// 위의 주석과 같은 구문
	if (window.XMLHttpRequest)
	{
		return new XMLHttpRequest();
	}
	else if (window.ActiveXObject)
	{
		// return new ActiveXObject("Msxml2.XMLHTTP");
		return new ActiveXObject("Microsoft.XMLHTTP");
	}
	else
	{
		return null;
	}
	
}