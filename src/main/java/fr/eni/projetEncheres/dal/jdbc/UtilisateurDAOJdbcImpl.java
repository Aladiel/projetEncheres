package fr.eni.projetEncheres.dal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.projetEncheres.BusinessException;
import fr.eni.projetEncheres.bo.Utilisateur;
import fr.eni.projetEncheres.dal.CodesResultatDAL;
import fr.eni.projetEncheres.dal.ConnectionProvider;
import fr.eni.projetEncheres.dal.dao.DAO;
import fr.eni.projetEncheres.dal.dao.UtilisateurDAO;

/**
 * @author Daphné
 */
public class UtilisateurDAOJdbcImpl implements DAO<Utilisateur>, UtilisateurDAO {
	
	private static final String SQL_SELECT_BY_ID = "select no_utilisateur, pseudo, "
			+ "nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur " 
			+ " from utilisateurs where no_utilisateur = ?";
	private static final String SQL_SELECT_ALL = "select no_utilisateur, pseudo, nom, prenom, "
			+ "email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur " +  
			" from utilisateurs";
	private static final String SQL_UPDATE = "update utilisateurs set pseudo=?,"
			+ "email=?,telephone=?,rue=?,code_postal=?,ville=?,mot-de-passe=?"
			+ " where no_utilisateur = ?";
	private static final String SQL_INSERT = "insert into utilisateurs(pseudo,nom,prenom,email,telephone,"
			+ "rue,code_postal,ville,mot_de_passe,credit,administrateur) values(?,?,?,?,?,?,?,?,?,?,?)";
	private static final String SQL_DELETE = "delete from utilisateurs where no_utilisateur = ?";
	private static final String SQL_SELECTID = "select no_utilisateur from utilisateurs WHERE pseudo = ? and mot_de_passe = ?";
	private static final String SQL_SELECTMAIL = "select no_utilisateur from utilisateurs WHERE email = ? and mot_de_passe = ?";
	private static final String SQL_SELECT_ONE_PARAMETER = "select pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe from utilisateurs WHERE no_utilisateur = ?";
	private static final String SQL_SELECT_CREDIT = "select credit from utilisateurs where no_utilisateur = ?";
	
	@Override
	public Utilisateur selectById(int no_utilisateur) throws BusinessException {
		
		Utilisateur utilisateur = null;
		
		
		
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SQL_SELECT_BY_ID);
			pstmt.setInt(1, no_utilisateur);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
					
					utilisateur = new Utilisateur(rs.getInt("no_utilisateur"),
							rs.getString("pseudo"),
							rs.getString("nom"),
							rs.getString("prenom"),
							rs.getString("email"),
							rs.getString("telephone"),
							rs.getString("rue"),
							rs.getString("code_postal"),
							rs.getString("ville"),
							rs.getString("mot_de_passe"),
							rs.getInt("credit"),
							rs.getBoolean("administrateur"));
			}
			try {
				if (rs != null){
					rs.close();
				}
				if (pstmt != null){
					pstmt.close();
				}
				if(cnx!=null){
					cnx.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			throw new BusinessException();
		} 
		return utilisateur;
	}
	
	@Override
	public List<Utilisateur> selectAll() throws BusinessException {
		
		List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SQL_SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			
			Utilisateur utilisateur = null;
			
			while (rs.next()) {
				if (rs.getInt("no_utilisateur") != utilisateur.getNo_utilisateur()) {
					
					utilisateur = new Utilisateur(rs.getInt("no_utilisateur"),
							rs.getString("pseudo"),
							rs.getString("nom"),
							rs.getString("prenom"),
							rs.getString("email"),
							rs.getString("telephone"),
							rs.getString("rue"),
							rs.getString("code_postal"),
							rs.getString("ville"),
							rs.getString("mot_de_passe"),
							rs.getInt("credit"),
							rs.getBoolean("administrateur"));
				}
				utilisateurs.add(utilisateur);
				try {
					if (rs != null){
						rs.close();
					}
					if (pstmt != null){
						pstmt.close();
					}
					if(cnx!=null){
						cnx.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}catch (SQLException e) {
			throw new BusinessException();
		} 
		return utilisateurs;
	}
	
	@Override
	public void insert(Utilisateur utilisateur) throws BusinessException {
		
		if(utilisateur == null)
		{
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_NULL);
			throw businessException;
		}
		
		try(Connection cnx = ConnectionProvider.getConnection()) {
			try {
				cnx.setAutoCommit(false);
				PreparedStatement pstmt = cnx.prepareStatement(SQL_INSERT, 
						PreparedStatement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, utilisateur.getPseudo());
				pstmt.setString(2, utilisateur.getNom());
				pstmt.setString(3, utilisateur.getPrenom());
				pstmt.setString(4, utilisateur.getEmail());
				pstmt.setString(5, utilisateur.getTelephone());
				pstmt.setString(6, utilisateur.getRue());
				pstmt.setString(7, utilisateur.getCode_postal());
				pstmt.setString(8, utilisateur.getVille());
				pstmt.setString(9, utilisateur.getMot_de_passe());
				pstmt.setInt(10, utilisateur.getCredit());
				pstmt.setBoolean(11, utilisateur.isAdministrateur());
				
				pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();
				if(rs.next()) {
					utilisateur.setNo_utilisateur(rs.getInt(1));
				}
				rs.close();
				pstmt.close();
				cnx.commit();
			} catch (Exception e) {
				e.printStackTrace();
				cnx.rollback();
				throw e;
			}	
		} catch(Exception e) {
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			throw businessException;
		}	
	}

	@Override
	public void update(Utilisateur data) throws BusinessException {

		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SQL_UPDATE);
		    pstmt.setString(1, data.getPseudo());
		    pstmt.setString(2, data.getEmail());
		    pstmt.setString(3, data.getTelephone());
		    pstmt.setString(4, data.getRue());
		    pstmt.setString(5, data.getCode_postal());
		    pstmt.setString(6, data.getVille());
		    pstmt.setString(7, data.getMot_de_passe());
		    pstmt.setInt(8, data.getNo_utilisateur());

		    
		    pstmt.executeUpdate();
		    try {
		        if (pstmt != null){
		            pstmt.close();
		        }
		        if(cnx !=null){
		            cnx.close();
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		} catch (SQLException e) {
		    throw new BusinessException();
		} 
	}

	@Override
	public void delete(int no_utilisateur) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SQL_DELETE);
		    pstmt.setInt(1, no_utilisateur);
		    pstmt.executeUpdate();
		    try {
		        if (pstmt != null){
		            pstmt.close();
		        }
		        if(cnx!=null){
		            cnx.close();
		        }
		    } catch (SQLException e) {
		        throw new BusinessException();
		    }
		} catch (SQLException e) {
		    throw new BusinessException();
		} 
	}
	
	// Vérifie dans la BDD l'existence de l'utilisateur qui rentre ses logins. S'il existe, renvoie le no_utiliateur. Sinon, renvoie -1
	
	public int selectLog(String id, String password) throws BusinessException {
		try (Connection cnx = ConnectionProvider.getConnection()) {
			PreparedStatement pstmt = cnx.prepareStatement(SQL_SELECTID);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			
			ResultSet rs = pstmt.executeQuery();
			int id_user;
			
			if (rs.next()) {
				id_user = rs.getInt("no_utilisateur");
				
			} else {
				id_user = -1;
			}
			return id_user;
		} catch (SQLException e) {
			throw new BusinessException();
		}
			
	}


	// Vérifie dans la BDD l'existence de l'utilisateur qui rentre ses logins. S'il existe, renvoie le no_utiliateur. Sinon, renvoie -1
		
		public int selectMail(String id, String password) throws BusinessException {
			try (Connection cnx = ConnectionProvider.getConnection()) {
				PreparedStatement pstmt = cnx.prepareStatement(SQL_SELECTMAIL);
				pstmt.setString(1, id);
				pstmt.setString(2, password);
				
				ResultSet rs = pstmt.executeQuery();
				int id_user;
				
				if (rs.next()) {
					id_user = rs.getInt("no_utilisateur");
					
				} else {
					id_user = -1;
				}
				return id_user;
			} catch (SQLException e) {
				throw new BusinessException();
			}
				
		}
		
		public String selectParameter(int userID, String param) throws BusinessException {
			try (Connection cnx = ConnectionProvider.getConnection()) {
				PreparedStatement pstmt = cnx.prepareStatement(SQL_SELECT_ONE_PARAMETER);
				pstmt.setInt(1, userID);
				
				ResultSet rs = pstmt.executeQuery();
				String result = null;
				String pseudo = null;
				String nom = null;
				String prenom = null;
				String mail = null;
				String telephone = null;
				String rue = null;
				String codepostal = null;
				String ville = null;
				String password = null;
				
				if (rs.next()) {
					pseudo = rs.getString("pseudo");
					nom = rs.getString("nom");
					prenom = rs.getString("prenom");
					mail = rs.getString("email");
					telephone = rs.getString("telephone");
					rue = rs.getString("rue");
					codepostal = rs.getString("code_postal");
					ville = rs.getString("ville");
					password = rs.getString("mot_de_passe");
					
					switch (param) {
						case "pseudo" : result = pseudo; break;
						case "nom" : result = nom; break;
						case "prenom" : result = prenom; break;
						case "mail" : result = mail; break;
						case "telephone" : result = telephone; break;
						case "rue" : result = rue; break;
						case "codepostal" : result = codepostal; break;
						case "ville" : result = ville; break;
						case "password" : result = password; break;
					}
				}
				
				try {
					if (rs != null){
						rs.close();
					}
					if (pstmt != null){
						pstmt.close();
					}
					if(cnx!=null){
						cnx.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				return result;
				
			} catch (SQLException e) {
				throw new BusinessException();
			}
		}

		@Override
		public int selectCredit(int userID) throws BusinessException {
			try (Connection cnx = ConnectionProvider.getConnection()) {
				PreparedStatement pstmt = cnx.prepareStatement(SQL_SELECT_CREDIT);
				pstmt.setInt(1, userID);
				
				ResultSet rs = pstmt.executeQuery();
				
				int credit = 0;
				if (rs.next()) {
					credit = rs.getInt("credit");
				}
				try {
					if (rs != null){
						rs.close();
					}
					if (pstmt != null){
						pstmt.close();
					}
					if(cnx!=null){
						cnx.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				return credit;
				
			} catch (SQLException e) {
				throw new BusinessException();
			}
		}	
}




