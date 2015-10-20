package cs4347.jdbcProject.ecomm.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import cs4347.jdbcProject.ecomm.dao.CustomerDAO;
import cs4347.jdbcProject.ecomm.util.DAOException;
import cs4347.jdbcProject.ecomm.entity.Address;
import cs4347.jdbcProject.ecomm.entity.Customer;

public class CustomerDaoImpl implements CustomerDAO
{

	@Override
	public Customer create(Connection connection, Customer customer) throws SQLException, DAOException {
		
		return null;
	}

	@Override
	public Customer retrieve(Connection connection, Long id) throws SQLException, DAOException {
		Address address = new Address();
		final String selectQuery = "SELECT id, firstName, lastName, gender, dob, email, address, creditcard WHERE id = ?";
		if(id==null){
			throw new DAOException("Trying to retrieve a Customer with NULL ID");
		}
		
		PreparedStatement ps = null;
		try{
			ps = connection.prepareStatement(selectQuery);
			ps.setLong(1, id); //setting the id in the selectquery that is loaded into the prepare statement
			ResultSet rs = ps.executeQuery();
			if(!rs.next()){ 
				return null;
			}
			
			Customer cust = new Customer(); //creating a new customre object for what we will retrieve from the database
			cust.setId(rs.getLong("id"));
			cust.setFirstName(rs.getString("firstName"));
			cust.setLastName(rs.getString("lastName"));
			cust.setGender(rs.getString("gender").charAt(0));
			cust.setDob(rs.getDate("dob"));
			cust.setEmail(rs.getString("email"));
			Address ad = new Address();
			ad.setAddress1(rs.getString("address1"));
			ad.setAddress2(rs.getString("address2"));
			ad.setCity(rs.getString("city"));
			ad.setState(rs.getString("state"));
			ad.setZipcode(rs.getString("zipcode"));
			cust.setAddress(ad);
			CreditCard cc = new CreditCard();
			cust.setCreditCard(rs.getString("CreditCard"));
		}
		finally{
			
		}
		return null;
	}

	@Override
	public int update(Connection connection, Customer customer) throws SQLException, DAOException {
		Long Id = customer.getId();
		if (Id==null){
			throw new DAOException("Trying to update a Customer with NULL ID");
		}
		
		PreparedStatement ps = null;
		try{
			final String updateSQL = "UPDATE customer SET firstName = ?, lastName = ?, gender = ?, dob = ?, email = ?, address =?, creditCard =? WHERE id = ?"; //check if CC/add needed
			ps = connection.prepareStatement(updateSQL);
			ps.setString(1,  customer.getFirstName());
			ps.setString(2, customer.getLastName());
			ps.setString(3, String.valueOf(customer.getGender()));
			ps.setDate(4, customer.getDob());
			ps.setString(5, customer.getEmail());
			ps.setString(6, String.valueOf(customer.getAddress())); //may not be correct
			ps.setString(7, String.valueOf(customer.getCreditCard())); //may not be correct
			ps.setLong(8, Id);
			
			int rows = ps.executeUpdate(); // actually sends the prepared statement to the mysql server
			return rows; //Returns the number of rows that were updated
		}
		finally{ //likely switch to an if statement if ps!=null/isClosed()
			ps.close();
			connection.close();
		}
		return 0;
	}

	@Override
	public int delete(Connection connection, Long id) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Customer> retrieveByZipCode(Connection connection, String zipCode) throws SQLException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Customer> retrieveByDOB(Connection connection, Date startDate, Date endDate)
			throws SQLException, DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}