package users.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import users.dao.UserDAO;
import users.model.User;

@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDAO dao = new UserDAO();
	
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
    	doGet(request,response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
      String contextpath = request.getContextPath();
      String requestURL = request.getRequestURL().toString();
      String action = requestURL.split(contextpath)[1];
      System.out.println(action);
      
		
		switch (action) {
		case "/new":
			showNewForm(request, response);
			break;
		case "/insert":
			insertUser(request, response);
			break;
		case "/delete":
			deleteUser(request, response);
			break;
		case "/edit":
			editUser(request, response);
			break;
		case "/update":
			updateUser(request, response);		
			break;
		default :
			userList(request, response);
			break;
		}		
	}
	
	


	private void userList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<User> userList = dao.selectAllUsers();
		RequestDispatcher rd = request.getRequestDispatcher("userList.jsp");
		request.setAttribute("userList", userList);
		rd.forward(request, response);
		//System.out.println("list-servlet");
		//System.out.println(userList.toString());
	}

	
	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		User user = new User(id,name,email,country);
		dao.updateUser(user);
		response.sendRedirect("list");
	}


	private void editUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		User existingUser = dao.userById(id);
		RequestDispatcher rd = request.getRequestDispatcher("userForm.jsp");
		request.setAttribute("user", existingUser);
		rd.forward(request,response);
	}

	
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		dao.deleteUser(id);
		response.sendRedirect("list");
	}


	private void insertUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//System.out.println("insert user start");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		User user = new User(name,email,country);
		dao.insertUser(user);
		response.sendRedirect("list");
		PrintWriter out = response.getWriter();
    	out.println("Row Inserted - servlet");
	}


	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("userForm.jsp");
		rd.forward(request, response);
	}
	
	

	

}
