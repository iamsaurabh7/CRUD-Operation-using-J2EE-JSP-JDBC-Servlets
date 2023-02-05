package org.myProject.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/update")
public class updateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user_name = request.getParameter("name");
		HttpSession session= request.getSession();
		String user_email = (String) (session.getAttribute("email"));
		String user_password = request.getParameter("password");
		String user_contact = request.getParameter("contact");
		
		String dburl = "jdbc:mysql://localhost:3306/my_project";
		
		String dbUserName = "root";
		String dbPassword = "admin";
		Connection con = null;
		PreparedStatement pst = null;
		RequestDispatcher dispatcher = null;
//		String qry = "insert into user_database(full_name,email_id,contact,password) values(?,?,?,?)";
		String qry = "update user_database set full_name=?,contact=?,password=? where email_id=?";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(dburl, dbUserName, dbPassword);
			pst = con.prepareStatement(qry);
			pst.setString(1, user_name);
			pst.setString(2, user_contact);
			pst.setString(3, user_password);
			pst.setString(4, user_email);
			int row_effected = pst.executeUpdate();
			
			dispatcher = request.getRequestDispatcher("update.jsp");
			if (row_effected > 0) {
				request.setAttribute("status", "success");
//				session.invalidate();
			} else {
				request.setAttribute("status", "fail");
			}
			dispatcher.forward(request, response);
//			session.invalidate();
//			response.sendRedirect("login.jsp");
			
		} catch (SQLException | ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
