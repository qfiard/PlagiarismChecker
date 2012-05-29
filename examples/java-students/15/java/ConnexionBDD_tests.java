import java.io.*;
import java.sql.*;
import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Calendar;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConnexionBDD_tests {
    
    private static ConnexionBDD co = null;

    private static String loginOk = "SEGZE03368";
    private static String mdpOk = "IVW24HJB2RU";
    private static String refOk = "PW-403570-TGG-27";
    private static int cmdOk = 0; // initialisé plus tard
    
    @Test
    public void testCreationAfficheErreurs() {
        String username = System.getProperty("user.name");
        try {
            co = new ConnexionBDD(username, username, true);
        }
        catch(SQLException e) {
            fail("SQLException: "+e.getMessage());
        }
        catch (ClassNotFoundException e) {
            fail("ClassNotFoundException: "+e.getMessage());
        }

        assertNotNull(co);
    }
    
    @Test
    public void testCreation() {
        String username = System.getProperty("user.name");
        try {
            co = new ConnexionBDD(username, username);
        }
        catch(SQLException e) {
            fail("SQLException: "+e.getMessage());
        }
        catch (ClassNotFoundException e) {
            fail("ClassNotFoundException: "+e.getMessage());
        }

        assertNotNull(co);
    }

    @Test
    public void testMauvaiseConnexion() {
        String estConnecte = co.connecteUtilisateur("nexiste", "pas");
        assertNull(estConnecte);
    }

    @Test
    public void testBonneConnexion() {
        // premiere personne de la BDD
        String estConnecte = co.connecteUtilisateur(loginOk, mdpOk);
        assertEquals("client", estConnecte);
    }
    
    @Test
    public void testChangerPrixBonneRef() {
        boolean estChange = co.changePrix(refOk, 1337);
        assertTrue(estChange);
    }
    
    @Test
    public void testChangerPrixMauvaiseRef() {
        boolean estChange = co.changePrix("nexistepas", 1337);
        assertFalse(estChange);
    }
    
    @Test
    public void testChangerPrixNegatif() {
        boolean estChange = co.changePrix(refOk, -1337);
        assertFalse(estChange);
    }
    
    @Test
    public void testChangerPrixOk() {
        boolean estChange = co.changePrix(refOk, 1337);
        assertTrue(estChange);
    }

    @Test
    public void testListerEmballeurs() {
        LinkedList<HashMap<String,Object>> liste = co.listeEmployes("emballeur");

        assertNotNull(liste);
        assertFalse(liste.isEmpty());
        // à faire uniquement si on a pas ajouté/enlevé un emballeur
        assertEquals(5, liste.size());
    }

    @Test
    public void testListerTransporteurs() {
        LinkedList<HashMap<String,Object>> liste = co.listeEmployes("transporteur");

        assertNotNull(liste);
        assertFalse(liste.isEmpty());
        // à faire uniquement si on a pas ajouté/enlevé un transporteur
        assertEquals(10, liste.size());
    }

    @Test
    public void testListerTousLesEmployes() {
        LinkedList<HashMap<String,Object>> liste = co.listeEmployes();

        assertNotNull(liste);
        assertFalse(liste.isEmpty());
        // à faire uniquement si on a pas ajouté/enlevé un employé
        assertEquals(15, liste.size());
    }

    @Test
    public void testListerClients() {
        LinkedList<HashMap<String,Object>> liste = co.listeClients();

        assertNotNull(liste);
        assertFalse(liste.isEmpty());
        // à faire uniquement si on a pas ajouté/enlevé un client
        assertEquals(100, liste.size());
    }

    @Test
    public void testListeEtendueClients() {
        LinkedList<HashMap<String,Object>> liste = co.listeClients(true);

        assertNotNull(liste);
        assertFalse(liste.isEmpty());
        // à faire uniquement si on a pas ajouté/enlevé un client
        assertEquals(100, liste.size());
        
        HashMap<String,Object> hm = liste.getFirst();
        assertNotNull(hm);

        assertTrue(hm.containsKey("commandes"));
        assertTrue(hm.containsKey("produits"));
    }

    @Test
    public void testListerProduits() {
        LinkedList<HashMap<String,Object>> liste = co.listeProduits();

        assertNotNull(liste);
        assertFalse(liste.isEmpty());
        // à faire uniquement si on a pas ajouté/enlevé un produit
        assertEquals(2000, liste.size());
    }

    @Test
    public void testListerProduitsRestants() {
        LinkedList<HashMap<String,Object>> liste = co.listeProduitsRestants();

        assertNotNull(liste);
        assertFalse(liste.isEmpty());
        // à faire uniquement si on a pas ajouté/enlevé un produit
        assertTrue(2000 >= liste.size());
    }

    @Test
    public void testListerProduitsParQuantiteMin() {
        LinkedList<HashMap<String,Object>> liste = co.listeProduits(0);

        assertNotNull(liste);
        assertFalse(liste.isEmpty());
        // à faire uniquement si on a pas ajouté/enlevé un produit
        assertTrue(2000 >= liste.size());
    }

    @Test
    public void testNouveauGerant() {
        boolean estNouvellePersonne
            = co.nouvellePersonne("Chuck", "Norris", "chucknorris", "42", "gerant");
        assertTrue(estNouvellePersonne);

        // impossible: la personne existe déjà
        estNouvellePersonne
            = co.nouvellePersonne("Chuck", "Norris", "chucknorris", "42", "gerant");
        assertFalse(estNouvellePersonne);

        // impossible: le login existe déjà
        estNouvellePersonne
            = co.nouvellePersonne("Chuque", "Nauris", "chucknorris", "24", "gerant");
        assertFalse(estNouvellePersonne);
    }

    @Test
    public void testNouvellePersonneMauvaisType() {
        boolean estNouvellePersonne
            = co.nouvellePersonne("Chuck", "Norris", "chucknorris", "42", "acteur");
        assertFalse(estNouvellePersonne);
    }

    @Test
    public void testSupprimeGerant() {
        boolean personneSupprimee = co.supprimePersonne("chucknorris");
        assertTrue(personneSupprimee);

        // impossible: la personne n'existe plus
        personneSupprimee = co.supprimePersonne("chucknorris");
        assertFalse(personneSupprimee);
    }

    @Test
    public void testCommandeMauvaisClient() {

        Calendar maintenant = Calendar.getInstance();
        Calendar apres      = Calendar.getInstance();
        apres.set(2012, 11, 21);

        HashMap<String, Integer> produits = new HashMap<String, Integer>();
        produits.put("GN-746901-SIY-63", 1);

        int commande
          = co.nouvelleCommande("nexistepas", maintenant, apres, produits);

        assertEquals(-1, commande);
    }

    @Test
    public void testCommandePasDeProduits() {

        Calendar maintenant = Calendar.getInstance();
        Calendar apres      = Calendar.getInstance();
        apres.set(2012, 11, 21);

        HashMap<String, Integer> produits = new HashMap<String, Integer>();

        int commande
          = co.nouvelleCommande(loginOk, maintenant, apres, produits);

        assertEquals(-1, commande);
    }

    @Test
    public void testCommandeNbDeProduitsNegatif() {

        Calendar maintenant = Calendar.getInstance();
        Calendar apres      = Calendar.getInstance();
        apres.set(2012, 11, 21);

        HashMap<String, Integer> produits = new HashMap<String, Integer>();
        produits.put("GN-746901-SIY-63", -1); 
        produits.put("TL-338853-AIN-30", 2); 

        int commande
          = co.nouvelleCommande(loginOk, maintenant, apres, produits);

        assertEquals(-1, commande);
    }

    @Test
    public void testCommandeNbDeProduitsNul() {

        Calendar maintenant = Calendar.getInstance();
        Calendar apres      = Calendar.getInstance();
        apres.set(2012, 11, 21);

        HashMap<String, Integer> produits = new HashMap<String, Integer>();
        produits.put("GN-746901-SIY-63", 0); 

        int commande
          = co.nouvelleCommande(loginOk, maintenant, apres, produits);

        assertEquals(-1, commande);
    }

    @Test
    public void testCommandeNbDeProduitsTropGrand() {

        Calendar maintenant = Calendar.getInstance();
        Calendar apres      = Calendar.getInstance();
        apres.set(2012, 11, 21);

        HashMap<String, Integer> produits = new HashMap<String, Integer>();
        produits.put("GN-746901-SIY-63", 99999999); 

        int commande
          = co.nouvelleCommande(loginOk, maintenant, apres, produits);

        assertEquals(-1, commande);
    }

    @Test
    public void testCommandeDatePrevuePassee() {

        Calendar maintenant = Calendar.getInstance();
        Calendar avant      = Calendar.getInstance();
        avant.set(1999, 11, 21);

        HashMap<String, Integer> produits = new HashMap<String, Integer>();
        produits.put("GN-746901-SIY-63", 0); 
        produits.put("TL-338853-AIN-30", 0); 

        int commande
          = co.nouvelleCommande(loginOk, maintenant, avant, produits);

        assertEquals(-1, commande);
    }

    @Test
    public void testCommandeMauvaiseRefProduit() {

        Calendar maintenant = Calendar.getInstance();
        Calendar apres      = Calendar.getInstance();
        apres.set(2012, 11, 21);

        HashMap<String, Integer> produits = new HashMap<String, Integer>();
        produits.put("nexistepas", 1); 

        int commande
          = co.nouvelleCommande(loginOk, maintenant, apres, produits);

        assertEquals(-1, commande);
    }

    @Test
    public void testCommandeOk() {

        Calendar maintenant = Calendar.getInstance();
        Calendar apres      = Calendar.getInstance();
        apres.set(2012, 11, 21);

        HashMap<String, Integer> produits = new HashMap<String, Integer>();
        produits.put("GN-746901-SIY-63", 1);

        int commande
          = co.nouvelleCommande(loginOk, maintenant, apres, produits);

        assertTrue(commande >= 0);

        // version courte
        int commande2 = co.nouvelleCommande(loginOk, apres, produits);
        assertTrue(commande2 > commande);
    }

    @Test
    public void testInfosMauvaisePersonne() {

        HashMap<String,String> hm = co.infosPersonne("nexistepas");

        assertNull(hm);
    }

    @Test
    public void testInfosPersonne() {

        HashMap<String,String> hm = co.infosPersonne(loginOk);

        assertNotNull(hm);
        assertFalse(hm.isEmpty());
        assertEquals(3, hm.size());
    }

    @Test
    public void testInfosCommandeIdNegatif() {

        HashMap<String,Object> hm = co.infosCommande(-1);

        assertNull(hm);
    }

    @Test
    public void testInfosCommandeInexistante() {

        HashMap<String,Object> hm = co.infosCommande(99999);

        assertNull(hm);
    }

    @Test
    public void testInfosCommandeOk() {

        HashMap<String,Integer> produits = new HashMap<String,Integer>();
        Calendar c = Calendar.getInstance();
        c.set(2014, 5, 15);

        int qte = 2;

        produits.put(refOk, qte);

        int id = co.nouvelleCommande(loginOk, c, produits);

        assertFalse(-1 == id);
    
        cmdOk = id;

        HashMap<String,Object> hm = co.infosCommande(id);

        assertNotNull(hm);
        assertFalse(hm.isEmpty());

        assertEquals(loginOk, hm.get("login client"));
    }

    @Test
    public void testInfosColisIdNegatif() {
        assertNull(co.infosColis(-1));
    }

    @Test
    public void testNouveauColisDateNulle() {

        HashMap<String,Integer> hm = new HashMap<String,Integer>(); 

        hm.put(refOk, 5);

        // méthode longue
        int id = co.nouveauColis(null, cmdOk, hm);
        assertFalse(-1 == id);

        // méthode courte
        id = co.nouveauColis(cmdOk, hm);
        assertFalse(-1 == id);
    }

    @Test
    public void testNouveauColisPasDeProduits() {

        HashMap<String,Integer> hm = new HashMap<String,Integer>(); 

        // pas de HashMap
        int id = co.nouveauColis(cmdOk, null);
        assertEquals(-1, id);

        // HashMap vide
        id = co.nouveauColis(Calendar.getInstance(), cmdOk, hm);
        assertEquals(-1, id);

        // HashMap avec quantité de produit = 0
        hm.put(refOk, 0);
        assertEquals(-1, id);
    }

    @Test
    public void testNouveauColisProduitInconnu() {

        HashMap<String,Integer> hm = new HashMap<String,Integer>(); 
        hm.put("nexistepas", 2);

        int id = co.nouveauColis(cmdOk, hm);
        assertEquals(-1, id);
    }

    @Test
    public void testNouveauColisOk() {

        HashMap<String,Integer> hm = new HashMap<String,Integer>(); 
        hm.put(refOk, 2);

        int id = co.nouveauColis(cmdOk, hm);
        assertFalse(-1 == id);
    }

    @Test
    public void testInfosColisOk() {

        HashMap<String,Integer> hm = new HashMap<String,Integer>(); 
        hm.put(refOk, 2);

        int id = co.nouveauColis(cmdOk, hm);
        assertFalse(-1 == id);

        HashMap<String,Object> colis = co.infosColis(id);

        assertNotNull(colis);
        assertFalse(colis.isEmpty());

        assertEquals(id, ((Integer)colis.get("id")).intValue());

        assertTrue(colis.containsKey("date d'emballage"));
        assertTrue(colis.containsKey("date d'expédition"));
        assertTrue(colis.containsKey("date de livraison"));
        assertTrue(colis.containsKey("état"));
        assertTrue(colis.containsKey("qualifiant"));
        assertTrue(colis.containsKey("id commande"));
        assertTrue(colis.containsKey("id"));
        assertTrue(colis.containsKey("référence produit"));
        assertTrue(colis.containsKey("quantité contenue"));
    }

    @Test
    public void testChangerLoginPareil() {
        boolean nouveauLoginOk = co.changerLogin(loginOk, loginOk);
        assertTrue(nouveauLoginOk);
    }

    @Test
    public void testChangerLoginNul() {
        boolean nouveauLoginOk = co.changerLogin(loginOk, null);
        assertFalse(nouveauLoginOk);
    }

    @Test
    public void testChangerLoginOk() {
        boolean nouveauLoginOk = co.changerLogin(loginOk, "chucknorris");
        assertTrue(nouveauLoginOk);
        nouveauLoginOk = co.changerLogin("chucknorris", loginOk);
        assertTrue(nouveauLoginOk);
    }

    @Test
    public void testChangerMdpPareil() {
        boolean nouveauMdpOk = co.changerMdp(loginOk, mdpOk, mdpOk);
        assertTrue(nouveauMdpOk);
    }

    @Test
    public void testChangerMdpNul() {
        boolean nouveauMdpOk = co.changerMdp(loginOk, mdpOk, null);
        assertFalse(nouveauMdpOk);
    }

    @Test
    public void testChangerMdpOk() {
        boolean nouveauMdpOk = co.changerMdp(loginOk, mdpOk, "42");
        assertTrue(nouveauMdpOk);
        nouveauMdpOk = co.changerMdp(loginOk, "42", mdpOk);
        assertTrue(nouveauMdpOk);
    }
    
    @Test
    public void testInfosProduitOk() {
        HashMap<String,Object> produit = co.infosProduit(refOk);

        assertNotNull(produit);
        assertFalse(produit.isEmpty());
        assertEquals(refOk, (String)produit.get("référence"));
    }

    @Test
    public void testProduitPlusVenduOk() {
        LinkedList<HashMap<String,Object>> produit = co.produitPlusVendu();

        assertNotNull(produit);
        assertFalse(produit.isEmpty());
    }

    @Test
    public void testListeClientsPlusDepensiesOk() {
        LinkedList<HashMap<String,Object>> liste = co.listeClientsPlusDepensies();

        assertNotNull(liste);
        assertFalse(liste.isEmpty());
    }

    @Test
    public void testNouvellePaletteListeColisVide() {
        int id_p = co.nouvellePalette(null);
        assertEquals(-1, id_p);
    }

    @Test
    public void testNouvellePaletteOk() {

        HashMap<String,Integer> hm = new HashMap<String,Integer>(); 
        hm.put(refOk, 2);

        int id_c = co.nouveauColis(cmdOk, hm);

        LinkedList<Integer> liste_colis = new LinkedList<Integer>();

        liste_colis.push(new Integer(id_c));

        int id_p = co.nouvellePalette(liste_colis);

        assertFalse(-1 == id_p);
    }
    
    @Test
    public void testListePaletteOk() {
        HashMap<String,Integer> hm = new HashMap<String,Integer>(); 
        hm.put(refOk, 2);

        int id_c = co.nouveauColis(cmdOk, hm);

        LinkedList<Integer> liste_colis = new LinkedList<Integer>();

        liste_colis.push(new Integer(id_c));

        int id_p = co.nouvellePalette(liste_colis);

        LinkedList<Integer> liste_colis2 = co.listePalette(id_p);

        assertNotNull(liste_colis2);
        assertEquals(liste_colis, liste_colis2);
    }

    @Test
    public void testLivrerColisOk() {

        HashMap<String,Integer> hm = new HashMap<String,Integer>(); 
        hm.put(refOk, 2);

        int id = co.nouveauColis(cmdOk, hm);

        assertTrue(co.livrerColis(id));
    }

    @Test
    public void testListeColisCommandeIdNegatif() {
        assertNull(co.listerColisParCommande(0));
        assertNull(co.listerColisParCommande(-42));
    }

    @Test
    public void testNouveauContainerIdsPalettesNulles() {
        assertEquals(-1, co.nouveauContainer(null, ""));
    }

    @Test
    public void testNouveauContainerIdsPalettesNegatives() {
        LinkedList<Integer> palettes = new LinkedList<Integer>();
        palettes.push(new Integer(-42));
        assertEquals(-1, co.nouveauContainer(palettes, ""));
    }

    @Test
    public void testNouveauContainerPalettesVides() {
        LinkedList<Integer> palettes = new LinkedList<Integer>();
        assertEquals(-1, co.nouveauContainer(palettes, ""));
    }
}
