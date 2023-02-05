package org.myProject.registration;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String user_name = request.getParameter("name");
		String user_email = request.getParameter("email");
		String user_password = request.getParameter("pass");
		String user_contact = request.getParameter("contact");

//		PrintWriter out = response.getWriter();
//		out.print(user_name);
//		out.print(user_email);
//		out.print(user_password);
//		out.print(user_contact);

		String dburl = "jdbc:mysql://localhost:3306/my_project";
		String dbUserName = "root";
		String dbPassword = "admin";
		Connection con = null;
		PreparedStatement pst = null;
		RequestDispatcher dispatcher = null;
		String qry = "insert into user_database(full_name,email_id,contact,password) values(?,?,?,?)";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(dburl, dbUserName, dbPassword);
			pst = con.prepareStatement(qry);
			pst.setString(1, user_name);
			pst.setString(2, user_email);
			pst.setString(3, user_contact);
			pst.setString(4, user_password);
			int row_effected = pst.executeUpdate();
			
			dispatcher = request.getRequestDispatcher("registration.jsp");
			if (row_effected > 0) {
				request.setAttribute("status", "success");
			} else {
				request.setAttribute("status", "fail");
			}
			dispatcher.forward(request, response);
			
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
