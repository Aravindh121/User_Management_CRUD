package users.dao;

import java.util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import users.model.User;
import users.util.ConnectionUtil;

public class UserDAO {
	
	
	//Insert User
	public void insertUser(User user) {
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO Users (name,email,country) VALUES (?,?,?)");
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getEmail());
			stmt.setString(3, user.getCountry());
			stmt.executeUpdate();
			//System.out.println("Inserted-DAO");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
	}
	
	
	//update User
	public boolean updateUser(User user) {
		boolean rowUpdated=false;
		
		Connection conn = null;	
		try {
			conn = ConnectionUtil.getConnection();
			PreparedStatement stmt = conn.prepareStatement("UPDATE Users SET name=?,email=?,country=? WHERE id=? ");
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getEmail());
			stmt.setString(3, user.getCountry());
			stmt.setInt(4, user.getId());
			
			rowUpdated = stmt.executeUpdate()>0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		
		return rowUpdated;
	}
	
	
	//select user by id
	public User userById(int id) {
		User user = null;
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE id=?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				user = new User(id, name, email, country);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		
		return user;
	}
	
	
	//select users
	public List<User> selectAllUsers(){
		List<User> users = new ArrayList<>();
		
		Connection conn = null;	
		try {
			conn = ConnectionUtil.getConnection();
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				users.add(new User(id, name, email, country));
				//System.out.println(users.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		
		return users;
	}
	
	
	//delete user
	public boolean deleteUser(int id) {
		boolean isDeleted = false;
		
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM Users WHERE id=?");
			stmt.setInt(1, id);
			isDeleted = stmt.executeUpdate()>0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}
		
		return isDeleted;		
	}
	
	private void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close(); // Instead of closing, it returns the connection to the pool
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
