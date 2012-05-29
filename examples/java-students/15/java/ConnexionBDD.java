import java.io.*;
import java.sql.*;
import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Calendar;

/**
 * Une connexion à la base de données
 **/
public class ConnexionBDD {
    private Connection co; //la connexion à la base

    private boolean err_print = false;

    /**
     * Types autorisés pour les personnes
     **/
    private static HashSet<String> typePersonnes = new HashSet<String>();

    static {
        typePersonnes.add("client");
        typePersonnes.add("douane");
        typePersonnes.add("emballeur");
        typePersonnes.add("gerant");
        typePersonnes.add("transporteur");
    }

    /**
     * Crée une nouvelle connexion à une base de données locale donnée
     * @param bd nom de la base de données
     * @param utilisateur nom de l'utilisateur
     * @param mdp mot de passe
     **/
    public ConnexionBDD(String bd, String utilisateur, String mdp) throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        co = DriverManager.getConnection("jdbc:postgresql://localhost/"+bd,utilisateur,mdp); 
    }

    /**
     * Crée une nouvelle connexion à une base de données locale donnée
     * @param bd nom de la base de données
     * @param utilisateur nom de l'utilisateur
     * @param mdp mot de passe
     * @param err_print <code>true</code> si l'affichage des erreurs est activé
     **/
    public ConnexionBDD(String bd, String utilisateur, String mdp, boolean err_print)
            throws SQLException, ClassNotFoundException {
        this(bd, utilisateur, mdp);
        this.err_print = err_print;
    }

    /**
     * Crée une nouvelle connexion à la base de données 'bd6'
     * @param utilisateur nom de l'utilisateur
     * @param mdp mot de passe
     **/
    public ConnexionBDD(String utilisateur, String mdp) throws SQLException, ClassNotFoundException {
        this("bd6", utilisateur, mdp);
    }

    /**
     * Crée une nouvelle connexion à la base de données 'bd6'
     * @param utilisateur nom de l'utilisateur
     * @param mdp mot de passe
     * @param err_print <code>true</code> si l'affichage des erreurs est activé
     **/
    public ConnexionBDD(String utilisateur, String mdp, boolean err_print)
            throws SQLException, ClassNotFoundException {
        this("bd6", utilisateur, mdp);
        this.err_print = err_print;
    }

    /**
     * Teste si la connexion de l'utilisateur donné est acceptée
     * @param login login de l'utilisateur
     * @param mdp mot de passe de l'utilisateur
     * @return le type de l'utilisateur si le login/mdp est mauvais
     **/
    public String connecteUtilisateur(String login, String mdp) {
        try {
            String q = "SELECT type_personne,mot_de_passe FROM personne WHERE login=?;";
            PreparedStatement ps = co.prepareStatement(q);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();

            if (!(rs.next() && (rs.getString("mot_de_passe").equals(mdp)))) {
                return null;
            }

            return rs.getString("type_personne");

        } catch (SQLException e) {
            p_err(e.getMessage());
            return null;
        }
    }

    // === Changements dans la BDD === //

    /**
     * Change le prix d'un produit du catalogue
     * @param ref référence du produit
     * @param nouveauPrix nouveau prix du produit
     * @return <code>true</code> si tout s'est bien passé, <code>false</code> si la
     * référence est mauvaise, et/ou le prix négatif.
     **/
    public boolean changePrix(String ref, float nouveauPrix) {
        if (nouveauPrix < 0) {
            p_err("Nouveau prix négatif.");
            return false;
        }

        try {
            PreparedStatement ps = co.prepareStatement("UPDATE catalogue SET prix=? WHERE ref=?;");
            ps.setFloat(1, nouveauPrix);
            ps.setString(2, ref);
            return (ps.executeUpdate() > 0);

        } catch (SQLException e) {
            p_err(e.getMessage());
            return false;
        }
    }

    /**
     * Change la quantité d'un produit dans le catalogue
     * @param ref référence du produit
     * @param nouvelleQuantite nouvelle quantité du produit dans le catalogue
     * @return true si le changement a été fait avec succès, false sinon (ou si
     * la nouvelle quantité est négative)
     **/
    public boolean changerQuantiteProduit(String ref, int nouvelleQuantite) {
        if (nouvelleQuantite < 0) {
            p_err("Nouvelle quantité négative.");
            return false;
        }

        String q = "UPDATE catalogue SET quantite_restante=? WHERE ref=?;";
        try {
            PreparedStatement ps = co.prepareStatement(q);
            ps.setInt(1, nouvelleQuantite);
            ps.setString(2, ref);
            int result = ps.executeUpdate();
            return (result == 1);

        } catch (SQLException e) {
            p_err(e.getMessage());
            return false;
        }
    }

    /**
     * Change le login d'un client
     * @param ancienLogin l'ancien login
     * @param nouveauLogin le nouveau login
     * @return <code>true</code> si tout s'est bien passé, <code>false</code> sinon
     **/
    public boolean changerLogin(String ancienLogin, String nouveauLogin) {

        if (ancienLogin == null || nouveauLogin == null) {
            return false;
        } else if (ancienLogin.equals(nouveauLogin)) {
            return true;
        }

        String q = "UPDATE personne SET login=? WHERE login=? AND type_personne='client';";

        try {
            PreparedStatement ps = co.prepareStatement(q);
            ps.setString(1, nouveauLogin);
            ps.setString(2, ancienLogin);

            int result = ps.executeUpdate();

            return (result == 1);
        }
        catch (SQLException e) {
            p_err(e.getMessage());
        }

        return false;
    }

    /**
     * Change le mot de passe d'un client
     * @param login le login du client
     * @param ancienMdp l'ancien mot de passe
     * @param nouveauMdp le nouveau mot de passe
     * @return <code>true</code> si tout s'est bien passé, <code>false</code> sinon
     **/
    public boolean changerMdp(String login, String ancienMdp, String nouveauMdp) {

        if (login == null || ancienMdp == null || nouveauMdp == null) {
            p_err("Login ou Mdp null.");
            return false;
        } else if (ancienMdp.equals(nouveauMdp)) {
            return true;
        }

        String q = "UPDATE personne SET mot_de_passe=? WHERE login=?";
        q += " AND mot_de_passe=? AND type_personne='client';";

        try {
            PreparedStatement ps = co.prepareStatement(q);
            ps.setString(1, nouveauMdp);
            ps.setString(2, login);
            ps.setString(3, ancienMdp);

            int result = ps.executeUpdate();

            return (result == 1);
        }
        catch (SQLException e) {
            p_err(e.getMessage());
        }

        return false;
    }

    // === Listes === //

    /**
     * Liste tous les employés
     * @return une liste de <code>HashMap</code> avec une correspondance entre nom de
     * colonnes et valeurs
     **/
    public LinkedList<HashMap<String,Object>> listeEmployes() {
        return listeEmployes("tous");
    }

    /**
     * Liste les employés selon leur type
     * @param type le type des employés ("tous", "transporteur", "emballeur")
     * @return une liste de <code>HashMap</code> avec une correspondance entre nom de
     * colonnes et valeurs
     **/
    public LinkedList<HashMap<String,Object>> listeEmployes(String type) {

        if (type == null) {
            p_err("type d'employé vide.");
            return null;
        }

        PreparedStatement ps = null;
        String q = "SELECT prenom,nom,login,type_personne FROM personne";

        if (type.equals("tous")) {
            q += " WHERE type_personne='transporteur'";
            q += " OR type_personne='emballeur'";
        } else {
            q += " WHERE type_personne=?::type_p";
        }
        q += " ORDER BY nom";

        ResultSet rs = null;

        try {
            ps = co.prepareStatement(q+";");
            if (!type.equals("tous")) {
                ps.setString(1, type);
            }
            rs = ps.executeQuery();
        } catch (SQLException e) {
            p_err(e.getMessage());
            return null;
        }

        LinkedList<HashMap<String,Object>> liste
            = new LinkedList<HashMap<String,Object>>();

        try {
            while(rs.next()) {
                HashMap<String,Object> hm = new HashMap<String,Object>();

                hm.put("prénom", rs.getString(1));
                hm.put("nom", rs.getString(2));
                hm.put("login", rs.getString(3));
                hm.put("type", rs.getString(4));

                liste.add(hm);
            }
        } catch (SQLException e) {
            p_err(e.getMessage());
            return null;
        }

        return liste;
    }

    /**
     * Liste les clients dans l'ordre de leurs dépenses
     * @return une liste de <code>HashMap</code> avec une correspondance entre nom de
     * colonnes et valeurs
     **/
    public LinkedList<HashMap<String,Object>> listeClientsPlusDepensies() {

        PreparedStatement ps = null;
        String q = "SELECT SUM(frais) AS fs,SUM(prix) as px FROM commande ";
        q += "WHERE id_client=? GROUP BY id_client;"; 

        ResultSet rs = null;
        LinkedList<HashMap<String,Object>> liste = listeClients(true); 
        int size = liste.size();
        try{
            for (int i = 0; i < size; i++){
                HashMap<String,Object> hm = liste.get(i);
                Object login = hm.get("login");
                ps = co.prepareStatement(q);
                ps.setString(1,(String) login);
                rs = ps.executeQuery();
                rs.next();
                int depense = rs.getInt("fs") + rs.getInt("px");
                hm.put("Total dépensé",new Integer(depense));
            }
        }catch(SQLException e) {
            p_err(e.getMessage());
        }
        return liste;
    }

    /**
     * Liste les clients
     * @return une liste de <code>HashMap</code> avec une correspondance entre nom de
     * colonnes et valeurs
     **/
    public LinkedList<HashMap<String,Object>> listeClients() {
        return listeClients(false);
    }

    /**
     * Liste les clients
     * @param etendue précise si la liste doit être étendue, par exemple en ajoutant
     * le nombre de commandes faites par ce client, le nombre de produits achetés,
     * le total dépensé, etc.
     * @return une liste de <code>HashMap</code> avec une correspondance entre nom de
     * colonnes et valeurs
     **/
    public LinkedList<HashMap<String,Object>> listeClients(boolean etendue) {

        PreparedStatement ps = null;
        String q = "SELECT prenom,nom,login,adresse,ville,code_postal,pays";
        q += ",telephone FROM personne NATURAL JOIN client";
        q += " WHERE personne.login=client.id ORDER BY nom;";

        ResultSet rs = null;
        ResultSet rs2 = null;

        try {
            ps = co.prepareStatement(q);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            p_err(e.getMessage());
            return null;
        }

        LinkedList<HashMap<String,Object>> liste
            = new LinkedList<HashMap<String,Object>>();

        try {
            while(rs.next()) {
                HashMap<String,Object> hm = new HashMap<String,Object>();

                hm.put("prénom", rs.getString(1));
                hm.put("nom", rs.getString(2));
                String login = rs.getString(3);
                hm.put("login", login);
                hm.put("adresse", rs.getString(4));
                hm.put("ville", rs.getString(5));
                hm.put("code postal", rs.getString(6));
                hm.put("pays", rs.getString(7));
                hm.put("téléphone", rs.getString(8));

                if (etendue) {
                    q = "SELECT COUNT(*) FROM commande WHERE id_client=?;";
                    ps = co.prepareStatement(q);
                    ps.setString(1, login);
                    rs2 = ps.executeQuery();
                    if (rs2.next()) {
                        hm.put("commandes", rs2.getInt(1));
                    }

                    q = "SELECT COUNT(*) FROM commande_produits WHERE";
                    q += " id_commande = ANY (SELECT id FROM commande WHERE";
                    q += " id_client=?);";
                    ps = co.prepareStatement(q);
                    ps.setString(1, login);
                    rs2 = ps.executeQuery();
                    if (!rs2.next()) {
                        hm.put("produits", rs2.getInt(1));
                    }
                }

                liste.add(hm);
            }
        } catch (SQLException e) {
            p_err(e.getMessage());
            return null;
        }

        return liste;
    }

    /**
     * Liste les produits
     * @return une liste de <code>HashMap</code> avec une correspondance entre nom de
     * colonnes et valeurs
     **/
    public LinkedList<HashMap<String,Object>> listeProduits() {
        return listeProduits(-1);
    }

    /**
     * Liste les produits disponibles
     * @return une liste de <code>HashMap</code> avec une correspondance entre nom de
     * colonnes et valeurs
     **/
    public LinkedList<HashMap<String,Object>> listeProduitsRestants() {
        return listeProduits(1);
    }

    /**
     * Liste les produits
     * @param quantiteMin quantité minimale des produits (ignorée si négative)
     * @return une liste de <code>HashMap</code> avec une correspondance entre nom de
     * colonnes et valeurs
     **/
    public LinkedList<HashMap<String,Object>> listeProduits(int quantiteMin) {

        PreparedStatement ps = null;
        String q = "SELECT ref,description,qualifiant,prix,poids,";
        q += "quantite_restante FROM catalogue";

        if (quantiteMin >= 0) {
            q += " WHERE quantite_restante>=?";
        }

        ResultSet rs = null;

        try {
            ps = co.prepareStatement(q+";");
            if (quantiteMin >= 0) {
                ps.setInt(1, quantiteMin);
            }
            rs = ps.executeQuery();
        } catch (SQLException e) {
p_err(e.getMessage());
            return null;
        }

        LinkedList<HashMap<String,Object>> liste
            = new LinkedList<HashMap<String,Object>>();

        try {
            while(rs.next()) {
                HashMap<String,Object> hm = new HashMap<String,Object>();

                hm.put("référence", rs.getString(1));
                hm.put("description", rs.getString(2));
                hm.put("qualifiant", rs.getString(3));
                hm.put("prix", rs.getFloat(4));
                hm.put("poids", rs.getFloat(5));
                hm.put("quantité restante", rs.getFloat(6));

                liste.add(hm);
            }
        } catch (SQLException e) {
p_err(e.getMessage());
            return null;
        }

        return liste;
    }

    /**
     * Liste les identifiants des colis d'une palette
     * @param id identifiant de la palette
     **/
    public LinkedList<Integer> listePalette(int id) {
        if (id <= 0) {
            return null;
        }

        try {
            LinkedList<Integer> liste = new LinkedList<Integer>();

            PreparedStatement ps
                = co.prepareStatement("SELECT id_colis FROM palette_colis WHERE id_palette=?;");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                liste.push(new Integer(rs.getInt("id_colis")));
            }
            return liste;
        }
        catch (SQLException e) {
            p_err(e.getMessage());
        }

        return null;
    }

    /**
     * Retourne une liste de commandes que le client a
     * fait.
     * @param login login du client
     * @return
     **/
    public LinkedList<HashMap<String,Object>> listeCommandesClient(String login) {
        return listeCommandesClient(login, false);
    }

    /**
     * Retourne une liste de commandes que le client a
     * fait.
     * @param login login du client
     * @param pretty <code>true</code> si le retour doit être préparé
     * pour l'affichage
     * @return
     **/
    public LinkedList<HashMap<String,Object>> listeCommandesClient(String login, boolean pretty) {
        if (login == null || login.length() == 0) {
            return null;
        }

        String q = "SELECT id FROM commande WHERE id_client=?";
        LinkedList<HashMap<String,Object>> liste
                = new LinkedList<HashMap<String,Object>>();
        try {
            PreparedStatement ps = co.prepareStatement(q);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            do {
                HashMap<String,Object> cmd = infosCommande(rs.getInt("id"), pretty);
                if (cmd != null) {
                    liste.push(cmd);
                }
            } while (rs.next());
            return liste;
        }
        catch (SQLException e) {
            p_err(e.getMessage());
        }
        return null;
    }

    /**
     * Retourne une liste des colis associés à une commande
     * @param id identifiant d'une commande
     * @return 
     **/
    public LinkedList<HashMap<String,Object>> listerColisParCommande(int id) {
        if (id <= 0) {
            p_err("Id de commande négatif ou nul.");
            return null;
        }

        String q = "SELECT id FROM colis WHERE id_commande=?;";
        try {
            PreparedStatement ps = co.prepareStatement(q);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            LinkedList<HashMap<String,Object>> liste
                = new LinkedList<HashMap<String,Object>>();

            while (rs.next()) {
                liste.push(infosColis(rs.getInt("id")));
            }
            return liste;
        }
        catch (SQLException e) {
            p_err(e.getMessage());
        }
        return null;
    }

    /**
     * Retourne une liste des produits associés à une commande
     * @param id identifiant d'une commande
     * @return 
     **/
    public LinkedList<HashMap<String,Object>> listerProduitsParCommande(int id) {
        if (id <= 0) {
            p_err("Id de commande négatif ou nul.");
            return null;
        }

        String q = "SELECT ref_produit,quantite FROM commande_produits WHERE id_commande=?;";
        try {
            PreparedStatement ps = co.prepareStatement(q);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            LinkedList<HashMap<String,Object>> liste
                = new LinkedList<HashMap<String,Object>>();

            while (rs.next()) {
                HashMap<String,Object> prod = infosProduit(rs.getString("ref_produit"));
                prod.put("quantité demandée", rs.getInt("quantite"));
            }
            return liste;
        }
        catch (SQLException e) {
            p_err(e.getMessage());
        }
        return null;
    }

    // === Informations === //

    /**
     * Retourne des informations sur l'utilisateur (nom, prénom, type)
     * @param login login de l'utilisateur
     **/
    public HashMap<String,String> infosPersonne(String login) {
        if (login == null) {
            return null;
        }

        String q = "SELECT nom,prenom,type_personne FROM personne";
        q += " WHERE login=? LIMIT 1;";

        try {
            PreparedStatement ps = co.prepareStatement(q);
            ps.setString(1, login);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }

            HashMap<String,String> hm = new HashMap<String,String>();

            hm.put("nom", rs.getString("nom"));
            hm.put("prénom", rs.getString("prenom"));
            hm.put("type personne", rs.getString("type_personne"));

            return hm;

        } catch (SQLException e) {
            p_err(e.getMessage());
            return null;
        }
    }

    /**
     * Retourne des informations sur le colis
     * @param id identifiant du colis
     **/
    public HashMap<String,Object> infosColis(int id) {
        if (id <= 0) {
            return null;
        }
        String q = "SELECT id_commande,date_emballage,date_expedie,date_livraison";
        q += ",etat,qualifiant FROM colis WHERE id=?;";

        try {
            PreparedStatement ps = co.prepareStatement(q);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }

            HashMap<String,Object> c = new HashMap<String,Object>();

            c.put("date d'emballage", rs.getDate("date_emballage"));
            c.put("date d'expédition", rs.getDate("date_expedie"));
            c.put("date de livraison", rs.getDate("date_livraison"));
            c.put("état", rs.getString("etat"));
            c.put("qualifiant", rs.getString("qualifiant"));
            c.put("id commande", rs.getInt("id_commande"));
            c.put("id", id);

            q = "SELECT ref_produit,quantite FROM colis_produits WHERE id_colis=?";
            ps = co.prepareStatement(q);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (!rs.next()) { // pas de produits dans le colis
                return null;
            }

            c.put("quantité contenue", 0);

            do {
                if (!c.containsKey("référence produit")) {
                    c.put("référence produit", rs.getString("ref_produit"));
                }
                c.put("quantité contenue",
                        ((Integer)c.get("quantité contenue")).intValue()+1);
            } while (rs.next());

            return c;
        }
        catch (SQLException e) {
            p_err(e.getMessage());
        }
        return null;
    }

    /**
     * Retourne des informations sur le produit
     * @param ref référence du produit
     **/
    public HashMap<String,Object> infosProduit(String ref) {
        String q = "SELECT description,qualifiant,prix,poids,quantite_restante,";
        q += "quantite_par_carton,cartons_par_palette FROM catalogue WHERE ";
        q += "ref=?;";

        try {
            PreparedStatement ps = co.prepareStatement(q);
            ps.setString(1, ref);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }

            HashMap<String,Object> hm = new HashMap<String,Object>();

            do {
                hm.put("référence", ref);
                hm.put("description", rs.getString("description"));
                hm.put("qualifiant", rs.getString("qualifiant"));
                hm.put("prix", rs.getInt("prix"));
                hm.put("poids", rs.getInt("poids"));
                hm.put("quantité restante", rs.getInt("quantite_restante"));
                hm.put("quantité par carton", rs.getInt("quantite_par_carton"));
                hm.put("cartons par palette", rs.getInt("cartons_par_palette"));
            } while (rs.next());
            return hm;
        }
        catch (SQLException e) {
            p_err(e.getMessage());
        }
        return null;
    }

    /**
     * Retourne des informations sur le produit dans l'ordre des plus vendu
     **/
    public LinkedList<HashMap<String,Object>> produitPlusVendu() {
        String q = "SELECT ref_produit ,SUM(quantite) AS qt FROM commande_produits ";
        q += "GROUP BY ref_produit ORDER BY qt DESC;";

        try{
            PreparedStatement ps = co.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            LinkedList<HashMap<String,Object>> liste
                = new LinkedList<HashMap<String,Object>>();

            HashMap<String,Object> hm;

            do {
                hm = infosProduit(rs.getString("ref_produit"));  
                hm.put("quantite", rs.getString("qt"));

                liste.add(hm);

            } while (rs.next());
            return liste;

        }catch(SQLException e){}
        return null;
    }

    /**
     * Retourne des informations sur la commande (date de commande, date
     * de livraison prévue, produits, colis concernés, login du client)
     * @param id l'identifiant de la commande
     **/
    public HashMap<String,Object> infosCommande(int id) {
        return infosCommande(id, false);
    }

    /**
     * Retourne des informations sur la commande (date de commande, date
     * de livraison prévue, produits, colis concernés, login du client)
     * @param id l'identifiant de la commande
     * @param pretty <code>true</code> si le retour doit être préparé
     * pour l'affichage
     **/
    public HashMap<String,Object> infosCommande(int id, boolean pretty) {

        if (id <= 0) {
            return null;
        }

        String q = "SELECT id_client,date_livree,date_commande,date_prevue,frais";
        q += ",prix FROM commande WHERE id=? LIMIT 1;";

        try {
            PreparedStatement ps = co.prepareStatement(q);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }

            HashMap<String,Object> retour = new HashMap<String,Object>();

            retour.put("id", id);
            if (!pretty) {
                retour.put("date de commande", rs.getDate("date_commande"));
                retour.put("date de livraison prévue", rs.getDate("date_prevue"));
                retour.put("date de livraison", rs.getDate("date_livree"));
            } else {
                retour.put("date de commande", rs.getString("date_commande"));
                
                String date_prevue = rs.getString("date_prevue");
                String date_livraison = rs.getString("date_livree");

                if (date_livraison.equals("null")) {
                    retour.put("date de livraison prévue", date_prevue);
                } else {
                    retour.put("date de livraison", date_livraison);
                }
            }
            retour.put("frais", rs.getFloat("frais"));
            retour.put("prix", rs.getFloat("prix"));
            retour.put("login client", rs.getString("id_client"));

            return retour;
        }
        catch (SQLException e) {
            p_err(e.getMessage());
        }

        return null;
    }

    // === Créations/Ajouts === //

    /**
     * Crée une nouvelle personne
     * @param prenom Prénom de la personne
     * @param nom Nom de la personne
     * @param login Login de la personne (doit être unique)
     * @param mdp Mot de passe de la personne
     * @param type Type de la personne
     * @return true si l'insertion s'est déroulée avec succès, false sinon ou
     * si le type n'est pas reconnu.
     *
     * Note: si la personne est un client ou une douane, il faut aussi faire un
     * ajout dans les tables "client" ou "douane".
     **/
    public boolean nouvellePersonne(String prenom, String nom,
            String login, String mdp, String type) {

        if (!typePersonnes.contains(type)) {
            return false;
        }

        String q = "INSERT INTO personne VALUES(?,?,?,?,?::type_p);";

        try {
            PreparedStatement ps = co.prepareStatement(q);
            ps.setString(1, prenom);
            ps.setString(2, nom);
            ps.setString(3, login);
            ps.setString(4, mdp);
            ps.setString(5, type);
            int result = ps.executeUpdate();
            return (result > 0);
        }
        catch (SQLException e) {
p_err(e.getMessage());
            return false;
        }
    }

    /**
     * Crée une nouvelle commande. La date de la commande est considérée
     * comme étant maintenant.
     * @param client login du client
     * @param date_prevue date de livraison prévue
     * @param produits mapping entre les références des produits et les
     * quantités commandées
     * @return l'identifiant de la commande, ou -1 si il y a eu une erreur
     **/
    public int nouvelleCommande(String client, Calendar date_prevue,
            HashMap<String, Integer> produits) {

        return nouvelleCommande(client, Calendar.getInstance(),
                date_prevue, produits);
    }

    /**
     * Crée une nouvelle commande
     * @param client login du client
     * @param date_commande date de la commande
     * @param date_prevue date de livraison prévue
     * @param produits mapping entre les références des produits et les
     * quantités commandées
     * @return l'identifiant de la commande, ou -1 si il y a eu une erreur
     **/
    public int nouvelleCommande(String client, Calendar date_commande,
            Calendar date_prevue, HashMap<String, Integer> produits) {

        if (produits.size() == 0) {
            return -1;
        }

        String q = "INSERT INTO commande (id_client,date_commande,date_prevue";
        q += ",frais) VALUES(?,?,?,?);";

        // Calendar -> Date -> long (millisecondes)
        long dc = date_commande.getTime().getTime();
        long dp = date_prevue.getTime().getTime();

        if (dp < dc) {
            return -1;
        }

        /*
           Frais de commande:
           a = nombre d'heures entre la date de commande et la date prévue
           frais = max( 100, 2*e^(11-log10(1000*a)) )
           (en gros, =100 pour une date éloignée, >100 pour une date plus
           proche)
           */
        long a = (dc-dp)/3600;
        float frais = (float)(Math.max(100, 2*Math.exp(12-Math.log10(a))));

        // la négation permet de prendre la valeur 'NaN'
        if (!(frais >= 100))
            frais = 100;

        try {
            PreparedStatement ps = co.prepareStatement(q);
            ps.setString(1, client);
            ps.setDate(2, new Date(dc));
            ps.setDate(3, new Date(dp));
            ps.setFloat(4, frais);
            int result = ps.executeUpdate();

            if (result == 0) {
                return -1;
            }

            q = "SELECT last_value FROM commande_id_seq;";
            ps = co.prepareStatement(q);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return -1;
            }

            int reserve, i, quantite, id_cmd = rs.getInt(1);

            String checkCatalogue = "SELECT quantite_restante FROM catalogue";
            checkCatalogue += " WHERE ref=? LIMIT 1;";
            PreparedStatement ps2;

            q = "INSERT INTO commande_produits VALUES (?,?,?)";

            // pour chaque produit, …
            for (i=1; i<produits.size(); i++) {
                q += ", (?,?,?)";
            }

            ps = co.prepareStatement(q+";");

            // … on ajoute 3 valeurs: id de la commande
            //                        ref du produit
            //                        quantité du produit
            i = 1;
            for (String ref : produits.keySet()) {

                ps2 = co.prepareStatement(checkCatalogue);
                ps2.setString(1, ref);
                rs = ps2.executeQuery();
                if (!rs.next()) {
                    return -1;
                }

                reserve = rs.getInt("quantite_restante");
                quantite = produits.get(ref);

                ps.setInt(i, id_cmd);
                ps.setString(i+1, ref);

                if (   (quantite <= 0)
                        || (reserve < quantite)
                        || (!changerQuantiteProduit(ref, reserve-quantite))) {
                    return -1;
                        }

                ps.setInt(i+2, quantite);

                i += 3;
            }

            result = ps.executeUpdate();

            if (result == produits.size()) {
                return id_cmd;
            }
        }
        catch (SQLException e) {
            p_err(e.getMessage());
        }
        return -1;
    }

    /**
     * Crée un nouveau colis, emballé aujoud'hui
     * @param id_commande Identifiant de la commande correspondante
     * @param produits Produits contenus dans ce colis
     * @return identifiant du produit, ou -1 s'il y a eu un problème
     **/
    public int nouveauColis(int id_commande, HashMap<String,Integer> produits) {
        return nouveauColis(null, id_commande, produits);
    }

    /**
     * Crée un nouveau colis
     * @param date_emballage Date d'emballage du produit
     * @param id_commande Identifiant de la commande correspondante
     * @param produits Produits contenus dans ce colis
     * @return identifiant du produit, ou -1 s'il y a eu un problème
     **/
    public int nouveauColis(Calendar date_emballage, int id_commande,
            HashMap<String,Integer> produits) {

        if (id_commande <= 0 || produits == null || produits.isEmpty()) {
            return -1;
        }

        // Calendar -> Date -> millisecondes
        long de = (date_emballage == null) ? 0 : date_emballage.getTime().getTime();

        String q = "INSERT INTO colis (id_commande,date_emballage)";
        q += " VALUES(?,?);";

        if (date_emballage == null) {
            q = "INSERT INTO colis (id_commande) VALUES(?);";
        }

        try {
            // insere le nouveau colis
            PreparedStatement ps = co.prepareStatement(q);
            ps.setInt(1, id_commande);
            if (date_emballage != null) {
                ps.setDate(2, new Date(de));
            }

            int result = ps.executeUpdate();

            if (result != 1) {
                return -1;
            }

            // recupere son identifiant
            q = "SELECT last_value FROM colis_id_seq;";
            ps = co.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return -1;
            }

            int id_colis = rs.getInt("last_value");

            // insere les produits
            q = "INSERT INTO colis_produits VALUES(?,?,?);";

            for (String ref: produits.keySet()) {
                ps = co.prepareStatement(q);
                ps.setInt(1, id_colis);
                ps.setString(2, ref);
                ps.setInt(3, produits.get(ref));

                result = ps.executeUpdate();

                if (result == -1) {
                    return -1;
                }
            }

            return id_colis;
        }
        catch (SQLException e) {
            p_err(e.getMessage());
        }

        return -1;
    }

    /**
     * Crée une nouvelle palette
     * @param id_colis liste des identifiants des colis qui sont sur
     * cette palette
     * @return identifiant de la palette, ou -1 s'il y a eu un problème
     **/
    public int nouvellePalette(LinkedList<Integer> id_colis) {

        if (id_colis == null || id_colis.size() == 0) {
            return -1;
        }

        try {
            PreparedStatement ps
                = co.prepareStatement("INSERT INTO palette DEFAULT VALUES;");
            int result = ps.executeUpdate();
            if (result != 1) {
                return -1;
            }
            ps = co.prepareStatement("SELECT last_value FROM palette_id_seq;");
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return -1;
            }
            int id_palette = rs.getInt("last_value");

            String q = "INSERT INTO palette_colis VALUES ("+id_palette+",?)";

            for (int i=1; i<id_colis.size(); i++) {
                q += ",("+id_palette+",?)";
            }

            ps = co.prepareStatement(q+";");
            for (int i=0; i<id_colis.size(); i++) {
                ps.setInt(i+1, id_colis.get(i).intValue());
            }
            result = ps.executeUpdate();

            return (result > 0) ? id_palette : -1;
        }
        catch (SQLException e) {
            p_err(e.getMessage());
        }
        return -1;
    }

    /**
     * Crée un nouveau container et retourne son identifiant
     * @param id_palettes identifiants des palettes
     * @param login_emballeur login de l'emballeur
     * @return identifiant du nouveau container ou -1 s'il y a
     * eu un problème
     **/
    public int nouveauContainer(LinkedList<Integer> id_palettes,
            String login_emballeur) {
        return nouveauContainer(id_palettes, login_emballeur, null);
    }

    /**
     * Crée un nouveau container et retourne son identifiant
     * @param id_palettes identifiants des palettes
     * @param login_emballeur login de l'emballeur
     * @param login_transporteur login du transporteur
     * @return identifiant du nouveau container ou -1 s'il y a
     * eu un problème
     **/
    public int nouveauContainer(LinkedList<Integer> id_palettes,
            String login_emballeur,
            String login_transporteur) {

        if (id_palettes == null || id_palettes.size() == 0) {
            return -1;
        }

        try {
            PreparedStatement ps
                = co.prepareStatement("INSERT INTO container (id_emballeur,id_transporteur) VALUES(?,?);");
            ps.setString(1, login_emballeur);
            ps.setString(2, login_transporteur);
            int result = ps.executeUpdate();
            if (result != 1) {
                return -1;
            }
            ps = co.prepareStatement("SELECT last_value FROM container_id_seq;");
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return -1;
            }
            int id_container = rs.getInt("last_value");

            String q = "INSERT INTO container_palettes VALUES ("+id_container+",?)";

            for (int i=1; i<id_palettes.size(); i++) {
                q += ",("+id_container+",?)";
            }

            ps = co.prepareStatement(q+";");
            for (int i=0; i<id_palettes.size(); i++) {
                ps.setInt(i+1, id_palettes.get(i).intValue());
            }
            result = ps.executeUpdate();

            return (result > 0) ? id_container : -1;
        }
        catch (SQLException e) {
            p_err(e.getMessage());
        }
        return -1;
    }

    // === Suppressions === //

    /**
     * Supprime une personne
     * @param login Login de la personne à supprimer
     * @return true si la suppression s'est déroulée avec succès
     *
     * Note: si la personne est un client ou une douane, il faut aussi faire
     * une suppression dans les tables "client" ou "douane".
     **/
    public boolean supprimePersonne(String login) {

        String q = "DELETE FROM personne WHERE login=?;";

        try {
            PreparedStatement ps = co.prepareStatement(q);
            ps.setString(1, login);
            int result = ps.executeUpdate();
            return (result > 0);
        }
        catch (SQLException e) {
p_err(e.getMessage());
            return false;
        }
    }

    /**
     * Marque un colis comme étant livré (si tous les autres colis de la
     * commande ont aussi étés livrés, tous les colis seront supprimés et
     * la date de livraison de la commande sera remplie).
     * @param id identifiant du colis
     * @return true si la livraison s'est déroulée avec succès
     **/
    public boolean livrerColis(int id) {

        if (id <= 0) {
            return false;
        }

        try {
            PreparedStatement ps
                = co.prepareStatement("UPDATE colis SET etat='livre'::etat_c WHERE id=?;");
            ps.setInt(1, id);

            int result = ps.executeUpdate();

            return (result == 1);
        }
        catch (SQLException e) {
            p_err(e.getMessage());
        }

        return false;
    }

    /**
     * Livre tous les colis d'une palette (et la supprime).
     * @param id identifiant de la palette
     * @return true si la livraison s'est déroulée avec succès
     **/
    public boolean livrerPalette(int id) {

        LinkedList<Integer> colis = listePalette(id);

        if (colis == null) {
            return false;
        }

        for (Integer i : colis) {
            if (!livrerColis(i.intValue())) {
                return false;
            }
        }

        // la suppression est faite par un trigger

        return true;
    }

    /**
     * Livre toutes les palettes d'un container (et le supprime)
     * @param id identifiant du container
     * @return true si la livraison s'est déroulée avec succès
     **/
    /*
    public boolean livrerContainer(int id) {

        LinkedList<Integer> palettes = listeContainer(id); // <- n'existe pas

        if (palettes == null) {
            return false;
        }

        for (Integer i : palettes) {
            if (!livrerPalette(i.intValue())) {
                return false;
            }
        }
        // la suppression est faite par un trigger

        return true;
    }
    */

    // === Debug/Affichage === //

    /**
     * Affiche sur STDERR si l'option <code>err_print</code> a été intialisée
     * à <code>true</code> lors de la création de l'objet
     **/
    private void p_err(Object... objs) {
        if (objs == null) {
            return;
        }
        if (this.err_print) {
            for (int i=0; i<objs.length; i++) {
                System.err.print(objs[i]);
            }
            System.err.println("");
        }
    }
}
