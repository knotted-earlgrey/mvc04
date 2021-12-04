/*
 * 9. EmployeeDAO.java
 * 데이터베이스 액션 처리 클래스
 * 직원 정보 입력 / 출력 / 수정 / 삭제 액션
 * Connection 객체에 대한 의존성 주입을 위한 준비
 * (인터페이스 형태의 속성 구성 => DataSource, setter 메소드 정의)
 */

package com.test.mvc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

public class EmployeeDAO implements IEmployeeDAO
{
	// 주요 속성 구성 → 인터페이스 형태
	private DataSource dataSource;
	
	// setter 메소드 정의
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	// 인터페이스 오버라이딩
	// 직원 리스트 조회
	@Override
	public ArrayList<Employee> list() throws SQLException
	{
		ArrayList<Employee> result = new ArrayList<Employee>();
		
		String sql="SELECT EMPLOYEEID, NAME, SSN, BIRTHDAY, LUNAR, LUNARNAME, TELEPHONE"
				+ ", DEPARTMENTID, DEPARTMENTNAME, POSITIONID, POSITIONNAME, REGIONID, REGIONNAME"
				+ ", BASICPAY, EXTRAPAY, PAY, GRADE FROM EMPLOYEEVIEW ORDER BY EMPLOYEEID";
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next())
		{
			Employee employee = new Employee();
			employee.setEmployeeId(rs.getString(1));
			employee.setName(rs.getString(2));
			employee.setSsn(rs.getString(3));
			employee.setBirthday(rs.getString(4));
			employee.setLunar(rs.getInt(5));
			employee.setLunarName(rs.getString(6));
			employee.setTelephone(rs.getString(7));
			employee.setDepartmentId(rs.getString(8));
			employee.setDepartmentName(rs.getString(9));
			employee.setPositionId(rs.getString(10));
			employee.setPositionName(rs.getString(11));
			employee.setRegionId(rs.getString(12));
			employee.setRegionName(rs.getString(13));
			employee.setBasicPay(rs.getInt(14));
			employee.setExtraPay(rs.getInt(15));
			employee.setPay(rs.getInt(16));
			employee.setGrade(rs.getInt(17));
			
			result.add(employee);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

	// 지역 리스트 조회
	@Override
	public ArrayList<Region> regionList() throws SQLException
	{
		ArrayList<Region> result = new ArrayList<Region>();
		
		String sql="SELECT REGIONID, REGIONNAME, DELCHECK FROM REGIONVIEW ORDER BY REGIONID";
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next())
		{
			Region region = new Region();
			region.setRegionId(rs.getString(1));
			region.setRegionName(rs.getString(2));
			region.setDelCheck(rs.getInt(3));
			
			result.add(region);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

	// 부서 리스트 조회
	@Override
	public ArrayList<Department> departmentList() throws SQLException
	{
		ArrayList<Department> result = new ArrayList<Department>();
		
		String sql="SELECT DEPARTMENTID, DEPARTMENTNAME, DELCHECK FROM DEPARTMENTVIEW ORDER BY DEPARTMENTID";
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next())
		{
			Department department = new Department();
			department.setDepartmentId(rs.getString(1));
			department.setDepartmentName(rs.getString(2));
			department.setDelCheck(rs.getInt(3));
			
			result.add(department);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

	// 직위 리스트 조회
	@Override
	public ArrayList<Position> positionList() throws SQLException
	{
		ArrayList<Position> result = new ArrayList<Position>();
		
		String sql="SELECT POSITIONID, POSITIONNAME, MINBASICPAY, DELCHECK FROM POSITIONVIEW ORDER BY POSITIONID";
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next())
		{
			Position position = new Position();
			position.setPositionId(rs.getString(1));
			position.setPositionName(rs.getString(2));
			position.setMinBasicPay(rs.getInt(3));
			position.setDelCheck(rs.getInt(4));
			
			result.add(position);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

	// 직위 아이디에 따른 최소 기본급 확인/검색
	@Override
	public int getMinBasicPay(String positionId) throws SQLException
	{
		int result=0;
		
		String sql="SELECT MINBASICPAY FROM POSITION WHERE POSITIONID=?";
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Integer.parseInt(positionId));
		ResultSet rs = pstmt.executeQuery();
		while (rs.next())
			result = rs.getInt(1);
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

	// 직원 데이터 추가
	@Override
	public int employeeAdd(Employee employee) throws SQLException
	{
		int result=0;
		
		String sql="INSERT INTO EMPLOYEE(EMPLOYEEID, NAME, SSN1, SSN2, BIRTHDAY, LUNAR, TELEPHONE"
				+ ", DEPARTMENTID, POSITIONID, REGIONID, BASICPAY, EXTRAPAY) VALUES(EMPLOYEESEQ.NEXTVAL"
				+ ", ?, ?, CRYPTPACK.ENCRYPT(?, ?), TO_DATE(?, 'YYYY-MM-DD'), ?"
				+ ", ?, ?, ?, ?, ?, ?)";
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, employee.getName());
		pstmt.setString(2, employee.getSsn1());
		pstmt.setString(3, employee.getSsn2());
		pstmt.setString(4, employee.getSsn2());
		pstmt.setString(5, employee.getBirthday());
		pstmt.setInt(6, employee.getLunar());
		pstmt.setString(7, employee.getTelephone());
		pstmt.setInt(8, Integer.parseInt(employee.getDepartmentId()));
		pstmt.setInt(9, Integer.parseInt(employee.getPositionId()));
		pstmt.setInt(10, Integer.parseInt(employee.getRegionId()));
		pstmt.setInt(11, employee.getBasicPay());
		pstmt.setInt(12, employee.getExtraPay());
		
		result = pstmt.executeUpdate();
		pstmt.close();
		conn.close();
		
		return result;
	}

	// 직원 데이터 삭제
	@Override
	public int remove(String employeeId) throws SQLException
	{
		int result=0;
		
		String sql="DELETE FROM EMPLOYEE WHERE EMPLOYEEID=?";
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Integer.parseInt(employeeId));
		result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}

	// 직원 데이터 수정
	@Override
	public int modify(Employee employee) throws SQLException
	{
		int result=0;
		
		String sql="UPDATE EMPLOYEE SET NAME=?, BIRTHDAY=TO_DATE(?, 'YYYY-MM-DD'), LUNAR=?, TELEPHONE=?"
				+ ", DEPARTMENTID=?, POSITIONID=?, REGIONID=?, BASICPAY=?, EXTRAPAY=?"
				+ ", SSN1=?, SSN2=CRYPTPACK.ENCRYPT(?, ?) WHERE EMPLOYEEID=?";
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, employee.getName());
		pstmt.setString(2, employee.getBirthday());
		pstmt.setInt(3, employee.getLunar());
		pstmt.setString(4, employee.getTelephone());
		pstmt.setInt(5, Integer.parseInt(employee.getDepartmentId()));
		pstmt.setInt(6, Integer.parseInt(employee.getPositionId()));
		pstmt.setInt(7, Integer.parseInt(employee.getRegionId()));
		pstmt.setInt(8, employee.getBasicPay());
		pstmt.setInt(9, employee.getExtraPay());
		pstmt.setString(10, employee.getSsn1());
		pstmt.setString(11, employee.getSsn2());
		pstmt.setString(12, employee.getSsn2());
		pstmt.setInt(13, Integer.parseInt(employee.getEmployeeId()));
		result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}

	// 아이디로 직원 검색
	@Override
	public Employee searchId(String employeeId) throws SQLException
	{
		Employee employee = new Employee();
		
		String sql="SELECT EMPLOYEEID, NAME, SSN1, TO_CHAR(BIRTHDAY, 'YYYY-MM-DD') AS BIRTHDAY, LUNAR, TELEPHONE"
				+ ", DEPARTMENTID, POSITIONID, REGIONID, BASICPAY, EXTRAPAY FROM EMPLOYEE WHERE EMPLOYEEID=?";
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Integer.parseInt(employeeId));
		ResultSet rs = pstmt.executeQuery();
		if (rs.next())
		{
			employee.setEmployeeId(rs.getString(1));
			employee.setName(rs.getString(2));
			employee.setSsn1(rs.getString(3));
			employee.setBirthday(rs.getString(4));
			employee.setLunar(rs.getInt(5));
			employee.setTelephone(rs.getString(6));
			employee.setDepartmentId(rs.getString(7));
			employee.setPositionId(rs.getString(8));
			employee.setRegionId(rs.getString(9));
			employee.setBasicPay(rs.getInt(10));
			employee.setExtraPay(rs.getInt(11));
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return employee;
	}
	
	// 일반 직원 로그인 메소드
	@Override
	public String login(String id, String pw) throws SQLException
	{
		String result = null;
		
		String sql="SELECT NAME FROM EMPLOYEE WHERE EMPLOYEEID=?"
				+ " AND SSN2 = CRYPTPACK.ENCRYPT(?, ?)";
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Integer.parseInt(id));
		pstmt.setString(2, pw);
		pstmt.setString(3, pw);
		
		ResultSet rs = pstmt.executeQuery();
		if (rs.next())
			result = rs.getString("NAME");
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

	// 관리자 로그인 메소드
	@Override
	public String logAdmin(String id, String pw) throws SQLException
	{
		String result = null;
		
		String sql="SELECT NAME FROM EMPLOYEE WHERE EMPLOYEEID=?"
				+ " AND SSN2 = CRYPTPACK.ENCRYPT(?, ?) AND GRADE=0";	// 관리자 : GRADE=0
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Integer.parseInt(id));
		pstmt.setString(2, pw);
		pstmt.setString(3, pw);
		
		ResultSet rs = pstmt.executeQuery();
		if (rs.next())
			result = rs.getString("NAME");
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	
}
