import java.io.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Calendar;

public class GenerateCommands {

    private static int NB_COMMANDES = 250;
    private static int NB_COMMANDES_NON_EXPEDIEES = 50;
    private static int NB_COMMANDES_EN_COLIS = 20;

    public static void main (String[] args) throws SQLException, ClassNotFoundException {
        String user = System.getProperty("user.name");
        ConnexionBDD co = new ConnexionBDD(user, user);

        // 250 commandes:
        // 50 non expédiées
        // 20 à moitié
        // 180 complètes

        LinkedList<HashMap<String,Object>> lhm = co.listeClients();
        LinkedList<HashMap<String,Object>> produits = co.listeProduitsRestants();

        // liste des logins des clients
        LinkedList<String> logins = new LinkedList<String>();

        // pour chaque ref de produit, [0] = quantité par carton,
        //                             [1] = cartons par palettes
        HashMap<String,Integer[]> qte_cartons_produits = new HashMap<String,Integer[]>();

        // associe, pour chaque id de commande, les références de produits avec
        // une liste d'identifiants de colis qui contiennent ce produit
        // {id_commande: {ref_produit:[id_c1, id_c2, …], … }, …}
        HashMap<Integer,HashMap<String,LinkedList<Integer>>> commandes_refs_colis
            = new HashMap<Integer,HashMap<String,LinkedList<Integer>>>();

        for (HashMap hm : lhm) {
            logins.push((String)hm.get("login"));
        }
        lhm = null;

        int nb_clients = logins.size();
        int nb_produits = produits.size();
        int i = 0; // curseur sur le tableau des commandes

        // liste des commandes: associe l'identifiant de la commande
        // à un mapping entre les références de produits et leur quantités
        // dans cette commande
        // commandes.get(i).get("_id") == l'id de la commande
        LinkedList<HashMap<String,Integer>> commandes
            = new LinkedList<HashMap<String,Integer>>();

        for (; i<NB_COMMANDES; i++) {
            String client = logins.get((int)(Math.random()*nb_clients));
            Calendar date_prevue = Calendar.getInstance();

            int jour = date_prevue.get(date_prevue.DAY_OF_MONTH);
            int mois = date_prevue.get(date_prevue.MONTH);
            int annee = date_prevue.get(date_prevue.YEAR);

            // >= année courante (max: année courante +4)
            int annee_prevue = (int)(Math.random()*5)+annee;
            // >= mois courant +2
            int mois_prevu = (annee_prevue == annee) ? (int)(Math.random())*(9-mois)+mois+2 : (int)(Math.random()*9)+2;
            int jour_prevu = (int)(Math.random()*25);

            date_prevue.set(annee_prevue, mois_prevu, jour_prevu);

            HashMap<String,Integer> produits_commande = new HashMap<String,Integer>();

            // nombre de produits différents
            int nb_produits_diff = (int)(Math.random()*10)+1;

            for (int j=0; j<nb_produits_diff; j++) {

                int id_produit, qte_max, qte = 0;
                String ref = "";

                // tant que le produit courant n'est pas disponible en bonne quantité,
                // le changer avec un autre aléatoirement
                while (qte == 0) {

                    id_produit = (int)(Math.random()*nb_produits);

                    ref = (String)produits.get(id_produit).get("référence");
                    qte_max = ((Float)produits.get(id_produit).get("quantité restante")).intValue();

                    qte = Math.min((int)(Math.random()*20)+1, qte_max/100);

                }

                // on stocke les informations sur les quantités par cartons/palette
                if (!qte_cartons_produits.containsKey(ref)) {
                    HashMap<String,Object> produit = co.infosProduit(ref);
                    Integer[] tmp = new Integer[2];
                    tmp[0] = (Integer)produit.get("quantité par carton");
                    tmp[1] = (Integer)produit.get("cartons par palette");
                    qte_cartons_produits.put(ref, tmp);
                }

                produits_commande.put(ref, qte);
            }

            int cmd = co.nouvelleCommande(client, date_prevue, produits_commande);

            if (cmd == -1) {
                System.err.println("Erreur: client="+client+", date_prevue="
                                    +jour_prevu+"/"+mois_prevu+"/"+annee_prevue
                                    + "(Aujourd'hui: "+jour+"/"+mois+"/"+annee+")");

                System.err.print("Produits (quantité): ");
                for (String ref : produits_commande.keySet()) {
                    System.err.print(ref+" ("+produits_commande.get(ref)+"), ");
                }
                System.err.println(".");

            } else {
                //System.out.println("Commande OK pour client "+client+".");
                //commandes[i] = cmd;
            
                produits_commande.put("_id", cmd);

                commandes.push(produits_commande);
            }
        }

        /* 
         * À ce stade, `commandes` contient les identifiant de 250 commandes
         * pas encore expédiées.
         *
         * On en garde 50, et on en expédie 200.
         */
        System.out.println(commandes.size()+" commandes réalisées.");
        i = NB_COMMANDES_NON_EXPEDIEES;
        
        for (;i<NB_COMMANDES;i++) {

            HashMap<String,LinkedList<Integer>> current_cmd_refs_colis = new HashMap<String,LinkedList<Integer>>();
            commandes_refs_colis.put(commandes.get(i).get("_id"), current_cmd_refs_colis);

            // colis de cette commande
            LinkedList<Integer> cmd_colis = new LinkedList<Integer>();

            // références des produits de cette commande
            HashMap<String,Integer> cmd_produits = commandes.get(i);

            for (String ref : cmd_produits.keySet()) {
                if (ref.equals("_id")) { continue; }
                
                // pour chaque produit

                int qte_par_carton = qte_cartons_produits.get(ref)[0];
                int qte = cmd_produits.get(ref);

                int tmp,nb_cartons = qte/qte_par_carton + 1; 

                while (nb_cartons > 0) {
                    tmp = Math.min(qte_par_carton, qte); // nb de produits dans ce carton
                    if (tmp == 0) { break; }
                    qte -= tmp;
                    nb_cartons--;

                    HashMap<String,Integer> current_colis_produits
                        = new HashMap<String,Integer>();
                    current_colis_produits.put(ref, tmp);
                    
                    // nouveau colis
                    int id_colis = co.nouveauColis(cmd_produits.get("_id"), current_colis_produits);

                    if (!current_cmd_refs_colis.containsKey(ref)) {
                        current_cmd_refs_colis.put(ref, new LinkedList<Integer>());
                    }
                    current_cmd_refs_colis.get(ref).push(id_colis);
                    
                    cmd_colis.push(id_colis);
                }
            }
        }

        /*
         * À ce stade, on a toujours 50 commandes non emballées, et
         * 200 commandes dans des colis. On en laisse 1=20 dans des colis,
         * et on continue;
         */
        System.out.println(commandes_refs_colis.size()+" commandes emballées dans des colis.");
        i = NB_COMMANDES_NON_EXPEDIEES + NB_COMMANDES_EN_COLIS;

        LinkedList<Integer> palettes = new LinkedList<Integer>();

        for (; i<NB_COMMANDES;i++) {

            // commande courante
            HashMap<String,Integer> cmd = commandes.get(i);
            // on recupere les colis de cette commande
            HashMap<String,LinkedList<Integer>> cmd_colis = commandes_refs_colis.get(cmd.get("_id"));

            // pour chaque référence différente
            for (String curr_ref : cmd_colis.keySet()) {
                int cartons_par_palette = qte_cartons_produits.get(curr_ref)[1];

                int qte_cartons = cmd_colis.get(curr_ref).size();

                while (qte_cartons > 0) {
                    int tmp = Math.min(cartons_par_palette, qte_cartons);

                    LinkedList<Integer> current_palette_colis
                        = new LinkedList<Integer>();

                    for (int k=0;k<tmp;k++) {
                        current_palette_colis.push(cmd_colis.get(curr_ref).pop());
                    }

                    int id_palette = co.nouvellePalette(current_palette_colis);

                    // debug
                    if (id_palette == -1) {
                        System.err.println("Erreur avec une palette (ref produit:"+curr_ref+").");
                        System.err.println(qte_cartons+" cartons, "+cartons_par_palette+" par palette.");
                        System.err.println("On essaye d'y mettre "+tmp+" cartons.");
                        System.err.println("(length="+current_palette_colis.size()+")");
                        System.err.print("Ids des colis: ");
                        for (int l=0;l<current_palette_colis.size();l++) {
                            System.err.print(current_palette_colis.get(l)+",");
                        }
                        System.err.println(".");

                        System.exit(-1);
                    }
                    // fin debug
                    
                    palettes.push(new Integer(id_palette));

                    qte_cartons -= tmp;
                }
            }
        }

        /*
         * À ce stade, il y a50 commandes non expédiées, 20 dans des colis,
         * et 180 (bientôt) livrées.
         */
        System.out.println(palettes.size()+" palettes remplies.");

        //TODO mettre les palettes dans les containers
        //     et livrer les containers

        while (palettes.size() > 0) {
            co.livrerPalette(palettes.pop().intValue());
        }

        System.out.println("180 commandes livrées.");
    }
}
