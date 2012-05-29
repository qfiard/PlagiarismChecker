
public enum Action {

	CHANGERPRIX(false),
	LICENCIER(false),
	V_EMPLOYES_CLIENTS(false),
	V_PRODUITS_PLUS_VENDUS(false),
	V_CLIENTS_PLUS_DEPENSIERS(false),
	PASSER_COMMANDE(false),
	V_ETAT_COLIS(false),
	V_PRODUITS_DISPOS(false),
	CHOISIR_DATE_LIVRAISON(false),
	CHANGER_MOT_PASSE(false),
	CHANGER_LOGIN(false),
	V_LISTE_COMMANDES_CLIENT(false),
	SAISIR_COLIS(false),
	SAISIR_PALETTE(false),
	V_CONTENU_COLIS(false),
	V_CONTENU_PALETTE(false),
	RENVOYER_COLIS(false),
	V_LISTE_PRODUITS_TRANSPORTES(false)
	;

	private Boolean droit;

	Action (Boolean droit) {
		this.droit = droit;
	}
}
