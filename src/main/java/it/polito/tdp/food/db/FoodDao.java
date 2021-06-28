package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}



	/*String sql = "SELECT food.food_code, food.display_name, COUNT(DISTINCT portion.portion_id) AS CNT " + 
"FROM food, `portion` " + 
"WHERE food.food_code=portion.food_code " + 
"GROUP BY food.food_code " + 
"HAVING CNT=? " +
"ORDER BY food.display_name ASC" ;*/

	public List<Food> getVertici(int porzioni, Map<Integer, Food> mappa){
		String sql = "SELECT distinct food_code "
				+ "FROM `portion` p "
				+ "GROUP BY p.food_code "
				+ "HAVING COUNT(DISTINCT p.portion_id) <= ?";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, porzioni);
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					Food f = mappa.get(res.getInt("food_code"));
					list.add(f);
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

		
	}
	
	public List<Adiacenza> getAdiacenze(Map<Integer, Food> idMap){
		String sql = "SELECT f1.food_code, f2.food_code, COUNT(DISTINCT c.condiment_code) AS conta, SUM( c.condiment_calories) AS somma "
				+ "FROM food_condiment f1, food_condiment f2, condiment c "
				+ "WHERE f1.condiment_code = f2.condiment_code AND "
				+ "f1.food_code <> f2.food_code AND "
				+ "c.condiment_code = f1.condiment_code "
				+ "GROUP BY f1.food_code, f2.food_code";
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Adiacenza> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					Food f1 = idMap.get(res.getInt("f1.food_code"));
					Food f2 = idMap.get(res.getInt("f2.food_code"));
					if(f1!=null && f2!=null) {
						Adiacenza a = new Adiacenza(f1, f2, res.getInt("conta"), res.getDouble("somma"));
						list.add(a);
					}
					
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
}