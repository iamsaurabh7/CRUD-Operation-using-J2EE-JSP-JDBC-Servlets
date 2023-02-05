package org.myProject.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String user_email = request.getParameter("username");
		String user_password = request.getParameter("password");
		HttpSession session = request.getSession();
		String dburl = "jdbc:mysql://localhost:3306/my_project";
		String dbUserName = "root";
		String dbPassword = "admin";
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		RequestDispatcher dispatcher = null;
		String qry = "select * from user_database where email_id=? and password=?";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(dburl, dbUserName, dbPassword);
			pst = con.prepareStatement(qry);
			pst.setString(1, user_email);
			pst.setString(2, user_password);
			rs = pst.executeQuery();
			if (rs.next()) {
				session.setAttribute("name", rs.getString("full_name"));
				session.setAttribute("email", rs.getString("email_id"));
				dispatcher = request.getRequestDispatcher("index.jsp");
			} else {
				request.setAttribute("status", "failed");
				dispatcher = request.getRequestDispatcher("login.jsp");
			}
			dispatcher.forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
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
